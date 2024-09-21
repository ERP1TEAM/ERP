package com.quickkoala.service.sales;

import org.springframework.data.domain.Page;

import com.quickkoala.dto.sales.SalesNoticeDTO;
import com.quickkoala.entity.sales.SalesNoticeEntity;

public interface SalesNoticeService {
	
	//공지사항 목록 출력 ( 전체 )
	Page<SalesNoticeEntity> getNotices(int page, int size);
	
	//공지사항 보기
    SalesNoticeEntity getNoticeById(Long id);
    
    //공지사항 저장
    void saveNotice(SalesNoticeDTO noticeDTO);
    
    //공지사항 목록 출력 ( 본인 회사 )
    Page<SalesNoticeDTO> getNoticesByCompanyCode(String companyCode, int page, int size);
}
