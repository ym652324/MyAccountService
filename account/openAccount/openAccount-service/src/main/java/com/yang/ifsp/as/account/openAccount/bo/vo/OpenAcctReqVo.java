package com.yang.ifsp.as.account.openAccount.bo.vo;

import com.yang.ifsp.common.protocol.AbstractMSOReq;

import java.io.Serializable;

public class OpenAcctReqVo extends AbstractMSOReq implements Serializable {


    private static final long serialVersionUID = -1668717461713223619L;

    private String custName;

    private String mobilePhone;

    private String bindCard;

    private String idNo;

    private String imageStatus;

    public String getCustName() {
        return custName;
    }

    public void setCustName(String custName) {
        this.custName = custName;
    }

    public String getMobilePhone() {
        return mobilePhone;
    }

    public void setMobilePhone(String mobilePhone) {
        this.mobilePhone = mobilePhone;
    }

    public String getBindCard() {
        return bindCard;
    }

    public void setBindCard(String bindCard) {
        this.bindCard = bindCard;
    }

    public String getIdNo() {
        return idNo;
    }

    public void setIdNo(String idNo) {
        this.idNo = idNo;
    }

    public String getImageStatus() {
        return imageStatus;
    }

    public void setImageStatus(String imageStatus) {
        this.imageStatus = imageStatus;
    }
}
