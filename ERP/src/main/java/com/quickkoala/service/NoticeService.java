package com.quickkoala.service;

import org.springframework.data.domain.Page;

import com.quickkoala.dto.sales.NoticeDTO;
import com.quickkoala.entity.sales.NoticeEntity;

public interface NoticeService {
	
	Page<NoticeEntity> getNotices(int page, int size);

    NoticeEntity getNoticeById(Long id);

    void saveNotice(NoticeDTO noticeDTO);
    
}
