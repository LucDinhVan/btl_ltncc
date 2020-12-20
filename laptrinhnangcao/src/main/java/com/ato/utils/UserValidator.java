package com.ato.utils;

import com.ato.model.dto.UsersDTO;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

public class UserValidator implements Validator {

    @Override
    public boolean supports(Class clazz) {
        return UsersDTO.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        UsersDTO user = (UsersDTO) target;

        if(user.getName() == null) {
            errors.rejectValue("name", "your_error_code");
        }

        // do "complex" validation here

    }

}
