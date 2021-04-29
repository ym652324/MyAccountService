package com.yang.ifsp.as.account.openAccount.openAccount.processor;

import com.yang.ifsp.as.account.openAccount.openAccount.bo.vo.OpenAcctResVo;
import com.yang.ifsp.as.account.openAccount.vo.OpenAccountReq;
import com.yang.ifsp.as.account.openAccount.vo.OpenAccountRes;
import org.springframework.beans.factory.annotation.Autowired;

public class verifyJsonReqProcessor {

    @Autowired
    private OpenAcctResVo openAcctResVo;

    @Autowired
    private OpenAccountReq openAccountReq;

    @Autowired
    private OpenAccountRes openAccountRes;


    public boolean verifyJsonReqMsg(OpenAccountReq openAccountReq, OpenAccountRes openAccountRes,OpenAcctResVo openAcctResVo){


        return true;
    }
}
