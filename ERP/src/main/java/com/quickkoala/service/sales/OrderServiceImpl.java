package com.quickkoala.service.sales;

import java.io.File;
import java.io.FileInputStream;
import java.time.LocalDate;
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
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.quickkoala.dto.sales.ClientsOrderProductsDTO;
import com.quickkoala.dto.sales.ClientsOrdersDTO;
import com.quickkoala.entity.sales.ClientsOrderProductsEntity;
import com.quickkoala.entity.sales.ClientsOrdersEntity;
import com.quickkoala.repository.sales.ClientsOrderProductsRepository;
import com.quickkoala.repository.sales.ClientsOrdersRepository;

@Service
public class OrderServiceImpl implements OrderService {

    @Autowired
    private ClientsOrdersRepository clientsOrdersRepository;
    
    @Autowired
    private ClientsOrderProductsRepository clientsOrderProductsRepository;

    @Transactional
    @Override
    public void saveOrder(List<ClientsOrdersDTO> orders) {
        for (ClientsOrdersDTO orderDTO : orders) {
            ClientsOrdersEntity order = findExistingOrder(orderDTO);

            // 주문 정보가 이미 저장되어 있지 않다면 새로 생성
            if (order == null) {
                order = new ClientsOrdersEntity();
                //주문자정보
                order.setName(orderDTO.getName());
                order.setTel(orderDTO.getTel());
                order.setEmail(orderDTO.getEmail());
                order.setPost(orderDTO.getPost());
                order.setAddress(orderDTO.getAddress());
                order.setAddressDetail(orderDTO.getAddressDetail());
                order.setClientMemo(orderDTO.getClientMemo());
                //관리자
                order.setManager(orderDTO.getManager());
                order.setManagerCompanyCode(orderDTO.getManagerCompanyCode());
                order.setManagerMemo(orderDTO.getManagerMemo());

                // 주문 날짜 설정
                order.setOrderDate(orderDTO.getOrderDate());
                
                //주문고유식별자
                // 고유한 주문 ID 생성 (order_id 설정)
                String orderId = generateOrderId(orderDTO.getOrderDate());
                order.setOrderId(orderId); // order_id 필드에 설정
                
                // 새 주문을 저장하고 order_id 가져오기
                clientsOrdersRepository.save(order);
            }

            // 상품 정보 저장
            for (ClientsOrderProductsDTO productDTO : orderDTO.getProducts()) {
                ClientsOrderProductsEntity product = new ClientsOrderProductsEntity();
                product.setClientsOrders(order);  // 이미 저장된 order_id와 연동
                product.setProductCode(productDTO.getProductCode());
                product.setProductName(productDTO.getProductName());
                product.setQty(productDTO.getQty());
                clientsOrderProductsRepository.save(product);
            }
        }
    }

    @Override
    public List<ClientsOrdersDTO> parseExcelFile(File file) {
        List<ClientsOrdersDTO> orders = new ArrayList<>();
        try (FileInputStream fis = new FileInputStream(file);
             XSSFWorkbook workbook = new XSSFWorkbook(fis)) {

            XSSFSheet sheet = workbook.getSheetAt(0);
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd"); // 엑셀 날짜 형식과 맞는 포맷터

            for (int i = 1; i <= sheet.getLastRowNum(); i++) {
                Row row = sheet.getRow(i);
                ClientsOrdersDTO orderDTO = new ClientsOrdersDTO();
                orderDTO.setName(row.getCell(0).getStringCellValue());
                orderDTO.setTel(row.getCell(1).getStringCellValue());
                orderDTO.setEmail(row.getCell(2).getStringCellValue());
                orderDTO.setPost(row.getCell(3).getStringCellValue());
                orderDTO.setAddress(row.getCell(4).getStringCellValue());
                orderDTO.setAddressDetail(row.getCell(5).getStringCellValue());
                // 주문자 메모 처리 (nullable)
                if (row.getCell(10) != null) {
                    orderDTO.setClientMemo(row.getCell(10).getStringCellValue());
                } else {
                	orderDTO.setClientMemo(null);  // 값이 비어있으면 null 설정
                }
                // 주문 날짜 처리
                if (row.getCell(8).getCellType() == CellType.NUMERIC) {
                    // 엑셀에서 날짜 타입으로 읽는 경우 (NUMERIC)
                    LocalDate orderDate = row.getCell(8).getLocalDateTimeCellValue().toLocalDate();
                    orderDTO.setOrderDate(orderDate);
                } else {
                    // 엑셀에서 문자열 형식으로 읽는 경우
                    String dateStr = row.getCell(8).getStringCellValue();
                    LocalDate orderDate = LocalDate.parse(dateStr, formatter);
                    orderDTO.setOrderDate(orderDate);
                }
                
                // 추가된 manager 관련 정보 처리
                //orderDTO.setManager();  쿠키에서 관리자 정보 추출해야함
                //orderDTO.setManagerCompanyCode(); 쿠키에서 관리자 정보 추출해야함
                
                
                // 담당자 메모 처리 (nullable)
                if (row.getCell(11) != null) {
                    orderDTO.setManagerMemo(row.getCell(11).getStringCellValue());
                } else {
                    orderDTO.setManagerMemo(null);  // 값이 비어있으면 null 설정
                }

                // 상품 정보 설정
                ClientsOrderProductsDTO productDTO = new ClientsOrderProductsDTO();
                productDTO.setProductCode(row.getCell(6).getStringCellValue());
                productDTO.setProductName(row.getCell(7).getStringCellValue());
                productDTO.setQty((int) row.getCell(9).getNumericCellValue());

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
    
    // 주문 날짜를 기반으로 고유한 customOrderId를 생성
    private String generateOrderId(LocalDate orderDate) {
        // 해당 날짜에 존재하는 주문의 개수를 가져옴
        Long orderCountForDate = clientsOrdersRepository.countByOrderDate(orderDate);

        // YYYYMMDD 형식의 날짜 문자열 생성
        String dateStr = orderDate.format(DateTimeFormatter.ofPattern("yyyyMMdd"));

        // 고유 식별자 생성 (날짜 + 001 형식으로)
        return dateStr + "-" + String.format("%03d", orderCountForDate + 1);
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
        List<ClientsOrderProductsEntity> productEntities = clientsOrderProductsRepository.findByClientsOrdersOrderId(orderId);
        return productEntities.stream().map(this::convertToDto).collect(Collectors.toList());
    }

    // Entity를 DTO로 변환
    private ClientsOrderProductsDTO convertToDto(ClientsOrderProductsEntity entity) {
        ClientsOrderProductsDTO dto = new ClientsOrderProductsDTO();
        dto.setProductCode(entity.getProductCode());
        dto.setProductName(entity.getProductName());
        dto.setQty(entity.getQty());
        return dto;
    }
    
    
    public Page<ClientsOrdersEntity> searchOrders(String searchType, String searchText, LocalDate searchDate, int page, int size) {
        Pageable pageable = PageRequest.of(page, size);

        if (searchDate != null) {
            // 날짜로 검색할 때
            if (searchType.equals("orderId")) {
                // 주문번호와 날짜로 검색
                return clientsOrdersRepository.findByOrderIdContainingAndOrderDate(searchText, searchDate, pageable);
            } else if (searchType.equals("name")) {
                // 주문자명과 날짜로 검색
                return clientsOrdersRepository.findByNameContainingAndOrderDate(searchText, searchDate, pageable);
            } else {
                // 날짜로만 검색
                return clientsOrdersRepository.findByOrderDate(searchDate, pageable);
            }
        } else if (searchType.equals("orderId")) {
            // 주문번호로만 검색
            return clientsOrdersRepository.findByOrderIdContaining(searchText, pageable);
        } else if (searchType.equals("name")) {
            // 주문자명으로만 검색
            return clientsOrdersRepository.findByNameContaining(searchText, pageable);
        }

        // 기본값: 전체 조회
        return clientsOrdersRepository.findAll(pageable);
    }
    
    public List<ClientsOrdersEntity> findAll(){
    	return clientsOrdersRepository.findAll();
    }

    
}