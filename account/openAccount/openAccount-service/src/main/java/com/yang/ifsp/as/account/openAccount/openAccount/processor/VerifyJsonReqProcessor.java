package com.yang.ifsp.as.account.openAccount.openAccount.processor;

import com.yang.ifsp.as.account.openAccount.openAccount.bo.vo.OpenAcctResVo;
import com.yang.ifsp.as.account.openAccount.vo.OpenAccountReq;
import com.yang.ifsp.as.account.openAccount.vo.OpenAccountRes;
import org.slf4j.LoggerFactory;
import org.slf4j.Logger;
import org.springframework.stereotype.Component;




@Component
public class VerifyJsonReqProcessor {

    private static Logger logger = LoggerFactory.getLogger(VerifyJsonReqProcessor.class);


    public boolean verifyJsonReqMsg(OpenAccountReq openAccountReq, OpenAccountRes openAccountRes,OpenAcctResVo openAcctResVo){


        return true;
    }
}
