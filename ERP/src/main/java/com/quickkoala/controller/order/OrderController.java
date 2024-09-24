package com.quickkoala.controller.order;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.quickkoala.dto.sales.ClientsOrderProductsDTO;
import com.quickkoala.dto.sales.ClientsOrdersDTO;
import com.quickkoala.service.order.OrderCancelService;
import com.quickkoala.service.order.OrderService;
import com.quickkoala.service.order.ViewOrderCancelService;
import com.quickkoala.service.order.ViewOrderOngoingService;
import com.quickkoala.service.order.ViewOrderProductsService;
import com.quickkoala.service.sales.SalesOrderServiceImpl;
import com.quickkoala.token.config.JwtTokenProvider;
import com.quickkoala.utils.GetToken;

import jakarta.servlet.http.HttpServletRequest;


@Controller
@RequestMapping("main")
public class OrderController {
	
	@Autowired
	 private SalesOrderServiceImpl salesOrderService;
	    
	@Autowired
	private JwtTokenProvider jwtTokenProvider;
	//test
	String[] pcodes = {
		    "P0000001",
		    "P0000002",
		    "P0000003",
		    "P0000004",
		    "P0000005",
		    "P0000006",
		    "P0000007",
		    "P0000008",
		    "P0000009",
		    "P0000010",
		    "P0000011",
		    "P0000012",
		    "P0000013",
		    "P0000014",
		    "P0000015",
		    "P0000016",
		    "P0000017",
		    "P0000018",
		    "P0000019",
		    "P0000020",
		    "P0000021",
		    "P0000022",
		    "P0000023",
		    "P0000024",
		    "P0000025",
		    "P0000026",
		    "P0000027",
		    "P0000028",
		    "P0000029",
		    "P0000030"
		};
	
	String[] name = {
		    "홍길동",
		    "강감찬",
		    "김선숙",
		    "김하주",
		    "명동건",
		    "신희문"
		};
	
	
	@GetMapping("order/test")
	public String test(HttpServletRequest request) {
		 List<ClientsOrdersDTO> orders = new ArrayList<ClientsOrdersDTO>();
		 System.out.println("order test");
		 List<ClientsOrderProductsDTO> products = new ArrayList<ClientsOrderProductsDTO>();
		 
		 ClientsOrdersDTO temp =new ClientsOrdersDTO();
		 temp.setAddress("ㅇㅇ시 ㅇㅇ구 ㅇㅇ동");
		 temp.setAddressDetail("303호");
		 temp.setClientMemo(null);
		 temp.setCreatedDt(LocalDateTime.now());
		 temp.setEmail("lol@lol.com");
		 temp.setManager(GetToken.getManagerName(request));
		 temp.setManagerCompanyCode(GetToken.getCompanyCode(request));
		 temp.setName(name[(int)Math.floor((double)Math.random()*6)]);
		 
		 temp.setOrderDate(LocalDateTime.now());
		 temp.setPost("34920");
		 temp.setTel("01044448888");
		 
		 for(int ran=0; ran<(int)Math.ceil((double)Math.random()*4); ran++) {
			 ClientsOrderProductsDTO ptemp1 = new ClientsOrderProductsDTO();
			 ptemp1.setProductCode(pcodes[(int)Math.floor((double)Math.random()*30)]);
			 ptemp1.setProductName("?");
			 ptemp1.setQty((int)Math.ceil((double)Math.random()*6));
			 products.add(ptemp1);
		 }
		 temp.setProducts(products);
		 orders.add(temp);
		 String token = jwtTokenProvider.resolveToken(request);
		 System.out.println("save order");
		 salesOrderService.saveOrder(orders,token);
		
		return "order/test";
	}
		
	@GetMapping("order/ongoing")
	public String ongoing() {
		return "order/ongoing";
	}
	
	@GetMapping("order/cancel")
	public String cancel() {
		return "order/cancel";
	}
	
}
