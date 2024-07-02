package com.g11.FresherManage.exception.base;

public class AccessDeniedException extends BaseException{
    public AccessDeniedException() {
        setCode("com.ncsgroup.profiling.exception.base.AccessDeniedException");
        setStatus(403);
    }
}
