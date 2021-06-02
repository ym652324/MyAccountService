package com.yang.ifsp.as.account.openAccount.processor;

import com.yang.ifsp.as.account.openAccount.db.dao.AccountInfoDOMapper;
import com.yang.ifsp.as.account.openAccount.db.dao.AccountSupplementTxnInfDoMapper;
import com.yang.ifsp.as.account.openAccount.db.dao.OpenAcctTxnInfoDOMapper;
import com.yang.ifsp.as.account.openAccount.db.model.AccountInfoDO;
import com.yang.ifsp.as.account.openAccount.db.model.AccountSupplementTxnInfDo;
import com.yang.ifsp.as.account.openAccount.db.model.OpenAcctTxnInfoDO;
import com.yang.ifsp.as.account.openAccount.util.MakeCommon;
import com.yang.ifsp.as.account.openAccount.vo.OpenAccountReq;
import com.yang.ifsp.as.account.openAccount.vo.OpenAccountRes;
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

    @Autowired
    AccountSupplementTxnInfDoMapper accountSupplementTxnInfDoMapper;

    public int insertModel(Object...objs){
//        CreateModelUtil.createModel(srcObj, obj);
        if(objs[0] instanceof OpenAcctTxnInfoDO){
            OpenAcctTxnInfoDO openAcctTxnInfoDO = (OpenAcctTxnInfoDO)objs[0];
            OpenAccountReq req = (OpenAccountReq)objs[1];
            MakeCommon.makeOpenAcctInsertComm(openAcctTxnInfoDO,req);
            openAcctTxnInfoDO.setAccounttype(req.getAccountType());
            openAcctTxnInfoDO.setUsertype(req.getUserType());
            return openAcctTxnInfoDOMapper.insertSelective(openAcctTxnInfoDO);
        }
        if(objs[0] instanceof AccountInfoDO){
            AccountInfoDO accountInfoDO = (AccountInfoDO) objs[0];
            OpenAcctTxnInfoDO openAcctTxnInfoDO = (OpenAcctTxnInfoDO) objs[1];
            OpenAccountReq openAccountReq = (OpenAccountReq) objs[2];
            accountInfoDO.setCustname(openAcctTxnInfoDO.getCustname());
            accountInfoDO.setBindcard(openAcctTxnInfoDO.getBindcard());
            accountInfoDO.setEaccount(openAcctTxnInfoDO.getEaccount());
            accountInfoDO.setUserid(openAcctTxnInfoDO.getUserid());
            accountInfoDO.setIdno(openAcctTxnInfoDO.getIdno());
            accountInfoDO.setMoney(new BigDecimal(0));
            accountInfoDO.setAccounttype(openAcctTxnInfoDO.getAccounttype());
            accountInfoDO.setAccountstatus("S01");
            accountInfoDO.setImagestatus(openAcctTxnInfoDO.getImage()==null||openAcctTxnInfoDO.getImage().length==0?"M02":"M01");
            accountInfoDO.setLogpassword(openAccountReq.getLogPassword());
            accountInfoDO.setPaypassword(openAccountReq.getPayPassword());
            return accountInfoDOMapper.insertSelective(accountInfoDO);
        }
        if(objs[0] instanceof AccountSupplementTxnInfDo){
            AccountSupplementTxnInfDo accountSupplementTxnInfDo = (AccountSupplementTxnInfDo) objs[0];
            OpenAccountReq req = (OpenAccountReq)objs[1];
            MakeCommon.makeOpenAcctInsertComm(accountSupplementTxnInfDo,req);
            accountSupplementTxnInfDo.setDatasource("D01");
            return accountSupplementTxnInfDoMapper.insertSelective(accountSupplementTxnInfDo);
        }

        return 0;
    }

    public void updateModel(Object obj,Object srcObj) {
        if(obj instanceof OpenAcctTxnInfoDO){
            OpenAcctTxnInfoDO openAcctTxnInfoDO = (OpenAcctTxnInfoDO)obj;
            OpenAccountRes openAccountRes = (OpenAccountRes)srcObj;
            MakeCommon.makeOpenAcctUpdateComm(openAcctTxnInfoDO,openAccountRes);
            openAcctTxnInfoDO.setEaccount(openAccountRes.geteAccount());
            openAcctTxnInfoDOMapper.updateByPrimaryKeySelective(openAcctTxnInfoDO);
        }
        if(obj instanceof AccountInfoDO){
            AccountInfoDO accountInfoDO = (AccountInfoDO)obj;
            OpenAccountReq openAccountReq = (OpenAccountReq) srcObj;
            accountInfoDO.setPaypassword(openAccountReq.getPayPassword());
            accountInfoDOMapper.updateByPrimaryKeySelective(accountInfoDO);
        }
        if(obj instanceof AccountSupplementTxnInfDo){
            AccountSupplementTxnInfDo accountSupplementTxnInfDo = (AccountSupplementTxnInfDo) obj;
            OpenAccountRes openAccountRes = (OpenAccountRes)srcObj;
            MakeCommon.makeOpenAcctUpdateComm(accountSupplementTxnInfDo,openAccountRes);
            accountSupplementTxnInfDoMapper.updateByPrimaryKeySelective(accountSupplementTxnInfDo);
        }
    }
}
