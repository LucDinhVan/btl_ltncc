package com.ato.rest;

import com.ato.config.spirngConfig.Constants;
import com.ato.config.spirngConfig.LogConfig;
import com.ato.model.baseModel.Logs;
import com.ato.model.dto.ColorDTO;
import com.ato.service.serviceImpl.ColorsServiceImpl;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.Collections;

@RestController
@Log4j2
@RequestMapping("/api")
public class ColorResource {
    @Autowired
    ColorsServiceImpl colorsService;
    @Autowired
    LogConfig logConfig;

    private static final String ENTITY = "COLOR";

    @GetMapping("/color/getAll")
    public ResponseEntity<Object> getAllRoles(HttpServletRequest request) {
        try {
            return ResponseEntity.status(200).body(Collections.singletonMap(Constants.DATA, colorsService.getAll()));
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            return ResponseEntity.status(400).body(Collections.singletonMap(Constants.DETAIL, e.getMessage()));
        } finally {
            Logs logs1 = new Logs(Constants.SEARCH, ENTITY, request);
            logConfig.submit(logs1);
        }
    }

    @PostMapping("/color/doSearch")
    public ResponseEntity<Object> getAll(@RequestBody ColorDTO obj, HttpServletRequest request) {
        try {
            obj.setPage(Integer.parseInt(request.getParameter("page")));
            obj.setPageSize(Integer.parseInt(request.getParameter("color")));
            return ResponseEntity.status(200).body(Collections.singletonMap(Constants.DATA, colorsService.doSearch(obj)));
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            return ResponseEntity.status(400).body(Collections.singletonMap(Constants.DETAIL, e.getMessage()));
        } finally {
            Logs logs1 = new Logs(Constants.SEARCH, ENTITY, request);
            logConfig.submit(logs1);
        }
    }

    @PutMapping("/color/insert")
    public ResponseEntity<Object> insert(@RequestBody ColorDTO obj, BindingResult result, HttpServletRequest request) {
        try {
            if (result.hasErrors()) {
                return ResponseEntity.status(400).body(Collections.singletonMap(Constants.DETAIL, result.getFieldError()));
            } else {
                return ResponseEntity.status(200).body(Collections.singletonMap(Constants.DATA, colorsService.insert(obj)));
            }
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            return ResponseEntity.status(400).body(Collections.singletonMap(Constants.DETAIL, e.getMessage()));
        } finally {
            Logs logs1 = new Logs(Constants.CREATE, ENTITY, request);
            logConfig.submit(logs1);
        }
    }
    @PutMapping("/color/update")
    public ResponseEntity<Object> update(@RequestBody ColorDTO obj, BindingResult result, HttpServletRequest request) {
        try {
            if (result.hasErrors()) {
                return ResponseEntity.status(400).body(Collections.singletonMap(Constants.DETAIL, result.getFieldError()));
            } else {
                return ResponseEntity.status(200).body(Collections.singletonMap(Constants.DATA, colorsService.update(obj)));
            }
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            return ResponseEntity.status(400).body(Collections.singletonMap(Constants.DETAIL, e.getMessage()));
        } finally {
            Logs logs1 = new Logs(Constants.UPDATE, ENTITY, request);
            logConfig.submit(logs1);
        }
    }

    @DeleteMapping("/color/{id}")
    public ResponseEntity<Object> delete(@PathVariable Long id, HttpServletRequest request) {
        try {
            return ResponseEntity.status(200).body(Collections.singletonMap(Constants.DATA, colorsService.delete(id)));
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            return ResponseEntity.status(400).body(Collections.singletonMap(Constants.DETAIL, e.getMessage()));
        } finally {
            Logs logs1 = new Logs(Constants.DELETE, ENTITY, request);
            logConfig.submit(logs1);
        }
    }
}
