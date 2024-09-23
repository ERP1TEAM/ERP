package com.quickkoala.service.supplier;

import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.quickkoala.dto.supplier.SupplierNoticeDTO;
import com.quickkoala.entity.supplier.SupplierNoticeEntity;
import com.quickkoala.repository.supplier.SupplierNoticeRepository;

@Service
public class SupplierNoticeServiceImpl implements SupplierNoticeService {
	
    @Autowired
    private SupplierNoticeRepository noticeRepository;

    //전체 공지사항
    @Override
    public Page<SupplierNoticeEntity> getNotices(int page, int size) {
        // 작성일자 기준으로 역순 정렬
        Pageable pageable = PageRequest.of(page, size, Sort.by(Sort.Direction.DESC, "createdAt"));
        return noticeRepository.findAll(pageable);
    }
    
    public Page<SupplierNoticeDTO> getNoticesByCompanyCode(String companyCode, int page, int size) {
        Page<SupplierNoticeEntity> notices = noticeRepository.getNoticesByManagerCompanyCode(companyCode, PageRequest.of(page, size));
        
        // 순번을 역순으로 설정
        AtomicInteger index = new AtomicInteger((int) notices.getTotalElements() - (page * size));
        List<SupplierNoticeDTO> noticeDTOs = notices.map(notice -> {
            SupplierNoticeDTO dto = new SupplierNoticeDTO(notice);
            dto.setNo(index.getAndDecrement()); // 임의의 순번 설정
            return dto;
        }).getContent();
        
        return new PageImpl<>(noticeDTOs, PageRequest.of(page, size), notices.getTotalElements());
    }


    
    // 조회수 증가를 포함한 공지사항 조회
    @Transactional
    public SupplierNoticeEntity getNoticeById(Long id) {
        // 공지사항을 조회하고
        SupplierNoticeEntity notice = noticeRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("해당 공지사항을 찾을 수 없습니다. ID: " + id));
        
        // 조회수 증가
        notice.setViews(notice.getViews() + 1);
        
        // 변경사항을 저장
        noticeRepository.save(notice);
        
        return notice;
    }

    @Override
    public void saveNotice(SupplierNoticeDTO noticeDTO) {
        SupplierNoticeEntity noticeEntity = convertDtoToEntity(noticeDTO);
        noticeRepository.save(noticeEntity);
    }



    private SupplierNoticeEntity convertDtoToEntity(SupplierNoticeDTO noticeDTO) {
        SupplierNoticeEntity entity = new SupplierNoticeEntity();
        entity.setId(noticeDTO.getId());
        entity.setTitle(noticeDTO.getTitle());
        entity.setContent(noticeDTO.getContent());
        entity.setManager(noticeDTO.getManager());
        entity.setCompanyCode(noticeDTO.getCompanyCode());
        entity.setCreatedAt(noticeDTO.getCreatedAt());
        entity.setViews(noticeDTO.getViews());
        return entity;
    }
}
