package com.ato.utils;

import com.ato.config.spirngConfig.Constants;
import com.ato.model.dto.ChangePassDTO;
import com.ato.model.dto.UsersDTO;
import org.apache.commons.lang3.StringUtils;
import org.hibernate.service.spi.ServiceException;

import javax.servlet.http.HttpServletRequest;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.text.NumberFormat;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

public class Utils {

    private Utils() {
    }

    public static String getIp(HttpServletRequest request) {
//         doan lay ip may khach ( đang lấy sai địa chỉ ip local )
        String remoteAddr = "";
        if (request != null) {
            remoteAddr = request.getHeader("X-FORWARDED-FOR");
            if (remoteAddr == null || "".equals(remoteAddr)) {
                try {
                    remoteAddr = Inet4Address.getLocalHost().getHostAddress();
                } catch (UnknownHostException e) {
                    remoteAddr = request.getRemoteAddr();
                }
//                remoteAddr=  Inet4Address.getLocalHost().getHostAddress();
//                remoteAddr = request.getRemoteAddr();
            }
        }
        return remoteAddr;
    }

    public static String getNameClient(){
        try {
            InetAddress addr;
            addr = InetAddress.getLocalHost();
            return addr.getHostName();
        } catch (UnknownHostException ex) {
            return null;
        }
    }

    private static Map<String, String> getRequestHeadersInMap(HttpServletRequest request) {
        Map<String, String> result = new HashMap<>();
        Enumeration headerNames = request.getHeaderNames();
        while (headerNames.hasMoreElements()) {
            String key = (String) headerNames.nextElement();
            String value = request.getHeader(key);
            result.put(key, value);
        }
        return result;
    }

    public static String formatCurrency(BigDecimal value) {
        NumberFormat format = NumberFormat.getCurrencyInstance(new Locale("vi", "VN"));
        format.setMinimumFractionDigits(3);
        format.setMaximumFractionDigits(4);
        format.setRoundingMode(RoundingMode.HALF_EVEN);
        return format.format(value);
    }


    public static void checkLogin(UsersDTO obj) {
        if (ValidateUtils.isEmpty(obj.getName())) {
            throw new ServiceException(Translator.toLocale(Constants.LOGIN_USER_REQUIRED));
        }
        if (ValidateUtils.isEmpty(obj.getPass())) {
            throw new ServiceException(Translator.toLocale(Constants.LOGIN_PASS_REQUIRED));
        }
        if (ValidateUtils.checkLength(obj.getPass(), 0, 6)) {
            throw new ServiceException(Translator.toLocale(Constants.LOGIN_PASS_MIN));
        }
        if (ValidateUtils.checkLengthMax(obj.getPass(), 60)) {
            throw new ServiceException(Translator.toLocale(Constants.LOGIN_PASS_MAX));
        }
    }

    public static void checkRestPass(ChangePassDTO obj) {
        if (StringUtils.isNotEmpty(obj.getUserName())) {
            if (ValidateUtils.isEmpty(obj.getOldPass())) {
                throw new ServiceException(Translator.toLocale(Constants.LOGIN_PASS_OLL_REQUIRED));
            }

            if (ValidateUtils.checkLength(obj.getOldPass(), 0, 6)) {
                throw new ServiceException(Translator.toLocale(Constants.LOGIN_PASS_OLL_MIN));
            }

            if (ValidateUtils.checkLengthMax(obj.getOldPass(), 60)) {
                throw new ServiceException(Translator.toLocale(Constants.LOGIN_PASS_OLL_MAX));
            }
        }

        if (ValidateUtils.isEmpty(obj.getNewPass())) {
            throw new ServiceException(Translator.toLocale(Constants.LOGIN_PASS_REQUIRED));
        }
        if (ValidateUtils.checkLength(obj.getNewPass(), 0, 6)) {
            throw new ServiceException(Translator.toLocale(Constants.LOGIN_PASS_MIN));
        }
        if (ValidateUtils.checkLengthMax(obj.getNewPass(), 60)) {
            throw new ServiceException(Translator.toLocale(Constants.LOGIN_PASS_MAX));
        }

        if (ValidateUtils.isEmpty(obj.getComPass())) {
            throw new ServiceException(Translator.toLocale(Constants.LOGIN_PASS_COM_REQUIRED));
        }

        if (ValidateUtils.checkLength(obj.getComPass(), 0, 6)) {
            throw new ServiceException(Translator.toLocale(Constants.LOGIN_PASS_COM_MIN));
        }

        if (ValidateUtils.checkLengthMax(obj.getComPass(), 60)) {
            throw new ServiceException(Translator.toLocale(Constants.LOGIN_PASS_COM_MAX));
        }
    }

    public static void checkInsert(UsersDTO usersDTO) {
        if (usersDTO.getName() == null) {
            throw new ServiceException("Tên người dùng không được để trống");
        }
        if (usersDTO.getFullname() == null) {
            throw new ServiceException("Tên đầy đủ người dùng không được để trống");
        }
        if (usersDTO.getMail() == null) {
            throw new ServiceException("Tài khoản Email không được để trống");
        }
        if (usersDTO.getPass() == null) {
            throw new ServiceException("Mật khẩu không được để trống");
        }
        if (usersDTO.getOrBirthUser() == null) {
            throw new ServiceException("Ngày sinh không được để trống");
        }
    }

}
