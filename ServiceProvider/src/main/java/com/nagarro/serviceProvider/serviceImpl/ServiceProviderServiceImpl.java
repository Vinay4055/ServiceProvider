package com.nagarro.serviceProvider.serviceImpl;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Service;

import com.google.gson.Gson;
import com.nagarro.serviceProvider.Mapper;
import com.nagarro.serviceProvider.common.NotificationStatus;
import com.nagarro.serviceProvider.common.ServiceRequestStatus;
import com.nagarro.serviceProvider.delegate.ServiceProviderDelegate;
import com.nagarro.serviceProvider.entity.Notification;
import com.nagarro.serviceProvider.entity.ServiceProvider;
import com.nagarro.serviceProvider.entity.ServiceRequest;
import com.nagarro.serviceProvider.model.AcceptServiceRequestResponse;
import com.nagarro.serviceProvider.service.NotificationService;
import com.nagarro.serviceProvider.service.ServiceProviderService;

@Service
public class ServiceProviderServiceImpl implements ServiceProviderService {
	@Autowired
	NotificationService notificationService;
	@Autowired
	ServiceProviderDelegate serviceProviderDelegate;
	@Autowired
	JmsTemplate jmsTemplate;
	@Autowired
	Mapper mapper;
	@Autowired
	Gson gson;
	static List<String> notificationArrayList1 = new ArrayList<>();
	static List<String> notificationArrayList2 = new ArrayList<>();
	static List<String> notificationArrayList3 = new ArrayList<>();
	static List<ServiceProvider> serviceProviderList = new ArrayList<>();
	static {

		notificationArrayList1.add("1");
		notificationArrayList1.add("2");
		notificationArrayList1.add("3");
		notificationArrayList2.add("4");
		notificationArrayList2.add("5");
		notificationArrayList2.add("6");
		notificationArrayList3.add("7");
		notificationArrayList3.add("8");
		notificationArrayList3.add("9");
		List<String> serviceArrayList1 = new ArrayList<>();
		List<String> serviceArrayList2 = new ArrayList<>();
		List<String> serviceArrayList3 = new ArrayList<>();
		serviceArrayList1.add("1");
		serviceArrayList1.add("2");
		serviceArrayList1.add("3");
		serviceArrayList2.add("4");
		serviceArrayList2.add("5");
		serviceArrayList2.add("6");
		serviceArrayList3.add("7");
		serviceArrayList3.add("8");
		serviceArrayList3.add("9");

		serviceProviderList.add(new ServiceProvider("1", "Vinay", "Prajapati", "Mr", "vkvinay180@gmail.com",
				"8076837497", "30/78-A Vishwas Nagar", "Shahdara", "DELHI", "DELHI", 110032, "INDIA", "1234567890", "1",
				notificationArrayList1, serviceArrayList1));
		serviceProviderList.add(new ServiceProvider("2", "Gourav", "Kansal", "Mr", "gouravkansal0@gmail.com",
				"8076837498", "30/79-A Vishwas Nagar", "Shahdara", "DELHI", "DELHI", 110032, "INDIA", "1234567890", "1",
				notificationArrayList2, serviceArrayList2));
		serviceProviderList.add(new ServiceProvider("3", "Nimish", "Kumar", "Mr", "nimish@gmail.com", "8076837490",
				"30/88-A Buland Nagar", "Buland Nagar", "Buland Nagar District", "UP", 110092, "INDIA", "1234567890",
				"1", notificationArrayList3, serviceArrayList3));
	}

	@Override
	public ServiceProvider getServiceProviderById(String serviceProviderId) {
		Optional<ServiceProvider> searchedServiceProvider = serviceProviderList.stream()
				.filter(serviceProvider -> serviceProvider.getId().equals(serviceProviderId)).findFirst();
		if (searchedServiceProvider.isPresent()) {
			return searchedServiceProvider.get();
		} else
			return null;
	}

	@Override
	public List<ServiceProvider> getServiceProvidersBasedOnDistrict(String district) {
		List<ServiceProvider> searchedServiceProviderList = serviceProviderList.stream()
				.filter(serviceProvider -> serviceProvider.getDistrict().equalsIgnoreCase(district))
				.collect(Collectors.toList());
		if (!searchedServiceProviderList.isEmpty()) {
			return searchedServiceProviderList;
		} else
			return null;
	}

	@Override
	public Boolean createServiceProvider(ServiceProvider serviceProvider) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Boolean deleteServiceProvider(String serviceProviderId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Boolean editServiceProvider(ServiceProvider serviceProvider) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<ServiceProvider> filterServiceProviderBasedOnServiceCategory(List<ServiceProvider> serviceProviderList,
			String categoryId) {
		return serviceProviderList.stream()
				.filter(serviceProvider -> serviceProvider.getCategoryId().equals(categoryId))
				.collect(Collectors.toList());
	}

	@Override
	public void updateServiceProviderList(List<ServiceProvider> modifiedList) {
		for (ServiceProvider modifiedRecord : modifiedList) {
			for (ServiceProvider actualRecord : serviceProviderList) {
				if (modifiedRecord.getId().equals(actualRecord.getId())) {
					actualRecord.setNotificationId(modifiedRecord.getNotificationId());
					actualRecord.setServiceRequestId(modifiedRecord.getServiceRequestId());
				}
			}
		}
	}

	public ServiceRequest getServiceRequestById(String serviceRequestId) {
		Optional<ServiceRequest> searchedServiceRequest = NotificationServiceImpl.serviceRequestList.stream()
				.filter(serviceRequest -> serviceRequest.getId().equals(serviceRequestId)).findFirst();
		if (searchedServiceRequest.isPresent()) {
			return searchedServiceRequest.get();
		} else
			return null;
	}

	public List<ServiceProvider> getServiceProviderDetailsBasedOnCategory(String categoryId) {

		return serviceProviderList.stream()
				.filter(serviceProvider -> serviceProvider.getCategoryId().equals(categoryId))
				.collect(Collectors.toList());

	}

	public List<ServiceProvider> getServiceProviderSubscribedWithNotification(String notificationId) {
		Notification notification = NotificationServiceImpl.getNotificationFromNotificationId(notificationId);
		String serviceRequestId = notification.getServiceRequestId();
		ServiceRequest serviceRequest = getServiceRequestById(serviceRequestId);
		String serviceId = serviceRequest.getServiceId();
		String categoryId = serviceProviderDelegate.callServiceProvidedAndGetCategoryId(serviceId);
		return getServiceProviderDetailsBasedOnCategory(categoryId);
	}

	@Override
	public Boolean acceptPendingNotification(String serviceProviderId, String notificationId) {
		ServiceProvider serviceProvider = getServiceProviderById(serviceProviderId);
		List<Notification> pendingNotificationList = notificationService.getPendingNotifications(serviceProviderId);
		for (Notification pendingNotification : pendingNotificationList) {
			if (pendingNotification.getId().equals(notificationId)) {
				if (pendingNotification.getStatus().compareTo(NotificationStatus.PENDING) == 0) { // Again Checking The
					List<ServiceProvider> otherServiceProviders = getServiceProviderSubscribedWithNotification(
							notificationId); // Status
					for (ServiceProvider otherServiceProvider : otherServiceProviders) {
						if (!serviceProviderId.equals(otherServiceProvider.getId()))
							otherServiceProvider.getNotificationId().remove(notificationId);
					}
					pendingNotification.setStatus(NotificationStatus.ACCEPT);
					String serviceRequestId = pendingNotification.getServiceRequestId();
					ServiceRequest serviceRequest = getServiceRequestById(serviceRequestId);
					serviceRequest.setStatusOfRequest(ServiceRequestStatus.CONFIRMED);
					serviceProvider.getServiceRequestId().add(serviceRequest.getServiceId());
					System.out.println("Notification List = " + NotificationServiceImpl.notificationList);

					AcceptServiceRequestResponse acceptServiceRequestResponse = new AcceptServiceRequestResponse(
							mapper.convertServiceProviderEntityToModel(serviceProvider), serviceRequestId);
					jmsTemplate.convertAndSend("RequestAcceptedByServiceProvider",
							gson.toJson(acceptServiceRequestResponse));
					jmsTemplate.convertAndSend("RequestAcceptedByServiceProviderEventForManageServiceRequest",
							gson.toJson(acceptServiceRequestResponse));
					return true;
				}
			}

		}
		return false;

	}

	@Override
	public Boolean denyPendingNotification(String serviceProviderId, String notificationId) {
		ServiceProvider serviceProvider = getServiceProviderById(serviceProviderId);
		List<String> notificationIdList = serviceProvider.getNotificationId();
		for (String notification : notificationIdList) {
			if (notification.equals(notificationId))
				notificationIdList.remove(notificationId);
		}
		return false;

	}

	public ServiceProvider getServiceProviderByEmailId(String emailId) {
		Optional<ServiceProvider> foundServiceProvider = serviceProviderList.stream()
				.filter(serviceProvider -> serviceProvider.getEmail().equals(emailId)).findFirst();
		if (foundServiceProvider.isPresent())
			return foundServiceProvider.get();
		else
			return null;
	}

	@JmsListener(destination = "CancelServiceRequestFromServiceProvider")
	public void cancelServiceRequest(String serviceRequestId) {
		String serviceRequestIdObject = gson.fromJson(serviceRequestId, String.class);
		ServiceRequest serviceRequest = getServiceRequestById(serviceRequestIdObject);
		serviceRequest.setStatusOfRequest(ServiceRequestStatus.CANCEL);

	}

	
}
