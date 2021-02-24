package com.nagarro.serviceProvider;

import java.util.ArrayList;
import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.nagarro.serviceProvider.model.ServiceProvider;
import com.nagarro.serviceProvider.model.ServiceRequest;

@Component
public class Mapper {
	@Autowired
	ModelMapper modelMapper;

	public ServiceProvider convertServiceProviderEntityToModel(
			com.nagarro.serviceProvider.entity.ServiceProvider source) {
		return modelMapper.map(source, ServiceProvider.class);
	}

	public com.nagarro.serviceProvider.entity.ServiceProvider convertServiceProviderModelToEntity(
			ServiceProvider source) {
		return modelMapper.map(source, com.nagarro.serviceProvider.entity.ServiceProvider.class);
	}

	public List<ServiceProvider> convertServiceProviderEntityListToModelList(
			List<com.nagarro.serviceProvider.entity.ServiceProvider> source) {
		List<ServiceProvider> convertedList = new ArrayList<>();

		for (int i = 0; i < source.size(); i++) {
			com.nagarro.serviceProvider.entity.ServiceProvider serviceProviderEntity = source.get(i);

			ServiceProvider serviceProviderModel = convertServiceProviderEntityToModel(serviceProviderEntity);

			convertedList.add(serviceProviderModel);

		}
		return convertedList;
	}

	public List<com.nagarro.serviceProvider.entity.ServiceProvider> convertServiceProviderModelListToEntityList(
			List<ServiceProvider> source) {
		List<com.nagarro.serviceProvider.entity.ServiceProvider> convertedList = new ArrayList<>();

		for (int i = 0; i < source.size(); i++) {
			ServiceProvider serviceProviderModel = source.get(i);

			com.nagarro.serviceProvider.entity.ServiceProvider serviceProviderEntity = convertServiceProviderModelToEntity(
					serviceProviderModel);

			convertedList.add(serviceProviderEntity);

		}
		return convertedList;
	}
	public com.nagarro.serviceProvider.entity.ServiceRequest convertServiceRequestModelToEntity(
			ServiceRequest source) {
		return modelMapper.map(source, com.nagarro.serviceProvider.entity.ServiceRequest.class);
	}
}
