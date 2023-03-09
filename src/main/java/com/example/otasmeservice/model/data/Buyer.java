package com.example.otasmeservice.model.data;
import com.example.otasmeservice.model.enums.AdditionalBuyerIdType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;

import jakarta.persistence.*;

@Entity
@Table(name = "buyer")
public class Buyer {
    @Id
    @GeneratedValue
    private int id;

    @Enumerated(EnumType.STRING)
    @Column(name = "additional_buyer_id_type")
    private AdditionalBuyerIdType additionalBuyerIdType;

    private String additionalBuyerId;
    private String buyerName;
    private String phoneNumber;
    private String postalCode;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "province_id")
    private Province province;

    public int getBuyerId() {
        return id;
    }

    public void setBuyerId(int id) {
        this.id= id;
    }

    public String getAdditionalBuyerId() {
        return additionalBuyerId;
    }

    public void setAdditionalBuyerId(String additionalBuyerId) {
        this.additionalBuyerId = additionalBuyerId;
    }

    public String getBuyerName() {
        return buyerName;
    }

    public void setBuyerName(String buyerName) {
        this.buyerName = buyerName;
    }

    public String getPostalCode() {
        return postalCode;
    }

    public void setPostalCode(String postalCode) {
        this.postalCode = postalCode;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public AdditionalBuyerIdType getAdditionalBuyerIdType() {
        return additionalBuyerIdType;
    }

    public void setAdditionalBuyerIdType(AdditionalBuyerIdType additionalBuyerIdType) {
        this.additionalBuyerIdType = additionalBuyerIdType;
    }

    public Province getProvince() {
        return province;
    }

    public void setProvince(Province province) {
        this.province = province;
    }
}
