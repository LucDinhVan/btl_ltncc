package com.ato.service.businessSerivce;

import com.ato.config.springSecurity.CustomAuthenticationProvider;
import com.ato.config.springSecurity.CustomUserDetails;
import com.ato.dao.ObjectsDAO;
import com.ato.model.dto.ObjectsDTO;
import com.ato.service.IAppAuthorizer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.util.List;

@Service("appAuthorizer")
public class AppAuthorizerImpl implements IAppAuthorizer {

    @Autowired
    CustomAuthenticationProvider CustomAuthenticationProvider;

    @Autowired
    ObjectsDAO ObjectsDAO;

    /*
        kiểm tra từng api gọi vào xem user có quyền đó không
        true: có quyền
        false: không có quyền

     */

    @Override
    public boolean authorize(Authentication authentication, String action, Object callerObj) {
        String menuCode = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest().getRequestURI(); // lấy đường dẫn api
        String securedPath = menuCode.substring( 1 );//Bỏ dấu "/" ở đầu Path

        if (securedPath == null || "".equals( securedPath.trim() )) {
            return true;
        }
        boolean isAllow = false;
        try {
            CustomUserDetails myUserDetails = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            if (myUserDetails == null) {
                return isAllow;
            }

            Long userId = myUserDetails.getUser().getId();
            if (userId == null || "".equals( userId )) {
                return isAllow;
            }
//            kiểm tra user xem có quyền truy cập hay k?
            //Truy vấn vào CSDL theo userId + menuCode + action
            //Nếu có quyền thì
            try {
                List<ObjectsDTO> list = ObjectsDAO.getRole(userId);
                if(list.size()>0) {
//                    for (int i = 0; i < list.size(); i++){
//                        if(list.get( i ).getPartApi().equals( securedPath )){
                    return true;
//                        }
//                    }
//                    return true;
                }
            }
            catch (Exception e){
                System.out.println(e);
            }
        } catch (Exception e) {
//            logger.error(e.toString(), e);
            throw e;
        }
        return isAllow;
    }
}
