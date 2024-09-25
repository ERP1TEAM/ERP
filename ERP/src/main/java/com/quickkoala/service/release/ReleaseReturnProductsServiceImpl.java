package com.quickkoala.service.release;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.quickkoala.entity.release.ReleaseReturnProductsEntity;
import com.quickkoala.entity.release.ReleaseReturnProductsEntity.ReleaseReturnReason;
import com.quickkoala.entity.release.ReleaseReturnProductsEntity.ReleaseReturnStatus;
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
		            entity.setStatus(ReleaseReturnStatus.valueOf(status));
		            releaseReturnProductsRepository.save(entity);
		            return "OK";
		        } catch (IllegalArgumentException e) {
		            return "NO";
		        }
		}
		return "NO";
	}

	@Override
	public String saveProduct(String rCode,String lCode,int qty, String reason,String manager) {
		ReleaseReturnProductsEntity entity = new ReleaseReturnProductsEntity();
		entity.setLotNumber(lCode);
		entity.setNumber(0);
		entity.setReason(ReleaseReturnReason.valueOf(reason));
		entity.setQty(qty);
		entity.setStatus(ReleaseReturnStatus.대기);
		entity.setRelNumber(rCode);
		entity.setManager(manager);
		ReleaseReturnProductsEntity temp = releaseReturnProductsRepository.save(entity);
		if(temp==null) {
			return "NO";
		}else {
			return "OK";
		}
	}
	
}
