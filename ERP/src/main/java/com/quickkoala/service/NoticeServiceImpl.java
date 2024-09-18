package com.quickkoala.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.quickkoala.dto.sales.NoticeDTO;
import com.quickkoala.entity.sales.NoticeEntity;
import com.quickkoala.repository.sales.NoticeRepository;

@Service
public class NoticeServiceImpl implements NoticeService {
	
    @Autowired
    private NoticeRepository noticeRepository;

    @Override
    public List<NoticeDTO> getAllNotices() {
        List<NoticeEntity> noticeEntities = noticeRepository.findAll();
        return noticeEntities.stream()
                .map(this::convertEntityToDto)
                .collect(Collectors.toList());
    }

    @Override
    public NoticeDTO getNoticeById(Long id) {
        NoticeEntity noticeEntity = noticeRepository.findById(id).orElseThrow(() -> new RuntimeException("Notice not found"));
        return convertEntityToDto(noticeEntity);
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
