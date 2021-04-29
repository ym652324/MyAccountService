package com.yang.ifsp.common.protocol;

import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;

public class AbstractMSORes implements Serializable {


    private static final long serialVersionUID = -5371935265835943807L;

    @ApiModelProperty(
            value = "请求流水号",
            required = true
    )
    private String reqUID;

    @ApiModelProperty(
            value = "交易码",
            required = true
    )
    private String tranCode;

    @ApiModelProperty(
            value = "响应码",
            required = true
    )
    private String respCode;

    @ApiModelProperty(
            value = "响应信息"
    )
    private String respMsg;



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

    public String getRespCode() {
        return respCode;
    }

    public void setRespCode(String respCode) {
        this.respCode = respCode;
    }

    public String getRespMsg() {
        return respMsg;
    }

    public void setRespMsg(String respMsg) {
        this.respMsg = respMsg;
    }
}
