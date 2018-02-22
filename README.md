# Spring Cloud - Microservices


### Spring Boot 2 project using Spring Cloud(Eureka, Ribbon, Feign, Hystrix, Zuul, Sleuth, Zipkin).

---
Microservices projects using Spring Cloud.

I have five projects in this repository.

netflix-eureka-naming-server          (Eureka Server)
netflix-zuul-api-gateway-server       (Zuul Gateway)
zipkin-distributed-tracing-server     (Zipkin Server)
currency-exchange-service             (Micro Service - Hystrix)
currency-conversion-service           (Micro Service - Ribbon/Feign)

Links:

`http://localhost:8761/`      	  -> Eureka Naming Server

`http://localhost:9411`           -> Zipkin Server

`http://localhost:8071/currency-exchange/from/EUR/to/BRL`		-> Instances of the currency exchange service

`http://localhost:8070/currency-exchange/from/EUR/to/BRL` 	-> Instances of the currency exchange service

`http://localhost:8100/currency-conversion-feign/from/EUR/to/BRL/quantity/108`   	-> Rest Call using Feign and Ribbon

`http://localhost:8100/currency-conversion/from/EUR/to/BRL/quantity/108`		    -> Rest Call using RestTemplate

`http://localhost:8765/currency-exchange-service/currency-exchange/from/EUR/to/BRL`	-> Using Zuul to call the currency exchange service 

`http://localhost:8765/currency-conversion-service/currency-conversion-feign/from/EUR/to/BRL/quantity/108` -> Using Zuul to call the currency conversion service 

`http://localhost:8765/currency-conversion-service/currency-conversion-feign/from/sdf/to/BRL/quantity/108` -> Hystrix FallBack

---

Order to start the projects:

netflix-eureka-naming-server    
zipkin-distributed-tracing-server    
currency-exchange-service          
currency-conversion-service     
netflix-zuul-api-gateway-server
