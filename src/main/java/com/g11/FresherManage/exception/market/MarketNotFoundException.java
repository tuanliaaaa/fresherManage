package com.g11.FresherManage.exception.market;

import com.g11.FresherManage.exception.base.NotFoundException;

public class MarketNotFoundException extends NotFoundException {
    public MarketNotFoundException() {this.setMessage("Market NotFound");}
}

