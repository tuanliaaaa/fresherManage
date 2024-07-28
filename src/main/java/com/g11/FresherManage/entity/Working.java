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
    public Working(String workingName, String workingType, String workingStatus, Working market)
    {
        this.workingName = workingName;
        this.workingType = workingType;
        this.workingStatus = workingStatus;
        this.market = market;
    }
}
