package com.jeffdev.prueba.config;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.servlet.http.HttpServletResponse;


@EnableWebSecurity
@ConditionalOnBean(ConfigurationBeans.class)
public class SecurityConfig extends WebSecurityConfigurerAdapter {


    private boolean corsEnabled;
    private String jwtKey;

    private final CustomPassEnconder passwordEncoder;
    private final CustomUserDetailsService userDetailsService;
    static final Logger logger = LogManager.getLogger("login");

    public SecurityConfig(
            @Autowired CustomPassEnconder passwordEncoder,
            @Autowired CustomUserDetailsService userDetailsService,
            @Value("${cors.enabled:false}") boolean corsEnabled,
            @Value("${jwtKey}") String jwtKey
    ) {
        this.passwordEncoder = passwordEncoder;
        this.userDetailsService = userDetailsService;
        this.corsEnabled = corsEnabled;
        this.jwtKey = jwtKey;
    }


    @Override
    protected void configure(HttpSecurity httpSecurity) throws Exception {
        applyCors(httpSecurity)
                .csrf().disable()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .exceptionHandling().authenticationEntryPoint(unauthorizedResponse())
                .and()
                .logout()
                .and()
                .addFilter(new AuthenticationFilter(authenticationManagerBean(), jwtKey))
                .addFilterAfter(new AuthorizationFilter(jwtKey), UsernamePasswordAuthenticationFilter.class)
                .authorizeRequests()
                .antMatchers(HttpMethod.POST, "/login").permitAll()
                .antMatchers(HttpMethod.POST, "/").permitAll()
                .antMatchers("/api/public/**").permitAll()
                .antMatchers("/api/**").authenticated()
                .anyRequest().permitAll();
        httpSecurity.headers().frameOptions().sameOrigin();
    }

    private HttpSecurity applyCors(HttpSecurity httpSecurity) throws Exception {
        if (corsEnabled) {
            return httpSecurity.cors().and();
        } else {
            return httpSecurity;
        }
    }

    private AuthenticationEntryPoint unauthorizedResponse() {
        return (req, rsp, e) -> {
            rsp.sendError(HttpServletResponse.SC_UNAUTHORIZED);
        };
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(this.userDetailsService).passwordEncoder(this.passwordEncoder);
    }

}
