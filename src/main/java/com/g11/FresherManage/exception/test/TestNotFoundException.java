package com.g11.FresherManage.exception.test;

import com.g11.FresherManage.exception.base.NotFoundException;


public class TestNotFoundException extends NotFoundException {
    public TestNotFoundException(){
        setMessage("Test not found");
        setCode("com.g11.FresherManage.exception.test.TestNotFoundException");
    }
}