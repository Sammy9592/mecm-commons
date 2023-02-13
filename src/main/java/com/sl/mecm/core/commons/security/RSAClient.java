package com.sl.mecm.core.commons.security;

import com.sl.mecm.core.commons.exception.MECMServiceException;
import com.sl.mecm.core.commons.utils.RSAUtils;

import java.io.InputStream;
import java.security.Key;
import java.security.KeyStore;
import java.security.PrivateKey;
import java.security.PublicKey;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class RSAClient {

    private RSAClient(){}

    public static final String STORE_TYPE_PKCS12 = "PKCS12";

//    private static final RSAClient ACCESS_TOKEN_INSTANCE = new RSAClient();
//    static {
//        ACCESS_TOKEN_INSTANCE
//                .init("tokenkey.p12", "mecmkeystore120966815#", "tokenPrivateKey", "mecmkeystore120966815#")
//                .setEncryptAction(ACCESS_TOKEN_INSTANCE.publicKey)
//                .setDecryptAction(ACCESS_TOKEN_INSTANCE.privateKey);
//    }

    private PrivateKey privateKey;
    private PublicKey publicKey;
    private EncryptAction encryptAction;
    private DecryptAction decryptAction;

//    public static RSAClient accessTokenClient(){
//        return ACCESS_TOKEN_INSTANCE;
//    }

    public PrivateKey getPrivateKey() {
        return privateKey;
    }

    public PublicKey getPublicKey() {
        return publicKey;
    }

    public String encryptString(String plain){
        return this.encryptAction.encryptString(plain);
    }

    public String decryptString(String encryptedString){
        return this.decryptAction.decryptString(encryptedString);
    }

    private RSAClient init(String filePath, String storePwd, String alias, String keyPwd){
        try (InputStream ins = Thread.currentThread().getContextClassLoader().getResourceAsStream(filePath)){
            KeyStore keyStore = KeyStore.getInstance(STORE_TYPE_PKCS12);
            keyStore.load(ins, storePwd.toCharArray());
            privateKey = (PrivateKey) keyStore.getKey(alias, keyPwd.toCharArray());
            publicKey = keyStore.getCertificate(alias).getPublicKey();
        } catch (Exception e) {
            log.error("load key error:" + e.getMessage(), e);
            throw new MECMServiceException("503", "parse security key error:" + e.getMessage(), null, e);
        }
        return this;
    }

    private RSAClient setEncryptAction(Key actionKey){
        this.encryptAction = new EncryptAction(actionKey);
        return this;
    }

    private RSAClient setDecryptAction(Key actionKey){
        this.decryptAction = new DecryptAction(actionKey);
        return this;
    }

    public static class Builder{

        public static final int KEY_TYPE_PUBLIC = 0;
        public static final int KEY_TYPE_PRIVATE = 1;

        private String filePath;
        private String storePwd;
        private String alias;
        private String privateKeyPwd;
        private int encryptKeyType = -1;
        private int decryptKeyType = -1;

        public RSAClient build(){
            RSAClient client = new RSAClient();
            encryptKeyType = encryptKeyType == -1 ? KEY_TYPE_PUBLIC : encryptKeyType;
            decryptKeyType = decryptKeyType == -1 ? KEY_TYPE_PRIVATE : decryptKeyType;
            return client.init(filePath, storePwd, alias, privateKeyPwd)
                    .setEncryptAction(encryptKeyType == KEY_TYPE_PUBLIC ? client.publicKey : client.privateKey)
                    .setDecryptAction(decryptKeyType == KEY_TYPE_PRIVATE ? client.privateKey : client.publicKey);
        }

        public Builder setFilePath(String filePath) {
            this.filePath = filePath;
            return this;
        }


        public Builder setStorePwd(String storePwd) {
            this.storePwd = storePwd;
            return this;
        }

        public Builder setAlias(String alias) {
            this.alias = alias;
            return this;
        }

        public Builder setPrivateKeyPwd(String privateKeyPwd) {
            this.privateKeyPwd = privateKeyPwd;
            return this;
        }

        public Builder setEncryptKeyType(int encryptKeyType) {
            this.encryptKeyType = encryptKeyType;
            return this;
        }

        public Builder setDecryptKeyType(int decryptKeyType) {
            this.decryptKeyType = decryptKeyType;
            return this;
        }
    }

    private static class EncryptAction{

        private Key actionKey;

        public EncryptAction(Key actionKey) {
            this.actionKey = actionKey;
        }

        public String encryptString(String plain){
            return RSAUtils.encryptString(actionKey, plain);
        }
    }

    private static class DecryptAction{

        private Key actionKey;

        public DecryptAction(Key actionKey) {
            this.actionKey = actionKey;
        }

        public String decryptString(String encryptedString){
            return RSAUtils.decryptString(actionKey, encryptedString);
        }
    }
}
