# spring-boot-user-microservice

This is a simple micro-service to get and update user details based on Spring boot framework.

To get it running, steps to be followed as below:
<B>Pre-requisite :</B> Java 8
1. Download the repository, open command prompt and run maven command <B>mvnw spring-boot:run</B> from the directory where this is downloaded.
2. Once the command has run successfully, URLs to get user details can be accessed from browser http://localhost:8080/user/1 where 1 is the employee id for the user. 5 users are loaded in memory databse(H2) on startup. Those user details are in this file /src/main/resouces/users.json.
3. URL to update the user details is http://localhost:8080/user/update and JSON to be provided in the post request body
Sample JSON:
<pre>
{
    "empId": 10,
    "gender": "M",
    "name": {
        "id": 20,
        "title": "Mr",
        "firstName": "Charlie",
        "lastName": "Smith"
    },
    "address": {
        "id": 1,
        "street": "121 Pacific Highway",
        "city": "Sydney",
        "state": "NSW",
        "postCode": 2077
    }
}
</pre>
