package com.quickkoala.service.release;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.quickkoala.dto.release.ReleaseCancelDto;
import com.quickkoala.dto.release.ReleaseOngoingDto;
import com.quickkoala.dto.release.ReleaseReturnDto;
import com.quickkoala.entity.release.ViewReleaseCancelEntity;
import com.quickkoala.entity.release.ViewReleaseCompleteEntity;
import com.quickkoala.entity.release.ViewReleaseOngoingEntity;
import com.quickkoala.entity.release.ViewReleaseReturnProductsEntity;
import com.quickkoala.entity.release.OrderReleaseEntity.ReleaseStatus;
import com.quickkoala.entity.release.ReleaseReturnProductsEntity.ReleaseRefundStatus;
import com.quickkoala.repository.release.ViewReleaseReturnProductsRepository;

@Service
public class ViewReleaseReturnProductsServiceImpl implements ViewReleaseReturnProductsService{
	
	@Autowired
	private ViewReleaseReturnProductsRepository viewReleaseReturnProductsRepository;
	
	@Override
	public Page<ViewReleaseReturnProductsEntity> getAll(int pg, int size,String select, String param) {
		Pageable pageable = (Pageable) PageRequest.of(pg, size, Sort.by(Sort.Order.desc("relNumber")));
		List<ViewReleaseReturnProductsEntity> before = viewReleaseReturnProductsRepository.findAll(pageable).getContent();
		List<ReleaseReturnDto> after = new ArrayList<ReleaseReturnDto>();
		if(select.equals(null)||select.equals("null")) {
			return viewReleaseReturnProductsRepository.findAll(pageable);
		}else if(select.equals("1")&&param!=null) {
			return viewReleaseReturnProductsRepository.findByRelNumberContainingOrderByRelNumberDesc(param, pageable);
		}else if(select.equals("2")&&param!=null) {
			return viewReleaseReturnProductsRepository.findByLotNumberContainingOrderByRelNumberDesc(param, pageable);
		}else if(select.equals("3")&&param!=null) {
			return viewReleaseReturnProductsRepository.findByProductNameContainingOrderByRelNumberDesc(param, pageable);
		}else if(select.equals("4")&&param!=null) {
			return viewReleaseReturnProductsRepository.findBySupplierNameContainingOrderByRelNumberDesc(param, pageable);
		}else {
			return Page.empty();
		}
	}
}