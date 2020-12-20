package com.ato.model.dto;

import com.ato.model.baseModel.AtoBaseDTO;
import com.ato.model.bo.Product;
import com.ato.model.bo.ProductSizeColor;
import lombok.Data;

@Data
public class ProductSizeColorDTO extends AtoBaseDTO {

    private Long id;
    private Long idProduct;
    private Long idSize;
    private String nameSize;
    private String nameColor;
    private Long idColor;
    private Long amount;

    public ProductSizeColor toModel() {
        ProductSizeColor productSizeColor = new ProductSizeColor();
        productSizeColor.setId(this.id);
        productSizeColor.setIdColor(this.idColor);
        productSizeColor.setIdSize(this.idSize);
        productSizeColor.setIdProduct(this.idProduct);
        productSizeColor.setAmount(this.amount);
        return productSizeColor;
    }

}
