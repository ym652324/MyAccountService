package com.yang.ifsp.as.account.openAccount.impl;

import com.yang.ifsp.as.account.openAccount.api.OpenAccountService;
import com.yang.ifsp.as.account.openAccount.bo.impl.OpenAcctBoImpl;
import com.yang.ifsp.as.account.openAccount.processor.VerifyJsonReqProcessor;
import com.yang.ifsp.as.account.openAccount.vo.OpenAccountReq;
import com.yang.ifsp.as.account.openAccount.vo.OpenAccountRes;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;


@RestController
public class OpenAccountServiceImpl implements OpenAccountService {

    @Autowired
    VerifyJsonReqProcessor verifyJsonReqProcessor;

    @Autowired
    OpenAcctBoImpl openAcctBoImpl;

    @Override
    public @ResponseBody
    OpenAccountRes doOpenAccount(@RequestBody OpenAccountReq openAccountReq) {
        OpenAccountRes openAccountRes = new OpenAccountRes();

        boolean reqFlag = verifyJsonReqProcessor.verifyJsonReqMsg(openAccountReq,openAccountRes);
        if(!reqFlag){
            return openAccountRes;
        }

        String reqUid = openAccountReq.getReqUID();
        boolean reqUidFlag = verifyJsonReqProcessor.verifyReqUid(openAccountReq,reqUid,openAccountRes);
        if(!reqUidFlag){
            return openAccountRes;
        }

        openAccountRes = openAcctBoImpl.process(openAccountReq);

        return  openAccountRes;

    }
}
