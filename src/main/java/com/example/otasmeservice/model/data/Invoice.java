package com.example.otasmeservice.model.data;



import com.example.otasmeservice.model.enums.InvoiceStatusEnum;
import com.example.otasmeservice.model.enums.InvoiceTypeEnum;
import com.example.otasmeservice.model.enums.NoteType;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.sql.Date;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "invoice")
public class Invoice {
    @Id
    @GeneratedValue
    private Long id;

    @Column(name = "invoice_type", nullable = false)
    @Enumerated(EnumType.STRING)
    private InvoiceTypeEnum invoiceTypeCode;

    @Column(name = "note_type", columnDefinition = "VARCHAR(15)")
    @Enumerated(EnumType.STRING)
    private NoteType noteType;

    @Column(name = "buyer_invoice_number", columnDefinition = "VARCHAR(255)")
    private String buyerInvoiceNumber;

    @Column(name = "issue_date", nullable = false)
    private Date issueDate;

    @Column(name = "invoice_number", nullable = false, columnDefinition = "VARCHAR(255)")
    private String invoiceNumber;

    @Column(name= "invoice_status",columnDefinition = "VARCHAR(255)")
    @Enumerated(EnumType.STRING)
    private InvoiceStatusEnum invoiceStatus;

    @Column(name = "invoice_unique_identifier",nullable = false,columnDefinition = "VARCHAR(255)")
    private String invoiceUniqueIdentifier;

    @Column(name = "edit_done",nullable = false)
    private Boolean editDone;


    private String qrCode;

    private String notes;

    private String reasonOfNote;

    @Column(name= "total_excluding_taxes", precision=38, scale=3)
    private BigDecimal totalExcludingTaxes;

    @Column(name = "total_discounts_amount", precision = 38, scale = 3)
    private BigDecimal totalDiscountsAmount;

    @Column(name = "total_payable_amount", precision = 38, scale = 3)
    private BigDecimal totalPayableAmount;

    @Column(name = "total_refund_amount", precision = 38, scale = 3)
    private BigDecimal totalRefundAmount;

    @Column(name = "total_general_taxes_amount", precision = 38, scale = 3)
    private BigDecimal totalGeneralTaxesAmount;

    @Column(name = "total_special_taxes_amount", precision = 38, scale = 3)
    private BigDecimal totalSpecialTaxesAmount;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "original_invoice_id", referencedColumnName = "id")
    private Invoice originalInvoice;

    @OneToOne
    private User user;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "buyer_id")
    private Buyer buyer;

    @OneToOne
    private Activity activity;

    @Column(name = "xml_file", columnDefinition = "BLOB")
    private byte[] xmlFile;

    @OneToMany(fetch = FetchType.EAGER,cascade=CascadeType.ALL, mappedBy="invoice")
    private Set<InvoiceItem> invoiceItems = new HashSet<>();

    public Activity getActivity() {
        return activity;
    }

    public void setActivity(Activity activity) {
        this.activity = activity;
    }


    public Long getInvoiceId() {
        return id;
    }

    public void setInvoiceId(Long id) {
        this.id = id;
    }

    public InvoiceTypeEnum getInvoiceTypeCode() {
        return invoiceTypeCode;
    }

    public void setInvoiceTypeCode(InvoiceTypeEnum invoiceTypeCode) {
        this.invoiceTypeCode = invoiceTypeCode;
    }

    public Date getIssueDate() {
        return issueDate;
    }

    public void setIssueDate(Date issueDate) {
        this.issueDate = issueDate;
    }

    public String getInvoiceNumber() {
        return invoiceNumber;
    }

    public void setInvoiceNumber(String invoiceNumber) {
        this.invoiceNumber = invoiceNumber;
    }

    public InvoiceStatusEnum getInvoiceStatus() {
        return invoiceStatus;
    }

    public void setInvoiceStatus(InvoiceStatusEnum invoiceStatus) {
        this.invoiceStatus = invoiceStatus;
    }

    public String getInvoiceUniqueIdentifier() {
        return invoiceUniqueIdentifier;
    }

    public void setInvoiceUniqueIdentifier(String invoiceUniqueIdentifier) {
        this.invoiceUniqueIdentifier = invoiceUniqueIdentifier;
    }

    public String getQrCode() {
        return qrCode;
    }

    public void setQrCode(String qrCode) {
        this.qrCode = qrCode;
    }

    public String getReasonOfNote() {
        return reasonOfNote;
    }

    public void setReasonOfNote(String reasonOfNote) {
        this.reasonOfNote = reasonOfNote;
    }

    public BigDecimal getTotalPayableAmount() {
        return totalPayableAmount;
    }

    public void setTotalPayableAmount(BigDecimal totalPayableAmount) {
        this.totalPayableAmount = totalPayableAmount;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Buyer getBuyer() {
        return buyer;
    }

    public void setBuyer(Buyer buyer) {
        this.buyer = buyer;
    }

    public String getBuyerInvoiceNumber() {
        return buyerInvoiceNumber;
    }

    public void setBuyerInvoiceNumber(String buyerInvoiceNumber) {
        this.buyerInvoiceNumber = buyerInvoiceNumber;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public BigDecimal getTotalExcludingTaxes() {
        return totalExcludingTaxes;
    }

    public void setTotalExcludingTaxes(BigDecimal totalExcludingTaxes) {
        this.totalExcludingTaxes = totalExcludingTaxes;
    }

    public BigDecimal getTotalDiscountsAmount() {
        return totalDiscountsAmount;
    }

    public void setTotalDiscountsAmount(BigDecimal totalDiscountsAmount) {
        this.totalDiscountsAmount = totalDiscountsAmount;
    }

    public BigDecimal getTotalGeneralTaxesAmount() {
        return totalGeneralTaxesAmount;
    }

    public void setTotalGeneralTaxesAmount(BigDecimal totalGeneralTaxesAmount) {
        this.totalGeneralTaxesAmount = totalGeneralTaxesAmount;
    }

    public byte[] getXmlFile() {
        return xmlFile;
    }

    public void setXmlFile(byte[] xmlFile) {
        this.xmlFile = xmlFile;
    }

    public Set<InvoiceItem> getInvoiceItems() {
        return invoiceItems;
    }

    public void setInvoiceItems(Set<InvoiceItem> invoiceItems) {
        this.invoiceItems = invoiceItems;
    }

    public NoteType getNoteType() {
        return noteType;
    }

    public void setNoteType(NoteType noteType) {
        this.noteType = noteType;
    }

    public BigDecimal getTotalSpecialTaxesAmount() {
        return totalSpecialTaxesAmount;
    }

    public void setTotalSpecialTaxesAmount(BigDecimal totalSpecialTaxesAmount) {
        this.totalSpecialTaxesAmount = totalSpecialTaxesAmount;
    }

    public Boolean getEditDone() {
        return editDone;
    }

    public void setEditDone(Boolean editDone) {
        this.editDone = editDone;
    }

    public BigDecimal getTotalRefundAmount() {
        return totalRefundAmount;
    }

    public void setTotalRefundAmount(BigDecimal totalRefundAmount) {
        this.totalRefundAmount = totalRefundAmount;
    }

    public Invoice getOriginalInvoice() {
        return originalInvoice;
    }

    public void setOriginalInvoice(Invoice originalInvoice) {
        this.originalInvoice = originalInvoice;
    }
}
