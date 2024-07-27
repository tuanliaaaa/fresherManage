package com.g11.FresherManage.exception.account;

import com.g11.FresherManage.exception.base.NotFoundException;

public class UsernameNotFoundException extends NotFoundException {
    public UsernameNotFoundException(){
        setMessage("User not found");
        setCode("com.g11.FresherManage.exception.account.UsernameNotFoundException");
    }
}