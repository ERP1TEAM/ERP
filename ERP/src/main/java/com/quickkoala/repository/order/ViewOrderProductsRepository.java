package com.quickkoala.repository.order;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.quickkoala.entity.order.ViewOrderProductsEntity;


@Repository
public interface ViewOrderProductsRepository extends JpaRepository<ViewOrderProductsEntity,Integer> {
	
	List<ViewOrderProductsEntity> findByOrderNumberOrderByItemIdDesc(String onum);


	
}
