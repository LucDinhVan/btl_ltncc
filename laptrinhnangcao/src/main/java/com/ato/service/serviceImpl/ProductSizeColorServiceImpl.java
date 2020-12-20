package com.ato.service.serviceImpl;

import com.ato.dao.ProductSizeColorDAO;
import com.ato.dao.ProductsDAO;
import com.ato.model.baseModel.DataListDTO;
import com.ato.model.bo.Product;
import com.ato.model.bo.ProductSizeColor;
import com.ato.model.dto.ProductSizeColorDTO;
import com.ato.service.IProductSizeColorService;
import com.ato.utils.Translator;
import lombok.extern.log4j.Log4j2;
import org.hibernate.service.spi.ServiceException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Log4j2
@Service
@Transactional
public class ProductSizeColorServiceImpl implements IProductSizeColorService {

    @Autowired
    ProductsDAO productsDAO;

    @Autowired
    ProductSizeColorDAO productSizeColorDAO;

    @Override
    public DataListDTO getAllSizeColor(Long id) {
        DataListDTO dataListDTO = new DataListDTO();
        try {
            Product product = productsDAO.findByFiled("id", id);
            if (product != null) {
                List<ProductSizeColorDTO> list = productSizeColorDAO.doSearch(product.getId());
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

    @Override
    public Long insert(ProductSizeColorDTO obj) {
        try {
            String[] cars = {"idSize", "idColor", "idProduct"};
            Object obj1[] = {obj.getIdSize(), obj.getIdColor(), obj.getIdProduct()};
            List<ProductSizeColor> list = productSizeColorDAO.find(cars, obj1);
            if (list.size() == 1) {
                ProductSizeColor productSizeColor = new ProductSizeColor();
                productSizeColor.setAmount(list.get(0).getAmount() + obj.getAmount());
                productSizeColor.setIdProduct(list.get(0).getIdProduct());
                productSizeColor.setId(list.get(0).getId());
                productSizeColor.setIdSize(list.get(0).getIdSize());
                productSizeColor.setIdColor(list.get(0).getIdColor());
                productSizeColorDAO.update(productSizeColor);
            } else if (list.size() == 0) {
                productSizeColorDAO.insert(obj.toModel());
            } else {
                throw new ServiceException("Có lỗi trong quá trình thêm mới");
            }
            return obj.getId();
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            throw new ServiceException("Có lỗi trong quá trình thêm mới");
        }
    }

    @Override
    public Long delete(Long id) {
        try {
            ProductSizeColor productSizeColor = productSizeColorDAO.find(id);
            if (productSizeColor == null) {
                throw new ServiceException("Mã sản phẩm không đúng");
            }
            productSizeColorDAO.delete(productSizeColor);
            return id;
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            throw new ServiceException(Translator.toLocale("error.users.false"));
        }
    }

}

