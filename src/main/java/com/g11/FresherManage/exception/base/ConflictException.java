package com.g11.FresherManage.exception.base;


import static com.g11.FresherManage.constants.FresherManagerConstants.StatusException.*;

public class ConflictException extends BaseException {
  public ConflictException(String id, String objectName) {
    setStatus(CONFLICT);
    setCode("com.g11.FresherManage.exception.base.ConflictException");
    addParam("id", id);
    addParam("objectName", objectName);
  }

  public ConflictException(){
    setStatus(CONFLICT);
    setCode("com.g11.FresherManage.exception.base.ConflictException");
  }
}
