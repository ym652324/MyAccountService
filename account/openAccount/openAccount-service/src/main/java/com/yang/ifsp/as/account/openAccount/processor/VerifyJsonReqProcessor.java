package com.yang.ifsp.as.account.openAccount.processor;

import com.yang.ifsp.as.account.openAccount.bo.vo.OpenAcctResVo;
import com.yang.ifsp.as.account.openAccount.constants.AccountEnums;
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
//    private CacheReqUidUtil cacheReqUidUtil;
    ReqUidUtil reqUidUtil;



    public boolean verifyJsonReqMsg(OpenAccountReq openAccountReq, OpenAccountRes openAccountRes, OpenAcctResVo openAcctResVo){
        logger.info("***********************校验请求报文***********************");
        Map errorMap = verifyRequest(openAccountReq,openAcctResVo);

        if(errorMap != null){
            //公共部分

            openAccountRes.setReqUID(openAccountReq.getReqUID());


            openAccountRes.setCustName(openAccountReq.getCustName());
            openAccountRes.setIdNo(openAccountReq.getIdNo());
            openAccountRes.setRespCode(openAcctResVo.getRespCode());
            openAccountRes.setRespMsg(openAcctResVo.getRespMsg());

            return false;
        }
        logger.info("***********************请求报文通过***********************");
        return true;
    }

    private Map verifyRequest(OpenAccountReq openAccountReq, OpenAcctResVo openAcctResVo) {
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
        return errorMap;
    }

    public boolean verifyReqUid(OpenAccountReq openAccountReq, String reqUid, OpenAcctResVo openAcctResVo, OpenAccountRes openAccountRes) {
        logger.info("*******************开始校验请求流水号********************");
        boolean repeatFlag = reqUidUtil.checkIsRepeat(reqUid);
        if(!repeatFlag){
            logger.error("请求流水号校验失败");
            openAcctResVo.setRespCode(AccountEnums.REQUID_CHECK_ERROR.getRespCode());
            openAcctResVo.setRespMsg(AccountEnums.REQUID_CHECK_ERROR.getRespMsg());

            openAccountRes.setReqUID(openAccountReq.getReqUID());
            openAccountRes.setCustName(openAccountReq.getCustName());
            openAccountRes.setIdNo(openAccountReq.getIdNo());
            openAccountRes.setRespCode(openAcctResVo.getRespCode());
            openAccountRes.setRespMsg(openAcctResVo.getRespMsg());

        }
        logger.info("*******************校验请求流水号[{}]通过********************"+reqUid);
        return true;
    }
}
