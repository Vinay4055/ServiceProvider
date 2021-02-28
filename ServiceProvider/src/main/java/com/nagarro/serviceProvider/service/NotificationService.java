package com.nagarro.serviceProvider.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.nagarro.serviceProvider.entity.ServiceProvider;
import com.nagarro.serviceProvider.model.ResponseForServiceProvider;

@Service
public interface NotificationService {
	public void sendsNotificationToServiceProviderList(List<ServiceProvider> serviceProvider,String serviceRequestId);
	public List<ResponseForServiceProvider> getPendingNotifications(String serviceProviderId);
	public List<ResponseForServiceProvider> getAcceptedNotifications(String serviceProviderId);

}
