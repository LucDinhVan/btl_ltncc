package com.ato.model.bo;

import lombok.Data;

import javax.persistence.*;
import java.math.BigDecimal;
import java.sql.Timestamp;

@Data
@Table(name = "bill")
@Entity
public class Bill {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Basic
    @Column(name = "id_user")
    private Long idUser;

    @Basic
    @Column(name = "heavy_buying")
    private Integer heavyBuying;

    @Basic
    @Column(name = "total_buy")
    private Long totalBuy;

    @Basic
    @Column(name = "discount")
    private Long discount;

    @Basic
    @Column(name = "counter")
    private String counter;

    @Basic
    @Column(name = "payment")
    private Long payment;

    @Basic
    @Column(name = "receiving_address")
    private String receivingAddress;

    @Basic
    @Column(name = "payment_method")
    private String paymentMethod;

    @Basic
    @Column(name = "buying_date")
    private Timestamp buyingDate;


}
