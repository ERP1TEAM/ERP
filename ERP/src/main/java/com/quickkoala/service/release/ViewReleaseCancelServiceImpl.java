package com.quickkoala.service.release;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.quickkoala.dto.release.ReleaseCancelDto;
import com.quickkoala.dto.release.ReleaseOngoingDto;
import com.quickkoala.entity.order.OrderCancelEntity;
import com.quickkoala.entity.order.OrderReleaseEntity;
import com.quickkoala.entity.order.ViewOrderCancelEntity;
import com.quickkoala.entity.order.OrderReleaseEntity.ReleaseStatus;
import com.quickkoala.entity.release.ViewReleaseCancelEntity;
import com.quickkoala.entity.release.ViewReleaseOngoingEntity;
import com.quickkoala.repository.release.ViewReleaseCancelRepository;

@Service
public class ViewReleaseCancelServiceImpl implements ViewReleaseCancelService{
	
	@Autowired
	private ViewReleaseCancelRepository viewReleaseCancelRepository;
	
	public List<ReleaseCancelDto> getAll(int pg, int size){
		Pageable pageable = (Pageable) PageRequest.of(pg, size, Sort.by(Sort.Order.desc("relNumber")));
		List<ViewReleaseCancelEntity> before = viewReleaseCancelRepository.findAll(pageable).getContent();
		System.out.println(before.size());
		List<ReleaseCancelDto> after = new ArrayList<ReleaseCancelDto>();
		for(ViewReleaseCancelEntity item: before) {
			ReleaseCancelDto dto = new ReleaseCancelDto();
			dto.setRelNumber(item.getRelNumber());
			dto.setOrderNumber(item.getOrderNumber());
			dto.setWho(item.getWho().toString());
			dto.setReason(item.getReason().toString());
			dto.setDt(item.getDt().toString());
			dto.setManager(item.getManager());
			dto.setMemo(item.getMemo());
			dto.setSalesCode(item.getSalesCode());
			dto.setSalesName(item.getSalesName());
			after.add(dto);
		}
		return after;
		
	}
	
}