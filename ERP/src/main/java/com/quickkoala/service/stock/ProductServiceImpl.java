package com.quickkoala.service.stock;

import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.quickkoala.dto.stock.ProductDto;
import com.quickkoala.entity.stock.LocationEntity;
import com.quickkoala.entity.stock.ProductEntity;
import com.quickkoala.entity.stock.ProductEntity.UseFlag;
import com.quickkoala.repository.stock.ProductRepository;

import jakarta.transaction.Transactional;

@Service
public class ProductServiceImpl implements ProductService{

	@Autowired
	private ProductRepository productRepository;
	
	
	//Entity -> DTO 변환
	@Override
	public ProductDto convertToProductDto(ProductEntity productEntity) {
		ProductDto maptoProductDto = new ProductDto();
		maptoProductDto.setCode(productEntity.getCode());
		maptoProductDto.setName(productEntity.getName());
		maptoProductDto.setPrice(productEntity.getPrice());
		maptoProductDto.setClassificationCode(productEntity.getClassificationCode());
		
		if (productEntity.getStorageLocation() != null) {
	        maptoProductDto.setStorageLocation(productEntity.getStorageLocation());
	    }
		
		maptoProductDto.setSupplierCode(productEntity.getSupplierCode());
		maptoProductDto.setMemo(productEntity.getMemo());
		maptoProductDto.setUseFlag(productEntity.getUseFlag().name());
		return maptoProductDto;
	}
	//DTO -> Entity 변환
	private ProductEntity convertToEntity(ProductDto productDto) {
        ProductEntity maptoProductEntity = new ProductEntity();
        maptoProductEntity.setCode(productDto.getCode());
        maptoProductEntity.setName(productDto.getName());
        maptoProductEntity.setPrice(productDto.getPrice());
        maptoProductEntity.setClassificationCode(productDto.getClassificationCode());
        
        if (productDto.getStorageLocation() != null) {
            maptoProductEntity.setStorageLocation(productDto.getStorageLocation());
        }
        
        maptoProductEntity.setSupplierCode(productDto.getSupplierCode());
        maptoProductEntity.setMemo(productDto.getMemo());
        
        String useFlagValue = productDto.getUseFlag();
		if (useFlagValue != null) {
		try {
		UseFlag useFlag = UseFlag.valueOf(productDto.getUseFlag().toUpperCase());
		maptoProductEntity.setUseFlag(useFlag);
		}catch(IllegalArgumentException e) {
			throw new IllegalArgumentException("잘못된 useFlag값 입니다.");
		}}else {
			throw new IllegalArgumentException("useFlag 값이 null입니다.");
		}
		
        return maptoProductEntity;
	}
	@Override
	public ProductEntity saveProduct(ProductDto productDto, String manager) {
		ProductEntity productEntity =convertToEntity(productDto);
		
		if(productRepository.existsByCode(productEntity .getCode())) {
			throw new IllegalArgumentException("이미 존재하는 상품 코드입니다");
		}
		if (productEntity.getCreatedDt() == null) {
            productEntity.setCreatedDt(LocalDateTime.now());
            productEntity.setCreatedManager(manager);
            productEntity.setUpdatedDt(LocalDateTime.now());
            productEntity.setManager(manager);
		}
		return productRepository.save(productEntity);
	}
	
	//상품코드 중복체크
	@Override
	public String checkduplicateCode() {
		String code;
		do {
			code = randomcode();
		}while(productRepository.existsByCode(code));
		return code;
	}
	
	//상품코드 난수번호
	private String randomcode() {
		String saverandomcode="P" + String.format("%07d",(int)(Math.random()*10000000));
		return saverandomcode;
	}
	
	@Transactional
	@Override
	public int modifyLocation(String productCode, String locationCode) {
		return productRepository.updateLocationCode(productCode, locationCode);
	}
}