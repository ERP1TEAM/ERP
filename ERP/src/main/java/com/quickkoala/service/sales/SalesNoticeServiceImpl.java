package com.quickkoala.service.sales;

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

import com.quickkoala.dto.sales.SalesNoticeDTO;
import com.quickkoala.entity.sales.SalesNoticeEntity;
import com.quickkoala.repository.sales.SalesNoticeRepository;

@Service
public class SalesNoticeServiceImpl implements SalesNoticeService {
	
    @Autowired
    private SalesNoticeRepository noticeRepository;

    //전체 공지사항
    @Override
    public Page<SalesNoticeEntity> getNotices(int page, int size) {
        // 작성일자 기준으로 역순 정렬
        Pageable pageable = PageRequest.of(page, size, Sort.by(Sort.Direction.DESC, "createdAt"));
        return noticeRepository.findAll(pageable);
    }
    
    //자사의 공지사항만 출력
    public Page<SalesNoticeDTO> getNoticesByCompanyCode(String companyCode, int page, int size) {
        Page<SalesNoticeEntity> notices = noticeRepository.getNoticesByManagerCompanyCode(companyCode, PageRequest.of(page, size));
        
        // 순번을 역순으로 설정
        AtomicInteger index = new AtomicInteger((int) notices.getTotalElements() - (page * size));
        List<SalesNoticeDTO> noticeDTOs = notices.map(notice -> {
            SalesNoticeDTO dto = new SalesNoticeDTO(notice);
            dto.setNo(index.getAndDecrement()); // 임의의 순번 설정
            return dto;
        }).getContent();
        
        return new PageImpl<>(noticeDTOs, PageRequest.of(page, size), notices.getTotalElements());
    }


    
    // 조회수 증가를 포함한 공지사항 조회
    @Transactional
    public SalesNoticeEntity getNoticeById(Long id) {
        // 공지사항을 조회하고
        SalesNoticeEntity notice = noticeRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("해당 공지사항을 찾을 수 없습니다. ID: " + id));
        
        // 조회수 증가
        notice.setViews(notice.getViews() + 1);
        
        // 변경사항을 저장
        noticeRepository.save(notice);
        
        return notice;
    }

    @Override
    public void saveNotice(SalesNoticeDTO noticeDTO) {
        SalesNoticeEntity noticeEntity = convertDtoToEntity(noticeDTO);
        noticeRepository.save(noticeEntity);
    }



    private SalesNoticeEntity convertDtoToEntity(SalesNoticeDTO noticeDTO) {
        SalesNoticeEntity entity = new SalesNoticeEntity();
        entity.setId(noticeDTO.getId());
        entity.setTitle(noticeDTO.getTitle());
        entity.setContent(noticeDTO.getContent());
        entity.setManager(noticeDTO.getManager());
        entity.setCompanyCode(noticeDTO.getCompanyCode());
        entity.setCreatedAt(noticeDTO.getCreatedAt());
        entity.setViews(noticeDTO.getViews());
        return entity;
    }
    
    
    //해당 공지사항 삭제
    public void deleteNoticeById(Long id) {
        noticeRepository.deleteById(id);
    }
}
