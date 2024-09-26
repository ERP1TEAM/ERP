package com.quickkoala.service.release;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.quickkoala.entity.release.OrderReleaseEntity.ReleaseStatus;
import com.quickkoala.entity.release.ViewReleaseOngoingEntity;
import com.quickkoala.entity.release.ViewReleaseProductsEntity;
import com.quickkoala.repository.release.ViewReleaseOngoingRepository;
import com.quickkoala.repository.release.ViewReleaseProductsRepository;

@Service
public class ViewReleaseOngoingServiceImpl implements ViewReleaseOngoingService{
	
	@Autowired
	private ViewReleaseOngoingRepository viewReleaseOngoingRepository;
	
	@Autowired
	private ViewReleaseProductsRepository viewReleaseProductsRepository;
	
	@Override
	public Page<ViewReleaseOngoingEntity> getAll(int pg, int size,String select, String param) {
		Pageable pageable = (Pageable) PageRequest.of(pg, size, Sort.by(Sort.Order.desc("number")));
		System.out.println(pg+"||"+size+"||"+select+"||"+param);
		if(select.equals(null)||select.equals("null")) {
			return viewReleaseOngoingRepository.findAll(pageable);
		}else if(select.equals("1")&&param!=null) {
			return viewReleaseOngoingRepository.findByNumberContainingOrderByNumberDesc(param,pageable);
		}else if(select.equals("2")&&param!=null) {
			return viewReleaseOngoingRepository.findByOrderNumberContainingOrderByNumberDesc(param,pageable);
		}else if(select.equals("3")&&param!=null) {
			return viewReleaseOngoingRepository.findBySalesNameContainingOrderByNumberDesc(param,pageable);
		}else if(select.equals("4")&&param!=null) {
			try {
				ReleaseStatus status = ReleaseStatus.valueOf(param.intern());
				Page<ViewReleaseOngoingEntity> temp = viewReleaseOngoingRepository.findByStatus(status,pageable);
		        return temp;
		    } catch (IllegalArgumentException e) {
		        return Page.empty(); 
		    }
		}else {
			return Page.empty();
		}
	}
	
	public List<ViewReleaseProductsEntity> getProducts(String relNumber){
		return viewReleaseProductsRepository.findByRelNumberOrderByLotNumberDesc(relNumber);
	}
	
	
}

