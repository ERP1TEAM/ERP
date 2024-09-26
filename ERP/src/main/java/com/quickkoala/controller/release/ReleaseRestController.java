package com.quickkoala.controller.release;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.quickkoala.entity.release.ViewReleaseCancelEntity;
import com.quickkoala.entity.release.ViewReleaseCompleteEntity;
import com.quickkoala.entity.release.ViewReleaseCompleteProductsEntity;
import com.quickkoala.entity.release.ViewReleaseOngoingEntity;
import com.quickkoala.entity.release.ViewReleaseProductsEntity;
import com.quickkoala.entity.release.ViewReleaseReturnProductsEntity;
import com.quickkoala.service.release.OrderReleaseService;
import com.quickkoala.service.release.ReleaseReturnProductsService;
import com.quickkoala.service.release.ViewReleaseCancelService;
import com.quickkoala.service.release.ViewReleaseCompleteProductsService;
import com.quickkoala.service.release.ViewReleaseCompleteService;
import com.quickkoala.service.release.ViewReleaseOngoingService;
import com.quickkoala.service.release.ViewReleaseProductsService;
import com.quickkoala.service.release.ViewReleaseReturnProductsService;
import com.quickkoala.utils.GetToken;

import jakarta.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("main")
public class ReleaseRestController {
	
	@Autowired
	private OrderReleaseService orderReleaseService;
	
	@Autowired
	private ReleaseReturnProductsService releaseRefundPrdouctsService;
	
	@Autowired
	private ViewReleaseOngoingService viewReleaseOngoingService;
	
	@Autowired
	private ViewReleaseCancelService viewReleaseCancelService;
	
	@Autowired
	private ViewReleaseCompleteService viewReleaseCompleteService;
	
	@Autowired
	private ViewReleaseProductsService viewReleaseProductsService;
	
	@Autowired
	private ViewReleaseCompleteProductsService viewReleaseCompleteProductsService;
	
	@Autowired
	private ViewReleaseReturnProductsService viewReleaseRefundPrdouctsService;
	
	private final int SIZE=10;
	
	@GetMapping("release/page")
	public Page<ViewReleaseOngoingEntity> pagingpaging
	(@RequestParam int pg, @RequestParam(required = false) String select,  @RequestParam(required = false) String param, @RequestParam(required = false) String startDate, @RequestParam(required = false) String endDate){
		return viewReleaseOngoingService.getAll(pg,SIZE,select,param,startDate,endDate);
	}
	
	@GetMapping("release/cancel/page")
	public Page<ViewReleaseCancelEntity> cancelpaging
	(@RequestParam int pg, @RequestParam(required = false) String select,  @RequestParam(required = false) String param, @RequestParam(required = false) String startDate, @RequestParam(required = false) String endDate){
		return viewReleaseCancelService.getAll(pg,SIZE,select,param,startDate,endDate);
	}
	
	@GetMapping("release/complete/page")
	public Page<ViewReleaseCompleteEntity> completepaging
	(@RequestParam int pg, @RequestParam(required = false) String select,  @RequestParam(required = false) String param, @RequestParam(required = false) String startDate, @RequestParam(required = false) String endDate){
		return viewReleaseCompleteService.getAll(pg,SIZE,select,param,startDate,endDate);
	}
	
	@GetMapping("release/return/page")
	public Page<ViewReleaseReturnProductsEntity> refundpaging(@RequestParam int pg, @RequestParam(required = false) String select,  @RequestParam(required = false) String param){
		return viewReleaseRefundPrdouctsService.getAll(pg,SIZE,select,param);
	}
	
	@PostMapping("release/detail")
	public List<ViewReleaseProductsEntity> detail(@RequestParam("rNum") String rNum) {
		return viewReleaseProductsService.getProducts(rNum);
	}
	
	@PostMapping("release/completedetail")
	public List<ViewReleaseCompleteProductsEntity> completedetail(@RequestParam("rNum") String rNum) {
		return viewReleaseCompleteProductsService.getProducts(rNum);
	}
	
	@PostMapping("release/cancel.do")
	public String cancel(@RequestParam String id, HttpServletRequest request) {
		return orderReleaseService.saveStatus(id,"출고취소",GetToken.getManagerName(request));
	}
	
	
	@PostMapping("release/complete.do")
	public String complete(@RequestParam String id, HttpServletRequest request) {
		return orderReleaseService.saveStatus(id,"출고완료",GetToken.getManagerName(request));
	}
	
	@PostMapping("release/postpone.do")
	public String postpone(@RequestParam String id, HttpServletRequest request) {
		return orderReleaseService.saveStatus(id,"출고지연",GetToken.getManagerName(request));
	}
	
	@PostMapping("release/return/discard")
	public String returnDiscard(@RequestParam("relNum") String relNum,@RequestParam("lotNum") String lotNum,@RequestParam("qty") int qty) {
		return releaseRefundPrdouctsService.saveStatus(relNum,lotNum,qty,"폐기");
	}
	
	@PostMapping("release/return/receive")
	public String returnReceive(@RequestParam("relNum") String relNum,@RequestParam("lotNum") String lotNum,@RequestParam("qty") int qty) {
		return releaseRefundPrdouctsService.saveStatus(relNum,lotNum,qty,"입고");
	}
	
	@PostMapping("release/return/add")
	public String addReturnProduct(String rCode,  String lCode, int qty,@RequestParam(required=false)String reason,HttpServletRequest request) {
		releaseRefundPrdouctsService.saveProduct(rCode,lCode,qty,reason,GetToken.getManagerName(request));
		return null;
		
		
	}
	
}
