package com.yang.ifsp.as.account.openAccount.bo.impl;

import com.yang.ifsp.as.account.openAccount.bo.OpenAcctBo;
import com.yang.ifsp.as.account.openAccount.constants.AccountEnums;
import com.yang.ifsp.as.account.openAccount.db.dao.AccountInfoDOMapper;
import com.yang.ifsp.as.account.openAccount.db.dao.UserInfoDOMapper;
import com.yang.ifsp.as.account.openAccount.db.model.AccountInfoDO;
import com.yang.ifsp.as.account.openAccount.db.model.OpenAcctTxnInfoDO;
import com.yang.ifsp.as.account.openAccount.db.model.UserInfoDO;
import com.yang.ifsp.as.account.openAccount.processor.DbProcessor;
import com.yang.ifsp.as.account.openAccount.util.CheckUtil;
import com.yang.ifsp.as.account.openAccount.util.MakeUidUtil;
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

    @Autowired
    AccountInfoDOMapper accountInfoDOMapper;

    @Autowired
    UserInfoDOMapper userInfoDOMapper;

    @Override
    public OpenAccountRes process(OpenAccountReq req) {
        OpenAccountRes res = new OpenAccountRes();
        OpenAcctTxnInfoDO openAcctTxnInfoDO = new OpenAcctTxnInfoDO();

        dbProcessor.insertModel(req,openAcctTxnInfoDO);
        logger.info("请求报文入库成功");
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
//        String name = req.getCustName();
//        String idNo = req.getIdNo();
//        String mobile = req.getMobilePhone();
//        String bindCard = req.getBindCard();
        String reqUid = req.getReqUID();
        String eAccount = req.geteAccount();
        String idNo = req.getIdNo();
        String custName = req.getCustName();
        String mobile = req.getMobilePhone();
        String userType = req.getUserType();
        String tranType = req.getTranType();
        String tranCode = req.getTranCode();


        //校验交易码
        if(StringUtils.isEmpty(tranCode)){
            logger.error("开户接口，交易码错误！");
            res.setRespCode(AccountEnums.VALIDATE_ERROR.getRespCode());
            res.setRespMsg(AccountEnums.VALIDATE_ERROR.getRespMsg());
            openAcctTxnInfoDO.setLastoperate("开户接口交易码校验");
            dbProcessor.updateModel(openAcctTxnInfoDO,res);
            return res;
        }



        //检查字段合法性
        HashMap<String,String> checkMap = new HashMap<>();
        checkMap.put("custName",req.getCustName());
        checkMap.put("idNo",req.getIdNo());
        checkMap.put("mobilePhone",req.getMobilePhone());
        checkMap.put("bindCard",req.getBindCard());
        HashMap<String,String> resultMap = CheckUtil.check(checkMap);
        if("false".equals(resultMap.get("0"))){
            logger.error("请求报文[{"+resultMap.get("1")+"]]字段校验失败");
            res.setRespCode(AccountEnums.FORMAT_ERROR.getRespCode());
            res.setRespMsg(AccountEnums.FORMAT_ERROR.getRespMsg());
            openAcctTxnInfoDO.setLastoperate("请求报文字段合法性校验");
            dbProcessor.updateModel(openAcctTxnInfoDO,res);
            return res;
        }


        if("T0001".equals(tranType)){

            logger.info("进入开户接口流程,请求流水号："+reqUid);

            //校验用户类型
            if(StringUtils.isEmpty(userType)){
                logger.error("开户接口，用户类型不能为空！");
                res.setRespCode(AccountEnums.VALIDATE_ERROR.getRespCode());
                res.setRespMsg(AccountEnums.VALIDATE_ERROR.getRespMsg());
                openAcctTxnInfoDO.setLastoperate("开户接口用户类型校验");
                dbProcessor.updateModel(openAcctTxnInfoDO,res);
                return res;
            }


            //校验手机号
            if(StringUtils.isEmpty(mobile)){
                logger.error("开户接口，手机号不能为空！");
                res.setRespCode(AccountEnums.VALIDATE_ERROR.getRespCode());
                res.setRespMsg(AccountEnums.VALIDATE_ERROR.getRespMsg());
                openAcctTxnInfoDO.setLastoperate("开户接口手机号校验");
                dbProcessor.updateModel(openAcctTxnInfoDO,res);
                return res;
            }


            //查库，校验开户类型，用户类型及开户数量
            String accountType = req.getAccountType();
            if(StringUtils.isEmpty(userType)||StringUtils.isEmpty(accountType)||!("1".equals(accountType)||"2".equals(accountType)||"3".equals(accountType))){
                logger.error("开户接口开户类型或用户类型不能为空，开户类型不能为1、2、3之外的值");
                res.setRespCode(AccountEnums.VALIDATE_ERROR.getRespCode());
                res.setRespMsg(AccountEnums.VALIDATE_ERROR.getRespMsg());
                openAcctTxnInfoDO.setLastoperate("开户接口开户类型及数量校验");
                dbProcessor.updateModel(openAcctTxnInfoDO,res);
                return res;
            }

            ArrayList<AccountInfoDO> accounts = accountInfoDOMapper.selectByIdNo(idNo);
            int acct1 = 0;
            int acct2 = 0;
            int acct3 =0;
            for(AccountInfoDO acc : accounts){
                if("1".equals(openAcctTxnInfoDO.getAccounttype())){
                    acct1++;
                }else if("2".equals(openAcctTxnInfoDO.getAccounttype())){
                    acct2++;
                }else{
                    acct3++;
                }
            }
            if(("1".equals(accountType) && acct1>=1)||("2".equals(accountType) && acct2>=2)||("1".equals(accountType) && acct3>=2)){
                logger.error("开户接口开户类型超过限定数目");
                res.setRespCode(AccountEnums.VALIDATE_ERROR.getRespCode());
                res.setRespMsg(AccountEnums.VALIDATE_ERROR.getRespMsg());
                openAcctTxnInfoDO.setLastoperate("开户接口开户数目校验");
                dbProcessor.updateModel(openAcctTxnInfoDO,res);
                return res;
            }
            //开过户的用户，获取用户号
            if(!accounts.isEmpty()){
                openAcctTxnInfoDO.setUserid(userInfoDOMapper.selectByIdNo(idNo).getUserid());
            }else{
                //未开户用户，生成用户号，插入用户表
                StringBuilder userId = new StringBuilder("652324");
                userId = userId.append(MakeUidUtil.getOrderNumber());
                openAcctTxnInfoDO.setUserid(userId.toString());
                UserInfoDO userInfoDO = new UserInfoDO();
                userInfoDO.setCustname(custName);
                userInfoDO.setIdno(idNo);
                userInfoDO.setImage(req.getImage());
                userInfoDO.setUsertype(userType);
                userInfoDO.setMobilephone(mobile);
                userInfoDO.setUserid(userId.toString());
                userInfoDOMapper.insert(userInfoDO);

            }
            //生成电子账号
            StringBuilder account = new StringBuilder("666666");
            account = account.append(MakeUidUtil.getOrderNumber());
            openAcctTxnInfoDO.setLastoperate("生成电子账号");
            res.seteAccount(account.toString());
            res.setRespCode(AccountEnums.OPENACCT_SUCCESS.getRespCode());
            res.setRespMsg(AccountEnums.OPENACCT_SUCCESS.getRespCode());
            dbProcessor.updateModel(openAcctTxnInfoDO,res);

            return res;






        }else if("T0002".equals(tranType)){
            logger.info("进入补录接口流程，请求流水号："+reqUid);
            if(StringUtils.isEmpty(eAccount)){
                logger.error("补录接口电子账号不能为空");
                res.setRespCode(AccountEnums.VALIDATE_ERROR.getRespCode());
                res.setRespMsg(AccountEnums.VALIDATE_ERROR.getRespMsg());
                openAcctTxnInfoDO.setLastoperate("补录接口电子账号非空校验");
//                openAcctTxnInfoDO.setRespcode(res.getRespCode());
//                openAcctTxnInfoDO.setRespmsg(res.getRespMsg());
                dbProcessor.updateModel(openAcctTxnInfoDO,res);
                return res;
            }



        }else{
            logger.error("无效的交易码:"+tranType);
            res.setRespCode(AccountEnums.TRAN_CODE_ERROR.getRespCode());
            res.setRespMsg(AccountEnums.TRAN_CODE_ERROR.getRespMsg());
            openAcctTxnInfoDO.setLastoperate("请求报文入库");
            dbProcessor.updateModel(openAcctTxnInfoDO,res);
        }

        dbProcessor.updateModel(openAcctTxnInfoDO,res);
        return res;
    }
}
