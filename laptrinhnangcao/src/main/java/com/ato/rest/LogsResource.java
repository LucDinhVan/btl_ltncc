package com.ato.rest;

import com.ato.config.spirngConfig.Constants;
import com.ato.model.dto.LogsDTO;
import com.ato.service.serviceImpl.LogsServiceImpl;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.Collections;

@RestController
@Log4j2
@RequestMapping("/api")
public class LogsResource {
    @Autowired
    LogsServiceImpl logsService;

    @PostMapping("/logs/doSearch")
    public ResponseEntity<Object> getAll(@RequestBody LogsDTO obj, HttpServletRequest request) {
        try {
            obj.setPage(Integer.parseInt(request.getParameter("page")));
            obj.setPageSize(Integer.parseInt(request.getParameter("size")));
            return ResponseEntity.status(200).body(Collections.singletonMap(Constants.DATA, logsService.doSearch(obj)));
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            return ResponseEntity.status(400).body(Collections.singletonMap(Constants.DETAIL, e.getMessage()));
        }
    }
}
