package com.quickkoala.service.release;
import java.time.LocalDate;
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
import com.quickkoala.entity.release.OrderReleaseEntity;
import com.quickkoala.entity.release.ReleaseCancelEntity;
import com.quickkoala.entity.release.ReleaseCompleteEntity;
import com.quickkoala.entity.release.ReleaseProductsEntity;
import com.quickkoala.entity.release.OrderReleaseEntity.ReleaseStatus;
import com.quickkoala.entity.release.ReleaseCancelEntity.ReleaseCancelReason;
import com.quickkoala.entity.release.ReleaseCancelEntity.ReleaseCancelWho;
import com.quickkoala.entity.sales.ClientsOrderProductsEntity;
import com.quickkoala.entity.stock.LotEntity;
import com.quickkoala.entity.stock.ProductEntity;
import com.quickkoala.repository.release.OrderReleaseRepository;
import com.quickkoala.repository.release.ReleaseCancelRepository;
import com.quickkoala.repository.release.ReleaseCompleteRepository;
import com.quickkoala.repository.release.ReleaseProductsRepository;
import com.quickkoala.repository.sales.ClientsOrderProductsRepository;
import com.quickkoala.repository.stock.LotRepository;
import com.quickkoala.repository.stock.ProductRepository;
import com.quickkoala.utils.GetToken;

import jakarta.transaction.Transactional;

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
	
	@Autowired
	private ProductRepository  productRepository;
	
	@Override
	public String saveStatus(String id, String status,String manager) {
		int result = 0;
		Optional<OrderReleaseEntity> optional =  orderReleaseRepository.findById(id);
		LocalDateTime date = LocalDateTime.now();
		if(optional.isPresent()) {
			if(status=="출고취소") {
				orderReleaseRepository.deleteByNumber(id);
				ReleaseCancelEntity releaseCancelEntity = new ReleaseCancelEntity();
				releaseCancelEntity.setDt(date);
				releaseCancelEntity.setManager(manager);
				releaseCancelEntity.setMemo(null);
				releaseCancelEntity.setReason(ReleaseCancelReason.기타);
				releaseCancelEntity.setRelNumber(optional.get().getNumber());
				releaseCancelEntity.setWho(ReleaseCancelWho.기타);
				ReleaseCancelEntity saved = releaseCancelRepository.save(releaseCancelEntity);
				if(saved!=null) {
					result=1;
				}
			}else if(status=="출고완료") {
				orderReleaseRepository.deleteByNumber(id);
				ReleaseCompleteEntity releaseCompleteEntity = new ReleaseCompleteEntity();
				releaseCompleteEntity.setDt(date);
				releaseCompleteEntity.setManager("김하주");
				releaseCompleteEntity.setMemo(null);
				releaseCompleteEntity.setRelNumber(optional.get().getNumber());
				ReleaseCompleteEntity saved = releaseCompleteRepository.save(releaseCompleteEntity);
				if(saved!=null) {
					result=1;
				}
			}else if(status=="출고지연"){
				result = orderReleaseRepository.updateStatus(id,OrderReleaseEntity.ReleaseStatus.valueOf(status));
			}
			
		}
		return (result>0)?"OK":"NO";
	}
	
	
	@Override
	@Transactional
	public String addReleaseFromOrder(OrderReleaseEntity orderReleaseEntity){
		boolean asignok = true;
		String newReleaseNumber = this.asignRelNumber();
		orderReleaseEntity.setNumber(newReleaseNumber);
		orderReleaseEntity.setStatus(ReleaseStatus.출고준비);
		 List<ClientsOrderProductsEntity> orderedList = clientsOrderProductsRepository.findByClientsOrdersOrderId(orderReleaseEntity.getOrderId());
		 List<ReleaseProductsEntity> releasedList = new ArrayList<ReleaseProductsEntity>();
		 ReleaseProductsEntity releaseProduct = null;
		 for(ClientsOrderProductsEntity released : orderedList) {
			 releaseProduct = new ReleaseProductsEntity();
			 releaseProduct.setLotNumber(null);
			 releaseProduct.setDt(orderReleaseEntity.getDt());
			 releaseProduct.setManager(orderReleaseEntity.getManager());
			 releaseProduct.setMemo(null);
			 releaseProduct.setQty(released.getQty());
			 releaseProduct.setRelNumber(orderReleaseEntity.getNumber());
			 releaseProduct.setProductCode(released.getProductCode());
			 Optional<ProductEntity> pe =productRepository.findByCode(released.getProductCode());
			 if(pe.isPresent()) {
				 releaseProduct.setSupplierCode(pe.get().getSupplierCode());
			 }else {
				 releaseProduct.setSupplierCode("error-code");
			 }
			 productRepository.flush();
			 ArrayList<ReleaseProductsEntity> asignResult =  (ArrayList)this.asignLotNumber(released.getProductCode(),orderReleaseEntity.getSalesCode(),released.getQty(),releaseProduct);
			 if(asignResult==null) {
				 asignok=false;
				 break;
			 }
			 releasedList.addAll(asignResult);
		 }
		
		 if(asignok) {
			 orderReleaseRepository.save(orderReleaseEntity);
			 releaseProductsRepository.saveAll(releasedList);
			 return "OK";
		 }else {
			 return "STOCKERROR";
		 }
	}
	
	@Override
	public String asignRelNumber() {
		String today = LocalDate.now().toString().replace("-", "");
		List<OrderReleaseEntity> li = orderReleaseRepository.findByNumberLikeOrderByNumberDesc("L"+today+"-%");
		String number =(li.size()==0)?"001":""+String.format("%03d",Integer.valueOf(li.get(0).getNumber().split("-")[1])+1);
		return "L"+today+"-"+number;
	}
	
	@Override
	public List<ReleaseProductsEntity> asignLotNumber(String pcode, String scode, Integer qty, ReleaseProductsEntity entity) {
		List<LotEntity> list = lotRepository.findAllByProductCodeOrderByLotNumberAsc(pcode);
	    List<ReleaseProductsEntity> rpe = new ArrayList<>();
	    Iterator<LotEntity> it = list.iterator();
	    return asignLotRecursively(entity, qty, it, rpe)?rpe:null;
	}

	private boolean asignLotRecursively(ReleaseProductsEntity entity, Integer remainingQty, Iterator<LotEntity> lotIterator, List<ReleaseProductsEntity> rpe) {
	    if (!lotIterator.hasNext() || remainingQty <= 0) {
	        if (remainingQty > 0) {
	            return false;
	        }
	        return true;
	    }
	    LotEntity lot = lotIterator.next();
	    int lotQty = lot.getQuantity();
	    ReleaseProductsEntity entity2 = new ReleaseProductsEntity();
	    entity2.setDt(entity.getDt());
	    entity2.setManager(entity.getManager());
	    entity2.setMemo(entity.getMemo());
	    entity2.setRelNumber(entity.getRelNumber());
	    entity2.setLotNumber(lot.getLotNumber());
	    entity2.setProductCode(entity.getProductCode());
	    Optional<ProductEntity> pe =productRepository.findByCode(entity.getProductCode());
		 if(pe.isPresent()) {
			 entity2.setSupplierCode(pe.get().getSupplierCode());
		 }else {
			return false;
		 }
		 productRepository.flush();
	    if (remainingQty <= lotQty) {
	        entity2.setQty(remainingQty);
	        rpe.add(entity2);
	        remainingQty = 0; 
	    } else {
	        entity2.setQty(lotQty);
	        rpe.add(entity2);
	        remainingQty -= lotQty;
	    }
	    return asignLotRecursively(entity, remainingQty, lotIterator, rpe);
	}
	
	

	
}
