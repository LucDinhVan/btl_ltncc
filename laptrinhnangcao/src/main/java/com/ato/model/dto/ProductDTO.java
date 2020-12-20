package com.ato.model.dto;

import com.ato.model.baseModel.AtoBaseDTO;
import com.ato.model.bo.ImageLink;
import com.ato.model.bo.Product;
import com.ato.model.bo.ProductSizeColor;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.List;

@Data
public class ProductDTO extends AtoBaseDTO {

    private Long id;
    @Size(max = 60, message = "{common.maxLength}")
    @NotBlank(message = "{common.product.name} {common.required}")
    private String name;
    @Size(max = 60, message = "{common.maxLength}")
    @NotBlank(message = "{common.product.code} {common.required}")
    private String code;
//    @NotBlank(message = "{common.product.price} {common.required}")
//    @Size(max = 60, message = "{common.maxLength}")
    private String price;
//    @Size(max = 60, message = "{common.maxLength}")
    private String parenObject;
    private Long idParen;
    private Integer quantity;
    private BigDecimal cost;
    private Long totalProduct;

    private Integer rate;
    private Timestamp updateTime;
    private Timestamp relaseDate;

    private List<ProductSizeColor> productSizeColorList;
    private List<ImageLink> imageLinks;

    // add model product-size-color
    private Long size;
    private Long color;
    private Long amount;
    private Long status;

    public Product toModel() {
        Product product = new Product();
        product.setId(this.id);
        product.setName(this.name);
        product.setCode(this.code);
        product.setQuantity(this.quantity);
        product.setCost(this.cost);
        product.setRate(this.rate);
        product.setStatus(this.status);
        product.setUpdateTime(this.updateTime);
        product.setIdObjects(this.idParen);
        product.setRelaseDate(this.relaseDate);
        return product;
    }

}
