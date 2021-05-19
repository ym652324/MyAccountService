package com.yang.ifsp.as.account.openAccount.impl;

import com.yang.ifsp.as.account.openAccount.api.OpenAccountService;
import com.yang.ifsp.as.account.openAccount.bo.OpenAcctBo;
import com.yang.ifsp.as.account.openAccount.bo.impl.OpenAcctBoImpl;
import com.yang.ifsp.as.account.openAccount.bo.vo.OpenAcctReqVo;
import com.yang.ifsp.as.account.openAccount.bo.vo.OpenAcctResVo;
import com.yang.ifsp.as.account.openAccount.processor.VerifyJsonReqProcessor;
import com.yang.ifsp.as.account.openAccount.util.ReqToVoUtil;
import com.yang.ifsp.as.account.openAccount.util.VoToResUtil;
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
        OpenAcctResVo openAcctResVo = new OpenAcctResVo();

        OpenAcctReqVo openAcctReqVo = new OpenAcctReqVo();
        ReqToVoUtil.openAcctReqToVo(openAccountReq,openAcctReqVo);

        boolean reqFlag = verifyJsonReqProcessor.verifyJsonReqMsg(openAccountReq,openAccountRes,openAcctResVo);
        if(!reqFlag){
            return openAccountRes;
        }

        String reqUid = openAccountReq.getReqUID();
        boolean reqUidFlag = verifyJsonReqProcessor.verifyReqUid(openAccountReq,reqUid,openAcctResVo,openAccountRes);
        if(!reqUidFlag){
            return openAccountRes;
        }




        openAcctResVo = openAcctBoImpl.process(openAcctReqVo);
        VoToResUtil.openAcctVoToRes(openAccountReq,openAcctResVo,openAccountRes);

        return  openAccountRes;

    }
}
