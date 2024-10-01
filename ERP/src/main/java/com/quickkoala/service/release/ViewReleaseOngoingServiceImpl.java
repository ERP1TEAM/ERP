package com.quickkoala.service.release;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.quickkoala.dto.release.ReleaseOngoingDto;
import com.quickkoala.entity.order.OrderEntity.OrderStatus;
import com.quickkoala.entity.release.OrderReleaseEntity;
import com.quickkoala.entity.release.ViewReleaseOngoingEntity;
import com.quickkoala.entity.release.ViewReleaseProductsEntity;
import com.quickkoala.entity.release.ViewReleaseReturnProductsEntity;
import com.quickkoala.entity.release.OrderReleaseEntity.ReleaseStatus;
import com.quickkoala.repository.release.ViewReleaseOngoingRepository;
import com.quickkoala.repository.release.ViewReleaseProductsRepository;

@Service
public class ViewReleaseOngoingServiceImpl implements ViewReleaseOngoingService{
	
	@Autowired
	private ViewReleaseOngoingRepository viewReleaseOngoingRepository;
	
	@Autowired
	private ViewReleaseProductsRepository viewReleaseProductsRepository;
	
	@Override
	public Page<ViewReleaseOngoingEntity> getAll(int pg, int size,String select, String param,String startDate,String endDate) {
		ReleaseStatus[] sts = {ReleaseStatus.출고준비,ReleaseStatus.출고지연};
		System.out.println(pg+"||"+size+"||"+select+"||"+param+"||"+startDate+"||"+endDate);
		try {
			
			if (startDate.equals("null") || endDate.equals("null") || endDate.equals("") || startDate.equals("")) {
			    Pageable pageable = PageRequest.of(pg, size, Sort.by(Sort.Order.desc("number")));
			    if (select.equals(null) || select.equals("null")) {
			        return viewReleaseOngoingRepository.findAllByStatusIn(sts, pageable);
			    } else if (select.equals("1") && param != null) {
			        return viewReleaseOngoingRepository.findByNumberContainingAndStatusIn(param, sts, pageable);
			    } else if (select.equals("2") && param != null) {
			        return viewReleaseOngoingRepository.findByOrderNumberContainingAndStatusIn(param, sts, pageable);
			    } else if (select.equals("3") && param != null) {
			        return viewReleaseOngoingRepository.findBySalesNameContainingAndStatusIn(param, sts, pageable);
			    }else if (select.equals("4") && param != null) {
			        return viewReleaseOngoingRepository.findAllByStatusIn(new ReleaseStatus[]{ReleaseStatus.valueOf(param)}, pageable);
			    } else {
			        return Page.empty();
			    }
			}else {
		LocalDateTime sd = LocalDate.parse(startDate).atStartOfDay();
		LocalDateTime ed = LocalDate.parse(endDate).atTime(23, 59, 59) ;
		Pageable pageable = (Pageable) PageRequest.of(pg, size, Sort.by(Sort.Order.desc("number")));
		if(select.equals(null)||select.equals("null")) {
			return viewReleaseOngoingRepository.findAllByStatusInAndDtBetween(sts,sd,ed,pageable);
		}else if(select.equals("1")&&param!=null) {
			return viewReleaseOngoingRepository.findByStatusInAndNumberContainingAndDtBetween(sts,param,sd,ed,pageable);
		}else if(select.equals("2")&&param!=null) {
			return viewReleaseOngoingRepository.findByStatusInAndOrderNumberContainingAndDtBetween(sts,param,sd,ed,pageable);
		}else if(select.equals("3")&&param!=null) {
			return viewReleaseOngoingRepository.findByStatusInAndSalesNameContainingAndDtBetween(sts,param,sd,ed,pageable);
		}else {
			return Page.empty();
		}
		
	}
		}catch(Exception e) {
			return Page.empty();
		}
		
	}
	
	public List<ViewReleaseProductsEntity> getProducts(String relNumber){
		return viewReleaseProductsRepository.findByRelNumberOrderByLotNumberDesc(relNumber);
	}
	
	
}

