package com.ato.model.bo;

import javax.persistence.*;
import java.sql.Timestamp;

@Entity
public class Action {
    private Long id;
    private String name;
    private String code;
    private String description;
    private Long status;
    private Timestamp updateTime;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Basic
    @Column(name = "name")
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Basic
    @Column(name = "code")
    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    @Basic
    @Column(name = "description")
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Basic
    @Column(name = "status")
    public Long getStatus() {
        return status;
    }

    public void setStatus(Long status) {
        this.status = status;
    }

    @Basic
    @Column(name = "update_time")
    public Timestamp getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Timestamp updateTime) {
        this.updateTime = updateTime;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Action action = (Action) o;
        return id == action.id &&
                status == action.status &&
                java.util.Objects.equals(name, action.name) &&
                java.util.Objects.equals(code, action.code) &&
                java.util.Objects.equals(description, action.description) &&
                java.util.Objects.equals(updateTime, action.updateTime);
    }

    @Override
    public int hashCode() {
        return java.util.Objects.hash(id, name, code, description, status, updateTime);
    }
}
