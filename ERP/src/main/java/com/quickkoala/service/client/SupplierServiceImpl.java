package com.quickkoala.service.client;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.quickkoala.entity.client.SupplierEntity;
import com.quickkoala.repository.client.SupplierRepository;

@Service
public class SupplierServiceImpl implements SupplierService {

	@Autowired
	private SupplierRepository supplierRepository;

	@Override
	public List<SupplierEntity> getAllData() {
		return supplierRepository.findAllByOrderByCreatedDateDesc();
	}

	@Override
	public List<SupplierEntity> searchByName(String term) {
		return supplierRepository.findByNameContainingIgnoreCase(term);
	}

	@Override
	public SupplierEntity getCode(String name) {
		System.out.println(name);
		return supplierRepository.findByName(name);
	}

	@Override
	public Page<SupplierEntity> getPaginatedData(int pno, int size) {
		Pageable pageable = PageRequest.of(pno - 1, size);
		return supplierRepository.findAllByOrderByCodeDesc(pageable);
	}

	@Override
	public Page<SupplierEntity> getPaginatedData(int pno, int size, String code, String word) {
		Page<SupplierEntity> result = null;
		Pageable pageable = PageRequest.of(pno - 1, size);
		if (code.equals("발주처코드")) {
			result = supplierRepository.findByCodeContainingOrderByCodeDesc(word, pageable);
		} else if (code.equals("발주처명")) {
			result = supplierRepository.findByNameContainingOrderByCodeDesc(word, pageable);
		}
		return result;
	}

	// 관리자 등록 - 코드로 관리자 소속사 확인
	@Override
	public Optional<SupplierEntity> findByCode(String code) {
		return supplierRepository.findByCode(code);
	}
}
