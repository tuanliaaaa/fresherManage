package com.g11.FresherManage.exception.workinghistory;

import com.g11.FresherManage.exception.base.BadRequestException;

public class EmployeeNotWorkinWorkingException extends BadRequestException {
    public EmployeeNotWorkinWorkingException() {
        this.setMessage("Employee is not working in working");
    }
}
