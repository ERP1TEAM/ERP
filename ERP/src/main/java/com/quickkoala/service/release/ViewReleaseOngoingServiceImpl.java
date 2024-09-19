package com.quickkoala.service.release;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.quickkoala.dto.release.ReleaseOngoingDto;
import com.quickkoala.entity.order.OrderReleaseEntity;
import com.quickkoala.entity.order.OrderReleaseEntity.ReleaseStatus;
import com.quickkoala.entity.release.ViewReleaseOngoingEntity;
import com.quickkoala.repository.release.ViewReleaseOngoingRepository;

@Service
public class ViewReleaseOngoingServiceImpl implements ViewReleaseOngoingService{
	
	@Autowired
	private ViewReleaseOngoingRepository viewReleaseOngoingRepository;
	
	@Override
	public List<ReleaseOngoingDto> getAll(int pg, int size) {
		ReleaseStatus[] status = {ReleaseStatus.출고준비,ReleaseStatus.출고지연};
		Pageable pageable = (Pageable) PageRequest.of(pg, size, Sort.by(Sort.Order.desc("number")));
		List<ViewReleaseOngoingEntity> before = viewReleaseOngoingRepository.findByStatusIn(status,pageable).getContent();
		List<ReleaseOngoingDto> after = new ArrayList<ReleaseOngoingDto>();
		for(ViewReleaseOngoingEntity item: before) {
			ReleaseOngoingDto dto = new ReleaseOngoingDto();
			dto.setNumber(item.getNumber());
			dto.setOrderNumber(item.getOrderNumber());
			dto.setOrderId(item.getOrderId());
			dto.setSalesCode(item.getSalesCode());
			dto.setSalesName(item.getSalesName());
			dto.setDt(item.getDt().toString());
			dto.setStatus(item.getStatus().toString());
			dto.setManager(item.getManager());
			dto.setMemo(item.getMemo());
			after.add(dto);
		}
		return after;
	}
	
	
}

