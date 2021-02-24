package com.nagarro.serviceProvider.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.nagarro.serviceProvider.entity.ServiceProvider;
@Service
public interface ServiceProviderService {
	public ServiceProvider getServiceProviderById(String serviceProviderId);
	public List<ServiceProvider> getServiceProvidersBasedOnDistrict(String district);
	public Boolean createServiceProvider(ServiceProvider serviceProvider);
	public Boolean deleteServiceProvider(String serviceProviderId);
	public Boolean editServiceProvider(ServiceProvider serviceProvider);
	public List<ServiceProvider> filterServiceProviderBasedOnServiceCategory(List<ServiceProvider> serviceProviderList,String categoryId);
	public void updateServiceProviderList(List<ServiceProvider> modifiedList);
	public Boolean acceptPendingNotification(String serviceProviderId,String notificationId);
	public Boolean denyPendingNotification(String serviceProviderId,String notificationId);
	
}
