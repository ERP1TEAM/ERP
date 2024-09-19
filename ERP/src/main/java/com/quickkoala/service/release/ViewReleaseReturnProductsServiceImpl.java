package com.quickkoala.service.release;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.quickkoala.dto.release.ReleaseOngoingDto;
import com.quickkoala.dto.release.ReleaseReturnDto;
import com.quickkoala.entity.order.OrderReleaseEntity.ReleaseStatus;
import com.quickkoala.entity.release.ViewReleaseCompleteEntity;
import com.quickkoala.entity.release.ViewReleaseOngoingEntity;
import com.quickkoala.entity.release.ViewReleaseReturnProductsEntity;
import com.quickkoala.entity.release.ReleaseReturnProductsEntity.ReleaseRefundStatus;
import com.quickkoala.repository.release.ViewReleaseReturnProductsRepository;

@Service
public class ViewReleaseReturnProductsServiceImpl implements ViewReleaseReturnProductsService{
	
	@Autowired
	private ViewReleaseReturnProductsRepository viewReleaseReturnProductsRepository;
	
	@Override
	public List<ReleaseReturnDto> getAll(int pg,int size) {
		ReleaseRefundStatus status = ReleaseRefundStatus.대기;
		
		Pageable pageable = (Pageable) PageRequest.of(pg, size, Sort.by(Sort.Order.desc("relNumber")));
		List<ViewReleaseReturnProductsEntity> before = viewReleaseReturnProductsRepository.findAllByStatus(status,pageable).getContent();;
		List<ReleaseReturnDto> after = new ArrayList<ReleaseReturnDto>();
		for(ViewReleaseReturnProductsEntity item: before) {
			ReleaseReturnDto dto = new ReleaseReturnDto();
			dto.setIdx(item.getIdx());
			dto.setRelNumber(item.getRelNumber());
			dto.setLotNumber(item.getLotNumber());
			dto.setDt(item.getDt().toString());
			dto.setStatus(item.getStatus().toString());
			dto.setReason(item.getReason().toString());
			dto.setManager(item.getManager());
			dto.setQty(item.getQty());
			dto.setProductCode(item.getProductCode());
			dto.setProductName(item.getProductName());
			dto.setSupplierCode(item.getSupplierCode());
			dto.setSupplierName(item.getSupplierName());
			after.add(dto);
		}
		return after;
	}
}