package com.quickkoala.repository.sales;


import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.quickkoala.entity.sales.NoticeEntity;

public interface NoticeRepository extends JpaRepository<NoticeEntity, Long> {
}