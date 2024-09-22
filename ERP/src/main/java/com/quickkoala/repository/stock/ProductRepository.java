package com.quickkoala.repository.stock;

import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import com.quickkoala.entity.stock.ProductEntity;

@Repository
public interface ProductRepository extends JpaRepository<ProductEntity,String>{
	
	public Optional<ProductEntity> findByCode(String code);
	
	@Query("SELECT p.price FROM ProductEntity p WHERE p.code IN :codes")
	List<Integer> findPricesByCodes(@Param("codes") List<String> codes);

}