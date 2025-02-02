package com.g11.FresherManage.entity;

import com.g11.FresherManage.dto.request.ResultRequest;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
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
    private Double testPoint;
    private LocalDateTime dueDate;
    private LocalDateTime startDate;
    private LocalDateTime endDate;
    private Integer numberTest;
    private Boolean status;
    @ManyToOne
    @JoinColumn(name = "test_id")
    private Test test;
    @ManyToOne
    @JoinColumn(name = "fresher_id")
    private Account fresher;
    @ManyToOne
    @JoinColumn(name = "mentor_id")
    private Account mentor;
    public Result(Account mentor, Account fresher, Double testPoint) {
        this.mentor=mentor;
        this.fresher=fresher;
        this.testPoint=testPoint;
    }
}
