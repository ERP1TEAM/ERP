package com.quickkoala.service.receive;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.quickkoala.dto.receive.ReceivingDto;
import com.quickkoala.service.stock.LotService;
import com.quickkoala.service.stock.ProductService;

@Service
public class ReceivingService {

    private final ReceiveDetailService receiveDetailService;
    private final LotService lotService;
    private final ProductService productService;
    private final ReceiveReturnService receiveReturnService;
    private final ReceiveTempService receiveTempService;

    public ReceivingService(ReceiveDetailService receiveDetailService, LotService lotService, 
                            ProductService productService, ReceiveReturnService receiveReturnService, 
                            ReceiveTempService receiveTempService) {
        this.receiveDetailService = receiveDetailService;
        this.lotService = lotService;
        this.productService = productService;
        this.receiveReturnService = receiveReturnService;
        this.receiveTempService = receiveTempService;
    }

    @Transactional
    public void processReceiving(ReceivingDto dto, String manager) {
        // 입고수량이 있을때
        if (dto.getReQty() != 0) {
            receiveDetailService.addData(dto.getCode(), dto.getReQty(), manager);
            lotService.addLot(dto);
            if (!dto.getLocation().equals("N")) {
                productService.modifyLocation(dto.getProductCode(), dto.getLocation());
            }
        }
        // 반품수량이 있을때
        if (dto.getCaQty() != 0) {
            receiveReturnService.addData(dto, manager);
        }

        // 임시 데이터 제거
        receiveTempService.removeData(dto.getCode());
    }
}
