package com.quickkoala.service.sales;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
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

    @Override
    public Page<NoticeEntity> getNotices(int page, int size) {
        // 작성일자 기준으로 역순 정렬
        Pageable pageable = PageRequest.of(page, size, Sort.by(Sort.Direction.DESC, "createdAt"));
        return noticeRepository.findAll(pageable);
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

    private NoticeDTO convertEntityToDto(NoticeEntity noticeEntity) {
        NoticeDTO dto = new NoticeDTO();
        dto.setId(noticeEntity.getId());
        dto.setTitle(noticeEntity.getTitle());
        dto.setContent(noticeEntity.getContent());
        dto.setManager(noticeEntity.getManager());
        dto.setCreatedAt(noticeEntity.getCreatedAt());
        dto.setViews(noticeEntity.getViews());
        return dto;
    }

    private NoticeEntity convertDtoToEntity(NoticeDTO noticeDTO) {
        NoticeEntity entity = new NoticeEntity();
        entity.setId(noticeDTO.getId());
        entity.setTitle(noticeDTO.getTitle());
        entity.setContent(noticeDTO.getContent());
        entity.setManager(noticeDTO.getManager());
        entity.setCreatedAt(noticeDTO.getCreatedAt());
        entity.setViews(noticeDTO.getViews());
        return entity;
    }
}
