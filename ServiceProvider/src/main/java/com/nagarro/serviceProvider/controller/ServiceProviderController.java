package com.nagarro.serviceProvider.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.nagarro.serviceProvider.Mapper;
import com.nagarro.serviceProvider.entity.Notification;
import com.nagarro.serviceProvider.model.NotificationActionRequest;
import com.nagarro.serviceProvider.model.ServiceProvider;
import com.nagarro.serviceProvider.service.NotificationService;
import com.nagarro.serviceProvider.service.ServiceProviderService;

@RestController
@RequestMapping("/serviceProvider")
public class ServiceProviderController {
	@Autowired
	ServiceProviderService serviceProviderService;
	@Autowired
	NotificationService notificationService;
	@Autowired
	Mapper mapper;

	@GetMapping("/{district}")
	public ResponseEntity<List<ServiceProvider>> getServiceProviderBasedOnLocation(
			@PathVariable(name = "district") String district) {
		if (!serviceProviderService.getServiceProvidersBasedOnDistrict(district).isEmpty()) {
			List<ServiceProvider> serviceProvider = mapper.convertServiceProviderEntityListToModelList(
					serviceProviderService.getServiceProvidersBasedOnDistrict(district));
			return new ResponseEntity<List<ServiceProvider>>(serviceProvider, HttpStatus.FOUND);
		} else
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
	}

	@GetMapping("/pendingNotification/{serviceProviderId}")
	public ResponseEntity<List<Notification>> displayPendingNotifications(
			@PathVariable(name = "serviceProviderId") String serviceProviderId) {
		return new ResponseEntity<List<Notification>>(notificationService.getPendingNotifications(serviceProviderId), HttpStatus.FOUND);
	}

	@GetMapping("/acceptedNotification/{serviceProviderId}")
	public ResponseEntity<List<Notification>> displayAcceptedNotifications(
			@PathVariable(name = "serviceProviderId") String serviceProviderId) {
		return new ResponseEntity<List<Notification>>(notificationService.getAcceptedNotifications(serviceProviderId), HttpStatus.FOUND);
	}

	@PutMapping("/acceptNotification/")
	public ResponseEntity<Boolean> acceptPendingNotification(
			@RequestBody @Valid NotificationActionRequest notificationActionRequest, BindingResult bindingResult) {
		if (bindingResult.hasErrors()) {
			return new ResponseEntity<>(HttpStatus.UNPROCESSABLE_ENTITY);
		} else {
			Boolean flag = serviceProviderService.acceptPendingNotification(notificationActionRequest.getServiceProviderId(),
					notificationActionRequest.getNotificationId());
			
			if(flag)
				return new ResponseEntity<Boolean>(flag,HttpStatus.ACCEPTED);
			else
				return new ResponseEntity<Boolean>(flag,HttpStatus.NOT_FOUND);

		}
	}
	@PutMapping("/denyNotification/")
	public ResponseEntity<Void> denyPendingNotification(
			@RequestBody @Valid NotificationActionRequest notificationActionRequest, BindingResult bindingResult) {
		if (bindingResult.hasErrors()) {
			return new ResponseEntity<Void>(HttpStatus.UNPROCESSABLE_ENTITY);
		} else {
			Boolean flag = serviceProviderService.denyPendingNotification(notificationActionRequest.getServiceProviderId(),
					notificationActionRequest.getNotificationId());
			if(flag)
				return new ResponseEntity<Void>(HttpStatus.ACCEPTED);
			else
				return new ResponseEntity<Void>(HttpStatus.NOT_FOUND);

		}
	}
}
