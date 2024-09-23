package com.quickkoala.entity.supplier;


import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;
import lombok.Data;

import java.time.LocalDateTime;

@Entity
@Data
@Table(name = "supplier_notices")
public class SupplierNoticeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;

    private String content;

    private String manager;
    
    @Column(name = "manager_company_code")
    private String companyCode;
    
    @Column(name = "created_at")
    private LocalDateTime createdAt;

    private int views;

    @PrePersist
    public void prePersist() {
        this.createdAt = LocalDateTime.now();
        this.views = 0;
    }

}
