package com.altimetrik.accountsPayable.api;

import java.util.List;

import java.util.Scanner;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import com.altimetrik.accountsPayable.businessLogic.AttachmentFetcher;
import com.altimetrik.accountsPayable.businessLogic.DatabaseObject;
import com.altimetrik.accountsPayable.db.DataDisplayer;

public class MyApplication {

	private static final int NTHREADS = 1;
	
	private static void runApplication() {
			ExecutorService executor = Executors.newFixedThreadPool(NTHREADS);
			
			executor.execute(new AttachmentFetcher());
			
			displayInterface();
	}
	
	private static void displayInterface(){
		Scanner sc = new Scanner(System.in);
		int choice = 0;
		do{
			System.out.println("______________________________");
			System.out.println("i) Get all details "); 
			System.out.println("ii) Mark approved and send email"); 
			System.out.println("iii) Exit ");
			System.out.println("______________________________"); 
			
			System.out.println("Enter your choice : "); 
			
			choice = sc.nextInt();
			
			switch(choice){
			case 1 : Options option1 = new DataDisplayer();
				List<DatabaseObject> obj = option1.showData();
					 for(DatabaseObject it : obj){
						 System.out.println(it); 
					 }
					 
				break;
			case 2 : System.out.println("Enter invoice number to approve "); 
					long invoiceNo = sc.nextLong();
					Options option2 = new DataDisplayer();
					String s = option2.approve(invoiceNo);
					System.out.println(s);
				break;
			case 3 : System.out.println("Closing application ... "); 
				sc.close();
				System.exit(0); 
				break;
				default :
					System.out.println("There's no such choice "); 
			}
		}while(true);
	}
	
	public static void main(String[] args) {
				runApplication();
	}

}
