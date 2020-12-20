package com.ato.service.businessSerivce;

import com.ato.config.spirngConfig.Constants;
import com.ato.model.dto.RecaptchaDTO;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

@Log4j2
@Service
public class CaptchaService {

    public boolean verify(String response) {
        RestTemplate restTemplate = new RestTemplate();
        MultiValueMap param = new LinkedMultiValueMap<>();
        param.add( "secret", Constants.RECAPTCHA_SECRET_KEY );
        param.add( "response", response );

        RecaptchaDTO recaptchaResponse;
        try {
            recaptchaResponse = restTemplate.postForObject( Constants.RECAPTCHA_VERIFY_URL, param, RecaptchaDTO.class );
            if(recaptchaResponse != null) {
                return recaptchaResponse.isSuccess();
            }
        } catch (RestClientException e) {
            log.error( e.getMessage(), e );
        }
        return false;
    }

}
