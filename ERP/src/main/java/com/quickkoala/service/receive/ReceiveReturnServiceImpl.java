package com.quickkoala.service.receive;

import java.time.LocalDate;
import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.quickkoala.dto.ReceivingDto;
import com.quickkoala.entity.receive.ReceiveReturnEntity;
import com.quickkoala.repository.receive.ReceiveReturnRepository;
import com.quickkoala.repository.supplier.DeliveryDetailRepository;
import com.quickkoala.utils.TodayUtils;

@Service
public class ReceiveReturnServiceImpl implements ReceiveReturnService{
	
	@Autowired
	private ReceiveReturnRepository receiveReturnRepository;
	
	@Override
	public ReceiveReturnEntity addData(ReceivingDto dto) {
		System.out.println(dto.getDeliveryCode());		
		
		int number = (int)this.countReturnsToday()+1;
		String formattedNumber = String.format("%03d", number);
		
		ReceiveReturnEntity data = new ReceiveReturnEntity();
		data.setCode("RE"+TodayUtils.getToday()+"-"+formattedNumber);
		data.setOrderNumber(dto.getOrderNumber());
		data.setDeliveryCode(dto.getDeliveryCode());
		data.setQuantity(dto.getCaQty());
		data.setReason(dto.getCon());
		data.setMemo(dto.getMemo());
		data.setDate(LocalDateTime.now());
		data.setManager(dto.getManager());
		
		
		return receiveReturnRepository.save(data);
	}
	
	public long countReturnsToday() {
        LocalDate today = LocalDate.now();
        LocalDateTime startOfDay = today.atStartOfDay();
        LocalDateTime endOfDay = today.plusDays(1).atStartOfDay();

        return receiveReturnRepository.countByDateRange(startOfDay, endOfDay);
    }
	
}
