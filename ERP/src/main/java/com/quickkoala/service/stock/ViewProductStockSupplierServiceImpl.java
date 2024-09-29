package com.quickkoala.service.stock;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.quickkoala.dto.stock.ViewProductStockSupplierDto;
import com.quickkoala.entity.stock.ViewProductStockSupplierEntity;
import com.quickkoala.repository.stock.ViewProductStockSupplierRepository;

@Service
public class ViewProductStockSupplierServiceImpl implements ViewProductStockSupplierService{

	@Autowired
	private ViewProductStockSupplierRepository viewproductstocksupplierRepository;
	
	//Entity -> DTO 변환
	@Override
	public ViewProductStockSupplierDto converToViewProductStockDto(ViewProductStockSupplierEntity viewproductstockEntity) {
		ViewProductStockSupplierDto maptoViewProductStockDto = new ViewProductStockSupplierDto();
		maptoViewProductStockDto.setProductCode(viewproductstockEntity.getProductCode());
		maptoViewProductStockDto.setProductName(viewproductstockEntity.getProductName());
		maptoViewProductStockDto.setSupplierCode(viewproductstockEntity.getSupplierCode());
		maptoViewProductStockDto.setSupplierName(viewproductstockEntity.getSupplierName());
		maptoViewProductStockDto.setPrice(viewproductstockEntity.getPrice());
		maptoViewProductStockDto.setTotalQty(viewproductstockEntity.getTotalQty());
		maptoViewProductStockDto.setAvailableQty(viewproductstockEntity.getAvailableQty());
		maptoViewProductStockDto.setUnavailableQty(viewproductstockEntity.getUnavailableQty());
		maptoViewProductStockDto.setSafetyQty(viewproductstockEntity.getSafetyQty());
		maptoViewProductStockDto.setUseFlag(viewproductstockEntity.getUseFlag().name());
		return maptoViewProductStockDto;
	}
	
	
	@Override
	public Page<ViewProductStockSupplierEntity> getSortedByTotalQtyMinusSafetyQty(int pno, int size, String code,
			String word) {
		 Pageable pageable = PageRequest.of(pno - 1, size);
		 Page<ViewProductStockSupplierEntity> pageData;

		 if(code.equals("1")) {
	            pageData = viewproductstocksupplierRepository.findAll(pageable);
	        } else if(code.equals("2")) {
	            pageData = viewproductstocksupplierRepository.findByProductNameContainingOrderByProductCodeDesc(word, pageable);
	        } else if(code.equals("3")) {
	            pageData = viewproductstocksupplierRepository.findBySupplierCodeContainingOrderByProductCodeDesc(word, pageable);
	        } else if(code.equals("4")) {
	            pageData = viewproductstocksupplierRepository.findBySupplierNameContainingOrderByProductCodeDesc(word, pageable);
	        } else {
	            pageData = viewproductstocksupplierRepository.findAll(pageable);
	        }

	        // 가져온 데이터 리스트를 총재고 - 안전재고 값으로 정렬
	        List<ViewProductStockSupplierEntity> sortedEntities = pageData.getContent().stream()
	            .sorted(Comparator.comparingInt(e -> e.getTotalQty() - e.getSafetyQty()))
	            .collect(Collectors.toList());

	        // 정렬된 데이터를 다시 PageImpl로 변환해서 반환
	        return new PageImpl<>(sortedEntities, pageable, pageData.getTotalElements());
	    }
	
	@Override
	public List<ViewProductStockSupplierDto> getAllOrdersByProductCode() {
		List<ViewProductStockSupplierEntity> lisViewproductstockEntity = viewproductstocksupplierRepository.findAllByOrderByProductCodeDesc();
		List<ViewProductStockSupplierDto> lisViewproductstockDto = new ArrayList<>();
		
		for (ViewProductStockSupplierEntity viewproductstockEntity : lisViewproductstockEntity) {
			ViewProductStockSupplierDto viewProductStockDto = converToViewProductStockDto(viewproductstockEntity);
			lisViewproductstockDto.add(viewProductStockDto);
		}
		
		return lisViewproductstockDto;
	}
	
	@Override
	public Page<ViewProductStockSupplierEntity> getPaginatedData(int pno, int size) {
		Pageable pageable = PageRequest.of(pno-1, size);
		return viewproductstocksupplierRepository.findAllByOrderByProductCodeDesc(pageable);
	}
	
	@Override
	public Page<ViewProductStockSupplierEntity> getPaginatedData(int pno, int size, String code, String word) {
		Page<ViewProductStockSupplierEntity> result = null;
		Pageable pageable = PageRequest.of(pno-1, size);
		if(code.equals("1")) {
			result = viewproductstocksupplierRepository.findByProductCodeContainingOrderByProductCodeDesc(word, pageable);
		}else if(code.equals("2")) {
			result = viewproductstocksupplierRepository.findByProductNameContainingOrderByProductCodeDesc(word, pageable);
		}else if(code.equals("3")) {
			result = viewproductstocksupplierRepository.findBySupplierCodeContainingOrderByProductCodeDesc(word, pageable);
		}else if(code.equals("4")) {
			result = viewproductstocksupplierRepository.findBySupplierNameContainingOrderByProductCodeDesc(word, pageable);
		}
		return result;
	}
	
	@Override
	public Page<ViewProductStockSupplierEntity> getinventoryPaginatedData(int pno, int size, String code, String word) {
		Page<ViewProductStockSupplierEntity> result = null;
		Pageable pageable = PageRequest.of(pno-1, size);
		if(code.equals("1")) {
			result = viewproductstocksupplierRepository.findByProductCodeContainingOrderByProductCodeDesc(word, pageable);
		}else if(code.equals("2")) {
			result = viewproductstocksupplierRepository.findByProductNameContainingOrderByProductCodeDesc(word, pageable);
		}else if(code.equals("3")) {
			result = viewproductstocksupplierRepository.findBySupplierCodeContainingOrderByProductCodeDesc(word, pageable);
		}else if(code.equals("4")) {
			result = viewproductstocksupplierRepository.findBySupplierNameContainingOrderByProductCodeDesc(word, pageable);
		}else if(code.equals("5")) {
			result = viewproductstocksupplierRepository.findByLocationCodeContainingOrderByProductCodeDesc(word, pageable);
		}else if(code.equals("6")) {
			result = viewproductstocksupplierRepository.findByClassificationCodeContainingOrderByProductCodeDesc(word, pageable);
		}
		return result;
	}
	
}