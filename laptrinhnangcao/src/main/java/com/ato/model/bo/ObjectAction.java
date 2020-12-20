package com.ato.model.bo;

import lombok.Data;

import javax.persistence.*;
import java.sql.Timestamp;

@Entity
@Table(name = "object_action", schema = "ShopQuanAo", catalog = "")
@Data
public class ObjectAction {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;
    @Basic
    @Column(name = "id_objects")
    private Long idObjects;
    @Basic
    @Column(name = "id_action")
    private Long idAction;
    @Basic
    @Column(name = "update_time")
    private Timestamp updateTime;
}
