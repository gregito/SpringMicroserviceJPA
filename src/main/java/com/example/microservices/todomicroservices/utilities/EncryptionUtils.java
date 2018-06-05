package com.example.microservices.todomicroservices.utilities;

import org.jasypt.util.text.BasicTextEncryptor;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class EncryptionUtils {

    private final BasicTextEncryptor textEncryptor;

    public EncryptionUtils() {
        textEncryptor = new BasicTextEncryptor();
        textEncryptor.setPassword(String.valueOf(new Date().getTime()));
    }

    public String encrypt(String data) {
        return textEncryptor.encrypt(data);
    }

    public String decrypt(String encriptedData) {
        return textEncryptor.decrypt(encriptedData);
    }

}
