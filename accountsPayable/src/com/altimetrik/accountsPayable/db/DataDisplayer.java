package com.altimetrik.accountsPayable.db;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
//import java.util.concurrent.Callable;
//import java.util.concurrent.ExecutorService;
//import java.util.concurrent.Executors;
//import java.util.concurrent.Future;

import com.altimetrik.accountsPayable.api.Options;
import com.altimetrik.accountsPayable.businessLogic.DatabaseObject;
import com.altimetrik.accountsPayable.businessLogic.EmailSender;

public class DataDisplayer implements Options{

	private static Connection conn = null;
	private static PreparedStatement pStatement = null;
	private static ResultSet rs = null;
	
	public List<DatabaseObject> showData(){
		
			DatabaseObject obj = null;
			List<DatabaseObject> arr = null;
		try{
			Class.forName("com.mysql.jdbc.Driver");
			conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/kamal","root","");
			pStatement = conn.prepareStatement("select * from invoiceDetails");
			rs = pStatement.executeQuery();
			
			arr = new ArrayList<DatabaseObject>();
			
			while(rs.next()){
				
				obj = new DatabaseObject(rs.getLong(1),rs.getDate(2),rs.getString(3),
						rs.getString(4),rs.getDouble(5),rs.getString(6),rs.getString(7));
				
				arr.add(obj);
				
			}
		}catch(Exception e){
			e.printStackTrace(); 
		}
		return arr;
	}
	
	public String approve(long invoiceNumber){
		try{
			Class.forName("com.mysql.jdbc.Driver");
			conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/kamal","root","");
			pStatement = conn.prepareStatement("select senderEmail from invoiceDetails where invoiceNo = ?");
			pStatement.setLong(1, invoiceNumber); 
			rs = pStatement.executeQuery();
			
			String senderEmailId = ""; 
			while(rs.next()){
				senderEmailId = rs.getString(1);
			}
			
			if(senderEmailId == ""){
				return new String("NO SUCH ID PRESENT ");
			}
			
			System.out.println("Sending Email ... "); 
			new EmailSender(senderEmailId);
			System.out.println("Email Sent "); 
			pStatement = conn.prepareStatement("update invoiceDetails set status = 'approved' where invoiceNo = ? ");
			pStatement.setLong(1, invoiceNumber); 
			pStatement.executeUpdate();
			
		}catch(Exception e){
			e.printStackTrace(); 
		}
		
		return new String("INVOICE APPROVED AND EMAIL SENT ");
	}
	
//	public static void main(String []args){
////		long invoiceNo = 906262136;
////		approve(invoiceNo);
//		showData();
//	}
	
}
