package com.example.otasmeservice.model.request;

import com.example.otasmeservice.model.enums.GeneralTaxType;
import com.example.otasmeservice.model.enums.InvoiceItemTypeEnum;
import lombok.*;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class InvoiceItemDTO {
    private String descriptionAr;
    private String descriptionEn;
    private BigDecimal unitPrice;
    private BigDecimal quantity;
    private BigDecimal subtotalAmount;
    private BigDecimal discountAmount;
    private BigDecimal discountPercentage;
    private GeneralTaxType vatRateType;
    private BigDecimal vatRate;
    private BigDecimal vatAmount;
    private BigDecimal totalAmount;
    private String uuid;
}
