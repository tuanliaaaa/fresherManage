package com.g11.FresherManage.exception.conservation;

import com.g11.FresherManage.exception.base.UnauthorizedException;

public class ConservationNotOfUser extends UnauthorizedException {
    public ConservationNotOfUser() {
        this.setMessage("Conservation Not Of User");
    }
}
