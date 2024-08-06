package com.g11.FresherManage.exception.workinghistory;

import com.g11.FresherManage.exception.base.BadRequestException;

public class FresherIsWorking extends BadRequestException {
    public FresherIsWorking() {
        this.setMessage("Fresher is Working");
        this.setCode("com.g11.FresherManage.exception.workinghistory.FresherIsWorking");
    }
}
