package com.example.otasmeservice.model.dto;

import lombok.*;

import java.io.Serializable;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class GenericErrorDTO implements Serializable {
    private String error;
}
