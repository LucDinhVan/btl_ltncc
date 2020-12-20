package com.ato.model.dto;

import com.ato.model.baseModel.AtoBaseDTO;
import com.ato.model.baseModel.Phone;
import com.ato.model.bo.Users;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.json.simple.JSONObject;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.sql.Timestamp;
import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Data
public class UsersDTO extends AtoBaseDTO {

    private Long id;

    @Size(max = 60, message = "{common.users.name} {common.maxLength}")
    @NotBlank(message = "{common.users.name} {common.required}")
    private String name;

    @NotBlank(message = "{common.users.fullname} {common.required}")
    @Size(max = 60, message = "{common.users.fullname} {common.maxLength}")
    private String fullname;

    @Size(max = 60, message = "{common.users.pass} {common.maxLength}")
    private String pass;

    @Size(max = 60, message = "{common.users.rePassword} {common.maxLength}")
    private String rePassword;

    @Size(max = 50, message = "{common.users.partImage} {common.maxLength}")
    private String partImage;

    private Timestamp orBirthUser;

    @NotBlank(message = "{common.users.mail} {common.required}")
    @Size(max = 100, message = "{common.users.mail} {common.maxLength}")
    @Email(message = "{common.users.mail} {common.format}")
    private String mail;

    @Size(max = 10, message = "{common.users.phone} {common.maxLength}")
    @Phone(message = "{common.users.phone} {common.format}")
    private String phone;

    private Long status;
    private String resetKey;
    private Timestamp resetDate;
    private String creator;
    private Timestamp creationTime;
    private String roleUser;
    private MultipartFile fileUpload;
    private List<Long> listRole;
    private JSONObject model;
    private String recaptchaReactive;

    public Users toModel() {
        Users users = new Users();
        users.setId(this.id);
        users.setName(this.name);
        users.setFullname(this.fullname);
        users.setPass(this.pass);
        users.setPartImage(this.partImage);
        users.setOrBirthUser(this.orBirthUser);
        users.setMail(this.mail);
        users.setPhone(this.phone);
        users.setStatus(this.status);
        users.setResetKey(this.resetKey);
        users.setResetDate(this.resetDate);
        users.setCreator(this.creator);
        users.setCreationTime(this.creationTime);
        return users;
    }

    public ChangePassDTO toChangePassDTO() {
        ChangePassDTO changePassDTO = new ChangePassDTO();
        changePassDTO.setUserName(this.name);
        changePassDTO.setEmail(this.mail);
        changePassDTO.setOldPass(this.pass);
        changePassDTO.setNewPass(this.rePassword);
        changePassDTO.setComPass(this.rePassword);
        return changePassDTO;
    }
}
