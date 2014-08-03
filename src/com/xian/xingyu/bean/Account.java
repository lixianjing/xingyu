
package com.xian.xingyu.bean;

public class Account {

    private long id;
    private String key;
    private String token;
    private long authTime;
    private int type;
    private int status;
    private int infoStatus;

    public Account() {
        super();
    }

    public Account(long id, String key, String token, long authTime, int type, int status,
            int infoStatus) {
        super();
        this.id = id;
        this.key = key;
        this.token = token;
        this.authTime = authTime;
        this.type = type;
        this.status = status;
        this.infoStatus = infoStatus;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public long getAuthTime() {
        return authTime;
    }

    public void setAuthTime(long authTime) {
        this.authTime = authTime;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public int getInfoStatus() {
        return infoStatus;
    }

    public void setInfoStatus(int infoStatus) {
        this.infoStatus = infoStatus;
    }

}
