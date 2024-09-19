package com.quickkoala.service.release;


import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.quickkoala.entity.order.OrderCancelEntity;
import com.quickkoala.entity.order.OrderEntity;
import com.quickkoala.entity.order.OrderReleaseEntity;
import com.quickkoala.entity.order.OrderReleaseEntity.ReleaseStatus;
import com.quickkoala.entity.release.ReleaseCancelEntity;
import com.quickkoala.entity.release.ReleaseCompleteEntity;
import com.quickkoala.entity.release.ReleaseProductsEntity;
import com.quickkoala.entity.release.ReleaseCancelEntity.ReleaseCancelReason;
import com.quickkoala.entity.release.ReleaseCancelEntity.ReleaseCancelWho;
import com.quickkoala.entity.sales.ClientsOrderProductsEntity;
import com.quickkoala.entity.stock.LotEntity;
import com.quickkoala.repository.order.OrderReleaseRepository;
import com.quickkoala.repository.release.ReleaseCancelRepository;
import com.quickkoala.repository.release.ReleaseCompleteRepository;
import com.quickkoala.repository.release.ReleaseProductsRepository;
import com.quickkoala.repository.sales.ClientsOrderProductsRepository;
import com.quickkoala.repository.stock.LotRepository;

@Service
public class OrderReleaseServiceImpl implements OrderReleaseService{
	
	@Autowired
	private OrderReleaseRepository orderReleaseRepository;
	
	@Autowired
	private ReleaseCancelRepository releaseCancelRepository;
	
	@Autowired
	private ReleaseCompleteRepository releaseCompleteRepository;
	
	@Autowired
	private ClientsOrderProductsRepository clientsOrderProductsRepository; 
	
	@Autowired
	private ReleaseProductsRepository releaseProductsRepository; 
	
	@Autowired
	private LotRepository lotRepository; 
	
	@Override
	public String saveStatus(String id, String status) {
		int result = orderReleaseRepository.updateStatus(id,OrderReleaseEntity.ReleaseStatus.valueOf(status));
		Optional<OrderReleaseEntity> optional =  orderReleaseRepository.findById(id);
		if(optional.isPresent()) {
			if(status=="출고지연") {
				
			}else if(status=="출고취소") {
				ReleaseCancelEntity releaseCancelEntity = new ReleaseCancelEntity();
				releaseCancelEntity.setDt(orderReleaseRepository.mysql_now2());
				releaseCancelEntity.setManager("김하주");
				releaseCancelEntity.setMemo(null);
				releaseCancelEntity.setReason(ReleaseCancelReason.기타);
				releaseCancelEntity.setRelNumber(optional.get().getNumber());
				releaseCancelEntity.setWho(ReleaseCancelWho.기타);
				releaseCancelRepository.save(releaseCancelEntity);
				
			}else if(status=="출고완료") {
				ReleaseCompleteEntity releaseCompleteEntity = new ReleaseCompleteEntity();
				releaseCompleteEntity.setDt(orderReleaseRepository.mysql_now2());
				releaseCompleteEntity.setManager("김하주");
				releaseCompleteEntity.setMemo(null);
				releaseCompleteEntity.setRelNumber(optional.get().getNumber());
				releaseCompleteRepository.save(releaseCompleteEntity);
			}else {
			}
			
		}
		
		
		
		return (result>0)?"OK":"NO";
	}
	
	@Override
	public void addReleaseFromOrder(OrderReleaseEntity entity){
		entity.setNumber(this.getMaxNumber());
		entity.setStatus(ReleaseStatus.출고준비);
		orderReleaseRepository.save(entity);
		/*
		 List<ClientsOrderProductsEntity> orderedList = clientsOrderProductsRepository.findByClientsOrdersOrderId(entity.getOrderId());
		 List<ReleaseProductsEntity> releasedList = new ArrayList<ReleaseProductsEntity>();
		 ReleaseProductsEntity releaseProduct = null;
		 for(ClientsOrderProductsEntity released : orderedList) {
			 releaseProduct = new ReleaseProductsEntity();
			 releaseProduct.setLotNumber(null);
			 releaseProduct.setDt(entity.getDt());
			 releaseProduct.setManager("김하주");
			 releaseProduct.setMemo(null);
			 releaseProduct.setQty(released.getQty());
			 releaseProduct.setRelNumber(entity.getNumber());
			 releaseProduct.setIdx(0);
			 releasedList.addAll((ArrayList)this.asignLotNumber(released.getProductCode(),entity.getSalesCode(),released.getQty(),releaseProduct));
		 }
		 releaseProductsRepository.saveAll(releasedList);
		 */
	}
	
	@Override
	public void addAllReleaseFromOrder(List<OrderReleaseEntity> list){
		
	}
	
	@Override
	public String getMaxNumber() {
		String today = orderReleaseRepository.mysql_now();
		List<OrderReleaseEntity> li = orderReleaseRepository.findByNumberLikeOrderByNumberDesc("L"+today+"-%");
		String number =(li.size()==0)?"001":""+String.format("%03d",Integer.valueOf(li.get(0).getNumber().split("-")[1])+1);
		return "L"+today+"-"+number;
	}
	
	@Override
	public List<ReleaseProductsEntity> asignLotNumber(String pcode, String scode, Integer qty, ReleaseProductsEntity entity) {
	    //List<LotEntity> list = lotRepository.findAllByProductCodeAndSupplierCodeOrderByLostNumberDesc(pcode, scode);
		List<LotEntity> list = lotRepository.findAllByProductCodeOrderByLotNumberDesc(pcode);
	    List<ReleaseProductsEntity> rpe = new ArrayList<>();
	    Iterator<LotEntity> it = list.iterator();
	    asignLotRecursively(entity, qty, it, rpe);
	    return rpe;
	}

	private void asignLotRecursively(ReleaseProductsEntity entity, Integer remainingQty, Iterator<LotEntity> lotIterator, List<ReleaseProductsEntity> rpe) {
	    if (!lotIterator.hasNext() || remainingQty <= 0) {
	        if (remainingQty > 0) {
	            System.out.println("할당할 수량 부족");
	        }
	        return;
	    }
	    LotEntity lot = lotIterator.next();
	    int lotQty = lot.getQuantity();
	    
	    ReleaseProductsEntity entity2 = new ReleaseProductsEntity();
	    entity2.setDt(entity.getDt());
	    entity2.setIdx(0);
	    entity2.setManager(entity.getManager());
	    entity2.setMemo(entity.getMemo());
	    entity2.setRelNumber(entity.getRelNumber());
	    entity2.setLotNumber(lot.getLotNumber());
	    
	    if (remainingQty <= lotQty) {
	        entity2.setQty(remainingQty);
	        rpe.add(entity2);
	        remainingQty = 0; 
	    } else {
	        entity2.setQty(lotQty);
	        rpe.add(entity2);
	        remainingQty -= lotQty;
	    }
	    asignLotRecursively(entity, remainingQty, lotIterator, rpe);
	}
	
	

	
}
