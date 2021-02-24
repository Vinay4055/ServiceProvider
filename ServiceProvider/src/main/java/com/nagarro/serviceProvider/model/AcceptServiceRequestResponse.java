package com.nagarro.serviceProvider.model;

import java.io.Serializable;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AcceptServiceRequestResponse {

public AcceptServiceRequestResponse(ServiceProvider serviceProvider, String serviceRequestId) {
		super();
		this.serviceProvider = serviceProvider;
		this.serviceRequestId = serviceRequestId;
	}
ServiceProvider serviceProvider;
String serviceRequestId;
}
