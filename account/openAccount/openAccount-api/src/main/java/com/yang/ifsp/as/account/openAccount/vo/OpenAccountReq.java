package com.yang.ifsp.as.account.openAccount.vo;

import com.yang.ifsp.common.protocol.AbstractMSOReq;
import io.swagger.annotations.ApiModelProperty;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;


public class OpenAccountReq extends AbstractMSOReq {

    private static final long serialVersionUID = 6846954613098125484L;

    @NotBlank
    @Length(max=40)
    @ApiModelProperty(value = "姓名",required = true)
    private String custName;

    @NotBlank
    @Length(max=20)
    @ApiModelProperty(value = "手机号")
    private String mobilePhone;

    @NotBlank
    @Length(max=40)
    @ApiModelProperty(value = "绑定卡")
    private String bindCard;


    @NotBlank
    @Length(max=20)
    @ApiModelProperty(value = "证件号码",required = true)
    private String idNo;


    @ApiModelProperty(value = "影像件")
    @Length(max=2)
    private String image;

    @NotBlank
    @Length(max=20)
    @ApiModelProperty(value = "登陆密码",required = true)
    private String logPassword;

    @NotBlank
    @Length(max=20)
    @ApiModelProperty(value = "支付密码")
    private String payPassword;


    @NotBlank
    @Length(max=40)
    @ApiModelProperty(value = "交易码",required = true)
    private String tranCode;

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
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

    @Override
    public String getTranCode() {
        return tranCode;
    }

    @Override
    public void setTranCode(String tranCode) {
        this.tranCode = tranCode;
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

    public String getImageStatus() {
        return image;
    }

    public void setImageStatus(String imageStatus) {
        this.image = image;
    }
}
