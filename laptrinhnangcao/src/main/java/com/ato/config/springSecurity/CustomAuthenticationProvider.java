package com.ato.config.springSecurity;

import com.ato.dao.ObjectsDAO;
import com.ato.model.dto.*;
import com.ato.service.businessSerivce.JwtService;
import com.ato.utils.Translator;
import org.hibernate.service.spi.ServiceException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class CustomAuthenticationProvider implements AuthenticationProvider {

    @Autowired
    JwtService jwtService;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Autowired
    ObjectsDAO objectsDAO;

    @Autowired
    UserDetailsService userDetailService;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        UsersDTO loginRequest = (UsersDTO) authentication.getPrincipal();
        CustomUserDetails customUserDetails = (CustomUserDetails) userDetailService.loadUserByUsername(loginRequest.getName());
        UsersDTO userDTO = customUserDetails.getUser();
        // logic xac thuc user
        UsernamePasswordAuthenticationToken result;
        if (userDTO.getName().equals(loginRequest.getName())
        ) {
            List<TreeDTO> listTreeDTO = getRoles(userDTO);
            customUserDetails.setList(listTreeDTO);
            customUserDetails.setUser(userDTO);
            UsernamePasswordAuthenticationToken authenticationToken =
                    new UsernamePasswordAuthenticationToken(customUserDetails.getUser().getName(), customUserDetails.getUser().getPass());
            String jwt = jwtService.createToken(authenticationToken, true);
            customUserDetails.setJwt(jwt);
            result = new UsernamePasswordAuthenticationToken(customUserDetails, customUserDetails.getPassword(),
                    customUserDetails.getAuthorities());
        } else {
            throw new ServiceException(Translator.toLocale("login.false"));
        }
        return result;
    }

    private List<TreeDTO> getRoles(UsersDTO userDTO) {
        List<ObjectsDTO> lstRoleAction = objectsDAO.getRoleAction(userDTO.getId());
        List<ObjectsDTO> lstRole = objectsDAO.getRole(userDTO.getId());
        List<ListActionDTO> listActionDTO = new ArrayList<>();
        List<TreeDTO> listTreeDTO = new ArrayList<>();
        if (lstRoleAction.size() > 0) {
            List<RoleActionDTO> listRoleActionDTO = new ArrayList<>();
            for (int i = 0; i < lstRoleAction.size() - 1; i++) {
                RoleActionDTO acitonDTO = new RoleActionDTO();
                if (lstRoleAction.get(i).getId().equals(lstRoleAction.get(i + 1).getId())) {
                    acitonDTO.setCodeAction(lstRoleAction.get(i).getCodeAction());
                    listRoleActionDTO.add(acitonDTO);
                } else if (!lstRoleAction.get(i).getId().equals(lstRoleAction.get(i + 1).getId())) {
                    acitonDTO.setCodeAction(lstRoleAction.get(i).getCodeAction());
                    listRoleActionDTO.add(acitonDTO);
                    ListActionDTO lsActionDTO = new ListActionDTO();
                    lsActionDTO.setId(lstRoleAction.get(i).getId());
                    lsActionDTO.setListAction(listRoleActionDTO);
                    listActionDTO.add(lsActionDTO);
                    listRoleActionDTO = new ArrayList<>();
                }
                if (lstRoleAction.size() == (i + 2)) {
                    acitonDTO.setCodeAction(lstRoleAction.get(i + 1).getCodeAction());
                    listRoleActionDTO.add(acitonDTO);
                    ListActionDTO lstActionDTO = new ListActionDTO();
                    lstActionDTO.setId(lstRoleAction.get(i + 1).getId());
                    lstActionDTO.setListAction(listRoleActionDTO);
                    listActionDTO.add(lstActionDTO);
                }
            }
        }
        for (ObjectsDTO objectsDTO : lstRole) {
            for (ListActionDTO actionDTO : listActionDTO) {
                if (objectsDTO.getId().equals(actionDTO.getId())) {
                    objectsDTO.setRole(actionDTO.getListAction());
                }
            }
            listTreeDTO.add(objectsDTO.toTree());
        }
        return listTreeDTO;
    }

    @Override
    public boolean supports(Class<?> aClass) {
        return false;
    }
}
