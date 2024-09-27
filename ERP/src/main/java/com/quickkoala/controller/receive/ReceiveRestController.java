package com.quickkoala.controller.receive;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.quickkoala.dto.receive.PurchaseListDto;
import com.quickkoala.dto.receive.ReceiveModalDto;
import com.quickkoala.dto.receive.ReceivingDto;
import com.quickkoala.dto.receive.SearchDto;
import com.quickkoala.entity.receive.ReceiveDetailEntity;
import com.quickkoala.entity.receive.ReceiveReturnEntity;
import com.quickkoala.entity.receive.ViewLocationProductEntity;
import com.quickkoala.entity.receive.ViewPurchaseDetailEntity;
import com.quickkoala.entity.receive.ViewPurchaseEntity;
import com.quickkoala.entity.receive.ViewReceiveEntity;
import com.quickkoala.entity.receive.ViewReceiveReturnEntity;
import com.quickkoala.entity.receive.ViewReceiveSummaryEntity;
import com.quickkoala.entity.receive.ViewReceiveTempEntity;
import com.quickkoala.service.receive.ReceiveDetailService;
import com.quickkoala.service.receive.ReceiveReturnService;
import com.quickkoala.service.receive.ReceiveTempService;
import com.quickkoala.service.receive.ViewLocationProductService;
import com.quickkoala.service.receive.ViewPurchaseDetailService;
import com.quickkoala.service.receive.ViewPurchaseService;
import com.quickkoala.service.receive.ViewReceiveReturnService;
import com.quickkoala.service.receive.ViewReceiveService;
import com.quickkoala.service.receive.ViewReceiveSummaryService;
import com.quickkoala.service.receive.ViewReceiveTempService;
import com.quickkoala.service.stock.LotService;
import com.quickkoala.service.stock.ProductService;
import com.quickkoala.service.supplier.PurchaseService;
import com.quickkoala.utils.GetToken;

import jakarta.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("main")
public class ReceiveRestController {

	private static final int SIZE = 10;
	
	@Autowired
	private PurchaseService purchaseService;

	@Autowired
	private ViewReceiveTempService viewReceiveTempService;

	@Autowired
	private ReceiveDetailService receiveDetailService;

	@Autowired
	private ReceiveReturnService receiveReturnService;

	@Autowired
	private ReceiveTempService receiveTempService;

	@Autowired
	private LotService lotService;

	@Autowired
	private ViewPurchaseDetailService viewPurchaseDetailService;

	@Autowired
	private ViewPurchaseService viewPurchaseService;

	@Autowired
	private ViewReceiveService viewReceiveService;

	@Autowired
	private ViewReceiveReturnService viewReceiveReturnService;

	@Autowired
	private ViewReceiveSummaryService viewReceiveSummaryService;

	@Autowired
	private ViewLocationProductService viewLocationProductService;
	
	@Autowired
	private ProductService productService;

	// @Autowired
	// private SupplierService supplierService;

	// 발주요청
	@PostMapping("receive/purchaseAdd")
	public ResponseEntity<String> purchaseAdd(@ModelAttribute PurchaseListDto orders, HttpServletRequest request) {
		purchaseService.addOrders(orders,GetToken.getManagerName(request));
		return ResponseEntity.ok("success");
	}

	// 발주요청 페이지 상품목록 모달 데이터
	@GetMapping("receive/productData")
	public ResponseEntity<List<ViewPurchaseEntity>> productData(@RequestParam String code, @RequestParam String word) {
		List<ViewPurchaseEntity> result = null;
		if (code.equals("") || word.equals("")) {
			result = viewPurchaseService.getAllData();
		} else {
			result = viewPurchaseService.getSearchData(code, word);
		}
		return ResponseEntity.ok(result);
	}

	// 발주내역 페이지 데이터
	@GetMapping("receive/purchaseData/{pno}/{status}")
	public ResponseEntity<Page<ViewPurchaseDetailEntity>> purchaseData(@PathVariable Integer pno, @PathVariable String status,
			@ModelAttribute SearchDto dto) {
		String sDate = dto.getSDate();
		String word = dto.getWord();
		Page<ViewPurchaseDetailEntity> result = null;
		if (status.equals("all")) {
			result = viewPurchaseDetailService.getPaginatedData(pno, SIZE, dto);
		} else {
			if (word.equals("") && sDate.equals("")) {
				result = viewPurchaseDetailService.getPaginatedDataByStatus(status, pno, SIZE);
			} else {
				result = viewPurchaseDetailService.getPaginatedData(pno, SIZE, dto);
			}
		}
		return ResponseEntity.ok(result);
	}

	// 가입고 페이지 데이터
	@GetMapping("receive/tempReceiveData/{pno}")
	public ResponseEntity<Page<ViewReceiveTempEntity>> tempReceiveData(@PathVariable Integer pno, @ModelAttribute SearchDto dto) {
		Page<ViewReceiveTempEntity> result = null;
		result = viewReceiveTempService.getPaginatedData(pno, SIZE, dto);
		return ResponseEntity.ok(result);
	}

	// 입고확정 모달
	@GetMapping("receive/receivingModal")
	public ResponseEntity<ReceiveModalDto> receivingModal(@ModelAttribute ReceiveModalDto dto) {
		List<String> locationCode = new ArrayList<>();
		if (viewLocationProductService.getCount(dto.getProductCode()) == 0) {
			List<ViewLocationProductEntity> loca = viewLocationProductService.getData();
			for (ViewLocationProductEntity ent : loca) {
				if (ent.getProductCode() == null) {
					locationCode.add(ent.getLocationCode());
				}

			}
			dto.setLocation(locationCode);
		}else {
			locationCode.add(viewLocationProductService.getLocationCode(dto.getProductCode()));
			dto.setLocation(locationCode);
		}
		return ResponseEntity.ok(dto);
	}

	// 입고확정
	@PostMapping("receive/receiving")
	public ResponseEntity<String> receiving(@ModelAttribute ReceivingDto dto, HttpServletRequest request) {
		ReceiveDetailEntity result = new ReceiveDetailEntity();
		ReceiveReturnEntity result2 = new ReceiveReturnEntity();
		
		// 입고수량이 있을때
		if (dto.getReQty() != 0) {
			result = receiveDetailService.addData(dto.getCode(), dto.getReQty(), GetToken.getManagerName(request));
			lotService.addLot(dto);
			if(!dto.getLocation().equals("N")) {
				int result3 = productService.modifyLocation(dto.getProductCode(), dto.getLocation());
				System.out.println(result3);
			}
		}
		// 반품수량이 있을때
		if (dto.getCaQty() != 0) {
			result2 = receiveReturnService.addData(dto, GetToken.getManagerName(request));
		}

		if (result == null || result2 == null) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("no");
		} else {
			receiveTempService.removeData(dto.getCode());
			return ResponseEntity.ok("ok");
		}
	}

	// 입고현황 데이터 + 페이징
	@GetMapping("receive/summaryData/{pno}")
	public ResponseEntity<Page<ViewReceiveSummaryEntity>> summaryData(@PathVariable Integer pno, @RequestParam String code,
			@RequestParam String word) {
		Page<ViewReceiveSummaryEntity> result = null;
		if (code.equals("") || word.equals("")) {
			result = viewReceiveSummaryService.getPaginatedData(pno, SIZE);
		} else {
			result = viewReceiveSummaryService.getPaginatedData(pno, SIZE, code, word);
		}
		return ResponseEntity.ok(result);
	}

	// 입고내역 데이터 + 페이징
	@GetMapping("receive/detailData/{pno}")
	public ResponseEntity<Page<ViewReceiveEntity>> detailData(@PathVariable Integer pno, @ModelAttribute SearchDto dto) {
		Page<ViewReceiveEntity> result = null;
		result = viewReceiveService.getPaginatedData(pno, SIZE, dto);
		return ResponseEntity.ok(result);
	}
	
	// 입고반품 데이터 + 페이징
	@GetMapping("receive/returnData/{pno}")
	public ResponseEntity<Page<ViewReceiveReturnEntity>> returnData(@PathVariable Integer pno, @ModelAttribute SearchDto dto) {
		Page<ViewReceiveReturnEntity> result = null;
		result = viewReceiveReturnService.getPaginatedData(pno, SIZE, dto);
		return ResponseEntity.ok(result);
	}

	// ** 사용안하는 코드 **//
	/*
	 * // 제조사 자동완성
	 * 
	 * @GetMapping("receive/autocomplete") public List<SupplierEntity>
	 * autocomplete(@RequestParam String term) { return
	 * supplierService.searchByName(term); }
	 * 
	 * // 엑셀 불러오기
	 * 
	 * @PostMapping("receive/upload-excel") public List<PurchaseDto>
	 * uploadExcel(@RequestParam("excel") MultipartFile file) { List<PurchaseDto>
	 * data = new ArrayList<>(); ExcelUpload eu = new ExcelUpload();
	 * 
	 * for (Row row : eu.uploadExcel(file)) { if (row.getRowNum() == 0) { continue;
	 * } PurchaseDto dto = new PurchaseDto();
	 * dto.setProduct_code(String.valueOf((int)
	 * row.getCell(0).getNumericCellValue()));
	 * dto.setSupplier(row.getCell(1).getStringCellValue());
	 * dto.setProduct(row.getCell(2).getStringCellValue()); dto.setQuantity((int)
	 * row.getCell(3).getNumericCellValue()); dto.setPrice((int)
	 * row.getCell(4).getNumericCellValue()); dto.setTotal_price((int)
	 * row.getCell(5).getNumericCellValue()); data.add(dto); } return data; }
	 */
}
