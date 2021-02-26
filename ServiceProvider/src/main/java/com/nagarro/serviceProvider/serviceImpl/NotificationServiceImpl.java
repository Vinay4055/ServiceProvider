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
import com.nagarro.serviceProvider.common.ServiceRequestStatus;
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
	static List<ServiceRequest> serviceRequestList = new ArrayList<>();
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
		serviceRequestList.add(new ServiceRequest("1", "1", "25-02-2021", "xyz@gmail.com", "vkvinay180@gmail.com", "NA",
				ServiceRequestStatus.CONFIRMED));
		serviceRequestList.add(new ServiceRequest("2", "1", "25-02-2021", "xyz@gmail.com", "vkvinay180@gmail.com", "NA",
				ServiceRequestStatus.CONFIRMED));
		serviceRequestList.add(new ServiceRequest("3", "1", "25-02-2021", "xyz@gmail.com", "vkvinay180@gmail.com", "NA",
				ServiceRequestStatus.CONFIRMED));
		serviceRequestList.add(new ServiceRequest("4", "1", "25-02-2021", "xyz@gmail.com", "gouravkansal0@gmail.com",
				"NA", ServiceRequestStatus.CONFIRMED));
		serviceRequestList.add(new ServiceRequest("5", "1", "25-02-2021", "xyz@gmail.com", "gouravkansal0@gmail.com",
				"NA", ServiceRequestStatus.CONFIRMED));
		serviceRequestList.add(new ServiceRequest("6", "1", "25-02-2021", "xyz@gmail.com", "gouravkansal0@gmail.com",
				"NA", ServiceRequestStatus.CONFIRMED));
		serviceRequestList.add(new ServiceRequest("7", "1", "25-02-2021", "xyz@gmail.com", "nimish@gmail.com", "NA",
				ServiceRequestStatus.CONFIRMED));
		serviceRequestList.add(new ServiceRequest("8", "1", "25-02-2021", "xyz@gmail.com", "nimish@gmail.com", "NA",
				ServiceRequestStatus.CONFIRMED));
		serviceRequestList.add(new ServiceRequest("9", "1", "25-02-2021", "xyz@gmail.com", "nimish@gmail.com", "NA",
				ServiceRequestStatus.CONFIRMED));

	}

	@Override
	public void sendsNotificationToServiceProviderList(List<ServiceProvider> serviceProviderList,
			String serviceRequestId) {
		Boolean notificationExists = false;
		Notification existingNotificationObject = null;
		for (Notification existingNotification : notificationList) {
			if(existingNotification.getServiceRequestId().equals(serviceRequestId)) {
				existingNotificationObject = existingNotification;
				existingNotificationObject.setStatus(NotificationStatus.PENDING);
				notificationExists = true;
			}
			
		}
		if (notificationExists) {
			notificationList.remove(existingNotificationObject);
			
		}
			Notification notification = new Notification(Utility.getRandomUuid(), LocalDateTime.now(),
					NotificationStatus.PENDING, serviceRequestId);
			notificationList.add(notification);
			for (ServiceProvider serviceProvider : serviceProviderList) {
				serviceProvider.getNotificationId().add(notification.getId());
				System.out.println("Notification Sent With Notification Entity " + notification);
			}
		
		serviceProviderService.updateServiceProviderList(serviceProviderList);
		
	}

	@JmsListener(destination = "ServiceRequestReceivedFromAdmin")
	public void acceptServiceRequestEvent(String serviceRequest) {
		NotificationRequest notificationRequestObject = gson.fromJson(serviceRequest, NotificationRequest.class);
		System.out.println("Service ID = " + notificationRequestObject.getServiceRequest().getServiceId());
		String categoryId = serviceProviderDelegate
				.callServiceProvidedAndGetCategoryId(notificationRequestObject.getServiceRequest().getServiceId());
		List<ServiceProvider> serviceProviderList = serviceProviderService.filterServiceProviderBasedOnServiceCategory(
				mapper.convertServiceProviderModelListToEntityList(notificationRequestObject.getServiceProviderList()),
				categoryId);
		ServiceRequest serviceRequestObject = mapper
				.convertServiceRequestModelToEntity(notificationRequestObject.getServiceRequest());
		System.out.println("New Request Object = " + serviceRequestObject);
		ServiceRequest existingServiceRequestObject = ServiceProviderServiceImpl
				.getServiceRequestById(serviceRequestObject.getId());
		System.out.println("Existing Request = " + existingServiceRequestObject);
		if (existingServiceRequestObject != null && existingServiceRequestObject.getStatusOfRequest()
				.compareTo(ServiceRequestStatus.CANCELLEDBYSERVICEPROVIDER) == 0) {
			System.out.println(
					"Inside If of existing service request = " + existingServiceRequestObject.getStatusOfRequest());
			existingServiceRequestObject.setStatusOfRequest(ServiceRequestStatus.PENDING);
		} else {
			System.out.println("Inside Else of existing");
			serviceRequestList
					.add(mapper.convertServiceRequestModelToEntity(notificationRequestObject.getServiceRequest()));
		}

		
		sendsNotificationToServiceProviderList(serviceProviderList,
				notificationRequestObject.getServiceRequest().getId());
	}

	public static Notification getNotificationFromNotificationId(String notificationId) {

		for (Notification notification : notificationList) {
			if (notification.getId().equals(notificationId)) {
				return notification;

			}

		}
		return null;
	}

	public static List<Notification> getNotificationListFromNotificationIdList(List<String> notificationIdList) {
		List<Notification> notificationListResult = new ArrayList<>();
		for (String notificationId : notificationIdList) {
			for (Notification notification : notificationList) {
				if (notification.getId().equals(notificationId)) {
					notificationListResult.add(notification);
					break;
				}
			}
		}
		return notificationListResult;
	}

	@Override
	public List<Notification> getPendingNotifications(String serviceProviderId) {
		ServiceProvider serviceProvider = serviceProviderService.getServiceProviderById(serviceProviderId);
		List<String> notificationIdList = serviceProvider.getNotificationId();

		List<Notification> notificationListOfServiceProvider = getNotificationListFromNotificationIdList(
				notificationIdList);
		System.out.println("Motification ID List = " + notificationListOfServiceProvider);
		List<Notification> pendingNotification = new ArrayList<>();

		for (Notification notification : notificationListOfServiceProvider) {
			String serviceRequestId = notification.getServiceRequestId();
			ServiceRequest serviceRequest = ServiceProviderServiceImpl.getServiceRequestById(serviceRequestId);
			if (notification.getStatus().compareTo(NotificationStatus.PENDING) == 0) {
				if (serviceRequest.getStatusOfRequest().compareTo(ServiceRequestStatus.CANCEL) != 0) {
					pendingNotification.add(notification);
				}
			}
			/*
			 * if (notification.getStatus().compareTo(NotificationStatus.ACCEPT) == 0 &&
			 * serviceRequest.getStatusOfRequest().compareTo(ServiceRequestStatus.PENDING)
			 * == 0) { // This case is possible if the service provider cancels the request
			 * after // accepting the request pendingNotification.add(notification); }
			 */

		}
		System.out.println("SERVICE PROVIDER = " + serviceProvider);
		System.out.println("Pending Notification = " + pendingNotification);
		return pendingNotification;
	}

	@Override
	public List<Notification> getAcceptedNotifications(String serviceProviderId) {
		ServiceProvider serviceProvider = serviceProviderService.getServiceProviderById(serviceProviderId);
		List<String> notificationIdList = serviceProvider.getNotificationId();

		List<Notification> notificationListOfServiceProvider = getNotificationListFromNotificationIdList(
				notificationIdList);
		List<Notification> acceptedNotification = new ArrayList<>();
		for (Notification notification : notificationListOfServiceProvider) {

			if (notification.getStatus().compareTo(NotificationStatus.ACCEPT) == 0) {
				System.out.println("Service Request Id = " + notification.getServiceRequestId());
				ServiceRequest serviceRequest = ServiceProviderServiceImpl
						.getServiceRequestById(notification.getServiceRequestId());
				System.out.println("Service Request Object = " + serviceRequest);
				/*
				 * if
				 * (serviceRequest.getStatusOfRequest().compareTo(ServiceRequestStatus.PENDING)
				 * == 0) { // Do Nothing System.out.println("Inside If"); } else
				 */
					acceptedNotification.add(notification);
			}
		}

		return acceptedNotification;
	}

}
