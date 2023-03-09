package com.example.otasmeservice.model.dto;

import com.example.otasmeservice.model.enums.GeneralTaxType;
import com.example.otasmeservice.model.enums.InvoiceItemTypeEnum;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.math.BigDecimal;
import java.math.BigInteger;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class InvoiceItemDTO implements Serializable {
    private String uuid;
    private String productDescription;
    private String isic4;
    private InvoiceItemTypeEnum invoiceItemType;
    private GeneralTaxType generalTaxType;
    private BigDecimal quantity;
    private BigDecimal unitPrice;
    private BigDecimal subtotalAmount;
    private BigDecimal discountAmount;
    private BigDecimal totalAmountAfterDiscount;
    private BigDecimal specialTaxAmount;
    private BigDecimal generalTaxPercentage;
    private BigDecimal generalTaxAmount;
    private BigDecimal totalAmountAfterTaxes;
    private BigDecimal totalAfterSpecialTax = new BigDecimal(BigInteger.ZERO);

}
