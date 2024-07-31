package com.g11.FresherManage.exception.tutorial;

import com.g11.FresherManage.exception.base.UnauthorizedException;

public class MentorNotTutorialThisFresherException extends UnauthorizedException {
    public MentorNotTutorialThisFresherException() {
        this.setMessage("Mentor Not Tutorial This Fresher");
    }
}
