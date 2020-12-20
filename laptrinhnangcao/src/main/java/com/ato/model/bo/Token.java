package com.ato.model.bo;

import javax.persistence.*;
import java.sql.Timestamp;

@Entity
public class Token {
    private Long id;
    private String usertoken;
    private String codeToken;
    private Long status;
    private Timestamp exp;

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
    @Column(name = "usertoken")
    public String getUsertoken() {
        return usertoken;
    }

    public void setUsertoken(String usertoken) {
        this.usertoken = usertoken;
    }

    @Basic
    @Column(name = "codeToken")
    public String getCodeToken() {
        return codeToken;
    }

    public void setCodeToken(String codeToken) {
        this.codeToken = codeToken;
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
    @Column(name = "exp")
    public Timestamp getExp() {
        return exp;
    }

    public void setExp(Timestamp exp) {
        this.exp = exp;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Token token = (Token) o;
        return java.util.Objects.equals(id, token.id) &&
                java.util.Objects.equals(usertoken, token.usertoken) &&
                java.util.Objects.equals(codeToken, token.codeToken) &&
                java.util.Objects.equals(status, token.status) &&
                java.util.Objects.equals(exp, token.exp);
    }

    @Override
    public int hashCode() {
        return java.util.Objects.hash(id, usertoken, codeToken, status, exp);
    }
}
