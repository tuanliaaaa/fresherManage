package com.g11.FresherManage.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import lombok.Data;

@Entity
@Data
public class Center extends Working{
    @ManyToOne
    @JoinColumn(name = "market_id")
    private Market markets;
}
