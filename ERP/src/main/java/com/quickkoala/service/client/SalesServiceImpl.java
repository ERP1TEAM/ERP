package com.quickkoala.service.client;

import java.time.LocalDateTime;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.quickkoala.entity.client.SalesEntity;
import com.quickkoala.repository.client.SalesRepository;

@Service
public class SalesServiceImpl implements SalesService {

	@Autowired
	private SalesRepository salesRepository;

	@Override
	public Page<SalesEntity> getPaginatedData(int pno, int size) {
		Pageable pageable = PageRequest.of(pno - 1, size);
		return salesRepository.findAllByOrderByCodeDesc(pageable);
	}

	@Override
	public Page<SalesEntity> getPaginatedData(int pno, int size, String code, String word) {
		Page<SalesEntity> result = null;
		Pageable pageable = PageRequest.of(pno - 1, size);
		if (code.equals("거래처코드")) {
			result = salesRepository.findByCodeContainingOrderByCodeDesc(word, pageable);
		} else if (code.equals("거래처명")) {
			result = salesRepository.findByNameContainingOrderByCodeDesc(word, pageable);
		}
		return result;
	}

	public Optional<SalesEntity> findByCode(String code) {
		return salesRepository.findByCode(code);
	}
	
	@Override
	public SalesEntity addSales(SalesEntity salesEntity) {
		String currentMaxCode = salesRepository.findMaxCode();
        String newCode = generateNewCode(currentMaxCode);
        salesEntity.setCode(newCode);
        salesEntity.setCreateDate(LocalDateTime.now());
        return salesRepository.save(salesEntity);
	}
	
	private String generateNewCode(String currentMaxCode) {
        if (currentMaxCode == null) {
            // 처음 저장하는 경우 기본값 설정
            return "SL0001";
        }
        
        // 기존 코드에서 숫자 부분 추출
        String numericPart = currentMaxCode.substring(2); // "SL"을 제외한 숫자 부분
        int newNumericValue = Integer.parseInt(numericPart) + 1; // 1 증가
        
        // 새 코드 생성
        return String.format("SL%04d", newNumericValue); // SL0002 형식으로 반환
    }
	
	@Override
	public SalesEntity getOne(String code) {
		return salesRepository.findBycode(code);
	}
	
	@Override
	public SalesEntity modifySales(SalesEntity salesEntity, String code) {
		salesEntity.setCode(code);
		salesEntity.setCreateDate(salesRepository.findBycode(code).getCreateDate());
		return salesRepository.save(salesEntity);
	}
}
