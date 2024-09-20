package com.quickkoala.service.sales;

import org.springframework.data.domain.Page;

import com.quickkoala.dto.sales.NoticeDTO;
import com.quickkoala.entity.sales.NoticeEntity;

public interface NoticeService {
	
	//공지사항 목록 출력 ( 전체 )
	Page<NoticeEntity> getNotices(int page, int size);
	
	//공지사항 보기
    NoticeEntity getNoticeById(Long id);
    
    //공지사항 저장
    void saveNotice(NoticeDTO noticeDTO);
    
    //공지사항 목록 출력 ( 본인 회사 )
    Page<NoticeDTO> getNoticesByCompanyCode(String companyCode, int page, int size);
}
