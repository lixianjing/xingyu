
package com.xian.xingyu.bean;

public class FileDataInfo {

    private long id;
    private int fileType;
    private long fileId;
    private String mime;
    private String uri;
    private String thumbUri;
    private long size;
    private long pos;
    private int type;
    private int status;
    private String seq;

    public FileDataInfo() {
        super();
    }

    public FileDataInfo(long id, int fileType, long fileId, String mime, String uri,
            String thumbUri, long size, long pos, int type, int status, String seq, int order) {
        super();
        this.id = id;
        this.fileType = fileType;
        this.fileId = fileId;
        this.mime = mime;
        this.uri = uri;
        this.thumbUri = thumbUri;
        this.size = size;
        this.pos = pos;
        this.type = type;
        this.status = status;
        this.seq = seq;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public int getFileType() {
        return fileType;
    }

    public void setFileType(int fileType) {
        this.fileType = fileType;
    }

    public long getFileId() {
        return fileId;
    }

    public void setFileId(long fileId) {
        this.fileId = fileId;
    }

    public String getMime() {
        return mime;
    }

    public void setMime(String mime) {
        this.mime = mime;
    }

    public String getUri() {
        return uri;
    }

    public void setUri(String uri) {
        this.uri = uri;
    }

    public String getThumbUri() {
        return thumbUri;
    }

    public void setThumbUri(String thumbUri) {
        this.thumbUri = thumbUri;
    }

    public long getSize() {
        return size;
    }

    public void setSize(long size) {
        this.size = size;
    }

    public long getPos() {
        return pos;
    }

    public void setPos(long pos) {
        this.pos = pos;
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

    public String getSeq() {
        return seq;
    }

    public void setSeq(String seq) {
        this.seq = seq;
    }


}
