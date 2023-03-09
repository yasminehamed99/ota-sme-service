package com.example.otasmeservice.model.data;



import com.example.otasmeservice.model.enums.GeneralTaxType;
import com.example.otasmeservice.model.enums.InvoiceItemTypeEnum;

import jakarta.persistence.*;
import java.math.BigDecimal;

@Entity
@Table(name = "invoice_item")
public class InvoiceItem {
    @Id
    @GeneratedValue
    private int id;

    @Column(name = "uuid")
    private String uuid;

    @Column(name = "product_description", columnDefinition = "VARCHAR2(500)")
    private String productDescription;

    @Column(name= "invoice_item_type",columnDefinition = "VARCHAR(15)")
    @Enumerated(EnumType.STRING)
    private InvoiceItemTypeEnum invoiceItemType;

    @Column(name = "quantity", precision = 38, scale = 3)
    private BigDecimal quantity;

    @Column(name = "unit_price", precision = 38, scale = 3)
    private BigDecimal unitPrice;

    @Column(name = "sub_total_amount", precision = 38, scale = 3)
    private BigDecimal subtotalAmount;

    @Column(name = "discount_amount", precision = 38, scale = 3)
    private BigDecimal discountAmount;

    @Column(name = "total_amount_after_discount", precision = 38, scale = 3)
    private BigDecimal totalAmountAfterDiscount;

    @Column(name = "special_tax_amount", precision = 38, scale = 3)
    private BigDecimal specialTaxAmount;

    @Column(name = "total_after_special_tax", precision = 38, scale = 3)
    private BigDecimal totalAfterSpecialTax;

    @Column(name = "general_tax_type")
    @Enumerated(EnumType.STRING)
    private GeneralTaxType generalTaxType;

    @Column(name = "general_tax_percentage", precision = 38, scale = 3)
    private BigDecimal generalTaxPercentage;

    @Column(name = "general_tax_amount", precision = 38, scale = 3)
    private BigDecimal generalTaxAmount;

    @Column(name = "total_Amount_After_Taxes", precision = 38, scale = 3)
    private BigDecimal totalAmountAfterTaxes;

    @Column(name = "isic4", columnDefinition = "VARCHAR2(2000)")
    private String isic4;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "invoice_id")
    private Invoice invoice;

    public int getInvoiceItemId() {
        return id;
    }

    public void setInvoiceItemId(int id) {
        this.id = id;
    }

    public String getProductDescription() {
        return productDescription;
    }

    public void setProductDescription(String productDescription) {
        this.productDescription = productDescription;
    }

    public BigDecimal getQuantity() {
        return quantity;
    }

    public void setQuantity(BigDecimal quantity) {
        this.quantity = quantity;
    }

    public BigDecimal getDiscountAmount() {
        return discountAmount;
    }

    public void setDiscountAmount(BigDecimal discountAmount) {
        this.discountAmount = discountAmount;
    }

    public BigDecimal getUnitPrice() {
        return unitPrice;
    }

    public void setUnitPrice(BigDecimal unitPrice) {
        this.unitPrice = unitPrice;
    }

    public Invoice getInvoice() {
        return invoice;
    }

    public void setInvoice(Invoice invoice) {
        this.invoice = invoice;
    }

    public BigDecimal getSubtotalAmount() {
        return subtotalAmount;
    }

    public void setSubtotalAmount(BigDecimal subtotalAmount) {
        this.subtotalAmount = subtotalAmount;
    }

    public BigDecimal getTotalAmountAfterDiscount() {
        return totalAmountAfterDiscount;
    }

    public void setTotalAmountAfterDiscount(BigDecimal totalAmountAfterDiscount) {
        this.totalAmountAfterDiscount = totalAmountAfterDiscount;
    }

    public BigDecimal getSpecialTaxAmount() {
        return specialTaxAmount;
    }

    public void setSpecialTaxAmount(BigDecimal specialTaxAmount) {
        this.specialTaxAmount = specialTaxAmount;
    }

    public BigDecimal getGeneralTaxPercentage() {
        return generalTaxPercentage;
    }

    public void setGeneralTaxPercentage(BigDecimal generalTaxPercentage) {
        this.generalTaxPercentage = generalTaxPercentage;
    }

    public BigDecimal getGeneralTaxAmount() {
        return generalTaxAmount;
    }

    public void setGeneralTaxAmount(BigDecimal generalTaxAmount) {
        this.generalTaxAmount = generalTaxAmount;
    }

    public BigDecimal getTotalAmountAfterTaxes() {
        return totalAmountAfterTaxes;
    }

    public void setTotalAmountAfterTaxes(BigDecimal totalAmountAfterTaxes) {
        this.totalAmountAfterTaxes = totalAmountAfterTaxes;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public InvoiceItemTypeEnum getInvoiceItemType() {
        return invoiceItemType;
    }

    public void setInvoiceItemType(InvoiceItemTypeEnum invoiceItemType) {
        this.invoiceItemType = invoiceItemType;
    }

    public GeneralTaxType getGeneralTaxType() {
        return generalTaxType;
    }

    public void setGeneralTaxType(GeneralTaxType generalTaxType) {
        this.generalTaxType = generalTaxType;
    }

    public BigDecimal getTotalAfterSpecialTax() {
        return totalAfterSpecialTax;
    }

    public void setTotalAfterSpecialTax(BigDecimal totalAfterSpecialTax) {
        this.totalAfterSpecialTax = totalAfterSpecialTax;
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public String getIsic4() {
        return isic4;
    }

    public void setIsic4(String isic4) {
        this.isic4 = isic4;
    }
}
