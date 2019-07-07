package com.altimetrik.accountsPayable.businessLogic;

import java.awt.geom.Rectangle2D;
import java.io.File;
import java.io.IOException;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.text.PDFTextStripperByArea;

import com.altimetrik.accountsPayable.db.DataCurator;


public class DataExtractor {

	private String path;
	private String [] arr = null;
	private String text1,text2,text3,text4,text5;
	
	public DataExtractor(String path,String senderEmail) throws CustomException{
		
		this.path = path;
		this.extractData();
		new DataCurator(text1,text2,text3,text4,text5,senderEmail);
	}
	
	public String getText1(){
		return this.text1;	
	}
	
	public String getText2(){
		return this.text2;
	}
	
	public String getText3(){
		return this.text3;
	}
	
	public String getText4(){
		return this.text4;
	}
	
	public String getText5(){
		return this.text5;
	}
	
	protected void extractData(){
		File file = new File(this.path);
		PDDocument doc = null;
		try{
			doc = PDDocument.load(file);
			PDPage page = doc.getPage(0);
			
			PDFTextStripperByArea pdfStripper = new PDFTextStripperByArea();
			Rectangle2D.Float Rect = new Rectangle2D.Float(51,129,40,15);  
			Rectangle2D.Float Rect2 = new Rectangle2D.Float(180,129,45,15);
			Rectangle2D.Float Rect3 = new Rectangle2D.Float(290,147,55,15);
			Rectangle2D.Float Rect4 = new Rectangle2D.Float(51,169,200,45);
			Rectangle2D.Float Rect5 = new Rectangle2D.Float(51,312,1000,400);
	        pdfStripper.addRegion( "invoice No", Rect );    
	        pdfStripper.addRegion( "invoice Date", Rect2 );
	        pdfStripper.addRegion( "Customer PO", Rect3 );
	        pdfStripper.addRegion( "address", Rect4 );
	        pdfStripper.addRegion( "total invoice", Rect5 );
	        pdfStripper.extractRegions(page); 
			
	        this.text1 = pdfStripper.getTextForRegion("invoice No").trim();
	        
	        this.text2 = pdfStripper.getTextForRegion("invoice Date").trim();
	        
	        this.text3 = pdfStripper.getTextForRegion("Customer PO").trim();
	  
	        this.text4 = pdfStripper.getTextForRegion("address").trim();
	        
	        this.text5 = pdfStripper.getTextForRegion("total invoice").trim();
	        
	        arr = this.text5.split("\n");
	        String temp = arr[arr.length-1];
	        this.text5 = "";
	        
	        for(int i=0;i<temp.length();i++){
	        	if(temp.charAt(i) == ',' || temp.charAt(i) == '$')	
	        		continue;
	        	this.text5 += temp.charAt(i);
	        }
	        
        
		}catch(IOException e){
			e.printStackTrace();
		} finally {
				try {
					if(doc != null)
						doc.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
		}
		
	}
	
}
