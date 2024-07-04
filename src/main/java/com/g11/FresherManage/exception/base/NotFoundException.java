package com.g11.FresherManage.exception.base;


import static com.g11.FresherManage.constants.FresherManagerConstants.StatusException.*;

public class NotFoundException extends BaseException {
  public NotFoundException(String id, String objectName) {
    setCode("com.g11.FresherManage.exception.base.NotFoundException");
    setStatus(NOT_FOUND);
    addParam("id", id);
    addParam("objectName", objectName);
  }

  public NotFoundException() {
    setCode("com.g11.FresherManage.exception.base.NotFoundException");
    setStatus(NOT_FOUND);
  }
}
