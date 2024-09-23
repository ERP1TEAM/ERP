package com.quickkoala.controller.main;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.quickkoala.dto.main.MainNoticeDTO;
import com.quickkoala.service.main.MainNoticeServiceImpl;
import com.quickkoala.token.config.JwtTokenProvider;

import jakarta.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/main")
public class MainNoticeRestController {
	
	@Autowired
    private MainNoticeServiceImpl noticeService;
	@Autowired
	private JwtTokenProvider jwtTokenProvider;

	@GetMapping("/filterNotices")
	@ResponseBody
	public Page<MainNoticeDTO> filterNotices(
	    @RequestParam(defaultValue = "0") int page, 
	    @RequestParam(defaultValue = "10") int size, 
	    HttpServletRequest request) {

	    String token = jwtTokenProvider.resolveToken(request);
	    String companyCode = jwtTokenProvider.getClaim(token, "code");

	    return noticeService.getNoticesByCompanyCode(companyCode, page, size);
	}

}
