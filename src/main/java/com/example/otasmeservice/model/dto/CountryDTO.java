package com.example.otasmeservice.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class CountryDTO {

    private String countryCode;
    private String countryNameEn;
    private String countryNameAr;
}
