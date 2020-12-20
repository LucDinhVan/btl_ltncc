package com.ato.model.dto;

import com.ato.model.baseModel.AtoBaseDTO;
import lombok.Data;
import org.springframework.http.HttpHeaders;

import java.util.List;

@Data
public class LoginDTO extends AtoBaseDTO {
    private HttpHeaders httpHeaders;
    private UsersDTO customUserDetails;
    private List<TreeDTO> listObjects;
    private String path;
}
