package com.ato.service.serviceImpl;

import com.ato.config.spirngConfig.Constants;
import com.ato.config.springSecurity.CustomUserDetails;
import com.ato.dao.UserRoleDAO;
import com.ato.dao.UsersDAO;
import com.ato.model.baseModel.DataListDTO;
import com.ato.model.bo.UserRole;
import com.ato.model.bo.Users;
import com.ato.model.dto.ChangePassDTO;
import com.ato.model.dto.LoginDTO;
import com.ato.model.dto.UsersDTO;
import com.ato.service.businessSerivce.CaptchaService;
import com.ato.service.businessSerivce.JwtService;
import com.ato.service.businessSerivce.UserDetailsServiceImpl;
import com.ato.utils.DateUtil;
import com.ato.utils.Translator;
import com.ato.utils.Utils;
import com.ato.utils.ValidateUtils;
import io.jsonwebtoken.lang.Assert;
import lombok.extern.log4j.Log4j2;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.StringUtils;
import org.hibernate.service.spi.ServiceException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Log4j2
@Service
public class LoginServiceImpl {

    @Autowired
    UsersDAO usersDao;

    @Autowired
    UserRoleDAO userRoleDAO;

    @Autowired
    JwtService tokenProvider;

    @Autowired
    AuthenticationProvider authenticationProvider;

    @Autowired
    UserDetailsServiceImpl userDetailsService;

    @Autowired
    Environment environment;

    @Autowired
    CaptchaService captchaService;

    public LoginDTO login(UsersDTO obj) {
        Utils.checkLogin(obj);
        boolean captchaVerified = captchaService.verify( obj.getRecaptchaReactive() );
        if (!captchaVerified) {
            log.error( Constants.GOOGLE_RECAPTCHA_FALSE );
            throw new ServiceException( Translator.toLocale( Constants.GOOGLE_RECAPTCHA_FALSE ) );
        }
        Users rs = usersDao.searchUsersId(obj.getName(), null, null);
        UsersDTO usersDTO = rs.toDto();

        if (usersDTO != null) {
            if (ValidateUtils.changePassword(usersDTO.getPass(), obj.getPass())) {

                Authentication authentication = authenticationProvider
                        .authenticate(new UsernamePasswordAuthenticationToken(obj, obj.getPass()));
                Assert.notNull(authentication, Constants.LOGIN_FALSE);
                LoginDTO login = new LoginDTO();
                CustomUserDetails customUserDetails = (CustomUserDetails) authentication.getPrincipal();
                String jwt = customUserDetails.getJwt();
                HttpHeaders httpHeaders = new HttpHeaders();
                httpHeaders.add(Constants.AUTHORIZATION, Constants.BEARER + jwt);
                login.setHttpHeaders(httpHeaders);
                login.setListObjects(customUserDetails.getList());
                login.setCustomUserDetails(customUserDetails.getUser());
                login.setPath(Translator.toLocale(Constants.URL_PART));
                return login;
            }
        }
        log.error(Constants.LOGIN_FALSE);
        throw new ServiceException(Translator.toLocale(Constants.LOGIN_NAME_FALSE));
    }

    public String resetPassword(ChangePassDTO changePassDTO) {
        UsersDTO userDTO = null;
        if (StringUtils.isNotEmpty(changePassDTO.getKey())) {
            try {
                Users rs = usersDao.searchUsersId(null, null, changePassDTO.getKey());
                userDTO = rs.toDto();
                if (DateUtil.compareDate(DateUtil.addMinutesToDate(userDTO.getResetDate(), 15), new Date()) == 1) {
                    log.error(Constants.TOKEN_ERROR);
                    throw new ServiceException(Translator.toLocale(Constants.TOKEN_ERROR));
                }
            } catch (Exception e) {
                log.error(e.getMessage(), e);
                throw new ServiceException(Translator.toLocale(Constants.TOKEN_ERROR));
            }
        }
        if (StringUtils.isNotEmpty(changePassDTO.getUserName())) {
            Users rs = usersDao.searchUsersId(changePassDTO.getUserName(), null, null);
            userDTO = rs.toDto();
        }
        if (userDTO != null) {
            Utils.checkRestPass(changePassDTO);
            if (StringUtils.isNotEmpty(changePassDTO.getUserName())) {
                if (!ValidateUtils.changePassword(userDTO.getPass(), changePassDTO.getOldPass())) {
                    log.error(Constants.LOGIN_PASS_CHANGE_FALSE);
                    throw new ServiceException(Translator.toLocale(Constants.LOGIN_PASS_CHANGE_FALSE));
                }
                if (ValidateUtils.checkResetPass(changePassDTO.getOldPass(), changePassDTO.getNewPass())) {
                    log.error(Constants.LOGIN_PASS_OLD_SS_FALSE);
                    throw new ServiceException(Translator.toLocale(Constants.LOGIN_PASS_OLD_SS_FALSE));
                }
            }
            if (!ValidateUtils.checkResetPass(changePassDTO.getComPass(), changePassDTO.getNewPass())) {
                log.error(Constants.LOGIN_PASS_NEW_SS_FALSE);
                throw new ServiceException(Translator.toLocale(Constants.LOGIN_PASS_NEW_SS_FALSE));
            }
            userDTO.setPass(new BCryptPasswordEncoder().encode(changePassDTO.getNewPass()));
            try {
                usersDao.updateResertKey(userDTO);
                if (StringUtils.isNotEmpty(changePassDTO.getKey())) {
                    userDTO.setResetKey(null);
                    userDTO.setResetDate(null);
                    usersDao.updateResertKey(userDTO);
                }
                log.info(Constants.CHANGE_PASS_SUS);
                return Translator.toLocale(Constants.CHANGE_PASS_SUS);
            } catch (ServiceException e) {
                log.error(Constants.CHANGE_PASS_FALSE, e);
                throw new ServiceException(Translator.toLocale(Constants.CHANGE_PASS_FALSE));
            }
        } else {
            log.error(Constants.CHANGE_NAME_FALSE);
            throw new ServiceException(Translator.toLocale(Constants.CHANGE_NAME_FALSE));
        }
    }

    public LoginDTO authenticationcate(HttpServletRequest request) {
        final String requestTokenHeader = request.getHeader(Constants.AUTHORIZATION);
        LoginDTO login = new LoginDTO();

        String username = null;
        String jwtToken = null;
        if (StringUtils.isNotEmpty(requestTokenHeader) && requestTokenHeader.startsWith(Constants.BEARER)) {
            jwtToken = requestTokenHeader.substring(7);
            try {
                username = tokenProvider.getAuthentication(jwtToken);
            } catch (Exception e) {
                log.info(e.getMessage(), e);
            }
        }
        Users rs = usersDao.searchUsersId(username, null, null);
        UsersDTO usersDTO = rs.toDto();
        if (usersDTO != null) {
            UserDetails userDetails = userDetailsService.loadUserByUsername(username);
            if (tokenProvider.validateToken(jwtToken, userDetails)) {
                Authentication authentication = authenticationProvider
                        .authenticate(new UsernamePasswordAuthenticationToken(usersDTO, usersDTO.getPass()));
                CustomUserDetails customUserDetails = (CustomUserDetails) authentication.getPrincipal();
                login.setListObjects(customUserDetails.getList());
            }
        } else {
            log.debug("error {}", Constants.LOGIN_NAME_FALSE);
            throw new ServiceException(Translator.toLocale(Constants.LOGIN_NAME_FALSE));
        }
        return login;
    }

    public UsersDTO forgotPassword(ChangePassDTO obj) {
        if (null != obj.getEmail()) {
            try {
                Users rs = usersDao.searchUsersId( null, obj.getEmail(), null );
                UsersDTO userDTO = rs.toDto();
                if (userDTO != null) {
                    return userDTO;
                }
            } catch (Exception e) {
                log.error(Translator.toLocale(Constants.LOGIN_EMAIL_FALSE), e);
                throw new ServiceException(Translator.toLocale(Constants.LOGIN_EMAIL_FALSE));
            }
        }
        log.error(Constants.LOGIN_EMAIL_FALSE);
        throw new ServiceException(Translator.toLocale(Constants.LOGIN_EMAIL_FALSE));
    }

    public String updateKey(UsersDTO usersDTO) {
        String refreshToken = RandomStringUtils.randomAlphanumeric(18);
        usersDTO.setResetKey(refreshToken);
        usersDTO.setResetDate(new Timestamp(System.currentTimeMillis()));
        usersDao.updateResertKey(usersDTO);
        return refreshToken;
    }

    public DataListDTO searchUsers(UsersDTO obj) {
        DataListDTO dataListDTO = new DataListDTO();
        try {
            List<UsersDTO> list = usersDao.searchUsers(obj);
            dataListDTO.setList(list);
            dataListDTO.setTotalPages(obj.getTotalRecord());
        } catch (Exception e) {
            throw new ServiceException(Translator.toLocale("error.users.false"));
        }
        return dataListDTO;
    }

    public Long delete(Long id){
        try {
            Users users = usersDao.find(id);
            usersDao.delete(users);
            return id;
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            throw new ServiceException(Translator.toLocale("error.users.false"));
        }
    }

    public Long insert(UsersDTO obj) {
        Utils.checkInsert(obj);
        if (obj.getName() != null) {
            Users users = usersDao.findByFiled("name", obj.getName());
            if (users != null) {
                if (obj.getName().equals(users.getName())) {
                    throw new ServiceException("Tên người dùng đã tồn tại");
                }
            }
        }
        if (obj.getMail() != null) {
            Users users = usersDao.findByFiled("mail", obj.getMail());
            if (users != null) {
                if (obj.getMail().equals(users.getMail())) {
                    throw new ServiceException("Tài khoản Email đã tồn tại");
                }
            }
        }
        try {
            CustomUserDetails principal = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            int length = obj.getListRole().size();
            obj.setCreationTime(new Timestamp(System.currentTimeMillis()));
            obj.setCreator(principal.getUsername());
            obj.setPass(new BCryptPasswordEncoder().encode(obj.getPass()));
            Long id = usersDao.insert(obj.toModel());
            if (length > 0) {
                for (int i = 0; i < length; i++) {
                    UserRole userRole = new UserRole();
                    userRole.setIdUser(id);
                    userRole.setIdRole(obj.getListRole().get(i));
                    userRole.setUpdateTime(new Timestamp(System.currentTimeMillis()));
                    userRoleDAO.insert(userRole);
                }
            }

            return obj.getId();
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            throw new ServiceException("Thêm mới ngừoi dùng không thành công");
        }
    }

    public Long update(UsersDTO obj){
        try {
            CustomUserDetails principal = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            if(obj.getPass() == null){
                obj.setPass(principal.getUser().getPass());
                obj.setCreator(principal.getUser().getCreator());
                obj.setCreationTime(principal.getUser().getCreationTime());
                obj.setResetDate(principal.getUser().getResetDate());
                obj.setResetKey(principal.getUser().getResetKey());
            } else {
                if (obj.getPass().equals(obj.getRePassword())){
                    obj.setPass(new BCryptPasswordEncoder().encode(obj.getPass()));
                    obj.setCreator(principal.getUser().getCreator());
                    obj.setCreationTime(principal.getUser().getCreationTime());
                    obj.setResetDate(principal.getUser().getResetDate());
                    obj.setResetKey(principal.getUser().getResetKey());
                } else {
                    throw new ServiceException("Mat khau nhap lai khong dung");
                }
            }
            usersDao.update(obj.toModel());
            List<UserRole> userRoleList = userRoleDAO.find("idUser", obj.getId());
            List<Long> list = new ArrayList<>();
            for (UserRole role : userRoleList) {
                list.add(role.getIdRole());
            }
            List<Long> base;
            // ds them
            base = new ArrayList<>( obj.getListRole());
            base.removeAll(list);
            for (Long aLong : base) {
                UserRole userRole = new UserRole();
                userRole.setIdUser(obj.getId());
                userRole.setIdRole(aLong);
                userRole.setUpdateTime(new Timestamp(System.currentTimeMillis()));
                userRoleDAO.insert(userRole);
            }
            // ds xoa
            base = new ArrayList<>(list);
            base.removeAll(obj.getListRole());
            for (Long aLong : base) {
                String[] cars = {"idUser", "idRole"};
                Object obj1[] = {obj.getId(), aLong};
                List<UserRole> userRoleLis1 = userRoleDAO.find(cars, obj1);
                for (UserRole userRole : userRoleLis1) {
                    userRoleDAO.delete(userRole);
                }
            }
            return obj.getId();
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            throw new ServiceException(e.getMessage());
        }
    }
}
