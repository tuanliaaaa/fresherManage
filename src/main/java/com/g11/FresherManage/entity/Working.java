package com.g11.FresherManage.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@NoArgsConstructor
public class Working {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int workingId;
    private String workingName;
    private String workingType;
    private String workingStatus;
    @ManyToOne
    @JoinColumn(name = "market_id")
    private Working market;
    public Working(String workingName, Working market)
    {
        this.workingName = workingName;
        this.workingType = "CENTER";
        this.workingStatus = "active";
        this.market = market;
    }
    public Working(String workingName)
    {
        this.workingName = workingName;
        this.workingType = "MARKET";
        this.workingStatus = "active";
    }
}
