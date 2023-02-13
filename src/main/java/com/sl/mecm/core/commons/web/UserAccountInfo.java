package com.sl.mecm.core.commons.web;

import java.io.Serializable;
import java.util.Date;
import java.util.UUID;

public class UserAccountInfo implements Serializable {

    private String accountId;

    private String authToken;

    private String username;

    private String merchantId;

    private String merchantRoles;

    private String status;

    private Date createTime;

    private Date updateTime;

    public String getAccountId() {
        return accountId;
    }

    public void setAccountId(String accountId) {
        this.accountId = accountId;
    }

    public String getAuthToken() {
        return authToken;
    }

    public void setAuthToken(String authToken) {
        this.authToken = authToken;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getMerchantId() {
        return merchantId;
    }

    public void setMerchantId(String merchantId) {
        this.merchantId = merchantId;
    }

    public String getMerchantRoles() {
        return merchantRoles;
    }

    public void setMerchantRoles(String merchantRoles) {
        this.merchantRoles = merchantRoles;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public static UserAccountInfo createEmptyUserAccount(){
        UserAccountInfo emptyUserAccount = new UserAccountInfo();
        emptyUserAccount.accountId = UUID.randomUUID().toString();
        emptyUserAccount.authToken = "";
        emptyUserAccount.status = "NA";
        emptyUserAccount.merchantId = UUID.randomUUID().toString();
        emptyUserAccount.merchantRoles = "";
        emptyUserAccount.username = "[EMPTY-ACCOUNT]";
        emptyUserAccount.createTime = new Date();
        emptyUserAccount.updateTime = new Date();
        return emptyUserAccount;
    }
}
