package com.nagarro.serviceProvider.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.nagarro.serviceProvider.Mapper;
import com.nagarro.serviceProvider.model.ServiceProvider;
import com.nagarro.serviceProvider.service.ServiceProviderService;

@RestController
@RequestMapping("/serviceProvider")
public class ServiceProviderController {
	@Autowired
	ServiceProviderService serviceProviderService;
	@Autowired
	Mapper mapper;

	@GetMapping("/{district}")
	public ResponseEntity<List<ServiceProvider>> getServiceProviderBasedOnLocation(
			@PathVariable(name = "district") String district) {
		if(!serviceProviderService.getServiceProvidersBasedOnDistrict(district).isEmpty()) {
		List<ServiceProvider> serviceProvider = mapper.convertServiceProviderEntityListToModelList(
				serviceProviderService.getServiceProvidersBasedOnDistrict(district));
		return new ResponseEntity<List<ServiceProvider>>(serviceProvider,HttpStatus.FOUND);
		}
		else
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
	}
}
