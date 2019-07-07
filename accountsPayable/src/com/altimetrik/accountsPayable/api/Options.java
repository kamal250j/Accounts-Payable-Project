package com.altimetrik.accountsPayable.api;

import java.util.List;

import com.altimetrik.accountsPayable.businessLogic.DatabaseObject;

public interface Options {

	List<DatabaseObject> showData();
	String approve(long invoiceNo);
	
}
