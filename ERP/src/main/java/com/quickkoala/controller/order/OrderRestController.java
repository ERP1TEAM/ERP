package com.quickkoala.controller.order;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.quickkoala.dto.order.OrderCancelDto;
import com.quickkoala.dto.order.OrderOngoingDto;
import com.quickkoala.entity.order.ViewOrderProductsEntity;
import com.quickkoala.service.order.OrderCancelService;
import com.quickkoala.service.order.OrderService;
import com.quickkoala.service.order.ViewOrderCancelService;
import com.quickkoala.service.order.ViewOrderOngoingService;
import com.quickkoala.service.order.ViewOrderProductsService;


@RestController
@RequestMapping("main")
public class OrderRestController {
		
	@Autowired
	private OrderService orderService;
	
	@Autowired
	private OrderCancelService orderCancelService;
	
	@Autowired
	private ViewOrderOngoingService viewOrderOngoingService;
	
	@Autowired
	private ViewOrderProductsService viewOrderProductsService;
	
	@Autowired
	private ViewOrderCancelService viewOrderCancelService;
	
	
	private final int size = 5;
	@PostMapping("order/paging")
	public List<OrderOngoingDto> paging(@RequestParam int pg){
		return this.viewOrderOngoingService.getAll(pg,size);
	}
	
	@PostMapping("order/cancelpaging")
	public List<OrderCancelDto> cancelPaging(@RequestParam int pg){
		return this.viewOrderCancelService.getAll(pg,size);
	}
	
	@PostMapping("order/detailok.do")
	public List<ViewOrderProductsEntity> detail(String orderNum) {
		return this.viewOrderProductsService.getAll(orderNum);
	}
	
	@PutMapping("order/approveok.do")
	public String approve(@RequestParam String id){
		//return this.orderService.updateStatus(id,"승인");
		return this.orderService.updateApproved(id);
	}
	
	@PutMapping("order/cancelapprovedok.do")
	public String cancelapproved(@RequestParam String id){
		return this.orderService.updateStatus(id,"미승인");
	}
	
	@PutMapping("order/cancelok.do")
	public String cancel(@RequestParam String id){
		return this.orderService.updateStatus(id,"취소");
	}
	
	@PostMapping("order/requestReceptionok.do")
	public String requestReception(@RequestParam String reqPdt,@RequestParam String reqSup,@RequestParam String reqNum){
		//상품코드 + 발주처 + 주문할 qty -> 입고 요청
		return null;	// OK | NO
	}
	
	@PostMapping("order/approveSelectedok.do")
	public String approveSelected(@RequestParam String ids[]){
		//return this.orderService.updateStatusMultipleIds(Arrays.asList(ids),"승인");
		return null;
	}
	
	@PostMapping("order/denySelectedok.do")
	public String denySelected(@RequestParam String ids[]){
		//return this.orderService.updateStatusMultipleIds(Arrays.asList(ids),"취소");
		return null;
	}
	
	
	
}
