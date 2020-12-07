# spring-boot-user-microservice
This is a simple micro-service to get and update user details based on Spring boot framework.
To get it running, steps to be followed as below: Pre-requisite: Java 8 or higher , jdk/bin in the PATH environment variable.
1)	Download the repository, open command prompt and run either of below commands from the directory where this is downloaded,

    a.	mvnw spring-boot:run, this option does not require maven installed on your machine.
    
    or
    
    b.	run maven command mvn clean install, then run java -jar user-details-0.0.1-SNAPSHOT.jar  from the target folder, this option requires maven installed,      
        M2_HOME is set and PATH has M2_HOME/bin.
        
2)	Once the command has run successfully, URLs to get user details can be accessed from browser http://localhost:8080/user/1 where 1 is the employee id for the user. 5 users are loaded in memory databse(H2) on startup. Those user details are in this file /src/main/resouces/users.json.
3)	URL to update the user details is http://localhost:8080/user/update and JSON to be provided in the post request body. For testing this REST client, POSTMAN or any other suitable tool can be used. 
Sample JSON:


<pre>
{
    "empId": 10,
    "gender": "M",
    "name": {
        "title": "Mr",
        "firstName": "Charlie",
        "lastName": "Smith"
    },
    "address": {
        "street": "121 Pacific Highway",
        "city": "Sydney",
        "state": "NSW",
        "postCode": 2077
    }
}
</pre>
