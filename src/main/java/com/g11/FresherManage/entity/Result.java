package com.g11.FresherManage.entity;

import com.g11.FresherManage.dto.request.ResultRequest;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Entity
@NoArgsConstructor
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
    public Result(Account mentor, Account fresher, Integer testPoint) {
        this.createTime=LocalDateTime.now();
        this.mentor=mentor;
        this.fresher=fresher;
        this.testPoint=testPoint;
    }
}
