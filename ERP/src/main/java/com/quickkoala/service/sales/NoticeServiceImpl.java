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

import com.quickkoala.dto.sales.NoticeDTO;
import com.quickkoala.entity.sales.NoticeEntity;
import com.quickkoala.repository.sales.NoticeRepository;

@Service
public class NoticeServiceImpl implements NoticeService {
	
    @Autowired
    private NoticeRepository noticeRepository;

    //전체 공지사항
    @Override
    public Page<NoticeEntity> getNotices(int page, int size) {
        // 작성일자 기준으로 역순 정렬
        Pageable pageable = PageRequest.of(page, size, Sort.by(Sort.Direction.DESC, "createdAt"));
        return noticeRepository.findAll(pageable);
    }
    
    public Page<NoticeDTO> getNoticesByCompanyCode(String companyCode, int page, int size) {
    	
        Page<NoticeEntity> notices = noticeRepository.getNoticesByManagerCompanyCode(companyCode, PageRequest.of(page, size));
        
        // 순번 추가
        AtomicInteger index = new AtomicInteger(1 + (page * size));
        List<NoticeDTO> noticeDTOs = notices.map(notice -> {
            NoticeDTO dto = new NoticeDTO(notice);
            dto.setId((long)index.getAndIncrement()); // 순번 설정
            return dto;
        }).getContent();
        
        return new PageImpl<>(noticeDTOs, PageRequest.of(page, size), notices.getTotalElements());
    }

    
    // 조회수 증가를 포함한 공지사항 조회
    @Transactional
    public NoticeEntity getNoticeById(Long id) {
        // 공지사항을 조회하고
        NoticeEntity notice = noticeRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("해당 공지사항을 찾을 수 없습니다. ID: " + id));
        
        // 조회수 증가
        notice.setViews(notice.getViews() + 1);
        
        // 변경사항을 저장
        noticeRepository.save(notice);
        
        return notice;
    }

    @Override
    public void saveNotice(NoticeDTO noticeDTO) {
        NoticeEntity noticeEntity = convertDtoToEntity(noticeDTO);
        noticeRepository.save(noticeEntity);
    }



    private NoticeEntity convertDtoToEntity(NoticeDTO noticeDTO) {
        NoticeEntity entity = new NoticeEntity();
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
