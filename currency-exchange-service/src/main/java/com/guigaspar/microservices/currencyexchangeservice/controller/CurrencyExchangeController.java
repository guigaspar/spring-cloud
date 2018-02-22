package com.guigaspar.microservices.currencyexchangeservice.controller;

import java.math.BigDecimal;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.guigaspar.microservices.currencyexchangeservice.model.ExchangeValue;
import com.guigaspar.microservices.currencyexchangeservice.repository.ExchangeValueRepository;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;

@RestController
public class CurrencyExchangeController {
	
	private Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	private Environment environment;
	
	@Autowired
	private ExchangeValueRepository repository;
	
	@GetMapping("currency-exchange/from/{from}/to/{to}")
	@HystrixCommand(fallbackMethod="fallbackRetrieveExchangeValue")
	public ExchangeValue retrieveExchangeValue(@PathVariable String from,
											@PathVariable String to){
		ExchangeValue exchangeValue = repository.findByFromAndTo(from, to);
		exchangeValue.setPort(Integer.parseInt(environment.getProperty("local.server.port")));
		logger.info("{}", exchangeValue);
		return exchangeValue;
		
	}
	
	public ExchangeValue fallbackRetrieveExchangeValue(@PathVariable String from,
				@PathVariable String to){
		
		ExchangeValue exchangeValue = new ExchangeValue(100L, from, to, BigDecimal.valueOf(10000));
		exchangeValue.setPort(Integer.parseInt(environment.getProperty("local.server.port")));
		logger.info("{}", exchangeValue);
		return exchangeValue;
	
	}
}
