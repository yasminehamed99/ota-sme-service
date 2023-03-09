package com.example.otasmeservice.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class InvoiceCreationResponseDTO {
    private String invoiceNumber;
    private String invoiceUniqueIdentifier;
}
