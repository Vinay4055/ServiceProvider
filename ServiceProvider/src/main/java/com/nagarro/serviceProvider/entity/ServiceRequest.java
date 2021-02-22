package com.nagarro.serviceProvider.entity;

import javax.validation.constraints.NotEmpty;

import com.nagarro.serviceProvider.common.ServiceRequestStatus;

import lombok.Getter;
import lombok.Setter;
@Getter
@Setter
public class ServiceRequest {
	@NotEmpty(message = "ServiceID is Mandatory")
	String id;
	@NotEmpty(message = "Service Date is Mandatory")
	String date;
	@NotEmpty(message = "Service Receiver is Mandatory")
	String emailIdOfServiceReceiver;
	String emailIdOfServiceProvider;
	String specialRequirement;
	@NotEmpty(message = "Service Request Status is Mandatory")
	ServiceRequestStatus statusOfRequest;	
}