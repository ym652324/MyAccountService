package com.yang.ifsp.as.account.openAccount.vo;

import com.yang.ifsp.common.protocol.AbstractMSOReq;
import io.swagger.annotations.ApiModelProperty;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;
import java.util.Arrays;


public class OpenAccountReq extends AbstractMSOReq {

    private static final long serialVersionUID = 6846954613098125484L;

    @NotBlank
    @Length(max=64)
    @ApiModelProperty(value = "姓名",required = true)
    private String custName;


    @Length(max=20)
    @ApiModelProperty(value = "手机号")
    private String mobilePhone;


    @Length(max=30)
    @ApiModelProperty(value = "绑定卡")
    private String bindCard;


    @NotBlank
    @Length(max=20)
    @ApiModelProperty(value = "证件号码",required = true)
    private String idNo;


    @ApiModelProperty(value = "影像件")
    private byte[] image;

    @NotBlank
    @Length(max=255)
    @ApiModelProperty(value = "登陆密码",required = true)
    private String logPassword;


    @Length(max=255)
    @ApiModelProperty(value = "支付密码")
    private String payPassword;


    @NotBlank
    @Length(max=20)
    @ApiModelProperty(value = "交易类型",required = true)
    private String tranType;



    @Length(max=30)
    @ApiModelProperty(value = "电子账户")
    private String eAccount;

    @NotBlank
    @Length(max=10)
    @ApiModelProperty(value = "开户类型")
    private String accountType;


    @NotBlank
    @Length(max=20)
    @ApiModelProperty(value = "用户类型")
    private String userType;

    public String getUserType() {
        return userType;
    }

    public void setUserType(String userType) {
        this.userType = userType;
    }

    public String getAccountType() {
        return accountType;
    }

    public void setAccountType(String accountType) {
        this.accountType = accountType;
    }

    public String geteAccount() {
        return eAccount;
    }

    public void seteAccount(String eAccount) {
        this.eAccount = eAccount;
    }

    public String getLogPassword() {
        return logPassword;
    }

    public void setLogPassword(String logPassword) {
        this.logPassword = logPassword;
    }

    public String getPayPassword() {
        return payPassword;
    }

    public void setPayPassword(String payPassword) {
        this.payPassword = payPassword;
    }

    public String getTranType() {
        return tranType;
    }

    public void setTranType(String tranType) {
        this.tranType = tranType;
    }

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

    public byte[] getImage() {
        return image;
    }

    public void setImage(byte[] image) {
        this.image = image;
    }


    @Override
    public String toString() {
        return "OpenAccountReq{" +
                "reqUid='" + getReqUID() + '\'' +
                "tranCode='" + getTranCode() + '\'' +
                "reqDatetime='" + getReqDatetime() + '\'' +
                "custName='" + custName + '\'' +
                ", mobilePhone='" + mobilePhone + '\'' +
                ", bindCard='" + bindCard + '\'' +
                ", idNo='" + idNo + '\'' +
                ", image=" + Arrays.toString(image) +
                ", logPassword= ********'" + '\'' +
                ", payPassword= ********'" + '\'' +
                ", tranType='" + tranType + '\'' +
                ", eAccount='" + eAccount + '\'' +
                ", accountType='" + accountType + '\'' +
                ", userType='" + userType + '\'' +
                '}';
    }
}
