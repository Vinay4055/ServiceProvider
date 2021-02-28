package com.nagarro.serviceProvider.model;

import com.nagarro.serviceProvider.entity.Notification;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ResponseForServiceProvider {
	public ResponseForServiceProvider() {
	}

	public ResponseForServiceProvider(ServiceRequest serviceRequest, Notification notification,ServiceProvided serviceProvided) {
		super();
		this.serviceRequest = serviceRequest;
		this.notification = notification;
		this.serviceProvided = serviceProvided;
	}

	ServiceRequest serviceRequest;
	Notification notification;
	ServiceProvided serviceProvided;
}
