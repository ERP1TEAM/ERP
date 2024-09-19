package com.quickkoala.dto.sales;

import java.time.LocalDateTime;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class NoticeDTO {
    private Long id;
    private String title;
    private String content;
    private String manager;
    private LocalDateTime createdAt;
    private int views;
}
