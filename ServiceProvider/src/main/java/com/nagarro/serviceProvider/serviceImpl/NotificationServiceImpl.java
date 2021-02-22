package com.nagarro.serviceProvider.serviceImpl;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import com.nagarro.serviceProvider.common.NotificationStatus;
import com.nagarro.serviceProvider.entity.Notification;
import com.nagarro.serviceProvider.entity.ServiceProvider;
import com.nagarro.serviceProvider.service.NotificationService;
import com.nagarro.serviceProvider.util.Utility;

public class NotificationServiceImpl implements NotificationService {
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
	public void sendsNotificationToServiceProvider(ServiceProvider serviceProvider, String serviceRequestId) {

		Notification notification = new Notification(Utility.getRandomUuid(), LocalDateTime.now(),
				NotificationStatus.PENDING, serviceRequestId);
		notificationList.add(notification);
		serviceProvider.getNotificationId().add(notification.getId());

	}

	@Override
	public void SendsNotificationToServiceProviderList(List<ServiceProvider> serviceProviderList,
			String serviceRequestId) {
		for (ServiceProvider serviceProvider : serviceProviderList)
			sendsNotificationToServiceProvider(serviceProvider, serviceRequestId);

	}

}
