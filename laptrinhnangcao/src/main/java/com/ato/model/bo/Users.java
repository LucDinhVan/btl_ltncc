package com.ato.model.bo;

import com.ato.model.baseModel.Phone;
import com.ato.model.dto.UsersDTO;
import lombok.Data;

import javax.persistence.*;
import java.sql.Timestamp;

@Entity
@Table(name = "users")
@Data
public class Users {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Basic
    @Column(name = "name")
    private String name;

    @Basic
    @Column(name = "fullname")
    private String fullname;

    @Basic
    @Column(name = "pass")
    private String pass;

    @Basic
    @Column(name = "part_image")
    private String partImage;

    @Basic
    @Column(name = "or_birth_user")
    private Timestamp orBirthUser;

    @Basic
    @Column(name = "mail")
    private String mail;

    @Basic
    @Column(name = "phone")
    @Phone(message = "Phone Number is invalid")
    private String phone;

    @Basic
    @Column(name = "status")
    private Long status;

    @Basic
    @Column(name = "reset_key")
    private String resetKey;

    @Basic
    @Column(name = "reset_date")
    private Timestamp resetDate;

    @Basic
    @Column(name = "creator")
    private String creator;

    @Basic
    @Column(name = "creation_time")
    private Timestamp creationTime;
    
    public UsersDTO toDto() {
        UsersDTO usersDTO = new UsersDTO();
        usersDTO.setId(this.id);
        usersDTO.setName(this.name);
        usersDTO.setFullname(this.fullname);
        usersDTO.setPass(this.pass);
        usersDTO.setPartImage(this.partImage);
        usersDTO.setOrBirthUser(this.orBirthUser);
        usersDTO.setMail(this.mail);
        usersDTO.setPhone(this.phone);
        usersDTO.setStatus(this.status);
        usersDTO.setResetKey(this.resetKey);
        usersDTO.setResetDate(this.resetDate);
        usersDTO.setCreator(this.creator);
        usersDTO.setCreationTime(this.creationTime);
        return usersDTO;
    }
}
