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
import com.yang.ifsp.as.account.openAccount.util.MakeMessage;
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
        MakeMessage.makeOpenAcctCommonRes(req,res);
        OpenAcctTxnInfoDO openAcctTxnInfoDO = new OpenAcctTxnInfoDO();

        dbProcessor.insertModel(openAcctTxnInfoDO,req);
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
        String bindCard = req.getBindCard();


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
                res.setRespCode(AccountEnums.OPENACCT_SUM_ERROR.getRespCode());
                res.setRespMsg(AccountEnums.OPENACCT_SUM_ERROR.getRespMsg());
                openAcctTxnInfoDO.setLastoperate("开户接口开户数目校验");
                dbProcessor.updateModel(openAcctTxnInfoDO,res);
                return res;
            }
            //开过户的用户，获取用户号

            if(!accounts.isEmpty()){
                openAcctTxnInfoDO.setUserid(userInfoDOMapper.selectByIdNo(idNo).getUserid());
            }else{
                //未开户用户，生成用户号，插入用户表
                StringBuilder userId = new StringBuilder("6523");
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
                logger.info("生成客户号："+userId);
                openAcctTxnInfoDO.setUserid(userId.toString());

            }
            //生成电子账号
            StringBuilder account = new StringBuilder("6666");
            account = account.append(MakeUidUtil.getOrderNumber());
            logger.info("生成电子账号："+account);
            openAcctTxnInfoDO.setLastoperate("生成电子账号");
            res.seteAccount(account.toString());
            res.setRespCode(AccountEnums.OPENACCT_SUCCESS.getRespCode());
            res.setRespMsg(AccountEnums.OPENACCT_SUCCESS.getRespMsg());
            dbProcessor.updateModel(openAcctTxnInfoDO,res);
            AccountInfoDO accountInfoDO = new AccountInfoDO();
            dbProcessor.insertModel(accountInfoDO,openAcctTxnInfoDO,req);

            logger.info("开户成功！电子账号为："+account);

            return res;



        }else if("T0002".equals(tranType)){
            logger.info("进入补录接口流程，请求流水号："+reqUid);
            if(StringUtils.isEmpty(eAccount)){
                logger.error("补录接口电子账号不能为空");
                res.setRespCode(AccountEnums.VALIDATE_ERROR.getRespCode());
                res.setRespMsg(AccountEnums.VALIDATE_ERROR.getRespMsg());
                openAcctTxnInfoDO.setLastoperate("补录接口电子账号非空校验");
                dbProcessor.updateModel(openAcctTxnInfoDO,res);
                return res;
            }

            byte[] image = req.getImage();
            if(StringUtils.isEmpty(custName) && StringUtils.isEmpty(bindCard) && StringUtils.isEmpty(mobile) &&
                    (image == null || image.length == 0) && StringUtils.isEmpty(req.getPayPassword()) &&
                    StringUtils.isEmpty(req.getLogPassword())
            ){
                logger.error("补录接口姓名、绑定卡、手机号、影像件、支付密码、登陆密码至少应有一个不为空");
                res.setRespCode(AccountEnums.VALIDATE_ERROR.getRespCode());
                res.setRespMsg(AccountEnums.VALIDATE_ERROR.getRespMsg());
                openAcctTxnInfoDO.setLastoperate("补录接口必输校验");
                dbProcessor.updateModel(openAcctTxnInfoDO,res);
                return res;
            }

            AccountInfoDO accountInfoDO = accountInfoDOMapper.selectByPrimaryKey(eAccount);

            if(!idNo.equals(accountInfoDO.getIdno())){
                logger.error("上传身份证号与原证件号不符");
                res.setRespCode(AccountEnums.NOT_EQUAL_ERROR.getRespCode());
                res.setRespMsg(AccountEnums.NOT_EQUAL_ERROR.getRespMsg());
                openAcctTxnInfoDO.setLastoperate("身份证一致性校验");
                dbProcessor.updateModel(openAcctTxnInfoDO,res);
                return res;
            }

            UserInfoDO userInfoDO = userInfoDOMapper.selectByIdNo(idNo);

            if(!StringUtils.isEmpty(custName)){
                accountInfoDO.setCustname(custName);
                userInfoDO.setCustname(custName);
            }
            if(!StringUtils.isEmpty(bindCard)){
                accountInfoDO.setBindcard(bindCard);
            }
            if(!StringUtils.isEmpty(mobile)){
                userInfoDO.setMobilephone(mobile);
            }
            if(!(image == null || image.length == 0)){
                userInfoDO.setImage(image);
            }
            if(!StringUtils.isEmpty(req.getLogPassword())) {
                accountInfoDO.setLogpassword(req.getLogPassword());
            }
            if(!StringUtils.isEmpty(req.getPayPassword())){
                accountInfoDO.setPaypassword(req.getPayPassword());
            }
            userInfoDOMapper.updateByPrimaryKeySelective(userInfoDO);
            accountInfoDOMapper.updateByPrimaryKeySelective(accountInfoDO);

            res.seteAccount(req.geteAccount());
            res.setRespCode(AccountEnums.ADD_INFO_SUCCESS.getRespCode());
            res.setRespMsg(AccountEnums.ADD_INFO_SUCCESS.getRespMsg());
            openAcctTxnInfoDO.setLastoperate("补录完成");
            logger.info("补录接口补录完成！流水号："+reqUid);


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
