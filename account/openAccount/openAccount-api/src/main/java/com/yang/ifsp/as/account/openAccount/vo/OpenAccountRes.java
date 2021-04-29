package com.yang.ifsp.as.account.openAccount.vo;

import com.yang.ifsp.common.protocol.AbstractMSORes;
import io.swagger.annotations.ApiModelProperty;


public class OpenAccountRes extends AbstractMSORes{


    private static final long serialVersionUID = -3866954618726243005L;

    @ApiModelProperty(
            value = "电子账户",
            required = true
    )
    private String eAccount;

    @ApiModelProperty(
            value = "姓名",
            required = true
    )
    private String custName;

    @ApiModelProperty(
            value = "证件号",
            required = true
    )
    private String idNo;

    public String geteAccount() {
        return eAccount;
    }

    public void seteAccount(String eAccount) {
        this.eAccount = eAccount;
    }

    public String getCustName() {
        return custName;
    }

    public void setCustName(String custName) {
        this.custName = custName;
    }

    public String getIdNo() {
        return idNo;
    }

    public void setIdNo(String idNo) {
        this.idNo = idNo;
    }

    @Override
    public String toString() {
        return "OpenAccountRes{" +
                "eAccount='" + eAccount + '\'' +
                ", custName='" + custName + '\'' +
                ", idNo='" + idNo + '\'' +
                '}';
    }
}
