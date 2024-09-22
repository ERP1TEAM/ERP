package com.quickkoala.dto.sales;

import java.time.LocalDateTime;

import com.quickkoala.entity.sales.SalesNoticeEntity;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SalesNoticeDTO {
    private Long id;
    private int no;          // 임의로 부여된 NO
    private String title;
    private String content;
    private String manager;
    private String companyCode;
    private LocalDateTime createdAt;
    private int views;
    
    // 기본 생성자 (기존 코드에서 사용될 수 있도록 제공)
    public SalesNoticeDTO() {
    }

    // NoticeEntity를 받는 생성자
    public SalesNoticeDTO(SalesNoticeEntity noticeEntity) {
        this.id = noticeEntity.getId();
        this.title = noticeEntity.getTitle();
        this.content = noticeEntity.getContent();
        this.manager = noticeEntity.getManager();
        this.companyCode = noticeEntity.getCompanyCode();
        this.createdAt = noticeEntity.getCreatedAt();
        this.views = noticeEntity.getViews();
    }
}
