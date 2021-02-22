package com.nagarro.serviceProvider.entity;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Service {
	String id;
	String name;
	Float cost;
	ServiceCategory category;
	
}
