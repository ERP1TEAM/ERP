package com.quickkoala.service.supplier;

import org.springframework.data.domain.Page;

import com.quickkoala.dto.supplier.SupplierNoticeDTO;
import com.quickkoala.entity.supplier.SupplierNoticeEntity;

public interface SupplierNoticeService {
	
	//공지사항 목록 출력 ( 전체 )
	Page<SupplierNoticeEntity> getNotices(int page, int size);
	
	//공지사항 보기
    SupplierNoticeEntity getNoticeById(Long id);
    
    //공지사항 저장
    void saveNotice(SupplierNoticeDTO noticeDTO);
    
    //공지사항 목록 출력 ( 본인 회사 )
    Page<SupplierNoticeDTO> getNoticesByCompanyCode(String companyCode, int page, int size);
}
