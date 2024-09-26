package com.quickkoala.service.release;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.quickkoala.entity.release.ReleaseProductsEntity;
import com.quickkoala.entity.release.ReleaseReturnProductsEntity;
import com.quickkoala.entity.release.ReleaseReturnProductsEntity.ReleaseReturnReason;
import com.quickkoala.entity.release.ReleaseReturnProductsEntity.ReleaseReturnStatus;
import com.quickkoala.repository.release.ReleaseProductsRepository;
import com.quickkoala.repository.release.ReleaseReturnProductsRepository;

@Service
public class ReleaseReturnProductsServiceImpl implements ReleaseReturnProductsService{

	@Autowired
	private ReleaseReturnProductsRepository releaseReturnProductsRepository;
	
	@Autowired
	private ReleaseProductsRepository releaseProductsRepository;

	@Override
	public String saveStatus(String relNum, String lotNum, int qty,String status) {
		if (relNum == null || lotNum == null || status == null) {
	        return "NO";
	    }
		List<ReleaseReturnProductsEntity> optional = releaseReturnProductsRepository.findByRelNumberAndLotNumber(relNum,lotNum);
		if(optional.size()==0) {
			return "NO";
		}else {
			for(ReleaseReturnProductsEntity item : optional) {
				if(item.getStatus()==ReleaseReturnStatus.대기){
					
					if(item.getQty()<qty) {
						return "NO";
					}
					else if(item.getQty()==qty){
						item.setStatus(ReleaseReturnStatus.valueOf(status));
						releaseReturnProductsRepository.save(item);
						return "OK";
						
					}else {
						int remain = item.getQty()-qty;
						item.setQty(remain);
						ReleaseReturnProductsEntity added = new ReleaseReturnProductsEntity();
						added.setDt(item.getDt());;
						added.setLotNumber(item.getLotNumber());
						added.setManager(item.getManager());
						added.setReason(item.getReason());
						added.setRelNumber(item.getRelNumber());
						added.setQty(qty);
						added.setStatus(ReleaseReturnStatus.valueOf(status));
						releaseReturnProductsRepository.save(item);
						releaseReturnProductsRepository.save(added);
						
						
					}
					return "OK";
				}
				
			}
			
		}
		return "NO";
	}

	@Override
	public String saveProduct(String rCode,String lCode,int qty, String reason,String manager) {
		
		List<ReleaseProductsEntity> optional = releaseProductsRepository.findByRelNumberAndLotNumber(rCode,lCode);
		
		if(optional.size()==0) {
			return "NO";
		}else {
			for(ReleaseProductsEntity item : optional) {
				if(item.getReturnFlag()=="N"){
					if(item.getQty()<qty) {
						return "NO";
					}
						else if(item.getQty()==qty){
						item.setReturnFlag("Y");
						releaseProductsRepository.save(item);
						
						ReleaseReturnProductsEntity entity = new ReleaseReturnProductsEntity();
						entity.setLotNumber(lCode);
						try {
						entity.setReason(ReleaseReturnReason.valueOf(reason));
						}catch(Exception e) {
							entity.setReason(null);
						}
						entity.setQty(qty);
						entity.setStatus(ReleaseReturnStatus.대기);
						entity.setRelNumber(rCode);
						entity.setManager(manager);
						@SuppressWarnings("unused")
						ReleaseReturnProductsEntity temp = releaseReturnProductsRepository.save(entity);
						return "OK";
					}else {
						int remain = item.getQty()-qty;
						item.setQty(remain);
						ReleaseProductsEntity rp = new ReleaseProductsEntity();
						rp.setDt(item.getDt());
						rp.setLotNumber(item.getLotNumber());
						rp.setManager(item.getManager());
						rp.setProductCode(item.getProductCode());
						rp.setQty(qty);
						rp.setRelNumber(item.getRelNumber());
						rp.setReturnFlag("Y");
						rp.setSupplierCode(item.getSupplierCode());
						releaseProductsRepository.save(rp);
						
						ReleaseReturnProductsEntity entity = new ReleaseReturnProductsEntity();
						entity.setLotNumber(lCode);
						try {
						entity.setReason(ReleaseReturnReason.valueOf(reason));
						}catch(Exception e) {
							entity.setReason(null);
						}
						entity.setQty(qty);
						entity.setStatus(ReleaseReturnStatus.대기);
						entity.setRelNumber(rCode);
						entity.setManager(manager);
						@SuppressWarnings("unused")
						ReleaseReturnProductsEntity temp = releaseReturnProductsRepository.save(entity);
						return "OK";
						
					}
				
				
			}
		}
		
	
	
}
		return "NO";
	}
}
