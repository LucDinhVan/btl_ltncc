package com.ato.model.bo;

import lombok.Data;

import javax.persistence.*;
import java.sql.Timestamp;

@Entity
@Table(name = "sizes", schema = "ShopQuanAo")
@Data
public class Sizes {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;
    @Basic
    @Column(name = "code")
    private String code;
    @Basic
    @Column(name = "name")
    private String name;
    @Basic
    @Column(name = "status")
    private Long status;
    @Basic
    @Column(name = "description")
    private String description;
    @Basic
    @Column(name = "update_time")
    private Timestamp updateTime;
}
