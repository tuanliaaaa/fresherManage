package com.g11.FresherManage.exception.base;

import static com.g11.FresherManage.constants.FresherManagerConstants.StatusException.UNAUTHORIZED;
public class UnauthorizedException extends BaseException{
    public UnauthorizedException() {
        setCode("com.11.FresherManage.exception.base.UnauthorizedException");
        setStatus(UNAUTHORIZED);
        setMessage("UNAUTHORIZED");
    }
}
