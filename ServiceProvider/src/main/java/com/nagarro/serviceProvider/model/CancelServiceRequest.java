package com.nagarro.serviceProvider.model;

import javax.validation.constraints.NotEmpty;

import lombok.Getter;
import lombok.Setter;
@Getter
@Setter
public class CancelServiceRequest {
	@NotEmpty(message = "ServiceRequest Id is mandatory")
	String serviceRequestId;
	@NotEmpty(message = "Service Provider Id is mandatory")
	String serviceProviderId;
}
