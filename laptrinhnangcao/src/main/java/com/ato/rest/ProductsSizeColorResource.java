package com.ato.rest;

import com.ato.config.spirngConfig.Constants;
import com.ato.config.spirngConfig.LogConfig;
import com.ato.model.baseModel.Logs;
import com.ato.model.dto.ProductSizeColorDTO;
import com.ato.service.serviceImpl.ProductSizeColorServiceImpl;
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
public class ProductsSizeColorResource {
    @Autowired
    ProductSizeColorServiceImpl productSizeColorService;

    @Autowired
    LogConfig logConfig;

    private static final String ENTITY = "PRODUCTS_SIZE_COLOR";

    @GetMapping("/products/detail/{id}")
    public ResponseEntity<Object> getByMaSanPham(HttpServletRequest request,  @PathVariable Long id) {
        try {
            return ResponseEntity.status(200).body(Collections.singletonMap(Constants.DATA, productSizeColorService.getAllSizeColor(id)));
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            return ResponseEntity.status(400).body(Collections.singletonMap(Constants.DETAIL, e.getMessage()));
        } finally {
            Logs logs1 = new Logs(Constants.SEARCH, ENTITY, request);
            logConfig.submit(logs1);
        }
    }

    @PutMapping("/productsSizeColor/insert")
    public ResponseEntity<Object> insert(@RequestBody @Valid ProductSizeColorDTO obj, BindingResult result, HttpServletRequest request) {
        try {
            if (result.hasErrors()) {
                return ResponseEntity.status(400).body(Collections.singletonMap(Constants.DETAIL, result.getFieldError().getDefaultMessage()));
            } else {
                return ResponseEntity.status(200).body(Collections.singletonMap(Constants.DATA, productSizeColorService.insert(obj)));
            }
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            return ResponseEntity.status(400).body(Collections.singletonMap(Constants.DETAIL, e.getMessage()));
        } finally {
            Logs logs1 = new Logs(Constants.CREATE, ENTITY, request);
            logConfig.submit(logs1);
        }
    }

    @DeleteMapping("/productsSizeColor/{id}")
    public ResponseEntity<Object> delete(@PathVariable Long id, HttpServletRequest request) {
        try {
            return ResponseEntity.status(200).body(Collections.singletonMap(Constants.DATA, productSizeColorService.delete(id)));
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            return ResponseEntity.status(400).body(Collections.singletonMap(Constants.DETAIL, e.getMessage()));
        } finally {
            Logs logs1 = new Logs(Constants.DELETE, ENTITY, request);
            logConfig.submit(logs1);
        }
    }
}
