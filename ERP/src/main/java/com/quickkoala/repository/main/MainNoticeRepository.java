package com.quickkoala.repository.main;


import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.quickkoala.entity.main.MainNoticeEntity;

public interface MainNoticeRepository extends JpaRepository<MainNoticeEntity, Long> {
    @Query("SELECT n FROM MainNoticeEntity n ORDER BY n.createdAt DESC")
    Page<MainNoticeEntity> getNotices(Pageable pageable);
    
    @Query("SELECT n FROM MainNoticeEntity n WHERE n.companyCode = :managerCompanyCode ORDER BY n.createdAt DESC")
    Page<MainNoticeEntity> getNoticesByManagerCompanyCode(String managerCompanyCode, Pageable pageable);
}