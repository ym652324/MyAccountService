package com.yang.ifsp.as.account.openAccount.processor;

import com.yang.ifsp.as.account.openAccount.db.dao.AccountInfoDOMapper;
import com.yang.ifsp.as.account.openAccount.db.dao.OpenAcctTxnInfoDOMapper;
import com.yang.ifsp.as.account.openAccount.db.model.AccountInfoDO;
import com.yang.ifsp.as.account.openAccount.db.model.OpenAcctTxnInfoDO;
import com.yang.ifsp.as.account.openAccount.vo.OpenAccountReq;
import com.yang.ifsp.as.account.openAccount.vo.OpenAccountRes;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.Date;


@Component
public class DbProcessor {

    private static Logger logger = LoggerFactory.getLogger(DbProcessor.class);

    @Autowired
    OpenAcctTxnInfoDOMapper openAcctTxnInfoDOMapper;

    @Autowired
    AccountInfoDOMapper accountInfoDOMapper;

    public int insertModel(Object...objs){
//        CreateModelUtil.createModel(srcObj, obj);
        if(objs[0] instanceof OpenAcctTxnInfoDO){
            OpenAcctTxnInfoDO openAcctTxnInfoDO = (OpenAcctTxnInfoDO)objs[0];
            OpenAccountReq req = (OpenAccountReq)objs[1];
            openAcctTxnInfoDO.setRequid(req.getReqUID());
            openAcctTxnInfoDO.setBindcard(req.getBindCard());
            openAcctTxnInfoDO.setCreatetime(new Date());
            openAcctTxnInfoDO.setCustname(req.getCustName());
            openAcctTxnInfoDO.setIdno(req.getIdNo());
            openAcctTxnInfoDO.setMobilephone(req.getMobilePhone());
            openAcctTxnInfoDO.setImage(req.getImage());
            openAcctTxnInfoDO.setEaccount(req.geteAccount());
            openAcctTxnInfoDO.setTrantype(req.getTranType());
            openAcctTxnInfoDO.setLastoperate("校验报文及流水号");
            openAcctTxnInfoDO.setLastupdatetime(new Date());
            openAcctTxnInfoDO.setAccounttype(req.getAccountType());
            logger.info("openAcctTxnInfoDO中AccountType："+openAcctTxnInfoDO.getAccounttype());
            openAcctTxnInfoDO.setUsertype(req.getUserType());
            return openAcctTxnInfoDOMapper.insertSelective(openAcctTxnInfoDO);
        }
        if(objs[0] instanceof AccountInfoDO){
            AccountInfoDO accountInfoDO = (AccountInfoDO) objs[0];
            OpenAcctTxnInfoDO openAcctTxnInfoDO = (OpenAcctTxnInfoDO) objs[1];
            OpenAccountReq openAccountReq = (OpenAccountReq) objs[2];
            accountInfoDO.setBindcard(openAcctTxnInfoDO.getBindcard());
            accountInfoDO.setEaccount(openAcctTxnInfoDO.getEaccount());
            accountInfoDO.setUserid(openAcctTxnInfoDO.getUserid());
            accountInfoDO.setIdno(openAcctTxnInfoDO.getIdno());
            accountInfoDO.setMoney(new BigDecimal(0));
            accountInfoDO.setAccounttype(openAcctTxnInfoDO.getAccounttype());
            logger.info("accountInfoDO中AccountType："+accountInfoDO.getAccounttype());
            accountInfoDO.setAccountstatus("S01");
            accountInfoDO.setImagestatus(openAcctTxnInfoDO.getImage()==null||openAcctTxnInfoDO.getImage().length==0?"M02":"M01");
            accountInfoDO.setLogpassword(openAccountReq.getLogPassword());
            accountInfoDO.setPaypassword(openAccountReq.getPayPassword());
            return accountInfoDOMapper.insertSelective(accountInfoDO);
        }

        return 0;
    }

    public void updateModel(Object obj,Object srcObj) {
        if(obj instanceof OpenAcctTxnInfoDO){
            OpenAcctTxnInfoDO openAcctTxnInfoDO = (OpenAcctTxnInfoDO)obj;
            OpenAccountRes openAccountRes = (OpenAccountRes)srcObj;
            openAccountRes.setRespDatetime(new Date());
            openAcctTxnInfoDO.setRespcode(openAccountRes.getRespCode());
            openAcctTxnInfoDO.setRespmsg(openAccountRes.getRespMsg());
            openAcctTxnInfoDO.setLastupdatetime(openAccountRes.getRespDatetime());
            openAcctTxnInfoDO.setEaccount(openAccountRes.geteAccount());
            openAcctTxnInfoDOMapper.updateByPrimaryKeySelective(openAcctTxnInfoDO);
        }
        if(obj instanceof AccountInfoDO){
            AccountInfoDO accountInfoDO = (AccountInfoDO)obj;
            OpenAccountReq openAccountReq = (OpenAccountReq) srcObj;
            logger.info("openAccountReq中Logpassword为："+openAccountReq.getLogPassword());
            accountInfoDO.setLogpassword(openAccountReq.getLogPassword());
            logger.info("accountInfoDO中Logpassword为："+accountInfoDO.getLogpassword());
            accountInfoDO.setPaypassword(openAccountReq.getPayPassword());
            accountInfoDOMapper.updateByPrimaryKeySelective(accountInfoDO);
        }
    }
}
