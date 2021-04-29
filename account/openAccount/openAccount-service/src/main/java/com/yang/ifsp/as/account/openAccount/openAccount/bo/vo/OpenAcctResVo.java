package com.yang.ifsp.as.account.openAccount.openAccount.bo.vo;

import com.yang.ifsp.common.protocol.AbstractMSORes;

import java.io.Serializable;

public class OpenAcctResVo extends AbstractMSORes implements Serializable {

    private static final long serialVersionUID = -5474529648482150012L;

    private String eAccount;

    public String geteAccount() {
        return eAccount;
    }

    public void seteAccount(String eAccount) {
        this.eAccount = eAccount;
    }
}
