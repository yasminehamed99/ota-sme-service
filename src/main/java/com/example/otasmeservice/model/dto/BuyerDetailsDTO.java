package com.example.otasmeservice.model.dto;

import com.example.otasmeservice.model.data.Country;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BuyerDetailsDTO {
    private String buyerName;
    private Country country;
    private SellerDTO taxpayerDTO;
}
