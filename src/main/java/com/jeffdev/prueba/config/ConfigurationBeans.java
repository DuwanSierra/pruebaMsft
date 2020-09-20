package com.jeffdev.prueba.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ConfigurationBeans {

    @Value("${pathFile}") String pathFile;

    @Bean
    public CustomPassEnconder passwordEncoder() {
        return new CustomPassEnconder();
    }

    @Bean
    public CustomUserDetailsService customUserDetailsService() {
        return new CustomUserDetailsService(pathFile);
    }
}
