package com.quickkoala.service.sales;

import java.io.File;
import java.io.FileInputStream;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.quickkoala.dto.sales.ClientsOrderProductsDTO;
import com.quickkoala.dto.sales.ClientsOrdersDTO;
import com.quickkoala.entity.order.MaxOrderNumberEntity;
import com.quickkoala.entity.order.OrderEntity;
import com.quickkoala.entity.order.OrderEntity.OrderStatus;
import com.quickkoala.entity.sales.ClientsOrderProductsEntity;
import com.quickkoala.entity.sales.ClientsOrdersEntity;
import com.quickkoala.repository.order.MaxOrderNumberRepository;
import com.quickkoala.repository.order.OrderRepository;
import com.quickkoala.repository.sales.ClientsOrderProductsRepository;
import com.quickkoala.repository.sales.ClientsOrdersRepository;
import com.quickkoala.repository.stock.ProductRepository;
import com.quickkoala.token.config.JwtTokenProvider;

@Service
public class SalesOrderServiceImpl implements SalesOrderService {

    @Autowired
    private ClientsOrdersRepository clientsOrdersRepository;
    
    @Autowired
    private ClientsOrderProductsRepository clientsOrderProductsRepository;
    
    @Autowired
    private JwtTokenProvider jwtTokenProvider;
    
    @Autowired
    private OrderRepository orderRepository;
    
    @Autowired
    private ProductRepository productRepository;
    
    @Autowired
    private MaxOrderNumberRepository maxOrderNumberRepository;

    @Transactional
    @Override
    public List<String> saveOrder(List<ClientsOrdersDTO> orders, String token) {
        LocalDateTime now = LocalDateTime.now();
        List<String> duplicateOrders = new ArrayList<>();  // 중복된 주문을 저장할 리스트

        for (ClientsOrdersDTO orderDTO : orders) {

            // 유효성 검사
            if (orderDTO.getOrderDate() == null || 
                orderDTO.getName() == null || 
                orderDTO.getTel() == null || 
                orderDTO.getEmail() == null) {
                continue;
            }

            ClientsOrdersEntity existingOrder = findExistingOrder(orderDTO);

            String orderId;
            ClientsOrdersEntity order;

            // 기존 주문이 있으면 해당 주문에 상품을 추가
            if (existingOrder != null) {
                order = existingOrder;
                orderId = order.getOrderId();
            } else {
                // 새로운 주문 생성
                orderId = generateOrderId(orderDTO.getOrderDate());
                order = new ClientsOrdersEntity();
                order.setOrderId(orderId);
                order.setName(orderDTO.getName());
                order.setTel(orderDTO.getTel());
                order.setEmail(orderDTO.getEmail());
                order.setPost(orderDTO.getPost());
                order.setAddress(orderDTO.getAddress());
                order.setAddressDetail(orderDTO.getAddressDetail());
                order.setClientMemo(orderDTO.getClientMemo());
                order.setManager(jwtTokenProvider.getName(token));
                order.setCode(jwtTokenProvider.getCode(token));
                order.setManagerMemo(orderDTO.getManagerMemo());
                order.setOrderDate(orderDTO.getOrderDate());
                order.setCreatedDt(now);
                
                clientsOrdersRepository.save(order);  // 주문 저장
            }

            // 상품 정보 저장 (동일한 orderId로 여러 상품 저장)
            List<String> productCodes = new ArrayList<>();
            for (ClientsOrderProductsDTO productDTO : orderDTO.getProducts()) {
                List<ClientsOrderProductsEntity> existingProducts = clientsOrderProductsRepository.findByClientsOrdersOrderIdAndProductCode(orderId, productDTO.getProductCode());

                // 중복된 상품이 없을 경우에만 저장
                if (existingProducts.isEmpty()) {
                    ClientsOrderProductsEntity newProduct = new ClientsOrderProductsEntity();
                    newProduct.setClientsOrders(order);  // 기존 또는 새로 생성한 주문과 연관
                    newProduct.setProductCode(productDTO.getProductCode());
                    newProduct.setProductName(productDTO.getProductName());
                    newProduct.setQty(productDTO.getQty());
                    clientsOrderProductsRepository.save(newProduct);
                    productCodes.add(productDTO.getProductCode());
                }else {
                	DateTimeFormatter format= DateTimeFormatter.ofPattern("yyyy-MM-dd h:mm:ss a");
                	duplicateOrders.add("[" + orderDTO.getOrderDate().format(format) + "]" + orderDTO.getName() + "-" + orderDTO.getTel());
                }
            }

            // OrderEntity 초기화 (기존 주문이 있는지 확인 후 생성)
            OrderEntity salesOrder = null;
            int orderTotal = 0;
            List<OrderEntity> temp = orderRepository.findByOrderId(orderId);
            if (!temp.isEmpty()) {
                salesOrder = temp.get(0);
                orderTotal = salesOrder.getOrderTotal();
            } else {
                // salesOrder가 없으면 새로 생성
                salesOrder = new OrderEntity();
                salesOrder.setDt(now);
                salesOrder.setManager(jwtTokenProvider.getName(token));
                salesOrder.setMemo(null);
                salesOrder.setStatus(OrderStatus.미승인);
                salesOrder.setSalesCode(jwtTokenProvider.getCode(token));
                salesOrder.setOrderId(orderId);  // 새로 생성한 orderId 사용
              //salesOrder.setNumber(generateOrderNumber(now));
                salesOrder.setNumber(temp(now));
            }

            // 주문 총액 계산
            List<Integer> productPrices = productRepository.findPricesByCodes(productCodes);
            for (Integer price : productPrices) {
                orderTotal += price;
            }

            // 주문 총액 업데이트 및 저장
            salesOrder.setOrderTotal(orderTotal);
            orderRepository.save(salesOrder);
        }

        return duplicateOrders;  // 중복된 주문 정보를 반환
    }


    @Override
    public List<ClientsOrdersDTO> parseExcelFile(File file) {
        List<ClientsOrdersDTO> orders = new ArrayList<>();
        try (FileInputStream fis = new FileInputStream(file);
             XSSFWorkbook workbook = new XSSFWorkbook(fis)) {

            XSSFSheet sheet = workbook.getSheetAt(0);
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

            for (int i = 1; i <= sheet.getLastRowNum(); i++) {
                Row row = sheet.getRow(i);
                ClientsOrdersDTO orderDTO = new ClientsOrdersDTO();

                // 각 셀에 대한 null 체크
                orderDTO.setName(getCellValueAsString(row, 0));
                orderDTO.setTel(getCellValueAsString(row, 1));
                orderDTO.setEmail(getCellValueAsString(row, 2));
                orderDTO.setPost(getCellValueAsString(row, 3));
                orderDTO.setAddress(getCellValueAsString(row, 4));
                orderDTO.setAddressDetail(getCellValueAsString(row, 5));

                // 주문자 메모 처리 (nullable)
                orderDTO.setClientMemo(getCellValueAsString(row, 10));

             // 주문 날짜 처리
                if (row.getCell(8) != null) {
                    if (row.getCell(8).getCellType() == CellType.NUMERIC) {
                        // 엑셀에서 날짜 타입으로 읽는 경우
                        LocalDateTime orderDate = row.getCell(8).getLocalDateTimeCellValue();
                        System.out.println("Parsed Numeric Order Date: " + orderDate); // 로그 추가
                        orderDTO.setOrderDate(orderDate);
                    } else if (row.getCell(8).getCellType() == CellType.STRING) {
                        // AM/PM 포맷의 날짜 문자열을 처리하는 포맷터
                        formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd h:mm:ss a");
                        String dateStr = row.getCell(8).getStringCellValue();
                        
                        try {
                            LocalDateTime orderDate = LocalDateTime.parse(dateStr, formatter);
                            System.out.println("Parsed String Order Date: " + orderDate); // 로그 추가
                            orderDTO.setOrderDate(orderDate);
                        } catch (Exception e) {
                            System.err.println("날짜 변환 오류: " + e.getMessage());
                            //orderDTO.setOrderDate(LocalDateTime.now());  // 기본값으로 현재 시간 설정
                        }
                    }
                } else {
                    System.out.println("Order Date is null");
                    //orderDTO.setOrderDate(LocalDateTime.now());
                }

                // 담당자 메모 처리 (nullable)
                orderDTO.setManagerMemo(getCellValueAsString(row, 11));

                // 상품 정보 설정
                ClientsOrderProductsDTO productDTO = new ClientsOrderProductsDTO();
                productDTO.setProductCode(getCellValueAsString(row, 6));
                productDTO.setProductName(getCellValueAsString(row, 7));
                if (row.getCell(9) != null && row.getCell(9).getCellType() == CellType.NUMERIC) {
                    productDTO.setQty((int) row.getCell(9).getNumericCellValue());
                }

                List<ClientsOrderProductsDTO> products = new ArrayList<>();
                products.add(productDTO);
                orderDTO.setProducts(products);

                orders.add(orderDTO);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return orders;
    }

    // 셀 값을 안전하게 가져오는 유틸리티 메서드
    private String getCellValueAsString(Row row, int cellIndex) {
        if (row.getCell(cellIndex) != null) {
            if (row.getCell(cellIndex).getCellType() == CellType.STRING) {
                return row.getCell(cellIndex).getStringCellValue();
            } else if (row.getCell(cellIndex).getCellType() == CellType.NUMERIC) {
                return String.valueOf((int) row.getCell(cellIndex).getNumericCellValue());
            }
        }
        return null;
    }

    
    // 주문 날짜를 기반으로 고유한 customOrderId를 생성
    private String generateOrderId(LocalDateTime orderDate) {
        // YYYYMMDD 형식의 날짜 문자열 생성
        String dateStr = orderDate.format(DateTimeFormatter.ofPattern("yyyyMMdd"));
        
        // 해당 날짜에 존재하는 주문 수를 카운트하여 그에 맞는 ID 생성
        List<String> existingOrderIds = clientsOrdersRepository.findOrderIdsByDate(dateStr);

        // 가장 높은 순번을 찾아 다음 순번 설정
        int nextNumber = 1; // 기본 값은 1 (001)
        if (!existingOrderIds.isEmpty()) {
            for (String orderId : existingOrderIds) {
                int existingNumber = Integer.parseInt(orderId.substring(orderId.length() - 3));
                if (existingNumber >= nextNumber) {
                    nextNumber = existingNumber + 1;
                }
            }
        }
        
        // 고유 식별자 생성 (날짜 + 순번 형식)
        return dateStr + "-" + String.format("%03d", nextNumber);
    }

    
    private String generateOrderNumber(LocalDateTime date) {
    	String orderDay=date.format(DateTimeFormatter.ofPattern("yyyyMMdd"));
        List<String> temp = orderRepository.findMaxOrderNumber(orderDay);
        String result=null;
        if(temp.size()==0) {
        	result=orderDay+"-001";
        }else {
        	String maxNumber=temp.get(0);
            result = maxNumber.split("-")[0]+"-"+String.format("%03d",Integer.valueOf(maxNumber.split("-")[1])+1);
        }
        return result;
    }
    
    private String temp(LocalDateTime date) {
    	LocalDate day = date.toLocalDate();
    	MaxOrderNumberEntity max = maxOrderNumberRepository.findByDt(day);
    	
    	if(max==null) {
    		MaxOrderNumberEntity temp = new MaxOrderNumberEntity();
    		temp.setDt(day);
    		temp.setNum(1);
    		maxOrderNumberRepository.save(temp);
    		return day.format(DateTimeFormatter.ofPattern("yyyyMMdd"))+"-001";
    	}else {
    		int newNumber = max.getNum()+1;
        	max.setNum(newNumber);
        	maxOrderNumberRepository.save(max);
        	return day.format(DateTimeFormatter.ofPattern("yyyyMMdd"))+"-"+String.format("%03d",newNumber);
    	}
    }
    
    //동일한 주문확인
    private ClientsOrdersEntity findExistingOrder(ClientsOrdersDTO orderDTO) {
        // 이름, 연락처, 주문 날짜를 기준으로 동일한 주문을 찾음
        return clientsOrdersRepository.findByNameAndTelAndOrderDate(
            orderDTO.getName(),
            orderDTO.getTel(),
            orderDTO.getOrderDate()
        ).orElse(null);  // 존재하지 않으면 null 반환
    }
    
    @Override
    public List<ClientsOrderProductsDTO> getOrderProductsByOrderId(String orderId) {
        // ClientsOrdersEntity를 가져옴
        ClientsOrdersEntity clientsOrdersEntity = clientsOrdersRepository.findByOrderId(orderId)
            .orElseThrow(() -> new IllegalArgumentException("Invalid order ID: " + orderId));
        String orderStatus = orderRepository.getStatusByOrderId(orderId)
        	    .map(OrderStatus::name)
        	    .orElse("처리중");
        // 주문 상태(OrderEntity) 가져오기
//        OrderEntity orderEntity = clientsOrdersEntity.getOrderEntity();
        //String orderStatus = orderEntity != null ? orderEntity.getStatus().name() : "처리중";

        // 주문에 해당하는 상품 목록을 가져옴
        List<ClientsOrderProductsEntity> products = clientsOrderProductsRepository.findByClientsOrdersOrderId(orderId);

        // 상품 목록을 DTO로 변환하면서 처리 상태를 함께 설정
        return products.stream().map(productEntity -> {
            ClientsOrderProductsDTO dto = new ClientsOrderProductsDTO();
            dto.setProductCode(productEntity.getProductCode());
            dto.setProductName(productEntity.getProductName());
            dto.setQty(productEntity.getQty());
            dto.setStatus(orderStatus);  // OrderEntity의 상태를 DTO에 설정
            dto.setManagerMemo(clientsOrdersEntity.getManagerMemo());
            dto.setClientMemo(clientsOrdersEntity.getClientMemo());
            return dto;
        }).collect(Collectors.toList());
    }

    
 // 검색 메서드 수정
    public Page<ClientsOrdersEntity> searchOrders(String managerCompanyCode, String searchType, String searchText, LocalDate startDate, LocalDate endDate, int page, int size) {
        // 페이지 요청 시 orderId 기준으로 오름차순 정렬을 추가
        Pageable pageable = PageRequest.of(page, size, Sort.by("orderId").descending());

        if (startDate != null && endDate != null) {
            LocalDateTime startOfDay = startDate.atStartOfDay();
            LocalDateTime endOfDay = endDate.atTime(LocalTime.MAX);
            if (searchType.equals("orderId")) {
                return clientsOrdersRepository.findByCodeAndOrderIdContainingAndOrderDateBetween(managerCompanyCode, searchText, startOfDay, endOfDay, pageable);
            } else if (searchType.equals("name")) {
                return clientsOrdersRepository.findByCodeAndNameContainingAndOrderDateBetween(managerCompanyCode, searchText, startOfDay, endOfDay, pageable);
            } else {
                return clientsOrdersRepository.findByCodeAndOrderDateBetween(managerCompanyCode, startOfDay, endOfDay, pageable);
            }
        } else if (startDate != null) {
            LocalDateTime startOfDay = startDate.atStartOfDay();
            if (searchType.equals("orderId")) {
                return clientsOrdersRepository.findByCodeAndOrderIdContainingAndOrderDate(managerCompanyCode, searchText, startOfDay, pageable);
            } else if (searchType.equals("name")) {
                return clientsOrdersRepository.findByCodeAndNameContainingAndOrderDate(managerCompanyCode, searchText, startOfDay, pageable);
            } else {
                return clientsOrdersRepository.findByCodeAndOrderDate(managerCompanyCode, startOfDay, pageable);
            }
        } else if (searchType.equals("orderId")) {
            return clientsOrdersRepository.findByCodeAndOrderIdContaining(managerCompanyCode, searchText, pageable);
        } else if (searchType.equals("name")) {
            return clientsOrdersRepository.findByCodeAndNameContaining(managerCompanyCode, searchText, pageable);
        }
        return clientsOrdersRepository.findByCode(managerCompanyCode, pageable);
    }



    // 회사 코드에 따른 모든 주문 조회
    public List<ClientsOrdersEntity> findByCode(String managerCompanyCode) {
        return clientsOrdersRepository.findByCode(managerCompanyCode);
    }

}