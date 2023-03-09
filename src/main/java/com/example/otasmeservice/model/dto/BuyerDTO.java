package com.example.otasmeservice.model.dto;


import com.example.otasmeservice.model.enums.AdditionalBuyerIdType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BuyerDTO {
    private int id;
    private String buyerName;
    private String postalCode;
    private String phoneNumber;
    private AdditionalBuyerIdType additionalBuyerIdType;
    private String additionalBuyerId;
    private ProvinceDTO provinceDTO;
}
