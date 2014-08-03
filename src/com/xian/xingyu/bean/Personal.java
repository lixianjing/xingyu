
package com.xian.xingyu.bean;

public class Personal {

    private long id;
    private long accountId;
    private byte[] iconByte;
    private String iconUri;
    private byte[] iconThumb;
    private String name;
    private String desc;
    private int gender;
    private String local;
    private int birthYear;
    private int birthMonth;
    private int birthDay;
    private int birthType;

    public Personal() {
        super();
    }

    public Personal(long id, long accountId, byte[] iconByte, String iconUri, byte[] iconThumb,
            String name, String desc, int gender, String local, int birth_year, int birth_month,
            int birth_day, int birth_type) {
        super();
        this.id = id;
        this.accountId = accountId;
        this.iconByte = iconByte;
        this.iconUri = iconUri;
        this.iconThumb = iconThumb;
        this.name = name;
        this.desc = desc;
        this.gender = gender;
        this.local = local;
        this.birthYear = birth_year;
        this.birthMonth = birth_month;
        this.birthDay = birth_day;
        this.birthType = birth_type;
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

    public byte[] getIconByte() {
        return iconByte;
    }

    public void setIconByte(byte[] iconByte) {
        this.iconByte = iconByte;
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
