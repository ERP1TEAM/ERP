package com.quickkoala.service.main;

import org.springframework.data.domain.Page;

import com.quickkoala.dto.main.MainNoticeDTO;
import com.quickkoala.entity.main.MainNoticeEntity;

public interface MainNoticeService {
	
	//공지사항 목록 출력 ( 전체 )
	Page<MainNoticeEntity> getNotices(int page, int size);
	
	//공지사항 보기
    MainNoticeEntity getNoticeById(Long id);
    
    //공지사항 저장
    void saveNotice(MainNoticeDTO noticeDTO);
    
    //공지사항 목록 출력 ( 본인 회사 )
    Page<MainNoticeDTO> getNoticesByCompanyCode(String companyCode, int page, int size);
}
