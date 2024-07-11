package com.g11.FresherManage.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Entity
public class Result {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int resultId;
    private Integer testPoint;
    private LocalDateTime createTime;
    @OneToOne
    @JoinColumn(name = "fresher_id")
    private Account fresher;
    @OneToOne
    @JoinColumn(name = "mentor_id")
    private Account mentor;
}
