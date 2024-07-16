package com.g11.FresherManage.entity;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
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
}
