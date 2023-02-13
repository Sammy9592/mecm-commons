package com.sl.mecm.core.commons.security;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import lombok.extern.slf4j.Slf4j;

@Slf4j
class RSAClientTest {

    private static final String PLAIN_STRING = "plain text 123456";
    private static final String ENCRYPTED_STRING = "gEogKOFUaDcfIRy7/ZaNgkEIfjDFpIyMXem5ZI6M7/cHY+pH6b6dYaGBk7miLjZ3G9B7odww4iLMhOmuPs7UtCrYJFZL6djzvPwxrs3miOz58TsQc16MQ/31/v+409Qj978AxcxVGauniRQ43GNOeTPgoY6h3i/ZxpWtcHNRO/VFTgfaBtmLMmDo1hcvRG9D+xHUwDp7777pyal6XOH4siYC3ZKvqN7A53nKCWCA/WgvCjlr8C3kn4vi+EBg8BnA3W72mACk9O7UBtS/02WPN71t1otuuAXsFTkkf7DaJWlsSIVdHEkj9ju422RhRpquqZlOaveBe7T9FRLmRNfuMg==";

    private static final RSAClient tokenRSAClient;
    static {
        tokenRSAClient = new RSAClient.Builder()
                .setFilePath("tokenkey.p12")
                .setAlias("tokenPrivateKey")
                .setStorePwd("mecmkeystore120966815#")
                .setPrivateKeyPwd("mecmkeystore120966815#")
                .build();
    }

    @Test
    void testEncryption(){
        String actual = tokenRSAClient.encryptString(PLAIN_STRING);
        log.info("encrypt string:" + actual);
        Assertions.assertNotNull(actual);
    }

    @Test
    void testDecryption(){
        String actual = tokenRSAClient.decryptString(ENCRYPTED_STRING);
        Assertions.assertEquals(PLAIN_STRING, actual);
    }
}