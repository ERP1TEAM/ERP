package com.quickkoala.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.quickkoala.entity.ViewReceiveEntity;
import com.quickkoala.repository.ViewReceiveRepository;

@Service
public class ViewReceiveServiceImpl implements ViewReceiveService {

	@Autowired
	private ViewReceiveRepository viewReceiveRepository;
	
	@Override
	public List<ViewReceiveEntity> getData() {
		return viewReceiveRepository.findAllByOrderByReceiveCodeDesc();
	}
	@Override
	public long getCount() {
		return viewReceiveRepository.count();
	}
	@Override
	public Page<ViewReceiveEntity> getPaginatedData(int pno, int size) {
		Pageable pageable = PageRequest.of(pno - 1, size); // 페이지 번호는 0부터 시작하므로 pno - 1
        return viewReceiveRepository.findAll(pageable);
	}
	
}
