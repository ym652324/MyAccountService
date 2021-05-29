package com.yang.ifsp.common.protocol;

import java.io.Serializable;
import java.util.Date;

import io.swagger.annotations.ApiModelProperty;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;

public abstract class AbstractMSOReq implements Serializable {
    private static final long serialVersionUID = -8425737953742671465L;


    @NotBlank
    @Length(max=20)
    @ApiModelProperty(value = "请求流水号",required = true)
    private String reqUID;


    @NotBlank
    @Length(max=20)
    @ApiModelProperty(value = "交易码",required = true)
    private String tranCode;


    @ApiModelProperty(
            value = "请求时间",
            required = true
    )
    private Date reqDatetime;

    public String getReqUID() {
        return reqUID;
    }

    public void setReqUID(String reqUID) {
        this.reqUID = reqUID;
    }

    public String getTranCode() {
        return tranCode;
    }

    public void setTranCode(String tranCode) {
        this.tranCode = tranCode;
    }

    public Date getReqDatetime() {
        return reqDatetime;
    }

    public void setReqDatetime(Date reqDatetime) {
        this.reqDatetime = reqDatetime;
    }
}
