package com.nagarro.serviceProvider.model;

import javax.validation.constraints.NotEmpty;

import lombok.Getter;
import lombok.Setter;
@Getter
@Setter
public class NotificationActionRequest {
	@NotEmpty(message = "Notification Id is mandatory")
	String notificationId;
	@NotEmpty(message = "Service Provider Id is mandatory")
	String serviceProviderId;
}
