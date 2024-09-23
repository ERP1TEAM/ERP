package com.quickkoala.repository.supplier;


import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.quickkoala.entity.sales.SalesNoticeEntity;
import com.quickkoala.entity.supplier.SupplierNoticeEntity;

public interface SupplierNoticeRepository extends JpaRepository<SalesNoticeEntity, Long> {
    @Query("SELECT n FROM SupplierNoticeEntity n ORDER BY n.createdAt DESC")
    Page<SupplierNoticeEntity> getNotices(Pageable pageable);
    
    @Query("SELECT n FROM SupplierNoticeEntity n WHERE n.companyCode = :managerCompanyCode ORDER BY n.createdAt DESC")
    Page<SupplierNoticeEntity> getNoticesByManagerCompanyCode(String managerCompanyCode, Pageable pageable);
}