package com.ato.service.serviceImpl;

import com.ato.dao.ImageLinkDAO;
import com.ato.dao.ProductSizeColorDAO;
import com.ato.dao.ProductsDAO;
import com.ato.model.baseModel.DataListDTO;
import com.ato.model.bo.ImageLink;
import com.ato.model.bo.Product;
import com.ato.model.bo.ProductSizeColor;
import com.ato.model.dto.ProductDTO;
import com.ato.service.IProductService;
import com.ato.utils.Translator;
import com.ato.utils.Utils;
import lombok.extern.log4j.Log4j2;
import org.hibernate.service.spi.ServiceException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;
import java.util.List;

@Log4j2
@Service
@Transactional
public class ProductsServiceImpl implements IProductService {

    @Autowired
    ProductsDAO productsDAO;

    @Autowired
    ImageLinkDAO imageLinkDAO;

    @Autowired
    ProductSizeColorDAO productSizeColorDAO;

    @Override
    public DataListDTO getAll() {
        DataListDTO dataListDTO = new DataListDTO();
        try {
            List<Product> list = productsDAO.findAll();
            dataListDTO.setList(list);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            throw new ServiceException(Translator.toLocale("error.users.false"));
        }
        return dataListDTO;
    }

    @Override
    public DataListDTO doSearch(ProductDTO obj) {
        DataListDTO dataListDTO = new DataListDTO();
        try {
            List<ProductDTO> list = productsDAO.doSearch(obj);
            for (ProductDTO productDTO : list) {
                productDTO.setPrice(Utils.formatCurrency(productDTO.getCost()));
                List<ImageLink> imageLinks = imageLinkDAO.find("idProduct", productDTO.getId());
                productDTO.setImageLinks(imageLinks);
            }
            dataListDTO.setList(list);
            dataListDTO.setTotalPages(obj.getTotalRecord());
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            throw new ServiceException(Translator.toLocale("error.users.false"));
        }
        return dataListDTO;
    }

    @Override
    public Long insert(ProductDTO obj){
        Product product = productsDAO.findByFiled("code", obj.getCode());
        if (product != null) {
            throw new ServiceException("Mã sản phẩm đã tồn tại");
        }
        try {
            obj.setUpdateTime(new Timestamp(System.currentTimeMillis()));
            obj.setName(obj.getName().toUpperCase());
            productsDAO.insert(obj.toModel());
//            Product product1 = productsDAO.findByFiled("code", obj.getCode());
//            if(obj.getProductSizeColorList().isEmpty()){
//                throw new ServiceException("có lỗi trong quá trình thêm mới sản phẩm");
//            }
//            IntStream.range(0, obj.getProductSizeColorList().size()).forEach(i -> {
//                ProductSizeColor productSizeColor = new ProductSizeColor();
//                productSizeColor.setIdColor(obj.getProductSizeColorList().get(i).getIdColor());
//                productSizeColor.setIdProduct(product1.getId());
//                productSizeColor.setIdSize(obj.getProductSizeColorList().get(i).getIdSize());
//                productSizeColor.setAmount(obj.getProductSizeColorList().get(i).getAmount());
//                productSizeColorDAO.insert(productSizeColor);
//            });
            return obj.getId();
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            throw new ServiceException("Thêm mới sản phẩm không thành công");
        }
    }

    @Override
    public Long update(ProductDTO obj){
        try {
            Product product = productsDAO.find(obj.getId());
            if (product == null){
                throw new ServiceException("Thông tin sản phẩm không đúng");
            }
            obj.setUpdateTime(new Timestamp(System.currentTimeMillis()));
            productsDAO.update(obj.toModel());
            return obj.getId();
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            throw new ServiceException(Translator.toLocale("error.users.false"));
        }
    }


    @Override
    public Long delete(Long id){
        try {
            Product product = productsDAO.find(id);
            if (product == null){
                throw new ServiceException("Mã sản phẩm không đúng");
            }
            productsDAO.delete(product);
            List<ProductSizeColor> productSizeColors = productSizeColorDAO.find("idProduct", product.getId());
            for (ProductSizeColor productSizeColor : productSizeColors) {
                productSizeColorDAO.delete(productSizeColor);
            }
            return id;
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            throw new ServiceException(Translator.toLocale("error.users.false"));
        }
    }

}

