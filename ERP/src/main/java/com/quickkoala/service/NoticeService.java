package com.quickkoala.service;

import java.util.List;

import com.quickkoala.dto.sales.NoticeDTO;

public interface NoticeService {
	
    List<NoticeDTO> getAllNotices();

    NoticeDTO getNoticeById(Long id);

    void saveNotice(NoticeDTO noticeDTO);
    
    

}
