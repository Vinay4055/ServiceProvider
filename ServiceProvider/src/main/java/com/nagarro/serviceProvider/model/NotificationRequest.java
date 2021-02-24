package com.nagarro.serviceProvider.model;

import java.util.List;

import lombok.Getter;
import lombok.Setter;
@Getter
@Setter
public class NotificationRequest {
	List<ServiceProvider> serviceProviderList;
	ServiceRequest serviceRequest;
}
