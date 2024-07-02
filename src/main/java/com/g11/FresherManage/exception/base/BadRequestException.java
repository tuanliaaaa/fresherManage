package com.g11.FresherManage.exception.base;


import static com.g11.FresherManage.constants.FresherManagerConstants.StatusException.*;

public class BadRequestException extends BaseException {
  public BadRequestException() {
    setCode("com.ncsgroup.profiling.exception.base.BadRequestException");
    setStatus(BAD_REQUEST);
  }
}
