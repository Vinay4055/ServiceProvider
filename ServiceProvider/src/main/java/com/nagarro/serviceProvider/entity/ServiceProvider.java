package com.nagarro.serviceProvider.entity;

import java.util.List;

import lombok.Getter;
import lombok.Setter;
@Getter
@Setter
public class ServiceProvider {
	public ServiceProvider() {
		
	}
	public ServiceProvider(String id, String firstName, String lastName, String title, String email, String telePhone,
			String address, String district, String city, String state, Integer zip, String country, String password,
			String categoryId, List<String> notificationId, List<String> serviceRequestId) {
		super();
		this.id = id;
		this.firstName = firstName;
		this.lastName = lastName;
		this.title = title;
		this.email = email;
		this.telePhone = telePhone;
		this.address = address;
		this.district = district;
		this.city = city;
		this.state = state;
		this.zip = zip;
		this.country = country;
		this.password = password;
		this.categoryId = categoryId;
		this.notificationId = notificationId;
		this.serviceRequestId = serviceRequestId;
	}
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
	@Override
	public String toString() {
		return "ServiceProvider [id=" + id + ", firstName=" + firstName + ", lastName=" + lastName + ", categoryId="
				+ categoryId + ", notificationId=" + notificationId + ", serviceRequestId=" + serviceRequestId + "]";
	}

}
