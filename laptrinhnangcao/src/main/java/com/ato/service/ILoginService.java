package com.ato.service;

import com.ato.model.dto.ChangePassDTO;
import com.ato.model.dto.LoginDTO;
import com.ato.model.dto.UsersDTO;

import javax.servlet.http.HttpServletRequest;

public interface ILoginService {
    boolean checkLogin(UsersDTO obj);
    boolean checkRestPass(ChangePassDTO obj);
    LoginDTO login(UsersDTO obj);
    String resetPassword(ChangePassDTO changePassDTO);
    LoginDTO authenticationcate(HttpServletRequest request);
    UsersDTO forgotPassword(ChangePassDTO obj);
    void updateKey(UsersDTO usersDTO);
}
