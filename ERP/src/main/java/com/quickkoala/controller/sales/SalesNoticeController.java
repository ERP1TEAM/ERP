package com.quickkoala.controller.sales;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.quickkoala.dto.sales.SalesNoticeDTO;
import com.quickkoala.entity.sales.SalesNoticeEntity;
import com.quickkoala.entity.supplier.SupplierNoticeEntity;
import com.quickkoala.service.sales.SalesNoticeServiceImpl;
import com.quickkoala.token.config.JwtTokenProvider;

import jakarta.servlet.http.HttpServletRequest;

@Controller
@RequestMapping("/sales")
public class SalesNoticeController {
	
	@Autowired
    private SalesNoticeServiceImpl noticeService;
	
	@Autowired
    private JwtTokenProvider jwtTokenProvider;
	
    // 공지사항 목록 조회
    @GetMapping("/home")
    public String home(Model model,
			            @RequestParam(defaultValue = "0") int page,
			            @RequestParam(defaultValue = "10") int size,
			            HttpServletRequest request) {
    		String token = jwtTokenProvider.resolveToken(request);
    	    // 토큰에서 code 추출
    	    String code = jwtTokenProvider.getClaim(token, "code");
			// 공지사항 목록을 가져와서 모델에 추가
			Page<SalesNoticeDTO> notices = noticeService.getNoticesByCompanyCode(code, page, size);
			model.addAttribute("notices", notices);
			return "sales/salesHome";
    }

    // 공지사항 작성 페이지
    @GetMapping("/noticeWrite")
    public String newNotice(Model model) {
        model.addAttribute("notice", new SalesNoticeDTO());
        return "sales/noticeWrite";
    }
    
    // 공지사항 상세 조회
    @GetMapping("/noticeView")
    public String viewNotice(@RequestParam("id") Long id, Model model) {
    	// 공지사항을 조회할 때 조회수를 증가시키는 로직 실행
        SalesNoticeEntity notice = noticeService.getNoticeById(id);

        // 모델에 조회된 공지사항을 추가
        model.addAttribute("notice", notice);
        return "sales/noticeView";
    }
    
	// 공지사항 저장
    @PostMapping("/save")
    public String saveNotice(@ModelAttribute SalesNoticeDTO noticeDTO) {
        noticeService.saveNotice(noticeDTO);
        return "redirect:/sales/home";
    }
    
}
