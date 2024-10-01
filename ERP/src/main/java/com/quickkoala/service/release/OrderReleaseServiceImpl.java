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

import com.quickkoala.entity.order.MaxOrderNumberEntity;
import com.quickkoala.entity.order.OrderCancelEntity;
import com.quickkoala.entity.order.OrderEntity;
import com.quickkoala.entity.release.MaxReleaseNumberEntity;
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
import com.quickkoala.repository.release.MaxReleaseNumberRepository;
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
	
	@Autowired
	private MaxReleaseNumberRepository maxReleaseNumberRepository;
	
	@Override
	public String saveStatus(String id, String status,String manager) {
		int result = 0;
		Optional<OrderReleaseEntity> optional =  orderReleaseRepository.findById(id);
		LocalDateTime date = LocalDateTime.now();
		if(optional.isPresent()) {
			if(status=="출고취소") {
				ReleaseCancelEntity releaseCancelEntity = new ReleaseCancelEntity();
				releaseCancelEntity.setDt(date);
				releaseCancelEntity.setManager(manager);
				releaseCancelEntity.setMemo(null);
				releaseCancelEntity.setReason(ReleaseCancelReason.기타);
				releaseCancelEntity.setRelNumber(optional.get().getNumber());
				releaseCancelEntity.setWho(ReleaseCancelWho.기타);
				releaseCancelEntity.setOrderNumber(optional.get().getOrderNumber());
				releaseCancelEntity.setSalesCode(optional.get().getSalesCode());
				orderReleaseRepository.updateStatus(id,OrderReleaseEntity.ReleaseStatus.출고취소);
				ReleaseCancelEntity saved = releaseCancelRepository.save(releaseCancelEntity);
				if(saved!=null) {
					result=1;
				}
			}else if(status=="출고완료") {
				try {
				ReleaseCompleteEntity releaseCompleteEntity = new ReleaseCompleteEntity();
				releaseCompleteEntity.setDt(date);
				releaseCompleteEntity.setManager(manager);
				releaseCompleteEntity.setMemo(null);
				releaseCompleteEntity.setOrderNumber(optional.get().getOrderNumber());
				releaseCompleteEntity.setSalesCode(optional.get().getSalesCode());
				releaseCompleteEntity.setRelNumber(optional.get().getNumber());
				orderReleaseRepository.updateStatus(id,OrderReleaseEntity.ReleaseStatus.출고완료);
				ReleaseCompleteEntity saved = releaseCompleteRepository.save(releaseCompleteEntity);
				if(saved!=null) {
					result=1;
				}}catch(Exception e) {
					e.printStackTrace();
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
		//String newReleaseNumber = this.asignRelNumber();
		String newReleaseNumber = temp(LocalDate.now());
		orderReleaseEntity.setNumber(newReleaseNumber);
		orderReleaseEntity.setStatus(ReleaseStatus.출고준비);
		 List<ClientsOrderProductsEntity> orderedList = clientsOrderProductsRepository.findByClientsOrdersOrderId(orderReleaseEntity.getOrderId());
		 List<ReleaseProductsEntity> releaseProducts = new ArrayList<ReleaseProductsEntity>();
		 ReleaseProductsEntity releaseProduct = null;
		 for(ClientsOrderProductsEntity released : orderedList) {
			 releaseProduct = new ReleaseProductsEntity();
			 releaseProduct.setReturnFlag("N");
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
			 releaseProducts.addAll(asignResult);
		 }
		
		 if(asignok) {
			 releaseProductsRepository.saveAll(releaseProducts);
			 orderReleaseRepository.save(orderReleaseEntity);
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
		
		//
		
		
		
		//
	}
	
	 private String temp(LocalDate day) {
	    	MaxReleaseNumberEntity max = maxReleaseNumberRepository.findByDt(day);
	    	String result = "";
	    	if(max==null) {
	    		MaxReleaseNumberEntity temp = new MaxReleaseNumberEntity();
	    		temp.setDt(day);
	    		temp.setNum(1);
	    		maxReleaseNumberRepository.save(temp);
	    		 result = "L"+day.format(DateTimeFormatter.ofPattern("yyyyMMdd"))+"-001";
	    	}else {
	    		int newNumber = max.getNum()+1;
	        	max.setNum(newNumber);
	        	maxReleaseNumberRepository.save(max);
	        	 result = "L"+day.format(DateTimeFormatter.ofPattern("yyyyMMdd"))+"-"+String.format("%03d",newNumber);
	    	}
	    	return result;
	    }
	    
	
	@Override
	public List<ReleaseProductsEntity> asignLotNumber(String pcode, String scode, Integer qty, ReleaseProductsEntity releaseProduct) {
		List<LotEntity> lots = lotRepository.findAllByProductCodeOrderByLotNumberAsc(pcode);
	    List<ReleaseProductsEntity> result = new ArrayList<>();
	    Iterator<LotEntity> lot = lots.iterator();
	    return asignLotRecursively(releaseProduct, qty, lot, result)?result:null;
	}
	int count=0;
	private boolean asignLotRecursively(ReleaseProductsEntity entity, Integer qty, Iterator<LotEntity> lotIterator, List<ReleaseProductsEntity> result) {
	    if (!lotIterator.hasNext() && qty > 0) {
	        return false;
	    } else if (qty == 0) {
	        return true;
	    }
	    
	    LotEntity lot = lotIterator.next();
	    int lotQty = lot.getQuantity();

	    if (lotQty <= 0) {
	        return asignLotRecursively(entity, qty, lotIterator, result);
	    }

	    ReleaseProductsEntity newLotAssigned = new ReleaseProductsEntity();
	    newLotAssigned.setDt(entity.getDt());
	    newLotAssigned.setManager(entity.getManager());
	    newLotAssigned.setMemo(entity.getMemo());
	    newLotAssigned.setRelNumber(entity.getRelNumber());
	    newLotAssigned.setReturnFlag("N");
	    newLotAssigned.setLotNumber(lot.getLotNumber());
	    newLotAssigned.setProductCode(entity.getProductCode());
	    
	    Optional<ProductEntity> pe = productRepository.findByCode(entity.getProductCode());
	    if (pe.isPresent()) {
	        newLotAssigned.setSupplierCode(pe.get().getSupplierCode());
	    } else {
	        return false;
	    }
	    if (qty <= lotQty) {
	        newLotAssigned.setQty(qty);
	        result.add(newLotAssigned);
	        qty = 0; 
	    } else {
	        newLotAssigned.setQty(lotQty);
	        result.add(newLotAssigned);
	        qty -= lotQty; 
	    }

	    return asignLotRecursively(entity, qty, lotIterator, result);
	}

	

	
}
