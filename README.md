# DataStore
DataStore is a file-based key-value data store that supports the basic CRD (create, read, and delete)
operations. This data store is meant to be used as a local storage for one single process on one
laptop. The data store must be exposed as a library to clients that can instantiate a class and work
with the data store



Installation:
 - Clone the project
 - Add required libraries. below i mentioned Required libraries for this project
 - Export Jar from this project.
 - Initiate the MainStore class and call the create,read or delete function with arguments.
 - Required libraries:
    - jackson-annotations-2.5.0.jar, jackson-annotations-2.5.0-sources.jar.
    - jackson-core-2.5.0.jar, jackson-core-2.5.0-sources.jar.
    - jackson-databind-2.5.0.jar, jackson-databind-2.5.0-sources.jar.
    - junit4-4.8.2.jar, hamcrest-core-1.3.jar.\
    
Usage:
- To Create DataStore follow the below steps:
      - To create datastore, Initialize the MainStore Object and call createData function with arguments.   
      for eg:
         ```MainStore dataStore = MainStore.getInstance();```\
         ```mapObj = dataStore.createData(key,value,timeLimit);```\
        - key - String type and should be unique.\
        - value - String type and should be json format.\
        - timeLimit(Optional) - integer type and seconds format.\
        - Return as map object key value pair. \
            ```{"Status"=200,                           ```\
            ``` "Message"="Data added successfully :-)"} ```
         
           

- To Read DataStore follow the below steps:
    - To read datastore, Initialize the MainStore Object and call readData function with arguments.
    for eg:
        ```MainStore dataStore = MainStore.getInstance();```\
        ```mapObj = dataStore.readData(key);```\
        key - String type and key which already created.\
        Return as map object key value pair.\
          ```{Status=200, ```\
         ``` "Message"= {"name":"GOKU","salary":600000.0,"age":24} }```
        
 - To Delete DataStore follow the below steps:
 
     - To read datastore, Initialize MainStore Object and call readData function with arguments.
      for eg:
        ```MainStore dataStore = MainStore.getInstance();```\
        ```mapObj = dataStore.deleteData(key);```\
        key - String type and key which already created.\
        Return as map object key value pair.\
        ```{"Status"=200, ```\
        ```"Message"="Data removed"}```
Notes:
   - File will be created at System User Directory and name as file.json( For eg:C:\Users\Pradeep\file.json).
   - DataStore limit, Which is capable upto 1GB.
   - Staus codes & Description:
        - Status: 200, Data created,deleted and read successfully.
        - Status: 406, Invalid key.
        - Status: 400, Time Limit Expired.
        - Status: 404, Data not found.
        - Status: 202, Key already exists.
        - Status: 204, Data Should be in Json Format.
        - Status: 206, Data size should less than or equal to 16kb.
        - Status: 208, DataStore limit reached.
        - Status: 300, Key length should be less than or equal to 32 char.

       
      
