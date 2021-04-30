package com.yang.ifsp.as.account.openAccount.openAccount.processor;

import com.yang.ifsp.as.account.openAccount.openAccount.bo.vo.OpenAcctResVo;
import com.yang.ifsp.as.account.openAccount.openAccount.util.MakeMessage;
import com.yang.ifsp.as.account.openAccount.vo.OpenAccountReq;
import com.yang.ifsp.as.account.openAccount.vo.OpenAccountRes;
import com.yang.ifsp.common.util.ValidatorUtil;
import org.slf4j.LoggerFactory;
import org.slf4j.Logger;
import org.springframework.stereotype.Component;

import java.util.Map;


@Component
public class VerifyJsonReqProcessor {

    private static Logger logger = LoggerFactory.getLogger(VerifyJsonReqProcessor.class);


    public boolean verifyJsonReqMsg(OpenAccountReq openAccountReq, OpenAccountRes openAccountRes,OpenAcctResVo openAcctResVo){
        logger.info("***********************校验请求报文***********************");
        Map errorMap = verifyRequest(openAccountReq,openAcctResVo);
        OpenAccountReq req = openAccountReq;
        OpenAccountRes res = openAccountRes;
        OpenAcctResVo resVo = openAcctResVo;
 //       MakeMessage.makeResHeadFromReq(req,res,resVo);

        return true;
    }

    private Map verifyRequest(OpenAccountReq openAccountReq, OpenAcctResVo openAcctResVo) {
        Map<String,StringBuilder> errorMap = ValidatorUtil.validate(openAccountReq);
        return errorMap;
    }
}
