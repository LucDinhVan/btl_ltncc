package com.ato.model.bo;

import lombok.Data;

import javax.persistence.*;
import java.util.Objects;

@Data
@Entity
@Table(name = "product_size_color", schema = "ShopQuanAo", catalog = "")
public class ProductSizeColor {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Basic
    @Column(name = "id_product")
    private Long idProduct;

    @Basic
    @Column(name = "id_size")
    private Long idSize;

    @Basic
    @Column(name = "id_color")
    private Long idColor;

    @Basic
    @Column(name = "amount")
    private Long amount;

}
