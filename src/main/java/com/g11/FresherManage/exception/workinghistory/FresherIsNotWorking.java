package com.g11.FresherManage.exception.workinghistory;

import com.g11.FresherManage.exception.base.BadRequestException;

public class FresherIsNotWorking extends BadRequestException {
    public FresherIsNotWorking() {
        this.setMessage("Fresher is Working");
        this.setCode("com.g11.FresherManage.exception.workinghistory.FresherIsWorking");
    }
}
