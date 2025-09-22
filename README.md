# sampleBackend

A sample backend service built with Spring Boot.

## How to set up
1. **Clone the repository to your local**
2. **Run maven command to build the project**
   ```bash
   mvn clean install
   ```
3. **Run the application:**   
You can either run the application using your IDE or use the following command:
   ```bash
   mvn spring-boot:run
   ```
4. Run all unit tests:
   ```bash
   mvn test
   ```
## Notice
This project uses H2 in-memory database for demonstration purposes. During the runtime, your can post orders as many as want. 
I also provide an GET endpoint: /api/orders to print all the orders in the database. However, once you stop the application, all the data will be lost.   
Here is a sample curl command to post an order:
```
curl --location 'localhost:8080/api/orders' \
--header 'Content-Type: application/json' \
--data '{
  "orderGuid": "123e4567-e89b-12d3-a456-426614174001",
  "customerName": "Bob",
  "createdAt": "2025-09-21T20:34:56Z",
  "items": [
    {
      "productId": "P001",
      "quantity": 2
    },
    {
      "productId": "P002",
      "quantity": 1
    }
  ]
}'
```