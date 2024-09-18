package com.quickkoala.controller.sales;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.quickkoala.dto.sales.NoticeDTO;
import com.quickkoala.service.NoticeService;

@Controller
@RequestMapping("/sales")
public class HomeController {

    @Autowired
    private NoticeService noticeService;

    // 공지사항 목록 조회
    @GetMapping("/home")
    public String home(Model model) {
        model.addAttribute("notices", noticeService.getAllNotices());
        return "sales/home";
    }

    // 공지사항 작성 페이지
    @GetMapping("/noticeWrite")
    public String newNotice(Model model) {
        model.addAttribute("notice", new NoticeDTO());
        return "sales/noticeWrite";
    }

    // 공지사항 저장
    @PostMapping("/save")
    public String saveNotice(@ModelAttribute NoticeDTO noticeDTO) {
        noticeService.saveNotice(noticeDTO);
        return "redirect:/sales/home";
    }

    // 공지사항 상세 조회
    @GetMapping("/noticeView/{id}")
    public String viewNotice(@PathVariable Long id, Model model) {
        model.addAttribute("notice", noticeService.getNoticeById(id));
        return "sales/noticeView";
    }
}
