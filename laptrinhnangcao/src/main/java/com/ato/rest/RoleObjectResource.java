package com.ato.rest;

import com.ato.config.spirngConfig.Constants;
import com.ato.config.spirngConfig.LogConfig;
import com.ato.model.baseModel.Logs;
import com.ato.model.dto.RoleObjectDTO;
import com.ato.service.serviceImpl.RoleObjectServiceImpl;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.Collections;

@RestController
@Log4j2
@RequestMapping("/api")
public class RoleObjectResource {
    @Autowired
    RoleObjectServiceImpl roleObjectService;

    @Autowired
    LogConfig logConfig;

    private static final String ENTITY = "ROLE-OBJECTS";

    @PostMapping("/object/updateObjRoleAction")
    public ResponseEntity<Object> updateObjRoleAction(@RequestBody RoleObjectDTO obj, HttpServletRequest request) {
        try {
            return ResponseEntity.status(200).body(Collections.singletonMap(Constants.DATA, roleObjectService.insert(obj)));
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            return ResponseEntity.status(400).body(Collections.singletonMap(Constants.DETAIL, e.getMessage()));
        } finally {
            Logs logs1 = new Logs(Constants.UPDATE, ENTITY, request);
            logConfig.submit(logs1);
        }
    }

}
