## CloudWalk
### 
I tried not to use too many frameworks. Only persistence and testing. I tried not to use SpringBatch in this case because I believe that the objective was to evaluate the code and not the ease of use of frameworks.
Thanks.

Interesting things:
- I used yubikey to sign this commit
- I will do some version in rust to compare performance

Requirements:
Java 1.8

Running:
Use this command to run:
```bash
mvn spring-boot:run
```

Development:
Change the value of param:
```
spring.datasource.url=jdbc:h2:file:/YOUR_DIRECTORY/batchdb;DB_CLOSE_ON_EXIT=FALSE
```
Example:
```
spring.datasource.url=jdbc:h2:file:c:/batchdb;DB_CLOSE_ON_EXIT=FALSE
```