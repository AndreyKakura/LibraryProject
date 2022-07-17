package com.kakura.libraryproject.util;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Optional;

public class PasswordEncoder {
    private static final Logger logger = LogManager.getLogger();
    private static final String HASH_FUNCTION = "SHA-256";
    private static final String SALT_KEY = "1tj2Fg98Pa";

    private PasswordEncoder() {

    }

    public static Optional<String> encode(String password) {
        StringBuilder hash = new StringBuilder();
        byte[] passwordBytes = password.getBytes(StandardCharsets.UTF_8);
        byte[] saltBytes = SALT_KEY.getBytes(StandardCharsets.UTF_8);
        try {
            MessageDigest digest = MessageDigest.getInstance(HASH_FUNCTION);
            digest.update(saltBytes);
            byte[] resultBytes = digest.digest(passwordBytes);
            for (byte next : resultBytes) {
                hash.append(next);
            }
            return Optional.of(hash.toString());
        } catch (NoSuchAlgorithmException e) {
            logger.warn("Password wasn't encoded.", e);
            return Optional.empty();
        }

    }

}
