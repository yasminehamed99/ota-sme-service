package com.example.otasmeservice.model.dto;
import com.example.otasmeservice.model.enums.InvoiceStatusEnum;
import com.example.otasmeservice.model.enums.InvoiceTypeEnum;
import com.example.otasmeservice.model.enums.NoteType;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class InvoiceListItemDTO {
    private String invoiceUniqueIdentifier;
    private String invoiceNumber;
    private InvoiceTypeEnum invoiceTypeCode;
    private String userName;
    private NoteType noteType;
    @JsonFormat(pattern = "dd-MM-yyyy")
    private LocalDate issueDate;
    private BigDecimal totalPayableAmount;
    private InvoiceStatusEnum invoiceStatusEnum;
}
