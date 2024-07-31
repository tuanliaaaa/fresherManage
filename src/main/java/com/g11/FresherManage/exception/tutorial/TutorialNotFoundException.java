package com.g11.FresherManage.exception.tutorial;

import com.g11.FresherManage.exception.base.NotFoundException;

public class TutorialNotFoundException extends NotFoundException {
    public TutorialNotFoundException()
    {
        setMessage("Tutorial Not Found");
        setCode("com.g11.FresherManage.exception.tutorial.TutorialNotFoundException");
    }
}
