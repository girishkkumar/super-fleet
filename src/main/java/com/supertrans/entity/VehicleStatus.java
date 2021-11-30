package com.supertrans.entity;

import java.util.EnumSet;
import java.util.HashMap;
import java.util.Map;

public enum VehicleStatus {

	SCHEDULED_FOR_MAINTENANCE("Scheduled for maintenance", 0), UNDERGOING_REPAIRS("Undergoing repairs", 1),
	READY_FOR_USE("Ready for use", 2);

	private final String key;
	private final Integer value;

	private static final Map<String, Integer> keyValueMap = new HashMap<>();

	private static final Map<Integer, String> valueKeyMmap = new HashMap<>();

	static {
		for (VehicleStatus notificationType : EnumSet.allOf(VehicleStatus.class)) {
			keyValueMap.put(notificationType.name(), notificationType.getValue());
			valueKeyMmap.put(notificationType.getValue(), notificationType.name());
		}
	}

	public static int findValueByKey(String key) {
		return keyValueMap.get(key);
	}

	public static String findKeyByValue(Integer value) {
		return valueKeyMmap.get(value);
	}

	VehicleStatus(String key, Integer value) {
		this.key = key;
		this.value = value;
	}

	public String getKey() {
		return key;
	}

	public Integer getValue() {
		return value;
	}

}
