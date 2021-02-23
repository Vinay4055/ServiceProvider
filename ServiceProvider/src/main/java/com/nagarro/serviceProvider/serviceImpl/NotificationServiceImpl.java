package com.nagarro.serviceProvider.serviceImpl;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Service;

import com.google.gson.Gson;
import com.nagarro.serviceProvider.Mapper;
import com.nagarro.serviceProvider.common.NotificationStatus;
import com.nagarro.serviceProvider.delegate.ServiceProviderDelegate;
import com.nagarro.serviceProvider.entity.Notification;
import com.nagarro.serviceProvider.entity.ServiceProvider;
import com.nagarro.serviceProvider.entity.ServiceRequest;
import com.nagarro.serviceProvider.model.NotificationRequest;
import com.nagarro.serviceProvider.service.NotificationService;
import com.nagarro.serviceProvider.service.ServiceProviderService;
import com.nagarro.serviceProvider.util.Utility;

@Service
public class NotificationServiceImpl implements NotificationService {
	@Autowired
	Gson gson;
	@Autowired
	ServiceProviderService serviceProviderService;
	@Autowired
	ServiceProviderDelegate serviceProviderDelegate;
	@Autowired
	Mapper mapper;
	static List<Notification> notificationList = new ArrayList<>();
	static {
		notificationList.add(new Notification("1", LocalDateTime.now(), NotificationStatus.ACCEPT, "1"));
		notificationList.add(new Notification("2", LocalDateTime.now(), NotificationStatus.ACCEPT, "2"));
		notificationList.add(new Notification("3", LocalDateTime.now(), NotificationStatus.ACCEPT, "3"));
		notificationList.add(new Notification("4", LocalDateTime.now(), NotificationStatus.ACCEPT, "4"));
		notificationList.add(new Notification("5", LocalDateTime.now(), NotificationStatus.ACCEPT, "5"));
		notificationList.add(new Notification("6", LocalDateTime.now(), NotificationStatus.ACCEPT, "6"));
		notificationList.add(new Notification("7", LocalDateTime.now(), NotificationStatus.ACCEPT, "7"));
		notificationList.add(new Notification("8", LocalDateTime.now(), NotificationStatus.ACCEPT, "8"));
		notificationList.add(new Notification("9", LocalDateTime.now(), NotificationStatus.ACCEPT, "9"));
	}

	

	@Override
	public void sendsNotificationToServiceProviderList(List<ServiceProvider> serviceProviderList,
			String serviceRequestId) {
		Notification notification = new Notification(Utility.getRandomUuid(), LocalDateTime.now(),
				NotificationStatus.PENDING, serviceRequestId);
		notificationList.add(notification);
		for (ServiceProvider serviceProvider : serviceProviderList) {
			serviceProvider.getNotificationId().add(notification.getId());
		}
		serviceProviderService.updateServiceProviderList(serviceProviderList);
		/*
		 * System.out.println(serviceProviderList);
		 * System.out.println("Notification List = "+notificationList);
		 * System.out.println("Complete Service Provider List "
		 * +ServiceProviderServiceImpl.serviceProviderList);
		 */
	}

	@JmsListener(destination = "ServiceRequestReceivedFromAdmin")
	public void acceptServiceRequestEvent(String serviceRequest) {
		NotificationRequest notificationRequestObject = gson.fromJson(serviceRequest, NotificationRequest.class);
		System.out.println("Service ID = "+notificationRequestObject.getServiceRequest().getServiceId());
		String categoryId = serviceProviderDelegate
				.callServiceProvidedAndGetCategoryId(notificationRequestObject.getServiceRequest().getServiceId());
		List<ServiceProvider> serviceProviderList = serviceProviderService.filterServiceProviderBasedOnServiceCategory(
				mapper.convertServiceProviderModelListToEntityList(notificationRequestObject.getServiceProviderList()),
				categoryId);
		sendsNotificationToServiceProviderList(serviceProviderList,notificationRequestObject.getServiceRequest().getId());
	}

}
