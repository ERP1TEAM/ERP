package com.quickkoala.service.stock;

import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.quickkoala.dto.stock.ProductDto;
import com.quickkoala.entity.stock.ProductEntity;
import com.quickkoala.entity.stock.ProductEntity.UseFlag;
import com.quickkoala.entity.stock.StockEntity;
import com.quickkoala.repository.stock.ProductRepository;
import com.quickkoala.repository.stock.StockRepository;

import jakarta.transaction.Transactional;

@Service
public class ProductServiceImpl implements ProductService{

	@Autowired
	private ProductRepository productRepository;
	@Autowired
	private StockRepository stockRepository;
	
	
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
	    }else {
            maptoProductDto.setStorageLocation(null);
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
        } else {
            maptoProductEntity.setStorageLocation(null);
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
	
	@Transactional
	@Override
	public ProductEntity saveProduct(ProductDto productDto, String manager) {
	    
	    try {
	    	ProductEntity productEntity = convertToEntity(productDto);
	    if (productRepository.existsByCode(productEntity.getCode())) {
	        throw new IllegalArgumentException("이미 존재하는 상품 코드입니다");
	    }
	    
	    LocalDateTime now = LocalDateTime.now();
	    
	    if (productEntity.getCreatedDt() == null) {
	        productEntity.setCreatedDt(now);
	        productEntity.setCreatedManager(manager);
	        productEntity.setUpdatedDt(now);
	        productEntity.setManager(manager);
	    }
	    
	    
	    ProductEntity savedProduct = productRepository.save(productEntity);
	    
	    StockEntity stock = new StockEntity();
        stock.setProductCode(savedProduct.getCode());
        stock.setTotalQty(0);
        stock.setAvailableQty(0);
        stock.setUnavailableQty(0);
        stock.setSafetyQty(0);
        stock.setManager(manager);
        stockRepository.save(stock);
        
        return savedProduct;
	    }catch(Exception e) {
	    	System.out.println("상품 저장 중 예외 발생"+e.getMessage());
	    	throw e;
	    }
        
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
	
	@Transactional
	@Override
	public void updateProductInfo(String productCode, ProductDto productDto) {
		
		if (productDto.getName() == null) {
	        throw new IllegalArgumentException("상품 이름이 null입니다.");
	    }
		
		String productName = productDto.getName();
	    String classificationCode = productDto.getClassificationCode();
	    int price = productDto.getPrice();
	    String useFlag = productDto.getUseFlag();

	    UseFlag useFlagEnum;
	    try {
	        useFlagEnum = UseFlag.valueOf(useFlag.toUpperCase());
	    } catch (IllegalArgumentException e) {
	        throw new IllegalArgumentException("잘못된 useFlag 값입니다.");
	    }
	    
	    int updatedCount = productRepository.updateProductInfo(
	            productCode,
	            productName,
	            classificationCode,
	            price,
	            useFlagEnum
	    );
	        if (updatedCount == 0) {
	            throw new RuntimeException("상품 업데이트에 실패했습니다.");
	        }
	    }
	
	@Transactional
	@Override
	public boolean deleteProductWithStockCheck(String productCode) {
		if (!productRepository.existsByCode(productCode)) {
            return false;
        }
        StockEntity stockEntity = stockRepository.findByProductCode(productCode);
        if (stockEntity != null && stockEntity.getTotalQty() > 0) {
            return false;
        }
        productRepository.deleteById(productCode);
        return true;
    }
	
}