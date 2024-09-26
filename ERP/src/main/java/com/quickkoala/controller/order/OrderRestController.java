package com.quickkoala.controller.order;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.quickkoala.dto.sales.ClientsOrderProductsDTO;
import com.quickkoala.dto.sales.ClientsOrdersDTO;
import com.quickkoala.entity.order.ViewOrderCancelEntity;
import com.quickkoala.entity.order.ViewOrderOngoingEntity;
import com.quickkoala.entity.order.ViewOrderProductsEntity;
import com.quickkoala.service.order.OrderCancelService;
import com.quickkoala.service.order.OrderService;
import com.quickkoala.service.order.ViewOrderCancelService;
import com.quickkoala.service.order.ViewOrderOngoingService;
import com.quickkoala.service.order.ViewOrderProductsService;
import com.quickkoala.service.sales.SalesOrderServiceImpl;
import com.quickkoala.token.config.JwtTokenProvider;
import com.quickkoala.utils.GetToken;

import jakarta.servlet.http.HttpServletRequest;


@RestController
@RequestMapping("main")
public class OrderRestController {
		
	@Autowired
	private OrderService orderService;
	
	@Autowired
	private ViewOrderOngoingService viewOrderOngoingService;
	
	@Autowired
	private ViewOrderProductsService viewOrderProductsService;
	
	@Autowired
	private ViewOrderCancelService viewOrderCancelService;
	
	private final int SIZE=10;
	
	@GetMapping("order/page")
	public Page<ViewOrderOngoingEntity> paging(@RequestParam int pg, @RequestParam(required = false) String select,  @RequestParam(required = false) String param){
		return this.viewOrderOngoingService.getAll(pg,SIZE,select,param);
	}
	
	@GetMapping("order/cancel/page")	
	public Page<ViewOrderCancelEntity> cancelPaging(@RequestParam int pg, @RequestParam(required = false) String select,  @RequestParam(required = false) String param, @RequestParam(required = false) String startDate, @RequestParam(required = false) String endDate){
		return this.viewOrderCancelService.getAll(pg,SIZE,select,param,startDate,endDate);
	}
	
	@PostMapping("order/detailok.do")
	public List<ViewOrderProductsEntity> detail(String orderNum) {
		return this.viewOrderProductsService.getAll(orderNum);
	}
	
	@GetMapping("order/post{num}")
	public String getModalContent(@PathVariable("num") int num) {
	    return "order/post :: post"+num; 
	}
	
	@PutMapping("order/approveok.do")
	public String approve(@RequestParam String id,HttpServletRequest request){
		return this.orderService.updateStatus(id,"승인",GetToken.getManagerName(request));
	}
	
	@PutMapping("order/cancelapprovedok.do")
	public String cancelapproved(@RequestParam String id,HttpServletRequest request){
		return this.orderService.updateStatus(id,"미승인",GetToken.getManagerName(request));
	}
	
	@PutMapping("order/cancelok.do")
	public String cancel(@RequestParam String id,HttpServletRequest request){
		return this.orderService.updateStatus(id,"취소",GetToken.getManagerName(request));
	}
	
	@PostMapping("order/requestReceptionok.do")
	public String requestReception(@RequestParam String reqPdt,@RequestParam String reqSup,@RequestParam String reqNum){
		//상품코드 + 발주처 + 주문할 qty -> 입고 요청
		return null;	// OK | NO
	}
	
	
	
}
