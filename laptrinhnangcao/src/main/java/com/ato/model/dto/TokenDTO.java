package com.ato.model.dto;

import com.ato.model.bo.Token;
import lombok.Data;

import javax.validation.constraints.Size;
import java.sql.Timestamp;

@Data
public class TokenDTO {
    private Long id;
    @Size(max = 60, message = "{common.token.usertoken} {common.maxLength}")
    private String usertoken;
    @Size(max = 60, message = "{common.token.code} {common.maxLength}")
    private String codeToken;
    private Long status;
    private Timestamp exp;

    public Token toModel() {
        Token token = new Token();
        token.setId(this.id);
        token.setUsertoken(this.usertoken);
        token.setCodeToken( this.codeToken );
        token.setStatus(this.status);
        token.setExp(this.exp);
        return token;
    }
}
