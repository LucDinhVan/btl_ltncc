package com.ato.service.businessSerivce;

import com.ato.config.springSecurity.CustomUserDetails;
import com.ato.dao.UsersDAO;
import com.ato.model.bo.Users;
import com.ato.model.dto.UsersDTO;
import com.ato.utils.Translator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    UsersDAO usersDAO;


    public UserDetailsServiceImpl() {
    }

    @Override
    public UserDetails loadUserByUsername(String username) {
        Users rs = usersDAO.searchUsersId(username, null, null);
        UsersDTO usersDTO = rs.toDto();
        if (usersDTO == null) {
            throw new UsernameNotFoundException(Translator.toLocale("user.notexist"));
        }
        return new CustomUserDetails(usersDTO, null);
    }

}
