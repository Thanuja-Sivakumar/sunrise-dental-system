package com.sunrise.dental.model;

import jakarta.persistence.*;
import java.math.BigDecimal;

@Entity
@Table(name = "treatment_types")
public class TreatmentType {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private BigDecimal cost;

    @Column(name = "consultation_fee", nullable = false)
    private BigDecimal consultationFee;

    public TreatmentType() {}

    public TreatmentType(String name, BigDecimal cost, BigDecimal consultationFee) {
        this.name = name;
        this.cost = cost;
        this.consultationFee = consultationFee;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public BigDecimal getCost() { return cost; }
    public void setCost(BigDecimal cost) { this.cost = cost; }
    public BigDecimal getConsultationFee() { return consultationFee; }
    public void setConsultationFee(BigDecimal consultationFee) { this.consultationFee = consultationFee; }
}
