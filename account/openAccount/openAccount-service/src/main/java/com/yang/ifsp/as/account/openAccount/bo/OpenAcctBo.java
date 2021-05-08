package com.yang.ifsp.as.account.openAccount.bo;

import com.yang.ifsp.as.account.openAccount.bo.vo.OpenAcctReqVo;
import com.yang.ifsp.as.account.openAccount.bo.vo.OpenAcctResVo;

public interface OpenAcctBo {
    public OpenAcctResVo process(OpenAcctReqVo openAcctReqVo);
}
