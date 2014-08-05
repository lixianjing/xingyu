
package com.xian.xingyu.bean;

public class PersonInfo {

    private long id;
    private long accountId;
    private byte[] icon;
    private String iconUri;
    private byte[] iconThumb;
    private String iconThumbUri;
    private String name;
    private String desc;
    private int gender;
    private String local;
    private int birthYear;
    private int birthMonth;
    private int birthDay;
    private int birthType;

    public PersonInfo() {
        super();
    }

    public PersonInfo(long id, long accountId, byte[] icon, String iconUri, byte[] iconThumb,
            String iconThumbUri, String name, String desc, int gender, String local, int birthYear,
            int birthMonth, int birthDay, int birthType) {
        super();
        this.id = id;
        this.accountId = accountId;
        this.icon = icon;
        this.iconUri = iconUri;
        this.iconThumb = iconThumb;
        this.iconThumbUri = iconThumbUri;
        this.name = name;
        this.desc = desc;
        this.gender = gender;
        this.local = local;
        this.birthYear = birthYear;
        this.birthMonth = birthMonth;
        this.birthDay = birthDay;
        this.birthType = birthType;
    }


    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getAccountId() {
        return accountId;
    }

    public void setAccountId(long accountId) {
        this.accountId = accountId;
    }

    public byte[] getIcon() {
        return icon;
    }
    
    public void setIcon(byte[] icon) {
        this.icon = icon;
    }
    
    public String getIconThumbUri() {
        return iconThumbUri;
    }

    public void setIconThumbUri(String iconThumbUri) {
        this.iconThumbUri = iconThumbUri;
    }


    public String getIconUri() {
        return iconUri;
    }

    public void setIconUri(String iconUri) {
        this.iconUri = iconUri;
    }

    public byte[] getIconThumb() {
        return iconThumb;
    }

    public void setIconThumb(byte[] iconThumb) {
        this.iconThumb = iconThumb;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public int getGender() {
        return gender;
    }

    public void setGender(int gender) {
        this.gender = gender;
    }

    public String getLocal() {
        return local;
    }

    public void setLocal(String local) {
        this.local = local;
    }

    public int getBirthYear() {
        return birthYear;
    }

    public void setBirthYear(int birthYear) {
        this.birthYear = birthYear;
    }

    public int getBirthMonth() {
        return birthMonth;
    }

    public void setBirthMonth(int birthMonth) {
        this.birthMonth = birthMonth;
    }

    public int getBirthDay() {
        return birthDay;
    }

    public void setBirthDay(int birthDay) {
        this.birthDay = birthDay;
    }

    public int getBirthType() {
        return birthType;
    }

    public void setBirthType(int birthType) {
        this.birthType = birthType;
    }

}
