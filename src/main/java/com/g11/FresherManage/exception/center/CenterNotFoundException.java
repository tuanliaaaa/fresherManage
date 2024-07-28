package com.g11.FresherManage.exception.center;

import com.g11.FresherManage.exception.base.NotFoundException;

public class CenterNotFoundException extends NotFoundException {
    public CenterNotFoundException() {this.setMessage("Center NotFound");}
}
