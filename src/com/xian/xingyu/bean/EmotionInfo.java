
package com.xian.xingyu.bean;

import java.util.List;

public class EmotionInfo {

    private long id;
    private String subject;
    private String content;
    private long stamp;
    private String local;
    private String localGps;
    private int type;
    private int status;
    private boolean hasPic;
    private int commentCount;
    private int favCount;
    private String userToken;
    private List<FileDataInfo> fileDateList;

    public EmotionInfo() {
        super();
    }

    public EmotionInfo(long id, String subject, String content, long stamp, String local,
            String localGps, int type, int status, boolean hasPic, int commentCount, int favCount,
            String userToken) {
        super();
        this.id = id;
        this.subject = subject;
        this.content = content;
        this.stamp = stamp;
        this.local = local;
        this.localGps = localGps;
        this.type = type;
        this.status = status;
        this.hasPic = hasPic;
        this.commentCount = commentCount;
        this.favCount = favCount;
        this.userToken = userToken;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
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

    public String getLocal() {
        return local;
    }

    public void setLocal(String local) {
        this.local = local;
    }

    public String getLocalGps() {
        return localGps;
    }

    public void setLocalGps(String localGps) {
        this.localGps = localGps;
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

    public String getUserToken() {
        return userToken;
    }

    public void setUserToken(String userToken) {
        this.userToken = userToken;
    }

    public List<FileDataInfo> getFileDateList() {
        return fileDateList;
    }

    public void setFileDateList(List<FileDataInfo> fileDateList) {
        this.fileDateList = fileDateList;
    }

}
