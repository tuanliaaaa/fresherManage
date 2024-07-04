package com.g11.FresherManage.exception.base;

import static com.g11.FresherManage.constants.FresherManagerConstants.StatusException.FORBIDDEN;

    public class AccessDeniedException extends BaseException{
    public AccessDeniedException() {
        setCode("com.11.FresherManage.exception.base.AccessDeniedException");
        setStatus(FORBIDDEN);
        setMessage("Access Denied");
    }
}
