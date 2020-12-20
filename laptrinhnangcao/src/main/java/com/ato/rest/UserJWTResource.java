package com.ato.rest;

import com.ato.config.spirngConfig.Constants;
import com.ato.config.spirngConfig.LogConfig;
import com.ato.model.baseModel.Logs;
import com.ato.model.dto.ChangePassDTO;
import com.ato.model.dto.UsersDTO;
import com.ato.service.businessSerivce.MailService;
import com.ato.service.serviceImpl.LoginServiceImpl;
import com.ato.utils.FileUtils;
import com.ato.utils.Translator;
import lombok.extern.log4j.Log4j2;
import org.hibernate.service.spi.ServiceException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.Collections;
import java.util.Objects;

@RestController
@Log4j2
@RequestMapping("/api")
public class UserJWTResource {

    private static final String ENTITY = "USER";
    @Autowired
    LoginServiceImpl loginServiceImpl;

    @Autowired
    MailService mailService;

    @Autowired
    LogConfig logConfig;

    @PostMapping("/login")
    public ResponseEntity<Object> login(@RequestBody UsersDTO obj, HttpServletRequest request) {
        try {
            return new ResponseEntity<>(loginServiceImpl.login(obj), HttpStatus.OK);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            if (e.getMessage().equals(Translator.toLocale(Constants.GOOGLE_RECAPTCHA_FALSE))) {
                return ResponseEntity.status(401).body(Collections.singletonMap(Constants.DETAIL, e.getMessage()));
            }
            return ResponseEntity.status(400).body(Collections.singletonMap(Constants.DETAIL, e.getMessage()));
        } finally {
            Logs logs1 = new Logs(obj.getName(), Constants.LOGIN, ENTITY, request);
            logConfig.submit(logs1);
        }
    }

    @PostMapping("/authenticationcate")
    public ResponseEntity<Object> authenticationcate(HttpServletRequest request) {
        return new ResponseEntity<>( loginServiceImpl.authenticationcate( request ), HttpStatus.OK );
    }

    @PostMapping(value = "/changePass")
    public ResponseEntity<Object> changePassword(@RequestBody ChangePassDTO changePassDTO, HttpServletRequest request) {
        try {
            String updateSuccess = loginServiceImpl.resetPassword(changePassDTO);
            return ResponseEntity.status(200).body(Collections.singletonMap(Constants.DATA, updateSuccess));
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            if (e.getMessage().equals(Translator.toLocale(Constants.TOKEN_ERROR))) {
                return ResponseEntity.status(401).body(Collections.singletonMap(Constants.DETAIL, e.getMessage()));
            }
            return ResponseEntity.status(400).body(Collections.singletonMap(Constants.DETAIL, e.getMessage()));
        } finally {
            Logs logs1 = new Logs(changePassDTO.getUserName(), Constants.CHANGEPASSWORD, ENTITY, request);
            logConfig.submit(logs1);
        }
    }

    @PostMapping("/sendSimpleEmail")
    public ResponseEntity<Object> sendSimpleEmail(@RequestBody ChangePassDTO changePassDTO, HttpServletRequest request) {
        try {
            UsersDTO usersDTO = loginServiceImpl.forgotPassword(changePassDTO);
            String emailTo = changePassDTO.getEmail();
            String refreshToken = loginServiceImpl.updateKey(usersDTO);
            String htmlMsg = Translator.toLocale(Constants.EMAIL_CONTENT1) + Translator.toLocale(Constants.EMAIL_PATH)
                    + refreshToken + "  " + Translator.toLocale(Constants.EMAIL_CONTENT2);
            if (!mailService.sendEmail(emailTo, Translator.toLocale(Constants.EMAIL_SUBJECT), htmlMsg, Constants.IS_MULTI_PART, Constants.IS_HTML)) {
                throw new ServiceException(Translator.toLocale(Constants.EMAIL_FALSE));
            }

            return ResponseEntity.status(200).body(Collections.singletonMap(Constants.DATA, Translator.toLocale(Constants.EMAIL_TRUE)));
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            return ResponseEntity.status(400).body(Collections.singletonMap(Constants.DETAIL, e.getMessage()));
        } finally {
            Logs logs1 = new Logs(changePassDTO.getUserName(),Constants.SENDSIMPLEMAIL, ENTITY, request);
            logConfig.submit(logs1);
        }
    }

    @PostMapping(value = "/users/doSearch")
    public ResponseEntity<Object> doSearch(@RequestBody UsersDTO paramSearch, HttpServletRequest request) {
        try {
            paramSearch.setPage(Integer.parseInt(request.getParameter("page")));
            paramSearch.setPageSize(Integer.parseInt(request.getParameter("size")));
            return ResponseEntity.status(200).body(Collections.singletonMap(Constants.DATA, loginServiceImpl.searchUsers(paramSearch)));
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            return ResponseEntity.status(400).body(Collections.singletonMap(Constants.DETAIL, e.getMessage()));
        } finally {
            Logs logs1 = new Logs(Constants.SEARCH, ENTITY, request);
            logConfig.submit(logs1);
        }
    }


    @DeleteMapping("/users/{id}")
    public ResponseEntity<Object> delete(@PathVariable Long id, HttpServletRequest request) {
        try {
            return ResponseEntity.status(200).body(Collections.singletonMap(Constants.DATA, loginServiceImpl.delete(id)));
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            return ResponseEntity.status(400).body(Collections.singletonMap(Constants.DETAIL, e.getMessage()));
        } finally {
            Logs logs1 = new Logs(Constants.DELETE, ENTITY, request);
            logConfig.submit(logs1);
        }
    }

    @PutMapping("/users/insert")
    public ResponseEntity<Object> insert(@RequestBody @Valid UsersDTO obj, BindingResult result, HttpServletRequest request) {
        try {
            if (result.hasErrors()) {
                return ResponseEntity.status(400).body(Collections.singletonMap(Constants.DETAIL, Objects.requireNonNull(result.getFieldError()).getDefaultMessage()));
            } else {
                return ResponseEntity.status(200).body(Collections.singletonMap(Constants.DATA, loginServiceImpl.insert(obj)));
            }
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            return ResponseEntity.status(400).body(Collections.singletonMap(Constants.DETAIL, e.getMessage()));
        } finally {
            Logs logs1 = new Logs(Constants.CREATE, ENTITY, request);
            logConfig.submit(logs1);
        }
    }

    @PutMapping("/users/update")
    public ResponseEntity<Object> update(@RequestBody @Valid UsersDTO obj, BindingResult result, HttpServletRequest request) {
        try {
            if (result.hasErrors()) {
                return ResponseEntity.status(400).body(Collections.singletonMap(Constants.DETAIL, result.getFieldError().getDefaultMessage()));
            } else {
                return ResponseEntity.status(200).body(Collections.singletonMap(Constants.DATA, loginServiceImpl.update(obj)));
            }
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            return ResponseEntity.status(400).body(Collections.singletonMap(Constants.DETAIL, e.getMessage()));
        } finally {
            Logs logs1 = new Logs(Constants.UPDATE, ENTITY, request);
            logConfig.submit(logs1);
        }
    }

    //    @RequestMapping(value = {"/searchUsers"}, method = RequestMethod.POST)
////    @PostMapping( "/searchUsers")
//    @PreAuthorize("@appAuthorizer.authorize(authentication, 'VIEW', this)")
//    public @ResponseBody
//    ResponseEntity<?> searchUsers(@RequestBody UsersDTO obj) {
////        List<UsersDTO> list = loginServiceImpl.searchUsers(obj);
//        return new ResponseEntity<>(loginServiceImpl.searchUsers(obj), HttpStatus.OK);
//    }
}
