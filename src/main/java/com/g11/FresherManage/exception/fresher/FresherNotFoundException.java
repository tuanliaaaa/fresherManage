package com.g11.FresherManage.exception.fresher;

import com.g11.FresherManage.exception.base.NotFoundException;

public class FresherNotFoundException extends NotFoundException {
    public FresherNotFoundException() {this.setMessage("Fresher NotFound");}
}
