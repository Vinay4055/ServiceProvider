package com.nagarro.serviceProvider.model;

import java.util.List;

import lombok.Getter;
import lombok.Setter;
@Getter
@Setter
public class ServiceProvider {
	String id;
	String firstName;
	String lastName;
	String title;
	String email;
	String telePhone;
	String address;
	String district;
	String city;
	String state;
	Integer zip;
	String country;
	String password;
	String categoryId;
	List<String> notificationId;
	List<String> serviceRequestId;

}
