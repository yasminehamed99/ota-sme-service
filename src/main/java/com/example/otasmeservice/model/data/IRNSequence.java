package com.example.otasmeservice.model.data;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "irn_sequence")
@Data

public class IRNSequence {

    @Id
    @GeneratedValue
    private Long id;

    @Column(name = "tax_number")
    private String taxNumber;
    @Column(name = "sequence")
    private Integer sequence;


}
