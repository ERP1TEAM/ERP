package com.quickkoala.controller.receive;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.quickkoala.dto.receive.ReceivingDto;
import com.quickkoala.entity.receive.ReceiveDetailEntity;
import com.quickkoala.service.receive.ReceiveDetailService;
import com.quickkoala.service.receive.ReceiveReturnService;
import com.quickkoala.service.receive.ReceiveTempService;
import com.quickkoala.service.receive.ReceivingService;
import com.quickkoala.service.stock.LotService;
import com.quickkoala.service.stock.ProductService;
import com.quickkoala.token.config.JwtTokenProvider;
import com.quickkoala.utils.GetToken;

import jakarta.servlet.http.HttpServletRequest;

@ExtendWith(MockitoExtension.class)
class ReceiveRestControllerTest {

    @InjectMocks
    private ReceiveRestController receiveRestController;

    @Mock
    private ReceivingService receivingService; // 서비스 모킹
    @Mock
    private ReceiveDetailService receiveDetailService; // 서비스 모킹
    @Mock
    private LotService lotService; // 서비스 모킹
    @Mock
    private ProductService productService; // 서비스 모킹
    @Mock
    private ReceiveReturnService receiveReturnService; // 서비스 모킹
    @Mock
    private ReceiveTempService receiveTempService; // 서비스 모킹
    @Mock
    private JwtTokenProvider mockJwtTokenProvider; // JwtTokenProvider 모킹

    @BeforeEach
    public void setup() {
        GetToken.init(mockJwtTokenProvider); // jwtTokenProvider 초기화
        
    }

    @Test
    public void testReceiving() {
        // 테스트 데이터 준비
        ReceivingDto dto = new ReceivingDto();
        dto.setOrderNumber("20240926-029");
        dto.setDeliveryCode("DT20240927-006");
        dto.setCode("RT20240927-003");
        dto.setCon("Some content");
        dto.setMemo("Test memo");
        dto.setLocation("W1-R5-3B");  
        dto.setProductCode("P0000004");
        dto.setWqty(100);
        dto.setReQty(10);  // 여기가 10으로 설정되어 있어야 호출됨
        dto.setCaQty(0);

        // Mockito의 when-thenReturn 구문을 사용하여 모킹
        String managerName = "Test Manager";
        when(mockJwtTokenProvider.getName(any())).thenReturn(managerName);

        // HttpServletRequest 모킹
        HttpServletRequest request = Mockito.mock(HttpServletRequest.class);
        when(request.getHeader("Authorization")).thenReturn("Bearer someToken");

        // addData의 동작을 정의
        when(receiveDetailService.addData(anyString(), anyInt(), anyString())).thenReturn(new ReceiveDetailEntity());

        // 메소드 호출
        ResponseEntity<String> response = receiveRestController.receiving(dto, request);

        // 결과 검증
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("success", response.getBody());

        // verify() 메소드를 사용하여 서비스 호출 검증
        verify(receivingService).processReceiving(dto, managerName); // processReceiving 검증
        System.out.println("process 호출되버림");
        verify(receiveDetailService).addData(dto.getCode(), dto.getReQty(), managerName); // addData 검증
        verify(lotService).addLot(dto); // addLot 검증
        verify(productService).modifyLocation(dto.getProductCode(), dto.getLocation()); // modifyLocation 검증
        verify(receiveTempService).removeData(dto.getCode()); // removeData 검증
    }



    @Test
    public void testReceivingWithZeroReQty() {
        // Mocking HttpServletRequest
        HttpServletRequest mockRequest = mock(HttpServletRequest.class);
        when(mockRequest.getAttribute("managerName")).thenReturn("Test Manager"); // "managerName"을 설정

        // Mocking GetToken
        GetToken.init(mockJwtTokenProvider); // JwtTokenProvider 초기화
        when(mockJwtTokenProvider.getName(any())).thenReturn("Test Manager");

        // 테스트 데이터 준비
        ReceivingDto dto = new ReceivingDto();
        dto.setOrderNumber("ORD123");
        dto.setDeliveryCode("DEL456");
        dto.setCode("CODE789");      // 필수 필드
        dto.setCon("Some content");
        dto.setMemo("Test memo");
        dto.setLocation("A1");       // 필수일 수 있음
        dto.setProductCode("P1");    // 필수 필드
        dto.setWqty(100);             // 필요 시 설정
        dto.setReQty(0);              // 필수 필드, 0으로 설정
        dto.setCaQty(0);             // 필수 필드, 0으로 설정

        // 메소드 호출
        ResponseEntity<String> response = receiveRestController.receiving(dto, mockRequest);

        // 결과 검증
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("success", response.getBody());

        // 서비스 메소드 호출 검증
        verify(receivingService).processReceiving(dto, "Test Manager");

        // 임시 데이터 제거가 호출되어야 함
        verify(receiveTempService).removeData(dto.getCode()); // "CODE789"로 확인

        // 확인을 위한 추가 로그 출력
        System.out.println("Called removeData with CODE789");

        // 추가 검증
        verifyNoMoreInteractions(receivingService, receiveReturnService, receiveDetailService, lotService, productService); // 이 서비스들은 호출되지 않아야 함
    }



}
