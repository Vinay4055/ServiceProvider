package com.nagarro.serviceProvider.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.nagarro.serviceProvider.entity.Notification;
import com.nagarro.serviceProvider.entity.ServiceProvider;

@Service
public interface NotificationService {
	public void sendsNotificationToServiceProviderList(List<ServiceProvider> serviceProvider,String serviceRequestId);
	public List<Notification> getPendingNotifications(String serviceProviderId);
	public List<Notification> getAcceptedNotifications(String serviceProviderId);

}
