package com.ato.rest;

import com.ato.config.spirngConfig.Constants;
import com.ato.config.spirngConfig.LogConfig;
import com.ato.model.baseModel.Logs;
import com.ato.model.dto.BillDTO;
import com.ato.service.serviceImpl.BillServiceImpl;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.Collections;

@RestController
@Log4j2
@RequestMapping("/api")
public class BillResource {
    @Autowired
    BillServiceImpl BillServiceImpl;

    @Autowired
    LogConfig logConfig;

    private static final String ENTITY = "BILL";

    @GetMapping("/bill/getAll")
    public ResponseEntity<Object> getAllRoles(HttpServletRequest request) {
        try {
            return ResponseEntity.status(200).body(Collections.singletonMap(Constants.DATA, BillServiceImpl.getAll()));
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            return ResponseEntity.status(400).body(Collections.singletonMap(Constants.DETAIL, e.getMessage()));
        } finally {
            Logs logs1 = new Logs(Constants.SEARCH, ENTITY, request);
            logConfig.submit(logs1);
        }
    }

    @PostMapping("/bill/doSearch")
    public ResponseEntity<Object> getAll(@RequestBody BillDTO obj, HttpServletRequest request) {
        try {
            obj.setPage(Integer.parseInt(request.getParameter("page")));
            obj.setPageSize(Integer.parseInt(request.getParameter("size")));
            return ResponseEntity.status(200).body(Collections.singletonMap(Constants.DATA, BillServiceImpl.doSearch(obj)));
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            return ResponseEntity.status(400).body(Collections.singletonMap(Constants.DETAIL, e.getMessage()));
        } finally {
            Logs logs1 = new Logs(Constants.SEARCH, ENTITY, request);
            logConfig.submit(logs1);
        }
    }

    @PutMapping("/bill/insert")
    public ResponseEntity<Object> insert(@RequestBody @Valid BillDTO obj, HttpServletRequest request) {
        try {
            return ResponseEntity.status(200).body(Collections.singletonMap(Constants.DATA, BillServiceImpl.insert(obj)));
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            return ResponseEntity.status(400).body(Collections.singletonMap(Constants.DETAIL, e.getMessage()));
        } finally {
            Logs logs1 = new Logs(Constants.CREATE, ENTITY, request);
            logConfig.submit(logs1);
        }
    }

    @PutMapping("/bill/update")
    public ResponseEntity<Object> update(@RequestBody @Valid BillDTO obj, HttpServletRequest request) {
        try {
            return ResponseEntity.status(200).body(Collections.singletonMap(Constants.DATA, BillServiceImpl.update(obj)));
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            return ResponseEntity.status(400).body(Collections.singletonMap(Constants.DETAIL, e.getMessage()));
        } finally {
            Logs logs1 = new Logs(Constants.UPDATE, ENTITY, request);
            logConfig.submit(logs1);
        }
    }

    @DeleteMapping("/bill/{id}")
    public ResponseEntity<Object> delete(@PathVariable Long id, HttpServletRequest request) {
        try {
            return ResponseEntity.status(200).body(Collections.singletonMap(Constants.DATA, BillServiceImpl.delete(id)));
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            return ResponseEntity.status(400).body(Collections.singletonMap(Constants.DETAIL, e.getMessage()));
        } finally {
            Logs logs1 = new Logs(Constants.DELETE, ENTITY, request);
            logConfig.submit(logs1);
        }
    }
}
