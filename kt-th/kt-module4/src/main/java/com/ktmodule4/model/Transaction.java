package com.ktmodule4.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Entity
@Table(name = "giao_dich")
@Getter
@Setter
public class Transaction {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
    @NotBlank(message="Mã giao dịch không được trống")
    @Pattern(regexp="MGD-\\d{4}", message="Mã giao dịch phải đúng định dạng MGD-XXXX")
    private String transactionCode;

    @ManyToOne
    @JoinColumn(name = "customer_id")
    private Customer customer;
    @NotNull
    @FutureOrPresent(message="Ngày giao dịch phải lớn hơn thời gian hiện tại")
    private LocalDate date;

    private String type;
    @NotNull
    @Min(value=500000, message="Đơn giá phải lớn hơn 500.000")
    private Double price;
    @NotNull
    @Min(value=20, message="Diện tích phải lớn hơn 20 m2")
    private Double area;
}
