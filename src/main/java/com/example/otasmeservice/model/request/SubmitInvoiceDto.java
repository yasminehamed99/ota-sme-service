package com.example.otasmeservice.model.request;

import com.example.otasmeservice.model.enums.InvoiceItemTypeEnum;
import com.example.otasmeservice.model.enums.InvoiceTypeEnum;
import com.example.otasmeservice.model.enums.NoteType;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SubmitInvoiceDto {
    private String invoiceNumber;
    private String buyerInvoiceNumber;
    @JsonFormat(pattern = "dd-MM-yyyy")
    private LocalDate invoiceIssueDate;
    private InvoiceItemTypeEnum invoiceTypeDescription;
    private InvoiceTypeEnum invoiceTypeTransaction;
    private NoteType noteType;
    private String invoiceReferenceNumber;
    private BuyerDTO buyer;
    private BigDecimal totalAmountExcludingVat;
    private BigDecimal totalDiscountsAmount;
    private BigDecimal totalVatAmount;
    private BigDecimal totalPayableAmount;
    private BigDecimal tottalIncludingVat;
    private List<InvoiceItemDTO> invoiceItemDTOList;
}
