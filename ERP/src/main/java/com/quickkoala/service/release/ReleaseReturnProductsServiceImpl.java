package com.quickkoala.service.release;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.quickkoala.entity.release.ReleaseReturnProductsEntity;
import com.quickkoala.entity.release.ReleaseReturnProductsEntity.ReleaseRefundStatus;
import com.quickkoala.repository.release.ReleaseReturnProductsRepository;

@Service
public class ReleaseReturnProductsServiceImpl implements ReleaseReturnProductsService{
	
	@Autowired
	private ReleaseReturnProductsRepository releaseReturnProductsRepository;

	@Override
	public String saveStatus(String relNum, String lotNum, int qty,String status) {
		if (relNum == null || lotNum == null || status == null) {
	        return "NO";
	    }
		Optional<ReleaseReturnProductsEntity> optional = releaseReturnProductsRepository.findByRelNumberAndLotNumber(relNum,lotNum);
		if(optional.isPresent()) {
			ReleaseReturnProductsEntity entity = optional.get();
			 try {
		            entity.setStatus(ReleaseRefundStatus.valueOf(status));
		            releaseReturnProductsRepository.save(entity);
		            return "OK";
		        } catch (IllegalArgumentException e) {
		            return "NO";
		        }
		}
		return "NO";
	}
	
}
