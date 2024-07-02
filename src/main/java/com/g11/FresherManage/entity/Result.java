package com.g11.FresherManage.entity;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
public class Result {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int resultId;
    @OneToOne
    @JoinColumn(name = "tested_id")
    private Tested tested;
    @OneToOne
    @JoinColumn(name = "mentor_id")
    private Mentor mentor;
}
