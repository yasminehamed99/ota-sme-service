package com.example.otasmeservice.model.dto;

import com.example.otasmeservice.model.enums.InvoiceStatus;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class SMEResult {
    private InvoiceStatus invoiceStatus;
    private String submittedInvoice;
    private String qrCode;
    private String invoiceNumber;
    private String invoiceUUID;
}
