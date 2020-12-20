package com.ato.model.baseModel;

import com.ato.model.dto.UsersDTO;
import com.ato.utils.SecurityUtils;
import com.ato.utils.Utils;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.persistence.*;
import javax.servlet.http.HttpServletRequest;
import java.sql.Timestamp;

@Entity
@Table(name = "logs")
@Data
@AllArgsConstructor
public class Logs {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Basic
    @Column(name = "user_impact")
    private String userImpact;

    @Basic
    @Column(name = "code_action")
    private String codeAction;

    @Basic
    @Column(name = "content")
    private String content;

    @Basic
    @Column(name = "name_client")
    private String nameClient;

    @Basic
    @Column(name = "impact_time")
    private Timestamp impactTime;

    @Basic
    @Column(name = "ip")
    private String ip;

    public Logs() {

    }

    public Logs(String codeAction, String content, HttpServletRequest request) {
        String user = SecurityUtils.getCurrentUser().orElse(new UsersDTO()).getName();
        this.setCodeAction(codeAction + " | " + ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest().getMethod());
        this.setContent(content + " | " + ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest().getRequestURL());
        this.setIp(Utils.getIp(request));
        this.setUserImpact(user);
        this.setNameClient(Utils.getNameClient());
        this.setImpactTime(new Timestamp(System.currentTimeMillis()));

    }

    public Logs(String user, String codeAction, String content, HttpServletRequest request) {
        this.setCodeAction(codeAction + " | " + ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest().getMethod());
        this.setContent(content + " | " + ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest().getRequestURL());
        this.setIp(Utils.getIp(request));
        this.setUserImpact(user);
        this.setNameClient(Utils.getNameClient());
        this.setImpactTime(new Timestamp(System.currentTimeMillis()));

    }
}
