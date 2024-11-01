package com.quickkoala.dto.main;

import java.time.LocalDateTime;

import com.quickkoala.entity.main.MainNoticeEntity;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MainNoticeDTO {
    private Long id;
    private int no;          // 임의로 부여된 NO
    private String title;
    private String content;
    private String manager;
    private String companyCode;
    private LocalDateTime createdAt;
    private int views;
    
    // 기본 생성자 (기존 코드에서 사용될 수 있도록 제공)
    public MainNoticeDTO() {
    }

    // NoticeEntity를 받는 생성자
    public MainNoticeDTO(MainNoticeEntity noticeEntity) {
        this.id = noticeEntity.getId();
        this.title = noticeEntity.getTitle();
        this.content = noticeEntity.getContent();
        this.manager = noticeEntity.getManager();
        this.companyCode = noticeEntity.getCompanyCode();
        this.createdAt = noticeEntity.getCreatedAt();
        this.views = noticeEntity.getViews();
    }
}
