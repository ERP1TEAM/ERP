package com.quickkoala.controller.sales;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.quickkoala.service.NoticeServiceImpl;

@RestController
@RequestMapping("/sales")
public class NoticeRestController {
	
	@Autowired
    private NoticeServiceImpl noticeService;
	


}
