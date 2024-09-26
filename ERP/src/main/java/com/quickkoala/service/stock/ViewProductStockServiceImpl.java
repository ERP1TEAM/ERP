package com.quickkoala.service.stock;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.quickkoala.dto.stock.ViewProductStockDto;
import com.quickkoala.entity.stock.ViewProductStockEntity;
import com.quickkoala.repository.stock.ViewProductStockRepository;

@Service
public class ViewProductStockServiceImpl implements ViewProductStockService{

	@Autowired
	private ViewProductStockRepository viewproductstockRepository;
	
	//Entity -> DTO 변환
	@Override
	public ViewProductStockDto converToViewProductStockDto(ViewProductStockEntity viewproductstockEntity) {
		ViewProductStockDto maptoViewProductStockDto = new ViewProductStockDto();
		maptoViewProductStockDto.setProductCode(viewproductstockEntity.getProductCode());
		maptoViewProductStockDto.setProductName(viewproductstockEntity.getProductName());
		maptoViewProductStockDto.setSupplierCode(viewproductstockEntity.getSupplierCode());
		maptoViewProductStockDto.setLocationCode(viewproductstockEntity.getLocationCode());
		maptoViewProductStockDto.setClassificationCode(viewproductstockEntity.getClassificationCode());
		maptoViewProductStockDto.setPrice(viewproductstockEntity.getPrice());
		maptoViewProductStockDto.setTotalQty(viewproductstockEntity.getTotalQty());
		maptoViewProductStockDto.setAvailableQty(viewproductstockEntity.getAvailableQty());
		maptoViewProductStockDto.setUnavailableQty(viewproductstockEntity.getUnavailableQty());
		maptoViewProductStockDto.setSafetyQty(viewproductstockEntity.getSafetyQty());
		maptoViewProductStockDto.setUseFlag(viewproductstockEntity.getUseFlag().name());
		maptoViewProductStockDto.setManager(viewproductstockEntity.getManager());
		maptoViewProductStockDto.setMemo(viewproductstockEntity.getMemo());
		return maptoViewProductStockDto;
	}
	
	@Override
	public List<ViewProductStockDto> getAllOrdersByProductCode() {
		List<ViewProductStockEntity> lisViewproductstockEntity = viewproductstockRepository.findAllByOrderByProductCodeDesc();
		List<ViewProductStockDto> lisViewproductstockDto = new ArrayList<>();
		
		for (ViewProductStockEntity viewproductstockEntity : lisViewproductstockEntity) {
			ViewProductStockDto viewProductStockDto = converToViewProductStockDto(viewproductstockEntity);
			lisViewproductstockDto.add(viewProductStockDto);
		}
		
		return lisViewproductstockDto;
	}
	
	@Override
	public Page<ViewProductStockEntity> getPaginatedData(int pno, int size) {
		Pageable pageable = PageRequest.of(pno-1, size);
		return viewproductstockRepository.findAllByOrderByProductCodeDesc(pageable);
	}
	
	@Override
	public Page<ViewProductStockEntity> getPaginatedData(int pno, int size, String code, String word) {
		Page<ViewProductStockEntity> result = null;
		Pageable pageable = PageRequest.of(pno-1, size);
		if(code.equals("1")) {
			result = viewproductstockRepository.findByProductCodeContainingOrderByProductCodeDesc(word, pageable);
		}else if(code.equals("2")) {
			result = viewproductstockRepository.findByProductNameContainingOrderByProductCodeDesc(word, pageable);
		}else if(code.equals("3")) {
			result = viewproductstockRepository.findBySupplierCodeContainingOrderByProductCodeDesc(word, pageable);
		}else if(code.equals("4")) {
			result = viewproductstockRepository.findByLocationCodeContainingOrderByProductCodeDesc(word, pageable);
		}else if(code.equals("5")) {
				result = viewproductstockRepository.findByClassificationCodeOrderByProductCodeDesc(word, pageable);
		}
		return result;
	}
}
