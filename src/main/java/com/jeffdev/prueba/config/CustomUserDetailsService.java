package com.jeffdev.prueba.config;


import com.jeffdev.prueba.objects.LoginObj;
import com.jeffdev.prueba.util.Utils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.Date;
import java.util.List;

@Service
public class CustomUserDetailsService implements UserDetailsService {


    private final Utils utils;
    static final Logger logger = LogManager.getLogger("login");

    String pathFile;


    public CustomUserDetailsService(@Value("${pathFile}") String pathFile
    ) {
        this.utils = new Utils();
        this.pathFile = pathFile;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User userDetails;
        try {
            LoginObj user = utils.readFile(pathFile, username);
            if (user != null) {
                userDetails = new User(user.getUsername(), user.getPassword(), getAuthority("USER"));
            } else {
                logger.info("Usuario: " + username + " Mensaje: datos incorrectos en el logueo Tipo: INFO");
                throw new UsernameNotFoundException("User not found.");
            }
            return userDetails;
        } catch (Exception e) {
            if (e instanceof UsernameNotFoundException) {
                throw new UsernameNotFoundException("User not found.");
            } else {
                logger.error("Usuario: " + username + " Mensaje: error Tipo: ERROR " + e);
            }
        }
        return null;
    }


    private List<GrantedAuthority> getAuthority(String role) {
        return Collections.singletonList(new SimpleGrantedAuthority(role));
    }
}
