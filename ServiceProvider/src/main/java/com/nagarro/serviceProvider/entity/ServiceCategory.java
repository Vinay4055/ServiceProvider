package com.nagarro.serviceProvider.entity;

import java.util.List;

import lombok.Getter;
import lombok.Setter;
@Getter
@Setter
public class ServiceCategory {
	String id;
	String name;
	List<Service> service;
}
