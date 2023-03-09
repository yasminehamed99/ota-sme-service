package com.example.otasmeservice.model.dto;


import com.example.otasmeservice.model.enums.InvoiceStatusEnum;
import com.example.otasmeservice.model.enums.InvoiceTypeEnum;
import com.example.otasmeservice.model.enums.NoteType;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class InvoiceDTO implements Serializable {
    private InvoiceTypeEnum invoiceTypeCode;
    private String invoiceNumber;
    private String buyerInvoiceNumber;
    @JsonFormat(pattern = "dd-MM-yyyy")
    private LocalDate issueDate;
    private InvoiceStatusEnum invoiceStatus;
    private NoteType noteType;
    private String invoiceUniqueIdentifier;
    private String originalInvoiceNumber;
    private String originalInvoiceUUID;
    private BigDecimal originalInvoiceTotal;
    private String qrCode;
    private String qrCodeImage;
    private String reasonOfNote;
    private String notes;
    private SellerDTO sellerDTO;
    private BuyerDTO buyerDTO;
    private ActivityDTO activityDTO;
    private BigDecimal totalAmountExcludingTaxes;
    private BigDecimal totalDiscountsAmount;
    private BigDecimal totalGeneralTaxesAmount;
    private BigDecimal totalSpecialTaxesAmount = new BigDecimal(BigInteger.ZERO);
    private BigDecimal totalPayableAmount;
    private List<InvoiceItemDTO> invoiceItemDTOList = new ArrayList<>();
    private String xml;
}
