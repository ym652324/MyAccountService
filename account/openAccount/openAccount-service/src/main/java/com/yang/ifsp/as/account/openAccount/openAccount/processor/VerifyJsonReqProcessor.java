package com.yang.ifsp.as.account.openAccount.openAccount.processor;

import com.yang.ifsp.as.account.openAccount.openAccount.bo.vo.OpenAcctResVo;
import com.yang.ifsp.as.account.openAccount.openAccount.constants.AccountEnums;
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
        Map<String,StringBuilder> errorMap = ValidatorUtil.validate(openAccountReq);
        if(errorMap != null){
            logger.error("***************校验请求报文非法*****************");
            StringBuffer stringBuffer= new StringBuffer();
            errorMap.forEach((s,stringBuilder) -> {
                stringBuilder.append(s+":"+errorMap.get(s)+",");
            });
            stringBuffer.deleteCharAt(stringBuffer.length()-1);
            openAcctResVo.setRespCode(AccountEnums.VALIDATE_ERROR.getRespCode());
            openAcctResVo.setRespMsg(AccountEnums.VALIDATE_ERROR.getRespMsg());
        }
        if(errorMap != null){
            return false;
        }
        logger.info("***********************请求报文通过***********************");
        return true;
    }

}
