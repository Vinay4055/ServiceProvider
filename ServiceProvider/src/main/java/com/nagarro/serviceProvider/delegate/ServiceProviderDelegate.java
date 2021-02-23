package com.nagarro.serviceProvider.delegate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import com.google.gson.Gson;
@Component
public class ServiceProviderDelegate {
	@Autowired
	RestTemplate restTemplate;
	@Autowired
	@Qualifier("gson")
	Gson gson;
	public String callServiceProvidedAndGetCategoryId(String serviceId) {
		String response = restTemplate
				.exchange("http://localhost:9999/serviceProvided/findCategoryId/{serviceId}"
				, HttpMethod.GET
				, null
				, new ParameterizedTypeReference<String>() {
			}, serviceId).getBody();
		String categoryId = gson.fromJson(response,String.class);
		System.out.println("cATEGOry Id = "+categoryId);
		return categoryId;		 
	}
	@Bean
	public RestTemplate restTemplate() {
		return new RestTemplate();
	}
}
