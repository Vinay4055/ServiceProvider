package com.nagarro.serviceProvider.util;

import java.util.UUID;

public class Utility {
	private Utility() throws Exception {
		throw new Exception("Utility Class can't be instantiated");
	}
	public static String getRandomUuid() {
		UUID uuid = UUID.randomUUID();
        return uuid.toString();
	}
}
