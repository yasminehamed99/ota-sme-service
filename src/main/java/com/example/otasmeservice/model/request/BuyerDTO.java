package com.example.otasmeservice.model.request;

import com.example.otasmeservice.model.enums.AdditionalBuyerIdType;
import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class BuyerDTO {
    private String buyerEnglishName;
    private String buyerArabicName;
    private String buyerVatNumber;
    private String buyerGroupVatNumber;
    private AdditionalBuyerIdType additionalBuyerIdType;
    private String additionalBuyerId;
    private String buyerStreetEn;
    private String buyerStreetAr;
    private String additionalBuyerStreetEn;
    private String additionalBuyerStreetAr;
    private String buyerBuildingNumber;
    private String additionalBuyerBuildingNumber;
    private String buyerCity;
    private String buyerPostalCode;
    private String buyerStateEn;
    private String buyerStateAr;
    private String buyerNeighborhoodEn;
    private String buyerNeighborhoodAr;
    private String buyerCountryCode;
}
