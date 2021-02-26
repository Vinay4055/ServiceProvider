package com.nagarro.serviceProvider.entity;

import javax.validation.constraints.NotEmpty;

import com.nagarro.serviceProvider.common.ServiceRequestStatus;

import lombok.Getter;
import lombok.Setter;
@Getter
@Setter
public class ServiceRequest {
	public ServiceRequest() {}
	public ServiceRequest(@NotEmpty(message = "ServiceRequestId is Mandatory") String id,
			@NotEmpty(message = "ServiceId is Mandatory") String serviceId,
			@NotEmpty(message = "Service Date is Mandatory") String date,
			@NotEmpty(message = "Service Receiver is Mandatory") String emailIdOfServiceReceiver,
			String emailIdOfServiceProvider, String specialRequirement,
			@NotEmpty(message = "Service Request Status is Mandatory") ServiceRequestStatus statusOfRequest) {
		super();
		this.id = id;
		this.serviceId = serviceId;
		this.date = date;
		this.emailIdOfServiceReceiver = emailIdOfServiceReceiver;
		this.emailIdOfServiceProvider = emailIdOfServiceProvider;
		this.specialRequirement = specialRequirement;
		this.statusOfRequest = statusOfRequest;
	}
	@NotEmpty(message = "ServiceRequestId is Mandatory")
	String id;
	@NotEmpty(message = "ServiceId is Mandatory")
	String serviceId;
	@NotEmpty(message = "Service Date is Mandatory")
	String date;
	@NotEmpty(message = "Service Receiver is Mandatory")
	String emailIdOfServiceReceiver;
	String emailIdOfServiceProvider;
	String specialRequirement;
	@NotEmpty(message = "Service Request Status is Mandatory")
	ServiceRequestStatus statusOfRequest;
	@Override
	public String toString() {
		return "ServiceRequest [id=" + id + ", serviceId=" + serviceId + ", date=" + date
				+ ", emailIdOfServiceReceiver=" + emailIdOfServiceReceiver + ", emailIdOfServiceProvider="
				+ emailIdOfServiceProvider + ", specialRequirement=" + specialRequirement + ", statusOfRequest="
				+ statusOfRequest + "]";
	}
	
	
}