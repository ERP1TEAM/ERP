package com.quickkoala.service.release;

public interface ReleaseReturnProductsService {
	public String saveStatus(String relNum,String lotNum,int qty,String status);
	public String saveProduct(String rCode,String lCode,int qty, String reason,String manager) ;
	
}
