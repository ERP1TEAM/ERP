package com.quickkoala.entity.stock;

import java.time.LocalDate;
import java.time.LocalDateTime; 

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Data
@Table(name = "lot")
public class LotEntity {
	@Id
    @Column(name = "lot_number", nullable = false, length = 20)
    private String lotNumber;

    @Column(name = "product_code", nullable = false, length = 8)
    private String productCode;

    @Column(name = "supplier_code", nullable = false, length = 6)
    private String supplierCode;

    @Column(name = "storage_location", nullable = false, length = 8)
    private String storageLocation;

    @Column(name = "quantity", nullable = false)
    private int quantity;

    @Column(name = "production_date", nullable = false)
    private LocalDate productionDate;

    @Column(name = "receive_date", nullable = false)
    private LocalDateTime receiveDate;

    @Column(name = "last_update", nullable = false)
    private LocalDateTime lastUpdate;
}
