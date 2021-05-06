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
    @ApiModelProperty(value = "手机号",required = true)
    private String mobilePhone;

    @NotBlank
    @Length(max=40)
    @ApiModelProperty(value = "绑定卡",required = true)
    private String bindCard;


    @NotBlank
    @Length(max=20)
    @ApiModelProperty(value = "证件号码",required = true)
    private String idNo;



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
}
