package com.yang.ifsp.as.account.openAccount.openAccount.impl;

import com.yang.ifsp.as.account.openAccount.api.OpenAccountService;
import com.yang.ifsp.as.account.openAccount.openAccount.bo.vo.OpenAcctResVo;
import com.yang.ifsp.as.account.openAccount.openAccount.processor.VerifyJsonReqProcessor;
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

    @Override
    public @ResponseBody
    OpenAccountRes doOpenAccount(@RequestBody OpenAccountReq openAccountReq) {
        OpenAccountRes openAccountRes = new OpenAccountRes();
        OpenAcctResVo openAcctResVo = new OpenAcctResVo();

        Boolean reqFlag = verifyJsonReqProcessor.verifyJsonReqMsg(openAccountReq,openAccountRes,openAcctResVo);
        if(!reqFlag){
            return openAccountRes;
        }

        return  openAccountRes;

    }
}
