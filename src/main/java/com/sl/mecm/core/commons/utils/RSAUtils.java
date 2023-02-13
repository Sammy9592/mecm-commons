package com.sl.mecm.core.commons.utils;

import com.sl.mecm.core.commons.exception.MECMServiceException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.nio.charset.StandardCharsets;
import java.security.InvalidKeyException;
import java.security.Key;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.util.Base64;

import javax.crypto.Cipher;
import javax.crypto.NoSuchPaddingException;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class RSAUtils {

    private static final String TRANSFORMATION_RSA = "RSA/ECB/PKCS1Padding";

    private RSAUtils(){}

    public static String encryptString(Key key, String plain){
        try {
            Cipher cipher = Cipher.getInstance(TRANSFORMATION_RSA);
            cipher.init(Cipher.ENCRYPT_MODE, key);
            byte[] bytes = cipher.doFinal(plain.getBytes(StandardCharsets.UTF_8));
            return Base64.getEncoder().encodeToString(bytes);
        } catch (Exception e) {
            log.error("encryption error for plain text:" + plain, e);
            throw new MECMServiceException("503", "encrypt string error:" + e.getMessage(), null, e);
        }
    }

    public static String decryptString(Key key, String encryptString){
        try {
            Cipher cipher = Cipher.getInstance(TRANSFORMATION_RSA);
            cipher.init(Cipher.DECRYPT_MODE, key);
            byte[] bytes = Base64.getDecoder().decode(encryptString.getBytes(StandardCharsets.UTF_8));
            return new String(cipher.doFinal(bytes));
        } catch (Exception e) {
            log.error("decryption error for encrypted text:" + encryptString, e);
            throw new MECMServiceException("503", "decrypt string error:" + e.getMessage(), null, e);
        }
    }
}