package com.yang.ifsp.as.account.openAccount.api;

import com.yang.ifsp.as.account.openAccount.vo.OpenAccountReq;
import com.yang.ifsp.as.account.openAccount.vo.OpenAccountRes;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

public interface OpenAccountService {
    @PostMapping(path = "/openAccount-service/doOpenAccount")
    public @ResponseBody
    OpenAccountRes doOpenAccount(@RequestBody OpenAccountReq openAccountReq);
}
