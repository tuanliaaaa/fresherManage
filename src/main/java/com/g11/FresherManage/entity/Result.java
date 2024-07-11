package com.g11.FresherManage.entity;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
public class Result {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int resultId;
    private Integer test1;
    private Integer test2;
    private Integer test3;
    @OneToOne
    @JoinColumn(name = "fresher_id")
    private Account fresher;
    @OneToOne
    @JoinColumn(name = "mentor_id")
    private Account mentor;
}
