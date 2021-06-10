package com.yang.ifsp.as.account.batch.model;

import java.util.Date;

public class RecFileInfo {
    private String fId;
    private String fileName;
    private int status;
    private String fileHead;
    private Date createTime;

    public String getfId() {
        return fId;
    }

    public void setfId(String fId) {
        this.fId = fId;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getFileHead() {
        return fileHead;
    }

    public void setFileHead(String fileHead) {
        this.fileHead = fileHead;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    @Override
    public String toString() {
        return "RecFileInfo{" +
                "fId='" + fId + '\'' +
                ", fileName='" + fileName + '\'' +
                ", status=" + status +
                ", fileHead='" + fileHead + '\'' +
                ", createTime=" + createTime +
                '}';
    }
}
