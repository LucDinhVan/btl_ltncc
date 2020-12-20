package com.ato.model.bo;

import lombok.Data;

import javax.persistence.*;
import java.sql.Timestamp;

@Entity
@Table(name = "role_object", schema = "ShopQuanAo")
@Data
public class RoleObject {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;
    @Basic
    @Column(name = "id_role")
    private Long idRole;
    @Basic
    @Column(name = "id_object")
    private Long idObject;

    @Basic
    @Column(name = "id_action")
    private Long idAction;

    @Basic
    @Column(name = "update_time")
    private Timestamp updateTime;

}
