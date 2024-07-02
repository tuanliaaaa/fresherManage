package com.g11.FresherManage.entity;

import jakarta.persistence.*;
import lombok.Data;
import org.springframework.boot.autoconfigure.amqp.RabbitConnectionDetails;

@Data
@Entity
public class Employee {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int employeeId;
    private String firstName;
    private String lastName;
    private String email;
    private String phone;
    private String status;
    @OneToOne
    @JoinColumn(name = "account_id")
    private Account account;
    @OneToOne
    @JoinColumn(name = "working_id")
    private  Working working;

}
