package com.nagarro.serviceProvider.model;

import java.io.Serializable;
import java.util.List;

import lombok.Getter;
import lombok.Setter;
@Getter
@Setter
public class NotificationRequest implements Serializable{
	List<ServiceProvider> serviceProviderList;
	ServiceRequest serviceRequest;
}
