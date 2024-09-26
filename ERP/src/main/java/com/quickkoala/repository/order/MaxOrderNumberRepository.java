package com.quickkoala.repository.order;
import java.time.LocalDate;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.quickkoala.entity.order.MaxOrderNumberEntity;

@Repository
public interface MaxOrderNumberRepository extends JpaRepository<MaxOrderNumberEntity,LocalDate> {
	MaxOrderNumberEntity findByDt(LocalDate date);
}
