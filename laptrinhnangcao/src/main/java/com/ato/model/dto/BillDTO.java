package com.ato.model.dto;

import com.ato.model.baseModel.AtoBaseDTO;
import com.ato.model.bo.Bill;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.sql.Timestamp;

@Data
public class BillDTO extends AtoBaseDTO {

    private Long id;
    private Long idUser;
    private Integer heavyBuying;
    private Long totalBuy;
    private Long discount;
    @Size(max = 45, message = "{common.bill.counter} {common.maxLength}")
    @NotBlank(message = "{common.bill.counter}  {common.required}")
    private String counter;
    private Long payment;
    @Size(max = 60, message = "{common.bill.receivingAddress} {common.maxLength}")
    @NotBlank(message = "{common.action.receivingAddress} {common.required}")
    private String receivingAddress;
    @Size(max = 45, message = "{common.bill.paymentMethod}  {common.maxLength}")
    @NotBlank(message = "{common.action.paymentMethod} {common.required}")
    private String paymentMethod;
    private Timestamp buyingDate;


    public Bill toModel() {
        Bill bill = new Bill();
        bill.setId(this.id);
        bill.setIdUser(this.idUser);
        bill.setHeavyBuying(this.heavyBuying);
        bill.setTotalBuy(this.totalBuy);
        bill.setDiscount(this.discount);
        bill.setCounter(this.counter);
        bill.setPayment(this.payment);
        bill.setReceivingAddress(this.receivingAddress);
        bill.setPaymentMethod(this.paymentMethod);
        bill.setBuyingDate(this.buyingDate);
        return bill;
    }

}
