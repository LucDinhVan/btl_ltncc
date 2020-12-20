package com.ato.rest;

import com.ato.config.spirngConfig.Constants;
import com.ato.config.spirngConfig.LogConfig;
import com.ato.dao.ImagesDAO;
import com.ato.dao.ProductsDAO;
import com.ato.model.baseModel.Logs;
import com.ato.model.bo.ImageLink;
import com.ato.model.bo.Product;
import com.ato.model.dto.ImageLinkDTO;
import com.ato.model.dto.ProductSizeColorDTO;
import com.ato.service.serviceImpl.ImagesServiceImpl;
import com.ato.service.serviceImpl.ProductSizeColorServiceImpl;
import com.ato.utils.FileUtils;
import com.ato.utils.VNCharacterUtils;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.sql.Timestamp;
import java.util.Collections;

@RestController
@Log4j2
@RequestMapping("/api")
public class ImageResource {
    @Autowired
    ImagesServiceImpl imagesService;

    @Autowired
    LogConfig logConfig;

    @Autowired
    ImagesDAO imagesDAO;

    @Autowired
    ProductsDAO productsDAO;
    private static final String ENTITY = "IMAGE";

    @GetMapping("/images/{id}")
    public ResponseEntity<Object> getByHinhAnh(HttpServletRequest request,  @PathVariable Long id) {
        try {
            return ResponseEntity.status(200).body(Collections.singletonMap(Constants.DATA, imagesService.getAll(id)));
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            return ResponseEntity.status(400).body(Collections.singletonMap(Constants.DETAIL, e.getMessage()));
        } finally {
            Logs logs1 = new Logs(Constants.SEARCH, ENTITY, request);
            logConfig.submit(logs1);
        }
    }

    @PostMapping("/image/lock")
    public ResponseEntity<Object> insert(@RequestBody @Valid ImageLinkDTO obj, BindingResult result, HttpServletRequest request) {
        try {
            if (result.hasErrors()) {
                return ResponseEntity.status(400).body(Collections.singletonMap(Constants.DETAIL, result.getFieldError().getDefaultMessage()));
            } else {
                return ResponseEntity.status(200).body(Collections.singletonMap(Constants.DATA, imagesService.lock(obj)));
            }
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            return ResponseEntity.status(400).body(Collections.singletonMap(Constants.DETAIL, e.getMessage()));
        } finally {
            Logs logs1 = new Logs(Constants.CREATE, ENTITY, request);
            logConfig.submit(logs1);
        }
    }

    @DeleteMapping("/image/{id}")
    public ResponseEntity<Object> delete(@PathVariable Long id, HttpServletRequest request) {
        try {
            return ResponseEntity.status(200).body(Collections.singletonMap(Constants.DATA, imagesService.delete(id)));
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            return ResponseEntity.status(400).body(Collections.singletonMap(Constants.DETAIL, e.getMessage()));
        } finally {
            Logs logs1 = new Logs(Constants.DELETE, ENTITY, request);
            logConfig.submit(logs1);
        }
    }

    @PostMapping("/upload")
    @Transactional
    public ResponseEntity<Object> singleFileUpload(@RequestParam("file") MultipartFile file,
                                                   RedirectAttributes redirectAttributes, HttpServletRequest request) {
        if (file.isEmpty()) {
            redirectAttributes.addFlashAttribute("message", "Please select a file to upload");
            return ResponseEntity.status(400).body(Collections.singletonMap(Constants.DETAIL, "that bai"));
        }
        try {
            Long id = Long.parseLong(request.getParameter("id"));
            Product product = productsDAO.find(id);
            if(product == null){
                return ResponseEntity.status(400).body(Collections.singletonMap(Constants.DETAIL, "Upload hinh anh that bai"));
            }
            FileUtils fileUtils = new FileUtils();
            String path = fileUtils.uploadFile(file, product.getCode());
            ImageLink imageLink = new ImageLink();
            imageLink.setIdProduct(id);
            imageLink.setName(VNCharacterUtils.removeAccent1(file.getOriginalFilename()));
            imageLink.setImageLink(path);
            imageLink.setStatus(1);
            imageLink.setUpdateTime(new Timestamp(System.currentTimeMillis()));
            imagesDAO.insert(imageLink);
            return ResponseEntity.ok().body(Collections.singletonMap(Constants.DATA, "thanh cong"));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(Collections.singletonMap(Constants.DETAIL, e.getMessage()));
        }
    }

}
