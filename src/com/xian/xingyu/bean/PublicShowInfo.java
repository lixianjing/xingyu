package com.xian.xingyu.bean;

import java.util.List;

public class PublicShowInfo {

    private long id;
    private String content;
    private long stamp;
    private int type;
    private boolean hasPic;
    private int commentCount;
    private int favCount;
    private String picUri;
    private String token;
    private List<FileDataInfo> fileDateList;


    public PublicShowInfo() {
        super();
    }

    public PublicShowInfo(long id, String content, long stamp, int type, boolean hasPic,
            int commentCount, int favCount, String picUri, String token) {
        super();
        this.id = id;
        this.content = content;
        this.stamp = stamp;
        this.type = type;
        this.hasPic = hasPic;
        this.commentCount = commentCount;
        this.favCount = favCount;
        this.picUri = picUri;
        this.token = token;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public long getStamp() {
        return stamp;
    }

    public void setStamp(long stamp) {
        this.stamp = stamp;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public boolean isHasPic() {
        return hasPic;
    }

    public void setHasPic(boolean hasPic) {
        this.hasPic = hasPic;
    }

    public int getCommentCount() {
        return commentCount;
    }

    public void setCommentCount(int commentCount) {
        this.commentCount = commentCount;
    }

    public int getFavCount() {
        return favCount;
    }

    public void setFavCount(int favCount) {
        this.favCount = favCount;
    }

    public String getPicUri() {
        return picUri;
    }

    public void setPicUri(String picUri) {
        this.picUri = picUri;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public List<FileDataInfo> getFileDateList() {
        return fileDateList;
    }

    public void setFileDateList(List<FileDataInfo> fileDateList) {
        this.fileDateList = fileDateList;
    }



}
