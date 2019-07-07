package com.altimetrik.accountsPayable.businessLogic;

import java.util.Date;

public class DatabaseObject {

	private Long invoiceNo;
	private Date invoiceDate;
	private String customerPO;
	private String address;
	private Double totalCost;
	private String senderEmail;
	private String status;
	
	public DatabaseObject(Long invoiceNo,Date invoiceDate,String customerPO,String address,Double totalCost,String senderEmail,String status){
		this.invoiceNo = invoiceNo;
		this.invoiceDate = invoiceDate;
		this.customerPO = customerPO;
		this.address = address;
		this.totalCost = totalCost;
		this.senderEmail = senderEmail;
		this.status = status;
	}
	
	@Override
	public String toString(){
		String invoiceNo = "invoiceNo : " + Long.toString(this.invoiceNo) + "\n";
		String invoiceName = "invoiceDate : " + invoiceDate.toString() + "\n";
		String customerPO = "customerPO : " + this.customerPO + "\n";
		String address = "address : " + this.address + "\n";
		String totalCost = "totalCost : " + Double.toString(this.totalCost) + "\n";
		String senderEmail = "senderEmail : " + this.senderEmail + "\n";
		String status = "status : " + this.status + "\n";
		
		return (invoiceNo + invoiceName + customerPO + address + totalCost + senderEmail + status);
	}
	
	public Long getInvoiceNo(){
		return this.invoiceNo;
	}
	
}
