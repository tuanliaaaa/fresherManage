package com.g11.FresherManage.exception.result;

import com.g11.FresherManage.exception.base.NotFoundException;

public class ResultNotFoundException extends NotFoundException {
    public ResultNotFoundException() {
       setMessage("ResultNotFoundException");
       setCode("com.g11.FresherManage.result.resultNotFoundException");
    }
}
