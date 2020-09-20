package com.jeffdev.prueba.config;



import com.fasterxml.jackson.databind.ObjectMapper;
import com.jeffdev.prueba.objects.LoginObj;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.AutoConfigureOrder;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.stereotype.Service;

import javax.crypto.spec.SecretKeySpec;
import javax.servlet.FilterChain;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.bind.DatatypeConverter;
import java.io.IOException;
import java.security.Key;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;


public class AuthenticationFilter extends UsernamePasswordAuthenticationFilter {



    private String jwtKey;
    static final Logger logger = LogManager.getLogger("login");


    private final ObjectMapper objectMapper;
    private final AuthenticationManager authenticationManager;

    public AuthenticationFilter(AuthenticationManager authenticationManager, String jwtKey) {
        this.authenticationManager = authenticationManager;
        objectMapper = new ObjectMapper();
        this.jwtKey = jwtKey;
        setLoginPath();
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response)
            throws AuthenticationException {
        LoginObj credentials;

        try {
            credentials = getCredentials(request);
            UsernamePasswordAuthenticationToken token = createAuthenticationToken(credentials);
            return authenticationManager.authenticate(token);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }



    private LoginObj getCredentials(HttpServletRequest request) throws IOException {
        return objectMapper.readValue(request.getInputStream(), LoginObj.class);
    }

    private UsernamePasswordAuthenticationToken createAuthenticationToken(LoginObj credentials) {
        return new UsernamePasswordAuthenticationToken(
                credentials.getUsername(),
                credentials.getPassword(),
                Collections.emptyList()
        );
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request,
                                            HttpServletResponse response,
                                            FilterChain chain,
                                            Authentication auth) throws IOException {
        response.addHeader("Access-Control-Expose-Headers", "Authorization");
        response.addHeader("Access-Control-Allow-Headers", "Authorization, Origin, X-Requested-With, Content-Type, Accept, X-Custom-header");
        response.addHeader("Authorization", "Bearer " + createToken(auth));
        HashMap<String,Object> data = new HashMap<>();
        data.put("token","Bearer " +createToken(auth));
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        ObjectMapper mapper = new ObjectMapper();
        //Converting the Object to JSONString
        String jsonString = mapper.writeValueAsString(data);
        response.getWriter().write(jsonString);
        response.getWriter().flush();
        response.getWriter().close();
        logger.debug("Usuario: "+auth.getName()+" Mensaje: Logueo exitoso Tipo: DEBUG");
    }



    private String createToken(Authentication auth) {
        long expMillis;
        int tokenExpiration = 30;

        //The JWT signature algorithm we will be using to sign the token
        SignatureAlgorithm signatureAlgorithm = SignatureAlgorithm.HS512;

        long nowMillis = System.currentTimeMillis();
        Date now = new Date(nowMillis);

        //We will sign our JWT with our ApiKey secret
        byte[] apiKeySecretBytes = DatatypeConverter.parseBase64Binary(jwtKey);
        Key signingKey = new SecretKeySpec(apiKeySecretBytes, signatureAlgorithm.getJcaName());

        expMillis = TimeUnit.DAYS.toMillis(tokenExpiration);

        List<String> authorities = auth.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.toList());

        //Let's set the JWT Claims
        JwtBuilder builder = Jwts.builder()
                .setSubject(auth.getName())
                .claim("authorities", authorities)
                .setIssuedAt(now)
                .signWith(signatureAlgorithm, signingKey);

        //if it has been specified, let's add the expiration
        if (expMillis > 0) {
            Date exp = new Date(nowMillis + expMillis);
            builder.setExpiration(exp);
        }

        //Builds the JWT and serializes it to a compact, URL-safe string
        return builder.compact();
    }

    private void setLoginPath() {
        setRequiresAuthenticationRequestMatcher(
                new AntPathRequestMatcher("/login", "POST"));
    }
}
