package com.quickkoala.controller.sales;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.quickkoala.entity.sales.ClientsOrdersEntity;
import com.quickkoala.service.sales.OrderServiceImpl;
import com.quickkoala.token.config.JwtTokenProvider;

import jakarta.servlet.http.HttpServletRequest;

@Controller
@RequestMapping("/sales")
public class OrderController {
    
	@Autowired
	private OrderServiceImpl orderService;
	
	@Autowired
	private JwtTokenProvider jwtTokenProvider;
	
	@GetMapping("/order")
	public String order() {
		return "sales/order";
	}
	
	@GetMapping("/orderlist")
	public String orderlist(HttpServletRequest request, Model model) {
	    // 쿠키에서 JWT 토큰 추출
	    String token = jwtTokenProvider.resolveToken(request);
	    
	    // 토큰에서 code 추출
	    String code = jwtTokenProvider.getClaim(token, "code");
	    
	    // 해당 code와 일치하는 주문 정보 조회
	    List<ClientsOrdersEntity> orders = orderService.findByCode(code);
	    
	    // 조회된 주문 정보를 모델에 추가
	    model.addAttribute("orders", orders);
	    
	    return "sales/orderlist"; // Thymeleaf 템플릿 파일 이름 (orderlist.html)
	}

	
}
