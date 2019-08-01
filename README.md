# Login History 
### REST Service that displays all the login dates and the number of logged in times of a user/s in a certain criteria 

##### Build instructions
```
git clone https://github.com/chaixxiv/isr-login.git
cd isr-exam
mvn clean package
```

##### Run
```
cd isr-exam/
mvn spring-boot:run
Application is running on port 8080

Sample API Method:
http://localhost:8080/test/dates
```

##### SQL queries for creating the table and insert records are in the src/main/resources/sql
##### Automated acceptance test is in the src/test/resources/postman_collection.json
