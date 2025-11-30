package com.back.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(name = "resultPoints")
@Data
@NoArgsConstructor
public class Result {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "result_seq")
    @SequenceGenerator(name = "result_seq", sequenceName = "RESULT_SEQ", allocationSize = 1)
    private Long id;

    @Column(nullable = false)
    private Double x;

    @Column(nullable = false)
    private Double y;

    @Column(nullable = false)
    private Double r;

    @Column(name = "is_check", nullable = false)
    private Boolean checkResult;

    @Column(name = "timeNow", nullable = false)
    private LocalDateTime clickTime = LocalDateTime.now();

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    public Result(Double x, Double y, Double r, Boolean checkResult, User user) {
        this.x = x;
        this.y = y;
        this.r = r;
        this.checkResult = checkResult;
        this.user = user;
    }
}