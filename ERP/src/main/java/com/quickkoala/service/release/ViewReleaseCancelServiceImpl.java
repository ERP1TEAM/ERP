package com.quickkoala.service.release;
import java.time.LocalDate;
import java.time.LocalDateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import com.quickkoala.entity.release.ViewReleaseCancelEntity;
import com.quickkoala.repository.release.ViewReleaseCancelRepository;

@Service
public class ViewReleaseCancelServiceImpl implements ViewReleaseCancelService{
	
	@Autowired
	private ViewReleaseCancelRepository viewReleaseCancelRepository;
	
	public Page<ViewReleaseCancelEntity> getAll(int pg, int size,String select, String param, String startDate, String endDate) {
		try {
			
			if(startDate.equals("null")||endDate.equals("null")||endDate==""||startDate=="") {
		Pageable pageable = (Pageable) PageRequest.of(pg, size, Sort.by(Sort.Order.desc("relNumber")));
		if(select.equals(null)||select.equals("null")||select=="") {
			return viewReleaseCancelRepository.findAll(pageable);
		}else if(select.equals("1")&&param!=null) {
			return viewReleaseCancelRepository.findByRelNumberContaining(param, pageable);
		}else if(select.equals("2")&&param!=null) {
			return viewReleaseCancelRepository.findByOrderNumberContaining(param, pageable);
		}else if(select.equals("4")&&param!=null) {
			return viewReleaseCancelRepository.findBySalesNameContaining(param, pageable);
		}else {
			return Page.empty();
		}
			}else {
				LocalDateTime sd = LocalDate.parse(startDate).atStartOfDay();
				LocalDateTime ed = LocalDate.parse(endDate).atTime(23, 59, 59) ;
				Pageable pageable = (Pageable) PageRequest.of(pg, size, Sort.by(Sort.Order.desc("relNumber")));
				if(select.equals(null)||select.equals("null")||select=="") {
					return viewReleaseCancelRepository.findAllByDtBetween(sd,ed,pageable);
				}else if(select.equals("1")&&param!=null) {
					return viewReleaseCancelRepository.findByRelNumberContainingAndDtBetween(param,sd,ed, pageable);
				}else if(select.equals("2")&&param!=null) {
					return viewReleaseCancelRepository.findByOrderNumberContainingAndDtBetween(param,sd,ed, pageable);
				}else if(select.equals("4")&&param!=null) {
					return viewReleaseCancelRepository.findBySalesNameContainingAndDtBetween(param,sd,ed, pageable);
				}else {
					return Page.empty();
				}
				
			}
			
		}catch(Exception e) {
			return Page.empty();
		}
	}
	
}