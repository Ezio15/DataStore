package com.datastore.main;


import org.junit.Test;
import org.junit.runner.JUnitCore;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;

import static org.junit.Assert.*;  
import static org.junit.Assert.*;

public class UnitTest {

	private static DataUtils dataUtil = null;

	private static DataUtils getInstance() 
	{ 
		if (dataUtil == null) 
			dataUtil = new DataUtils(); 

		return dataUtil; 
	} 
	
	 @Test
	    public void testingJosnDataFormat() {
		 
		 dataUtil = getInstance();
	        
		 System.out.println("Testing Json format...");
			String jsonFormat="{\"name\":\"goku\",\"salary\":600000.0,\"age\":24}";		 
	        assertEquals(true, dataUtil.isJSON(jsonFormat));
	        
			String incorrectJson="{\"\":\"vegeta\",\"salary\":600000.0,\"age\"24}";		 
	        assertEquals(false, dataUtil.isJSON(incorrectJson));
	        
	        
	        
	        assertEquals(false, dataUtil.isJSON(null));

	        
	        String empty = "";
	        assertEquals(false, dataUtil.isJSON(empty));



	    }
	 
	 @Test
	 public void testingDataSize()
	 {
		 
		 dataUtil = getInstance();

		 System.out.println("Testing Data Size....");
		 	DataModels dm = new DataModels();
		 	dm.setKey("Key1");
		 	dm.setData("{\"name\":\"goku\",\"salary\":600000.0,\"age\":24}");
	        assertEquals(true, dataUtil.checkingDataSize(dm));
	        
	        
			 ObjectMapper mapper = new ObjectMapper();		 
		     ObjectNode objectNode= mapper.createObjectNode();

		 for(int i =0;i<1000;i++) {
		        objectNode.put("bookName+"+i+"", "Java");
		        objectNode.put("price", "100");
		}
		 
		 
		 dm.setKey("key1");
		 dm.setData(objectNode.toString());
	     assertEquals(false, dataUtil.checkingDataSize(dm));



	 }
		
	 
	 @Test
	 public void testingExistingKey() {
		 dataUtil = getInstance();

		 
		 DataModels dm = new DataModels();
		 
		 //if your datastore has field1 key, it return false
		 dm.setKey("field1");
		 assertEquals(false, dataUtil.checkExistingKey(dm));
		 
		 //if your datastore  doesn't has this key, it return true and proceed further for creating new one
		 dm.setKey("field111");
		 assertEquals(true, dataUtil.checkExistingKey(dm));

	 }
	 
	 @Test
	 public void testingDataStoreSize() {
		 dataUtil = getInstance();
		 
		 
		 
	 }
	
}
