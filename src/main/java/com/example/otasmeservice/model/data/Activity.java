package com.example.otasmeservice.model.data;
import com.example.otasmeservice.model.enums.ActivityUsage;

import jakarta.persistence.*;

@Entity
@Table(name = "activity")
public class Activity {

    @Id
    @GeneratedValue
    private Long id;

    @Column(name = "activity")
    private String activity;

    @Column(name = "description")
    private String description;

    @Column(name = "activity_usage")
    @Enumerated(EnumType.STRING)
    private ActivityUsage activityUsage;

    @Column(name = "invoice_type")
    private int invoiceType;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "taxpayer_id", nullable = false)
    private Taxpayer taxpayer;

    public Activity() {

    }

    public Activity(String activity, String description, Taxpayer taxpayer, ActivityUsage activityUsage, int invoiceType) {
        this.activity = activity;
        this.description = description;
        this.taxpayer = taxpayer;
        this.activityUsage = activityUsage;
        this.invoiceType = invoiceType;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getActivity() {
        return activity;
    }

    public void setActivity(String activity) {
        this.activity = activity;
    }

    public Taxpayer getTaxpayer() {
        return taxpayer;
    }

    public void setTaxpayer(Taxpayer taxpayer) {
        this.taxpayer = taxpayer;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public ActivityUsage getActivityUsage() {
        return activityUsage;
    }

    public void setActivityUsage(ActivityUsage activityUsage) {
        this.activityUsage = activityUsage;
    }

    public int getInvoiceType() {
        return invoiceType;
    }

    public void setInvoiceType(int invoiceType) {
        this.invoiceType = invoiceType;
    }
}
