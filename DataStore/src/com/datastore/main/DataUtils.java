package com.datastore.main;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.util.Date;
import java.util.List;

import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SequenceWriter;
import com.fasterxml.jackson.databind.SerializationFeature;

public class DataUtils {

	static File file =  new File(System.getProperty("user.home")+"\\file.json");


	public static boolean checkingDataSize(DataModels modelObj) {
		try {
			File tempFile  = new File(System.getProperty("user.home")+"\\tmp.txt");
			if(tempFile.createNewFile()) {

				FileWriter fw = new FileWriter(tempFile);
				fw.write(modelObj.getData());
				fw.close();

			}

			if(getFileSizeKiloBytes(tempFile)<16) {

				tempFile.delete();
				return true;
			}else
			{
				tempFile.delete();
				return false;
			}


		}catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}



	private static double getFileSizeKiloBytes(File file) {
		return (double) file.length() / 1024 ;
	}

	private static double getFileSizeMegaBytes(File file) {
		return (double) file.length() / (1024 * 1024) ;
	}

	public static boolean createDataStore(DataModels modelObj) {
		boolean result = false;
		//		File file = new File(FILE_NAME);


		try {
			FileWriter fileWriter = new FileWriter(file, true);

			ObjectMapper objectMapper = new ObjectMapper();
			objectMapper.configure(SerializationFeature.INDENT_OUTPUT, true);
			List<DataModels> myObjects = null ;

			try {
				myObjects = objectMapper.readValue(file, new TypeReference<List<DataModels>>(){});
			}catch (Exception e) {
				// TODO: handle exception
				System.out.println(e.getMessage());
			}


			modelObj.setDate(new Date());
			if(!(myObjects==null)) {
				PrintWriter writer = new PrintWriter(file);
				writer.print("");
				writer.close();
				myObjects.add(modelObj);
				SequenceWriter seqWriter = objectMapper.writer().writeValuesAsArray(fileWriter);

				for(DataModels list : myObjects) {
					seqWriter.write(list);

				}
				seqWriter.close();
				result = true;

			}else {
				SequenceWriter seqWriter = objectMapper.writer().writeValuesAsArray(fileWriter);
				seqWriter.write(modelObj);
				seqWriter.close();	
				//				myObjects = objectMapper.readValue(file, new TypeReference<List<DataModels>>(){});
				result = true;

			}



		}catch (Exception e) {
			
		}

		return result;
	}



	public static boolean checkExistingKey(DataModels modelObj) {
		//		boolean result  = false;
		List<DataModels> myObjects = null ;
		try {
			ObjectMapper objectMapper = new ObjectMapper();
			myObjects = objectMapper.readValue(file, new TypeReference<List<DataModels>>(){});

			for(DataModels dm : myObjects) {
				if(dm.getKey().equals(modelObj.getKey())) {
					return false;
				}

			}

		}catch (Exception e) {
			System.out.println("Exception : While checking for existing key");
			e.printStackTrace();
			return true;
		}
		return true;
	}




	public static boolean isJSON(String test) {
		try {
			System.out.println("data-"+test);
			System.out.println("Parsing data to json");
			if(test.isEmpty())
				return false;
			ObjectMapper mapper = new ObjectMapper();
			JsonFactory factory = mapper.getFactory();
			System.out.println("Parsing data to json1");

			JsonParser parser = factory.createParser(test);
			System.out.println("Parsing data to json2");
			JsonNode actualObj = mapper.readTree(parser);
			//	        new JSONObject(test);
		} catch (Exception ex) {
			System.out.println("Json parsing Excep"+ex.getMessage());
			return false;

		}
		return true;
	}



	public static String getData(String key) {

		try {



			ObjectMapper objectMapper = new ObjectMapper();
			objectMapper.configure(SerializationFeature.INDENT_OUTPUT, true);
			List<DataModels> myObjects = null ;
			myObjects = objectMapper.readValue(file, new TypeReference<List<DataModels>>(){});
			for(DataModels obj : myObjects) {
				if(obj.getKey().equals(key)) {
					long limit = obj.getTimeLimit();
					Date strt = obj.getDate();
					Date end = new Date();
					System.out.println(strt);
					System.out.println(end);
					System.out.println(limit);
					System.out.println("diff-"+(end.getTime()-strt.getTime())/1000);
					if(limit>=(end.getTime()-strt.getTime())/1000) {
						return obj.getData();
					}else
						return "expired";
				}
			}



		}catch (FileNotFoundException e) {
			return "file not found"; 

		}catch (Exception e) {
			return "";
		}
		return "data not found";
	}



	public static boolean removeData(String key) {
		boolean result = false;
		try {
			ObjectMapper objectMapper = new ObjectMapper();
			objectMapper.configure(SerializationFeature.INDENT_OUTPUT, true);
			List<DataModels> myObjects = null ;
			myObjects = objectMapper.readValue(file, new TypeReference<List<DataModels>>(){});
			DataModels remvoveObj = null;
			for(DataModels obj : myObjects) {
				if(obj.getKey().equals(key)) {
					System.out.println("before");
					remvoveObj = obj;
					System.out.println("File removed");
					result = true;
				}
			}
			if(result)
			{
				myObjects.remove(remvoveObj);
				result = createData(myObjects);
				//create datastore
				//		createDataStore(modelObj);

			}else {
				return false;
			}


		}catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return result;
	}



	private static boolean createData(List<DataModels> myObjects) {
		try {

			ObjectMapper objectMapper = new ObjectMapper();
			PrintWriter writer = new PrintWriter(file);
			writer.print("");
			writer.close();
			FileWriter fileWriter = new FileWriter(file, true);


			SequenceWriter seqWriter = objectMapper.writer().writeValuesAsArray(fileWriter);
			for(DataModels list : myObjects) {
				seqWriter.write(list);

			}
			seqWriter.close();
			return true;
		}catch (Exception e) {
			System.out.println("Exception : while creating data");}
		return false;
	}



	public static boolean checkDataStoreLimit() {
		try {
			if(getFileSizeMegaBytes(file)<=1024)
				return true;


		}catch (Exception e) {
			System.out.println("Exception: While cheking datastore limit");
		}
		return false;
	}





}
