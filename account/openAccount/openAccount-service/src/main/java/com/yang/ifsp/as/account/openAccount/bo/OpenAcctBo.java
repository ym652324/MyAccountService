package com.yang.ifsp.as.account.openAccount.bo;

import com.yang.ifsp.as.account.openAccount.vo.OpenAccountReq;
import com.yang.ifsp.as.account.openAccount.vo.OpenAccountRes;

public interface OpenAcctBo {
    public OpenAccountRes process(OpenAccountReq req);
}
