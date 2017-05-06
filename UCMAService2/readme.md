# Why?
To mock the UCMA server

# Prerequisite

* Linux machine
* Docker

# Installation

1. Clone the project
2. Start the mongoserver
    ```
    docker run -d --name mongodb -p 27017:27017 mongo
    ```
3. (Optional) Start the mongodb editor
    ```
    docker run -d --link mongodb:mongo -p 8082:8081 mongo-express
    
    ```
    http://localhost:8082/db/ucma/user
    *  username: jeff
       password: jeff
    *  username: pccw
       password: 1234
    *  username: user1
       password: 1234

4. Start the UCMA Server
    ```
    mvn clean package && java -jar target/UCMAService-0.0.1-SNAPSHOT.jar
    ```

5. Access on browser 

    * WSDL http://localhost:8083/ws/users.wsdl
    
# Test

## Request   

* POST: http://localhost:8083/ws
* Headers:
    * Content-Type: text/xml
* Body    
```
  <soapenv:Envelope xmlns:soapenv="http://schemas.xmlsoap.org/soap/envelope/"
                  xmlns:gs="http://immd.pccw.com/users">
     <soapenv:Header/>
     <soapenv:Body>      
        <gs:loginRequest>
            <gs:login>
               <gs:username>pccw</gs:username>
               <gs:password>1234</gs:password>
            </gs:login>      
        </gs:loginRequest>
     </soapenv:Body>
  </soapenv:Envelope>
```

## Response:
    
```
<SOAP-ENV:Envelope xmlns:SOAP-ENV="http://schemas.xmlsoap.org/soap/envelope/">
  <SOAP-ENV:Header/>
  <SOAP-ENV:Body>
    <ns2:loginResponse xmlns:ns2="http://immd.pccw.com/users">
      <ns2:user>
        <ns2:id>590a8a53da2a560dd488781d</ns2:id>
        <ns2:username>jeff</ns2:username>
        <ns2:password>$2a$10$F5W7EZ40MWfIcFZk/2vdLuAKmoBCZP1x3Sp/SlAXzL5rvlGYtbUma</ns2:password>
        <ns2:firstname>Jeffrey</ns2:firstname>
        <ns2:lastname>Valdenor</ns2:lastname>
        <ns2:roles>user,admin</ns2:roles>
      </ns2:user>
      <ns2:session>
        <ns2:x-auth-token>22bb583f-1c30-4a30-985f-84c6fa451a4e</ns2:x-auth-token>
      </ns2:session>
    </ns2:loginResponse>
  </SOAP-ENV:Body>
</SOAP-ENV:Envelope>
```
