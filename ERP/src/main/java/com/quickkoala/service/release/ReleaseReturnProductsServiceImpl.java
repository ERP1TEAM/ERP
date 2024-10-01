package com.quickkoala.service.release;
import java.time.LocalDateTime;
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
		System.out.println(status);
		if (relNum == null || lotNum == null || status == null) {
			System.out.println(1);
	        return "NO";
	    }
		List<ReleaseReturnProductsEntity> optional = releaseReturnProductsRepository.findByRelNumberAndLotNumber(relNum,lotNum);
		if(optional.size()==0) {
			System.out.println(2);
			return "NO";
		}else {
			for(ReleaseReturnProductsEntity item : optional) {
				if(item.getStatus()==ReleaseReturnStatus.대기){
					if(item.getQty()<qty) {
						System.out.println(3);
						return "NO";
					}
					else if(item.getQty()==qty){
						item.setStatus(ReleaseReturnStatus.valueOf(status));
						releaseReturnProductsRepository.save(item);
						System.out.println(4);
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
						//
						added.setSupplierCode(item.getSupplierCode());
						added.setProductCode(item.getProductCode());
						releaseReturnProductsRepository.save(item);
						releaseReturnProductsRepository.save(added);			
					}
					System.out.println(5);
					return "OK";
				}
			}
		}
		System.out.println(6);
		return "NO";
	}

	@Override
	public String saveProduct(String rCode,String lCode,int qty, String reason,String manager) {
		List<ReleaseProductsEntity> li = releaseProductsRepository.findByRelNumberAndLotNumber(rCode,lCode);
		System.out.println("return:"+li.size());
		
		if(li.size()==0) {
			return "NO";
		}else {
			System.out.println("start");
			for(ReleaseProductsEntity item : li) {
				System.out.println("NNN");
				if(item.getReturnFlag().equals("N")){
					System.out.println("OOOOKKK");
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
							entity.setReason(ReleaseReturnReason.기타);
						}
						entity.setSupplierCode(item.getSupplierCode()); 
						entity.setQty(qty);
						entity.setStatus(ReleaseReturnStatus.대기);
						entity.setRelNumber(rCode);
						entity.setManager(manager);
						entity.setProductCode(item.getProductCode());
						entity.setDt(LocalDateTime.now());
						ReleaseReturnProductsEntity temp = releaseReturnProductsRepository.save(entity);
						System.out.println(3333333);
						if(temp==null) {
							System.out.println(111111);
						}
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
							entity.setReason(ReleaseReturnReason.기타);
						}
						entity.setSupplierCode(item.getSupplierCode()); 
						entity.setProductCode(item.getProductCode());
						entity.setDt(LocalDateTime.now());
						entity.setQty(qty);
						entity.setStatus(ReleaseReturnStatus.대기);
						entity.setRelNumber(rCode);
						entity.setManager(manager);
						ReleaseReturnProductsEntity temp = releaseReturnProductsRepository.save(entity);
						System.out.println(4444444);
						if(temp==null) {
							System.out.println(222222);
						}
						return "OK";
						
					}
				
				
			}
		}
		
	
	
}
		return "NO";
	}
}
