package com.jeffdev.prueba;

import com.jeffdev.prueba.config.CustomPassEnconder;
import com.jeffdev.prueba.config.CustomUserDetailsService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class PruebaApplication {

    public static void main(String[] args) {
        SpringApplication.run(PruebaApplication.class, args);
    }

}
