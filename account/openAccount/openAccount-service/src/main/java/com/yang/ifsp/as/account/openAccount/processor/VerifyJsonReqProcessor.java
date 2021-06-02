package com.yang.ifsp.as.account.openAccount.processor;

import com.yang.ifsp.as.account.openAccount.constants.AccountEnums;
import com.yang.ifsp.as.account.openAccount.util.MakeCommon;
import com.yang.ifsp.as.account.openAccount.vo.OpenAccountReq;
import com.yang.ifsp.as.account.openAccount.vo.OpenAccountRes;
//import com.yang.ifsp.common.redis.CacheReqUidUtil;
import com.yang.ifsp.common.util.ReqUidUtil;
import com.yang.ifsp.common.util.ValidatorUtil;
import org.slf4j.LoggerFactory;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Map;



@Component
public class VerifyJsonReqProcessor {

    private static Logger logger = LoggerFactory.getLogger(VerifyJsonReqProcessor.class);

    @Autowired
    ReqUidUtil reqUidUtil;

    public boolean verifyJsonReqMsg(OpenAccountReq openAccountReq, OpenAccountRes openAccountRes){
        logger.info("***********************校验请求报文***********************");
        Map errorMap = verifyRequest(openAccountReq,openAccountRes);

        if(errorMap != null){
            MakeCommon.makeOpenAcctCommonRes(openAccountReq,openAccountRes);
            return false;
        }
        logger.info("***********************请求报文通过***********************");
        return true;
    }

    private Map verifyRequest(OpenAccountReq openAccountReq, OpenAccountRes openAccountRes) {
        Map<String,StringBuilder> errorMap = ValidatorUtil.validate(openAccountReq);
        if(errorMap != null){
            logger.error("***************校验请求报文非法*****************");
            StringBuffer stringBuffer= new StringBuffer();
            errorMap.forEach((s,stringBuilder) -> {
                stringBuffer.append(s+":"+errorMap.get(s)+",");
            });
            stringBuffer.deleteCharAt(stringBuffer.length()-1);
            openAccountRes.setRespCode(AccountEnums.VALIDATE_ERROR.getRespCode());
            openAccountRes.setRespMsg(AccountEnums.VALIDATE_ERROR.getRespMsg());
            logger.info("报文校验失败，具体原因为【"+stringBuffer+"】,返回内容：【"+AccountEnums.VALIDATE_ERROR.getRespMsg()+"】");
        }
        return errorMap;
    }

    public boolean verifyReqUid(OpenAccountReq openAccountReq, String reqUid,OpenAccountRes openAccountRes) {
        logger.info("*******************开始校验请求流水号********************");
        boolean repeatFlag = reqUidUtil.checkIsRepeat(reqUid);
        if(!repeatFlag){
            logger.error("请求流水号校验失败");
            openAccountRes.setRespCode(AccountEnums.REQUID_CHECK_ERROR.getRespCode());
            openAccountRes.setRespMsg(AccountEnums.REQUID_CHECK_ERROR.getRespMsg());

            MakeCommon.makeOpenAcctCommonRes(openAccountReq,openAccountRes);
            return false;

        }
        logger.info("*******************校验请求流水号【{"+reqUid+"}】校验通过********************");
        return true;
    }
}
