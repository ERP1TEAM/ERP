//상품 테이블 엔티티
package com.quickkoala.entity.stock;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Data
@Table(name = "product")
public class ProductEntity {

    @Id
    @Column(name = "code", length = 8, nullable = false)
    private String code;

    @Column(name = "supplier_code", length = 6, nullable = false)
    private String supplierCode;

    @Column(name = "classification_code", length = 6, nullable = false)
    private String classificationCode;

    @Column(name = "storage_location", length = 6)
    private String storageLocation;

    @Column(name = "use_flag", nullable = false)
    @Enumerated(EnumType.STRING)
    private UseFlag useFlag;

    @Column(name = "name", length = 200, nullable = false)
    private String name;

    @Column(name = "price", nullable = false)
    private int price;

    @Column(name = "created_dt", nullable = false)
    private LocalDateTime createdDt;

    @Column(name = "created_manager", length = 100, nullable = false)
    private String createdManager;

    @Column(name = "manager", length = 100, nullable = false)
    private String manager;

    @Column(name = "updated_dt", nullable = false)
    private LocalDateTime updatedDt;

    @Column(name = "memo", columnDefinition = "TEXT")
    private String memo;

    public enum UseFlag {
        Y, N
    }
}
