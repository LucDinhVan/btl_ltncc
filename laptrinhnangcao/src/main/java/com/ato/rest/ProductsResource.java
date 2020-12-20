package com.ato.rest;

import com.ato.config.spirngConfig.Constants;
import com.ato.config.spirngConfig.LogConfig;
import com.ato.model.baseModel.Logs;
import com.ato.model.dto.ProductDTO;
import com.ato.service.serviceImpl.ProductsServiceImpl;
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
public class ProductsResource {
    @Autowired
    ProductsServiceImpl ProductsServiceImpl;

    @Autowired
    LogConfig logConfig;

    private static final String ENTITY = "PRODUCTS";

    @GetMapping("/products/getAll")
    public ResponseEntity<Object> getAllRoles(HttpServletRequest request) {
        try {
            return ResponseEntity.status(200).body(Collections.singletonMap(Constants.DATA, ProductsServiceImpl.getAll()));
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            return ResponseEntity.status(400).body(Collections.singletonMap(Constants.DETAIL, e.getMessage()));
        } finally {
            Logs logs1 = new Logs(Constants.SEARCH, ENTITY, request);
            logConfig.submit(logs1);
        }
    }

    @PostMapping("/products/doSearch")
    public ResponseEntity<Object> getAll(@RequestBody ProductDTO obj, HttpServletRequest request) {
        try {
            obj.setPage(Integer.parseInt(request.getParameter("page")));
            obj.setPageSize(Integer.parseInt(request.getParameter("size")));
            return ResponseEntity.status(200).body(Collections.singletonMap(Constants.DATA, ProductsServiceImpl.doSearch(obj)));
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            return ResponseEntity.status(400).body(Collections.singletonMap(Constants.DETAIL, e.getMessage()));
        } finally {
            Logs logs1 = new Logs(Constants.SEARCH, ENTITY, request);
            logConfig.submit(logs1);
        }
    }

    @PostMapping("/products/doSearch1")
    public ResponseEntity<Object> getProductClient(@RequestBody ProductDTO obj, HttpServletRequest request) {
        try {
            return ResponseEntity.status(200).body(Collections.singletonMap(Constants.DATA, ProductsServiceImpl.doSearch(obj)));
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            return ResponseEntity.status(400).body(Collections.singletonMap(Constants.DETAIL, e.getMessage()));
        } finally {
            Logs logs1 = new Logs("test", Constants.SEARCH, ENTITY, request);
            logConfig.submit(logs1);
        }
    }

    @PutMapping("/products/insert")
    public ResponseEntity<Object> insert(@RequestBody @Valid ProductDTO obj, BindingResult result, HttpServletRequest request) {
        try {
            if (result.hasErrors()) {
                return ResponseEntity.status(400).body(Collections.singletonMap(Constants.DETAIL, result.getFieldError().getDefaultMessage()));
            } else {
                return ResponseEntity.status(200).body(Collections.singletonMap(Constants.DATA, ProductsServiceImpl.insert(obj)));
            }
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            return ResponseEntity.status(400).body(Collections.singletonMap(Constants.DETAIL, e.getMessage()));
        } finally {
            Logs logs1 = new Logs(Constants.CREATE, ENTITY, request);
            logConfig.submit(logs1);
        }
    }

    @PutMapping("/products/update")
    public ResponseEntity<Object> update(@RequestBody @Valid ProductDTO obj,BindingResult result, HttpServletRequest request) {
        try {
            if (result.hasErrors()) {
                return ResponseEntity.status(400).body(Collections.singletonMap(Constants.DETAIL, result.getFieldError().getDefaultMessage()));
            } else {
                return ResponseEntity.status(200).body(Collections.singletonMap(Constants.DATA, ProductsServiceImpl.update(obj)));
            }
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            return ResponseEntity.status(400).body(Collections.singletonMap(Constants.DETAIL, e.getMessage()));
        } finally {
            Logs logs1 = new Logs(Constants.UPDATE, ENTITY, request);
            logConfig.submit(logs1);
        }
    }

    @DeleteMapping("/products/{id}")
    public ResponseEntity<Object> delete(@PathVariable Long id, HttpServletRequest request) {
        try {
            return ResponseEntity.status(200).body(Collections.singletonMap(Constants.DATA, ProductsServiceImpl.delete(id)));
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            return ResponseEntity.status(400).body(Collections.singletonMap(Constants.DETAIL, e.getMessage()));
        } finally {
            Logs logs1 = new Logs(Constants.DELETE, ENTITY, request);
            logConfig.submit(logs1);
        }
    }
}
