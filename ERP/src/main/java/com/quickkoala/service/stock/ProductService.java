package com.quickkoala.service.stock;

import com.quickkoala.dto.stock.ProductDto;
import com.quickkoala.entity.stock.ProductEntity;

public interface ProductService {
	String checkduplicateCode();
	
	ProductEntity saveProduct (ProductDto productDto,String manager);
	ProductDto convertToProductDto(ProductEntity productEntity);
}
