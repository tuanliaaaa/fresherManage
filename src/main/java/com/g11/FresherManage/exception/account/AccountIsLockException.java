package com.g11.FresherManage.exception.account;

import com.g11.FresherManage.exception.base.AccessDeniedException;
import org.springframework.data.repository.query.Param;

import java.util.HashMap;
import java.util.Map;

import static com.g11.FresherManage.constants.FresherManagerConstants.StatusException.FORBIDDEN;

public class AccountIsLockException extends AccessDeniedException {
    public AccountIsLockException() {
        setCode("com.11.FresherManage.exception.account.AccountIsLockException");
        setStatus(FORBIDDEN);
        Map<String,String> params = new HashMap<String,String>();
        params.put("account","account is locked");
        setParams(params);
    }
}
