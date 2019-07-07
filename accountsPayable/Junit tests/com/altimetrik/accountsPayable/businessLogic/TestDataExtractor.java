package com.altimetrik.accountsPayable.businessLogic;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;

public class TestDataExtractor {

	DataExtractor obj = null;
	
	@Before
	public void set()throws CustomException{
		obj = new DataExtractor("C:\\Users\\kmuwani\\Acushnet.pdf","df");
		obj.extractData();
	}
	
	@Test
	public void testExtractData(){
		assertEquals("906262136",obj.getText1());
	}
	
	@Test
	public void testExtractData2() {
//		obj.extractData();
//		System.out.println(obj.getText2()); 
		assertEquals("08/01/18",obj.getText2());
	}
	
	@Test
	public void testExtractData3() {
//		obj.extractData();
		assertEquals("414308118",obj.getText3());
	}
	
	@Test
	public void testExtractData5() {
//		obj.extractData();
//		System.out.println("**" + obj.getText5() + "**"); 
		assertEquals("1355.71",obj.getText5());
	}
	
}
