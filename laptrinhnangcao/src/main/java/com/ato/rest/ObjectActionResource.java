package com.ato.rest;

import com.ato.config.spirngConfig.Constants;
import com.ato.config.spirngConfig.LogConfig;
import com.ato.model.baseModel.Logs;
import com.ato.model.bo.ObjectAction;
import com.ato.model.dto.ObjectActionDTO;
import com.ato.service.serviceImpl.ObjectActionServiceImpl;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.Collections;

@RestController
@Log4j2
@RequestMapping("/api")
public class ObjectActionResource {
    @Autowired
    ObjectActionServiceImpl objectActionService;

    @Autowired
    LogConfig logConfig;

    private static final String ENTITY = "OBJECTS-ACTION";

    @PostMapping("/objectAction/doSearch")
    public ResponseEntity<Object> getAll(@RequestBody ObjectAction obj, HttpServletRequest request) {
        try {
            return ResponseEntity.status(200).body(Collections.singletonMap(Constants.DATA, objectActionService.doSearch(obj)));
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            return ResponseEntity.status(400).body(Collections.singletonMap(Constants.DETAIL, e.getMessage()));
        } finally {
            Logs logs1 = new Logs(Constants.SEARCH, ENTITY, request);
            logConfig.submit(logs1);
        }
    }

    @PutMapping("/objectAction/insert")
    public ResponseEntity<Object> insert(@RequestBody @Valid ObjectActionDTO obj, BindingResult result, HttpServletRequest request) {
        try {
            if (result.hasErrors()) {
                return ResponseEntity.status(400).body(Collections.singletonMap(Constants.DETAIL, result.getFieldError()));
            } else {
                return ResponseEntity.status(200).body(Collections.singletonMap(Constants.DATA, objectActionService.insert(obj)));
            }
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            return ResponseEntity.status(400).body(Collections.singletonMap(Constants.DETAIL, e.getMessage()));
        } finally {
            Logs logs1 = new Logs(Constants.CREATE, ENTITY, request);
            logConfig.submit(logs1);
        }
    }

    @PostMapping("/objectAction/delete")
    public ResponseEntity<Object> delete(@RequestBody @Valid ObjectActionDTO obj, HttpServletRequest request) {
        try {
            return ResponseEntity.status(200).body(Collections.singletonMap(Constants.DATA, objectActionService.delete(obj)));
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            return ResponseEntity.status(400).body(Collections.singletonMap(Constants.DETAIL, e.getMessage()));
        } finally {
            Logs logs1 = new Logs(Constants.DELETE, ENTITY, request);
            logConfig.submit(logs1);
        }
    }
}
