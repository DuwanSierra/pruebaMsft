package com.jeffdev.prueba.config;


import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
public class CustomPassEnconder implements PasswordEncoder {

    @Override
    public String encode(CharSequence charSequence) {
        return DigestUtils.sha256Hex(charSequence.toString());
    }

    @Override
    public boolean matches(CharSequence charSequence, String s) {
        return charSequence.toString().equals(s);
    }
}
