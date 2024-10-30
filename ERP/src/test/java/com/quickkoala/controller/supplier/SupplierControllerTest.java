package com.quickkoala.controller.supplier;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.lenient;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.quickkoala.dto.supplier.DeliveryRegiDto;
import com.quickkoala.entity.supplier.DeliveryDetailEntity;
import com.quickkoala.service.receive.ReceiveTempService;
import com.quickkoala.service.supplier.DeliveryDetailService;

@ExtendWith(MockitoExtension.class)
public class SupplierControllerTest {

	@InjectMocks
    private SupplierRestController supplierRestController; // 실제 SupplierController 클래스를 설정

    @Mock
    private DeliveryDetailService deliveryDetailService; // 서비스 모킹
    @Mock
    private ReceiveTempService receiveTempService; // 서비스 모킹

    @Test
    public void testDeliveryRegiSuccess() {
        // 테스트 데이터 준비
        DeliveryRegiDto dto = new DeliveryRegiDto();
        dto.setData("13");
        dto.setEa(50);

        // Mockito의 when-thenReturn 구문을 사용하여 모킹
        DeliveryDetailEntity mockEntity = new DeliveryDetailEntity();
        mockEntity.setCode("mockCode");

        when(deliveryDetailService.addDelivery(any(), any())).thenReturn(mockEntity);
        // receiveTempService의 addDelivery는 void 메소드이므로 따로 모킹할 필요 없음

        // 메소드 호출
        ResponseEntity<String> response = supplierRestController.deliveryRegi(dto); // 실제 메소드 호출

        // 결과 검증
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("ok", response.getBody());
    }

    @Test
    public void testDeliveryRegiFailure() {
        // 테스트 데이터 준비
        DeliveryRegiDto dto = new DeliveryRegiDto();
        dto.setData("13");
        dto.setEa(50);

        // lenient()를 사용하여 예외 발생 상황 모킹
        lenient().when(deliveryDetailService.addDelivery(any(), any())).thenThrow(new RuntimeException("Error occurred"));

        // 메소드 호출
        ResponseEntity<String> response = supplierRestController.deliveryRegi(dto);

        // 결과 검증
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals("no", response.getBody());
    }
}
