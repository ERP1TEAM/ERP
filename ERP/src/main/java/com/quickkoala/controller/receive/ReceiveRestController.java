package com.quickkoala.controller.receive;

import java.util.List;
import java.util.function.Supplier;
import java.util.stream.Collectors;

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
import com.quickkoala.entity.receive.ViewLocationProductEntity;
import com.quickkoala.entity.receive.ViewPurchaseDetailEntity;
import com.quickkoala.entity.receive.ViewPurchaseEntity;
import com.quickkoala.entity.receive.ViewReceiveEntity;
import com.quickkoala.entity.receive.ViewReceiveReturnEntity;
import com.quickkoala.entity.receive.ViewReceiveSummaryEntity;
import com.quickkoala.entity.receive.ViewReceiveTempEntity;
import com.quickkoala.service.receive.ReceivingService;
import com.quickkoala.service.receive.ViewLocationProductService;
import com.quickkoala.service.receive.ViewPurchaseDetailService;
import com.quickkoala.service.receive.ViewPurchaseService;
import com.quickkoala.service.receive.ViewReceiveReturnService;
import com.quickkoala.service.receive.ViewReceiveService;
import com.quickkoala.service.receive.ViewReceiveSummaryService;
import com.quickkoala.service.receive.ViewReceiveTempService;
import com.quickkoala.service.supplier.PurchaseService;
import com.quickkoala.utils.GetToken;

import jakarta.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("main")
public class ReceiveRestController {

	private static final int DEFAULT_SIZE = 10;
    private static final int SUMMARY_SIZE = 20;
	
	private final PurchaseService purchaseService;
	private final ViewReceiveTempService viewReceiveTempService;
	private final ViewPurchaseDetailService viewPurchaseDetailService;
	private final ViewPurchaseService viewPurchaseService;
	private final ViewReceiveService viewReceiveService;
	private final ViewReceiveReturnService viewReceiveReturnService;
	private final ViewReceiveSummaryService viewReceiveSummaryService;
	private final ViewLocationProductService viewLocationProductService;
	private final ReceivingService receivingService;

	public ReceiveRestController(
		PurchaseService purchaseService,
		ViewReceiveTempService viewReceiveTempService,
		ViewPurchaseDetailService viewPurchaseDetailService,
		ViewPurchaseService viewPurchaseService,
		ViewReceiveService viewReceiveService,
		ViewReceiveReturnService viewReceiveReturnService,
		ViewReceiveSummaryService viewReceiveSummaryService,
		ViewLocationProductService viewLocationProductService,
		ReceivingService receivingService) {
		this.purchaseService = purchaseService;
		this.viewReceiveTempService = viewReceiveTempService;
		this.viewPurchaseDetailService = viewPurchaseDetailService;
		this.viewPurchaseService = viewPurchaseService;
		this.viewReceiveService = viewReceiveService;
		this.viewReceiveReturnService = viewReceiveReturnService;
		this.viewReceiveSummaryService = viewReceiveSummaryService;
		this.viewLocationProductService = viewLocationProductService;
		this.receivingService = receivingService;
	}
	
	// 발주요청
    @PostMapping("receive/purchaseAdd")
    public ResponseEntity<String> purchaseAdd(@ModelAttribute PurchaseListDto orders, HttpServletRequest request) {
        return processRequest(() -> {
            String managerName = GetToken.getManagerName(request);
            purchaseService.addOrders(orders, managerName);
        });
    }

    // 발주요청 페이지 상품목록 모달 데이터
    @GetMapping("receive/productData")
    public ResponseEntity<List<ViewPurchaseEntity>> productData(@RequestParam String code, @RequestParam String word) {
        List<ViewPurchaseEntity> result = code.isEmpty() || word.isEmpty() ? 
            viewPurchaseService.getAllData() : 
            viewPurchaseService.getSearchData(code, word);
        return ResponseEntity.ok(result);
    }

    // 발주내역 페이지 데이터
    @GetMapping("receive/purchaseData/{pno}/{status}")
    public ResponseEntity<Page<ViewPurchaseDetailEntity>> purchaseData(@PathVariable Integer pno, @PathVariable String status, @ModelAttribute SearchDto dto) {
        return ResponseEntity.ok(status.equals("all") || dto.hasFilters() ? 
            viewPurchaseDetailService.getPaginatedData(pno, DEFAULT_SIZE, dto) : 
            viewPurchaseDetailService.getPaginatedDataByStatus(status, pno, DEFAULT_SIZE));
    }

    // 가입고 페이지 데이터
    @GetMapping("receive/tempReceiveData/{pno}")
    public ResponseEntity<Page<ViewReceiveTempEntity>> tempReceiveData(@PathVariable Integer pno, @ModelAttribute SearchDto dto) {
        return processPagedData(() -> viewReceiveTempService.getPaginatedData(pno, DEFAULT_SIZE, dto));
    }

    // 입고확정 모달
    @GetMapping("receive/receivingModal")
    public ResponseEntity<ReceiveModalDto> receivingModal(@ModelAttribute ReceiveModalDto dto) {
        List<String> locationCode = viewLocationProductService.getCount(dto.getProductCode()) == 0 ?
            getAvailableLocationCodes() :
            List.of(viewLocationProductService.getLocationCode(dto.getProductCode()));
        dto.setLocation(locationCode);
        return ResponseEntity.ok(dto);
    }

    // 입고확정
    @PostMapping("receive/receiving")
    public ResponseEntity<String> receiving(@ModelAttribute ReceivingDto dto, HttpServletRequest request) {
        return processRequest(() -> {
            String manager = GetToken.getManagerName(request);
            receivingService.processReceiving(dto, manager);
        });
    }

    // 입고현황 데이터 + 페이징
    @GetMapping("receive/summaryData/{pno}")
    public ResponseEntity<Page<ViewReceiveSummaryEntity>> summaryData(@PathVariable Integer pno, @RequestParam String code, @RequestParam String word) {
        return ResponseEntity.ok(code.isEmpty() || word.isEmpty() ?
            viewReceiveSummaryService.getPaginatedData(pno, SUMMARY_SIZE) :
            viewReceiveSummaryService.getPaginatedData(pno, SUMMARY_SIZE, code, word));
    }

    // 입고내역 데이터 + 페이징
    @GetMapping("receive/detailData/{pno}")
    public ResponseEntity<Page<ViewReceiveEntity>> detailData(@PathVariable Integer pno, @ModelAttribute SearchDto dto) {
        return processPagedData(() -> viewReceiveService.getPaginatedData(pno, DEFAULT_SIZE, dto));
    }

    // 입고반품 데이터 + 페이징
    @GetMapping("receive/returnData/{pno}")
    public ResponseEntity<Page<ViewReceiveReturnEntity>> returnData(@PathVariable Integer pno, @ModelAttribute SearchDto dto) {
        return processPagedData(() -> viewReceiveReturnService.getPaginatedData(pno, DEFAULT_SIZE, dto));
    }

    // 공통 예외 처리 메서드
    private ResponseEntity<String> processRequest(Runnable action) {
        try {
            action.run();
            return ResponseEntity.ok("success");
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("no");
        }
    }

    // 공통 페이징 데이터 처리 메서드
    private <T> ResponseEntity<Page<T>> processPagedData(Supplier<Page<T>> supplier) {
        try {
            return ResponseEntity.ok(supplier.get());
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }

    // 사용 가능한 위치 코드 가져오기
    private List<String> getAvailableLocationCodes() {
        return viewLocationProductService.getData().stream()
            .filter(ent -> ent.getProductCode() == null)
            .map(ViewLocationProductEntity::getLocationCode)
            .collect(Collectors.toList());
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
