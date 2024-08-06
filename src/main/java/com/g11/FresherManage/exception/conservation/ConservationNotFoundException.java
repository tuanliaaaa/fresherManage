package com.g11.FresherManage.exception.conservation;
import com.g11.FresherManage.exception.base.NotFoundException;

public class ConservationNotFoundException extends NotFoundException {
    public ConservationNotFoundException(){
        setMessage("Conservation not found");
        setCode("com.g11.FresherManage.exception.conservation.ConservationNotFoundException");
    }
}
