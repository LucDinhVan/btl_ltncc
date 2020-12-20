package com.ato.model.bo;

import lombok.Data;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Objects;

@Entity
@Data
@Table(name = "image_link", schema = "ShopQuanAo", catalog = "")
public class ImageLink {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Basic
    @Column(name = "id_product")
    private Long idProduct;

    @Basic
    @Column(name = "name")
    private String name;

    @Basic
    @Column(name = "status")
    private Integer status;

    @Basic
    @Column(name = "update_time")
    private Timestamp updateTime;

    @Basic
    @Column(name = "image_link")
    private String imageLink;

}
