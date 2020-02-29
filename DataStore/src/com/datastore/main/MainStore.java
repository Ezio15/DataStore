package com.datastore.main;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;

import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.core.util.DefaultPrettyPrinter;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SequenceWriter;
import com.fasterxml.jackson.databind.SerializationFeature;

public class MainStore {

//	public static void main(String[] args) throws FileNotFoundException {
//
//		//		createData("field9", "data8", 5);
//		//		readData("field10");
//		//		deleteData("field9");
//		HashMap<String, String> map;
//		dataStore = MainStore.getInstance();
//
//		map = dataStore.createData("field1", "data11", 500);
////		map = dataStore.readData("field12");
////	
////		map = dataStore.deleteData("field11");
//		
//		System.out.println("Result");
//		System.out.println(map);
//
//	}
	private static MainStore dataStore = null;

	private static MainStore getInstance() 
	{ 
		if (dataStore == null) 
			dataStore = new MainStore(); 

		return dataStore; 
	} 
	
	public static synchronized HashMap<String, String> deleteData(String key) {
		HashMap<String , String> map = new HashMap<String, String>();

		boolean result = false;
		try {
			result = DataUtils.removeData(key);
			if(result)
			{
				map.put("Status", "200");
				map.put("Message", "Data removed");
			}else
			{
				map.put("Status", "406");
				map.put("Message", "Invalid key");
			}
		}catch (Exception e) {
			map.put("Status", "500");
			map.put("Message", "Server exception");
		}
		return map;
	}

	public static synchronized  HashMap<String, String> readData(String key) {
		String data;
		HashMap<String , String> map = new HashMap<String, String>();

		try {

			data=  DataUtils.getData(key);
			System.out.println(data);

			if(data.equals("expired")) {
				System.out.println("Limit expired");
				map.put("Status", "400");
				map.put("Message", "Data timelimit expired");
			}else if(data.equals("")) {
				System.out.println("Data is not there");
				map.put("Status", "404");
				map.put("Message", "Data not found");	
			}
			else
			{
				System.out.println("Success-"+data);
				map.put("Status", "200");
				map.put("Message", data);	
			}

		}catch (Exception e) {
			map.put("Status", "500");
			map.put("Message", "Server error");
		}
		return map;
	}

	public static synchronized HashMap<String, String> createData(String key, String data, int timeLimit) throws FileNotFoundException {
		HashMap<String , String> map = new HashMap<String, String>();

		try {
			boolean result = false;
//			String s="{\"name\":\"goku\",\"salary\":600000.0,\"age\":24}";		 

			DataModels modelObj = new DataModels();
			modelObj.setKey(key);
			modelObj.setData(data);
			modelObj.setTimeLimit(timeLimit);
//			modelObj.setData(s);

			if(modelObj.getKey().length()<=32) {
				if(DataUtils.checkDataStoreLimit()) {
					if(DataUtils.checkingDataSize(modelObj)) {
						if(DataUtils.isJSON(modelObj.getData())) {
							if(DataUtils.checkExistingKey(modelObj)) {
								result = DataUtils.createDataStore(modelObj);
								if(result)
									map.put("Status", "200");
								map.put("Message", "Data added successfully :-)");
							}else {
								System.out.println("Key already exists");
								map.put("Status","202" );
								map.put("Message", "Key already exists");
							}
						}else {
							System.out.println("Data should be in Json format");
							map.put("Status", "204");
							map.put("Message", "Data should be in Json format");

						}
					}else {
						System.out.println("Datastore Size exceeded");
						map.put("Status", "206");
						map.put("Message", "Data should be in Json format");

					}

				}else {
					System.out.println("Data size shuld be less than or 30kb");
					map.put("Status", "208");
					map.put("Message", "Data size shuld be less than or 30kb");

				}
			}
			else {
				System.out.println("Key length should be less than 32 char");
				map.put("Status", "300");
				map.put("Message", "Key length should be less than 32 char");


			}	



		}catch (Exception e) {

			System.out.println("Exception: While creating DataStore");
			map.put("Status", "500");
			map.put("Message", "Server error");


		}
		return map;

	}

}
