package com.ato.model.bo;

import lombok.Data;

import javax.persistence.*;
import java.sql.Timestamp;

@Entity
@Table(name = "user_role", schema = "ShopQuanAo")
@Data
public class UserRole {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Basic
    @Column(name = "id_user")
    private Long idUser;

    @Basic
    @Column(name = "id_role")
    private Long idRole;

    @Basic
    @Column(name = "update_time")
    private Timestamp updateTime;

}
