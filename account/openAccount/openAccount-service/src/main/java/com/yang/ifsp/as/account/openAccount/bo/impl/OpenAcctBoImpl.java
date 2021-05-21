package com.yang.ifsp.as.account.openAccount.bo.impl;

import com.yang.ifsp.as.account.openAccount.bo.OpenAcctBo;
import com.yang.ifsp.as.account.openAccount.constants.AccountEnums;
import com.yang.ifsp.as.account.openAccount.db.model.OpenAcctTxnInfoDO;
import com.yang.ifsp.as.account.openAccount.processor.DbProcessor;
import com.yang.ifsp.as.account.openAccount.util.CheckUtil;
import com.yang.ifsp.as.account.openAccount.util.MakeMessage;
import com.yang.ifsp.as.account.openAccount.vo.OpenAccountReq;
import com.yang.ifsp.as.account.openAccount.vo.OpenAccountRes;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;


@Service
public class OpenAcctBoImpl implements OpenAcctBo {

    private static Logger logger = LoggerFactory.getLogger(OpenAcctBoImpl.class);

    @Autowired
    DbProcessor dbProcessor;

    @Override
    public OpenAccountRes process(OpenAccountReq req) {
        OpenAccountRes res = new OpenAccountRes();
        String reqUid = req.getReqUID();
        OpenAcctTxnInfoDO openAcctTxnInfoDO = new OpenAcctTxnInfoDO();
        dbProcessor.insertModel(req,openAcctTxnInfoDO);
//        if(dbProcessor.insertModel(req,openAcctTxnInfoDO)==0){
//            logger.error("请求报文入库失败,流水号：[{}]"+reqUid);
//            res.setRespCode(AccountEnums.SYSTEM_INNRT_ERROR.getRespCode());
//            res.setRespMsg(AccountEnums.SYSTEM_INNRT_ERROR.getRespMsg());
//            MakeMessage.makeOpenAcctCommonRes(req,res);
//            openAcctTxnInfoDO.setRespcode(res.getRespCode());
//            openAcctTxnInfoDO.setRespmsg(res.getRespMsg());
//            openAcctTxnInfoDO.setLastupdatetime(new Date());
//            openAcctTxnInfoDO.setLastoperate("请求报文入库");
//            dbProcessor.updateModel(openAcctTxnInfoDO);
//            return res;
//        }
        String name = req.getCustName();
        String idNo = req.getIdNo();
        String mobile = req.getMobilePhone();
        String bindCard = req.getBindCard();
        String eAccount = req.geteAccount();
        HashSet<String> set = new HashSet<String>(Arrays.asList("name","idNo","mobile","bindCard"));
        HashMap<String,String> checkMap = CheckUtil.check(set);
        if("false".equals(checkMap.get(0))){
            logger.error("请求报文[{]]字段校验失败"+checkMap.get(1));
            res.setRespCode(AccountEnums.FORMAT_ERROR.getRespCode());
            res.setRespMsg(AccountEnums.FORMAT_ERROR.getRespMsg());
        }
        String tranCode = req.getTranCode();
        if("T0001".equals(tranCode)){
            logger.info("进入开户接口流程");




        }else if("T0002".equals(tranCode)){
            logger.info("进入补录接口流程");
            if(StringUtils.isEmpty(eAccount)){
                logger.error("补录接口电子账号不能为空");
                res.setRespCode(AccountEnums.VALIDATE_ERROR.getRespCode());
                res.setRespMsg(AccountEnums.VALIDATE_ERROR.getRespMsg());
            }



        }else{
            logger.error("无效的交易码");
            res.setRespCode(AccountEnums.TRAN_CODE_ERROR.getRespCode());
            res.setRespMsg(AccountEnums.TRAN_CODE_ERROR.getRespMsg());
            openAcctTxnInfoDO.setLastoperate("请求报文入库");
            openAcctTxnInfoDO.setLastupdatetime(new Date());
            openAcctTxnInfoDO.setRespcode(res.getRespCode());
            openAcctTxnInfoDO.setRespmsg(res.getRespMsg());
        }

        dbProcessor.updateModel(openAcctTxnInfoDO);
        return res;
    }
}
