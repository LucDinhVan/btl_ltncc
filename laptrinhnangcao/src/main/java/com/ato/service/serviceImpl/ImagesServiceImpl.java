package com.ato.service.serviceImpl;

import com.ato.dao.ImagesDAO;
import com.ato.dao.ProductSizeColorDAO;
import com.ato.dao.ProductsDAO;
import com.ato.model.baseModel.DataListDTO;
import com.ato.model.bo.Action;
import com.ato.model.bo.ImageLink;
import com.ato.model.bo.Product;
import com.ato.model.dto.ActionDTO;
import com.ato.model.dto.ImageLinkDTO;
import com.ato.utils.Translator;
import lombok.extern.log4j.Log4j2;
import org.hibernate.service.spi.ServiceException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.sql.Timestamp;
import java.util.List;

@Log4j2
@Service
@Transactional
public class ImagesServiceImpl {

    @Autowired
    ImagesDAO imagesDAO;

    @Autowired
    ProductsDAO productsDAO;

    @Autowired
    ProductSizeColorDAO productSizeColorDAO;

    public DataListDTO getAll(Long id) {
        DataListDTO dataListDTO = new DataListDTO();
        try {
            Product product = productsDAO.findByFiled("id", id);
            if (product != null) {
                List<ImageLink> list = imagesDAO.find("idProduct", product.getId());
                dataListDTO.setList(list);
            } else {
                log.error("thong tin san pham khong dung ");
                throw new ServiceException(Translator.toLocale("error.users.false"));
            }
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            throw new ServiceException(Translator.toLocale("error.users.false"));
        }
        return dataListDTO;
    }

    public Long lock(ImageLinkDTO obj) {
        try {
            Product product = productsDAO.findByFiled("id", obj.getIdProduct());
            if (product != null) {
                ImageLink imageLink = imagesDAO.find(obj.getId());
                if (imageLink.getStatus() == 1) {
                    imageLink.setStatus(0);
                } else if (imageLink.getStatus() == 0) {
                    imageLink.setStatus(1);
                }
                imagesDAO.update(imageLink);
            } else {
                log.error("thong tin san pham khong dung ");
                throw new ServiceException(Translator.toLocale("error.users.false"));
            }
            return obj.getId();
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            throw new ServiceException("Có lỗi trong quá trình thêm mới");
        }
    }

    public Long delete(Long id) {
        try {
            ImageLink imageLink = imagesDAO.find(id);
            if (imageLink == null){
                throw new ServiceException("Anh da duoc xoa!");
            }
            imagesDAO.delete(imageLink);
            return id;
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            throw new ServiceException(Translator.toLocale("error.users.false"));
        }
    }

//    public Long insert(MultipartFile file){
//        Action action = actionsDAO.findByFiled("code", obj.getCode());
//        if (action != null) {
//            throw new ServiceException("Mã Action đã tồn tại");
//        }
//        try {
//            obj.setUpdateTime(new Timestamp(System.currentTimeMillis()));
//            actionsDAO.insert(obj.toModel());
//            return obj.getId();
//        } catch (Exception e) {
//            log.error(e.getMessage(), e);
//            throw new ServiceException("Thêm mới action không thành công");
//        }
//    }

}

