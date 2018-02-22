package com.guigaspar.microservices.currencyconversionservice.controller;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.guigaspar.microservices.currencyconversionservice.bean.CurrencyConversionBean;
import com.guigaspar.microservices.currencyconversionservice.bean.CurrencyExchangeServiceProxy;

@RestController
public class CurrencyConversionController {
	
	private Logger logger = LoggerFactory.getLogger(this.getClass());

	@Autowired
	private CurrencyExchangeServiceProxy currencyExchangeServiceProxy;
	
	@GetMapping("/currency-conversion/from/{from}/to/{to}/quantity/{quantity}")
	public CurrencyConversionBean convertCurrency(@PathVariable String from,
			@PathVariable String to,
			@PathVariable BigDecimal quantity){
		
		Map<String, String> uriVariables = new HashMap<>();
		uriVariables.put("from", from);
		uriVariables.put("to", to);
		
		ResponseEntity<CurrencyConversionBean> responseEntity = new RestTemplate().getForEntity("http://localhost:8070/currency-exchange/from/{from}/to/{to}", 
				CurrencyConversionBean.class,
				uriVariables);
		
		CurrencyConversionBean response = new CurrencyConversionBean(responseEntity.getBody().getId(),
				from,to,responseEntity.getBody().getConversionMultiple(), quantity, 
				quantity.multiply(responseEntity.getBody().getConversionMultiple()), responseEntity.getBody().getPort());
		
		logger.info("{}", response);
		
		return response;
		
	}
	

	@GetMapping("/currency-conversion-feign/from/{from}/to/{to}/quantity/{quantity}")
	public CurrencyConversionBean convertCurrencyFeign(@PathVariable String from,
			@PathVariable String to,
			@PathVariable BigDecimal quantity){

		CurrencyConversionBean response = currencyExchangeServiceProxy.retrieveExchangeValue(from, to);	
		
		logger.info("{}", response);
		
		return new CurrencyConversionBean(response.getId(),
				from,to,response.getConversionMultiple(), quantity, 
				quantity.multiply(response.getConversionMultiple()), response.getPort());
		
	}
}
