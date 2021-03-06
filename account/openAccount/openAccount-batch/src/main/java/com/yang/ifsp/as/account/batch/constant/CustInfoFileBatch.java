package com.yang.ifsp.as.account.batch.constant;

import org.springframework.stereotype.Component;

@Component
public class CustInfoFileBatch {
    private String basePath;
    private String sourcePath;
    private String sourceBakPath;
    private String sendBakPath;
    private String sendPath;
    private String tempPath;
    private String fileNamePattern;

    private String fileExistFlagKey;
    private String sourceFileNameKey;
    private String sendFileNameKey;

    private String readLineNumKey;


    private String mvCmds;

    private String fileHeadKey;

    private String systemShell;
//    private String sendPath;
//    private String tempPath;
//    private String fileNamePattern;



    public CustInfoFileBatch(AccountBatchConfig accountBatchConfig) {
        this.basePath = "/Users/yangmeng/Desktop/test/";
        this.sourcePath = this.basePath+"custInfoFileBatch/recv/";
        this.sourceBakPath = this.basePath+"custInfoFileBatch/recvBak/";
        this.sendBakPath = this.basePath+"custInfoFileBatch/sendBak/";
        this.sendPath = this.basePath+"custInfoFileBatch/send/";
        this.tempPath = this.basePath+"custInfoFileBatch";
        this.fileNamePattern = "^\\w+_[A-Z]{1}_000001_\\w+.txt$";
        this.mvCmds = "custMvCmds";
        this.systemShell = "systemShell";
        this.readLineNumKey = "readLineNumKey";
        this.fileHeadKey = "fileHeadKey";
        this.fileExistFlagKey = "fileExitFlagKey";
        this.sourceFileNameKey = "sourceFileNameKey";

    }

    public String getBasePath() {
        return basePath;
    }

    public void setBasePath(String basePath) {
        this.basePath = basePath;
    }

    public String getSourcePath() {
        return sourcePath;
    }

    public void setSourcePath(String sourcePath) {
        this.sourcePath = sourcePath;
    }

    public String getSourceBakPath() {
        return sourceBakPath;
    }

    public void setSourceBakPath(String sourceBakPath) {
        this.sourceBakPath = sourceBakPath;
    }

    public String getSendBakPath() {
        return sendBakPath;
    }

    public void setSendBakPath(String sendBakPath) {
        this.sendBakPath = sendBakPath;
    }

    public String getSendPath() {
        return sendPath;
    }

    public void setSendPath(String sendPath) {
        this.sendPath = sendPath;
    }

    public String getTempPath() {
        return tempPath;
    }

    public void setTempPath(String tempPath) {
        this.tempPath = tempPath;
    }

    public String getFileNamePattern() {
        return fileNamePattern;
    }

    public void setFileNamePattern(String fileNamePattern) {
        this.fileNamePattern = fileNamePattern;
    }

    public String getFileExistFlagKey() {
        return fileExistFlagKey;
    }

    public void setFileExistFlagKey(String fileExistFlagKey) {
        this.fileExistFlagKey = fileExistFlagKey;
    }

    public String getSourceFileNameKey() {
        return sourceFileNameKey;
    }

    public void setSourceFileNameKey(String sourceFileNameKey) {
        this.sourceFileNameKey = sourceFileNameKey;
    }

    public String getSendFileNameKey() {
        return sendFileNameKey;
    }

    public void setSendFileNameKey(String sendFileNameKey) {
        this.sendFileNameKey = sendFileNameKey;
    }

    public String getReadLineNumKey() {
        return readLineNumKey;
    }

    public void setReadLineNumKey(String readLineNumKey) {
        this.readLineNumKey = readLineNumKey;
    }

    public String getMvCmds() {
        return mvCmds;
    }

    public void setMvCmds(String mvCmds) {
        this.mvCmds = mvCmds;
    }

    public String getSystemShell() {
        return systemShell;
    }

    public void setSystemShell(String systemShell) {
        this.systemShell = systemShell;
    }

    public String getFileHeadKey() {
        return fileHeadKey;
    }

    public void setFileHeadKey(String fileHeadKey) {
        this.fileHeadKey = fileHeadKey;
    }
}
