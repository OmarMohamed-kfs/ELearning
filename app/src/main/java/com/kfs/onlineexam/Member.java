package com.kfs.onlineexam;

public class Member {

    long code , id;
    int secretKey;
    String password;

    public Member(long code, long id, int secretKey, String password) {
        this.code = code;
        this.id = id;
        this.secretKey = secretKey;
        this.password = password;
    }

    public long getCode() {
        return code;
    }

    public void setCode(long code) {
        this.code = code;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public int getSecretKey() {
        return secretKey;
    }

    public void setSecretKey(int secretKey) {
        this.secretKey = secretKey;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

}
