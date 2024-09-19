package com.quickkoala.entity.sales;

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
@Table(name = "sales") 
@Data
public class SalesEntity {

	@Id
    @Column(name = "code", length = 6, nullable = false)
    private String code;

    @Column(name = "name", length = 255, nullable = false)
    private String name;

    @Column(name = "email", length = 255, nullable = false)
    private String email;

    @Column(name = "tel", length = 11, nullable = false)
    private String tel;

    @Column(name = "address", length = 500, nullable = false)
    private String address;

    @Column(name = "create_date", nullable = false, columnDefinition = "DATETIME DEFAULT NOW()")
    private LocalDateTime createDate;
}
