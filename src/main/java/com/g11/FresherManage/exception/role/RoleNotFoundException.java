package com.g11.FresherManage.exception.role;

import com.g11.FresherManage.exception.base.NotFoundException;

public class RoleNotFoundException  extends NotFoundException {
    public RoleNotFoundException(){
        setMessage("User not found");
        setCode("com.g11.FresherManage.exception.role.RoleNotFoundException");
    }
}
