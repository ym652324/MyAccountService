package com.yang.ifsp.as.account.openAccount.constants;

public enum  AccountEnums {

    //报文头响应码
    VALIDATE_ERROR("10001","报文字段不符合规范"),

    REQUID_CHECK_ERROR("1002","请求流水号重复"),

    SYSTEM_INNRT_ERROR("1003","系统内部错误失败"),

    TRAN_CODE_ERROR("1004","交易码错误"),

    FORMAT_ERROR("10005","报文字段格式错误"),

    OPENACCT_SUCCESS("20001","开户成功"),



    ;



    private String respCode;
    private String respMsg;

    AccountEnums(String respCode, String respMsg) {
        this.respCode = respCode;
        this.respMsg = respMsg;
    }

    public String getRespCode() {
        return respCode;
    }

    public void setRespCode(String respCode) {
        this.respCode = respCode;
    }

    public String getRespMsg() {
        return respMsg;
    }

    public void setRespMsg(String respMsg) {
        this.respMsg = respMsg;
    }

    //通过响应码获取枚举类
    public AccountEnums getEnumsByRespCode(String respCode){
        AccountEnums accountEnums = null;
        for(AccountEnums accountEnums1:values()){
            if(respCode.equals(accountEnums1.respCode)){
                accountEnums = accountEnums1;
            }
        }
        return accountEnums;
    }
}
