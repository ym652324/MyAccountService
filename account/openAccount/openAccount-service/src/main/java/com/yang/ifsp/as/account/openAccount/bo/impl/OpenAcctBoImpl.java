package com.yang.ifsp.as.account.openAccount.bo.impl;

import com.yang.ifsp.as.account.openAccount.bo.OpenAcctBo;
import com.yang.ifsp.as.account.openAccount.bo.vo.OpenAcctReqVo;
import com.yang.ifsp.as.account.openAccount.bo.vo.OpenAcctResVo;
import com.yang.ifsp.as.account.openAccount.constants.AccountEnums;
import com.yang.ifsp.as.account.openAccount.db.model.OpenAcctTxnInfoDO;
import com.yang.ifsp.as.account.openAccount.model.OpenAccountTxnInfo;
import com.yang.ifsp.as.account.openAccount.processor.DbProcessor;
import com.yang.ifsp.as.account.openAccount.processor.VerifyJsonReqProcessor;
import com.yang.ifsp.as.account.openAccount.util.MakeMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class OpenAcctBoImpl implements OpenAcctBo {

    private static Logger logger = LoggerFactory.getLogger(OpenAcctBoImpl.class);

    @Autowired
    DbProcessor dbProcessor;

    @Override
    public OpenAcctResVo process(OpenAcctReqVo reqVo) {
        OpenAcctResVo resVo = new OpenAcctResVo();
        String reqUid = reqVo.getReqUID();
        OpenAcctTxnInfoDO openAcctTxnInfoDO = new OpenAcctTxnInfoDO();
        if(dbProcessor.insertModel(reqVo,openAcctTxnInfoDO)==0){
            logger.error("请求报文入库失败");
            resVo.setRespCode(AccountEnums.SYSTEM_INNRT_ERROR.getRespCode());
            resVo.setRespMsg(AccountEnums.SYSTEM_INNRT_ERROR.getRespMsg());

            return resVo;
        }





        return resVo;
    }
}
