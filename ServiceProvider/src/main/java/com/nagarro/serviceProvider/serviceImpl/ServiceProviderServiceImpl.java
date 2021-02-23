package com.nagarro.serviceProvider.serviceImpl;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.nagarro.serviceProvider.entity.ServiceProvider;
import com.nagarro.serviceProvider.service.ServiceProviderService;

@Service
public class ServiceProviderServiceImpl implements ServiceProviderService {
	static List<String> notificationArrayList1 = new ArrayList<>();
	static List<String> notificationArrayList2 = new ArrayList<>();
	static List<String> notificationArrayList3 = new ArrayList<>();
	static List<ServiceProvider> serviceProviderList = new ArrayList<>();
	static {

		notificationArrayList1.add("1");
		notificationArrayList1.add("2");
		notificationArrayList1.add("3");
		notificationArrayList2.add("4");
		notificationArrayList2.add("5");
		notificationArrayList2.add("6");
		notificationArrayList3.add("7");
		notificationArrayList3.add("8");
		notificationArrayList3.add("9");
		List<String> serviceArrayList1 = new ArrayList<>();
		List<String> serviceArrayList2 = new ArrayList<>();
		List<String> serviceArrayList3 = new ArrayList<>();
		serviceArrayList1.add("1");
		serviceArrayList1.add("2");
		serviceArrayList1.add("3");
		serviceArrayList2.add("4");
		serviceArrayList2.add("5");
		serviceArrayList2.add("6");
		serviceArrayList3.add("7");
		serviceArrayList3.add("8");
		serviceArrayList3.add("9");

		serviceProviderList.add(new ServiceProvider("1", "Vinay", "Prajapati", "Mr", "vkvinay180@gmail.com",
				"8076837497", "30/78-A Vishwas Nagar", "Shahdara", "DELHI", "DELHI", 110032, "INDIA", "1234567890", "1",
				notificationArrayList1, serviceArrayList1));
		serviceProviderList.add(new ServiceProvider("2", "Gourav", "Kansal", "Mr", "gouravkansal0@gmail.com",
				"8076837498", "30/79-A Vishwas Nagar", "Shahdara", "DELHI", "DELHI", 110032, "INDIA", "1234567890", "1",
				notificationArrayList2, serviceArrayList2));
		serviceProviderList.add(new ServiceProvider("3", "Nimish", "Kumar", "Mr", "nimish@gmail.com", "8076837490",
				"30/88-A Buland Nagar", "Buland Nagar", "Buland Nagar District", "UP", 110092, "INDIA", "1234567890",
				"1", notificationArrayList3, serviceArrayList3));
	}

	@Override
	public ServiceProvider getServiceProviderById(String serviceProviderId) {
		Optional<ServiceProvider> searchedServiceProvider = serviceProviderList.stream()
				.filter(serviceProvider -> serviceProvider.getId().equals(serviceProviderId)).findFirst();
		if (searchedServiceProvider.isPresent()) {
			return searchedServiceProvider.get();
		} else
			return null;
	}

	@Override
	public List<ServiceProvider> getServiceProvidersBasedOnDistrict(String district) {
		List<ServiceProvider> searchedServiceProviderList = serviceProviderList.stream()
				.filter(serviceProvider -> serviceProvider.getDistrict().equalsIgnoreCase(district))
				.collect(Collectors.toList());
		if (!searchedServiceProviderList.isEmpty()) {
			return searchedServiceProviderList;
		} else
			return null;
	}

	@Override
	public Boolean createServiceProvider(ServiceProvider serviceProvider) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Boolean deleteServiceProvider(String serviceProviderId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Boolean editServiceProvider(ServiceProvider serviceProvider) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<ServiceProvider> filterServiceProviderBasedOnServiceCategory(List<ServiceProvider> serviceProviderList,
			String categoryId) {
		return serviceProviderList.stream()
				.filter(serviceProvider -> serviceProvider.getCategoryId().equals(categoryId))
				.collect(Collectors.toList());
	}
	@Override
	public void updateServiceProviderList(List<ServiceProvider> modifiedList) {
		for(ServiceProvider modifiedRecord:modifiedList) {
			for(ServiceProvider actualRecord:serviceProviderList) {
					if(modifiedRecord.getId().equals(actualRecord.getId())){
						actualRecord.setNotificationId(modifiedRecord.getNotificationId());
						actualRecord.setServiceRequestId(modifiedRecord.getServiceRequestId());
					}
			}
		}
	}

}
