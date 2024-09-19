package com.quickkoala.service.release;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.quickkoala.dto.release.ReleaseCancelDto;
import com.quickkoala.dto.release.ReleaseCompleteDto;
import com.quickkoala.entity.release.ViewReleaseCancelEntity;
import com.quickkoala.entity.release.ViewReleaseCompleteEntity;
import com.quickkoala.repository.release.ViewReleaseCompleteRepository;

@Service
public class ViewReleaseCompleteServiceImpl implements ViewReleaseCompleteService{
	
	@Autowired
	private ViewReleaseCompleteRepository viewReleaseCompleteRepository;
	
	@Override
	public List<ReleaseCompleteDto> getAll(int pg,int size) {
		Pageable pageable = (Pageable) PageRequest.of(pg, size, Sort.by(Sort.Order.desc("relNumber")));
		List<ViewReleaseCompleteEntity> before = viewReleaseCompleteRepository.findAll(pageable).getContent();
		List<ReleaseCompleteDto> after = new ArrayList<ReleaseCompleteDto>();
		for(ViewReleaseCompleteEntity item: before) {
			ReleaseCompleteDto dto = new ReleaseCompleteDto();
			dto.setRelNumber(item.getRelNumber());
			dto.setOrderNumber(item.getOrderNumber());
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