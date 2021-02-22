package com.nagarro.serviceProvider.entity;

import java.time.LocalDateTime;

import com.nagarro.serviceProvider.common.NotificationStatus;

import lombok.Getter;
import lombok.Setter;
@Getter
@Setter
public class Notification {
	
	public Notification(String id, LocalDateTime date, NotificationStatus status, String serviceRequestId) {
		super();
		this.id = id;
		this.date = date;
		this.status = status;
		this.serviceRequestId = serviceRequestId;
	}
	String id;
	LocalDateTime date;
	NotificationStatus status;
	String serviceRequestId;
}
