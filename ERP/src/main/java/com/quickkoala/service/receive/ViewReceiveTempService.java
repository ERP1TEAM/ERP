package com.quickkoala.service.receive;

import org.springframework.data.domain.Page;

import com.quickkoala.dto.receive.SearchDto;
import com.quickkoala.entity.receive.ViewReceiveTempEntity;

public interface ViewReceiveTempService {
	Page<ViewReceiveTempEntity> getPaginatedData(int pno, int size);
	Page<ViewReceiveTempEntity> getPaginatedData(int pno, int size, String code, String word);
	Page<ViewReceiveTempEntity> getPaginatedData(int pno, int size, SearchDto dto);
}
