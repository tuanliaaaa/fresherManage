package com.g11.FresherManage.entity;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
public class Tested {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int testedId;
    @OneToOne
    @JoinColumn(name = "test_id")
    private Test test;

}
