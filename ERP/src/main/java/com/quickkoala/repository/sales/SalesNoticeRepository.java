package com.quickkoala.repository.sales;


import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.quickkoala.entity.sales.SalesNoticeEntity;

public interface SalesNoticeRepository extends JpaRepository<SalesNoticeEntity, Long> {
    @Query("SELECT n FROM SalesNoticeEntity n ORDER BY n.createdAt DESC")
    Page<SalesNoticeEntity> getNotices(Pageable pageable);
    
    @Query("SELECT n FROM SalesNoticeEntity n WHERE n.companyCode = :managerCompanyCode ORDER BY n.createdAt DESC")
    Page<SalesNoticeEntity> getNoticesByManagerCompanyCode(String managerCompanyCode, Pageable pageable);
}