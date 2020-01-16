package com.example.restaurant.model;

import java.time.DayOfWeek;
import java.util.Map;

/**
 * Stores the Restaurant Name and schedules per day. 
 *
 */
public class RestaurantData {

	private String name;
	private Map<DayOfWeek, RestaurantSchedules> schedulesMap;
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Map<DayOfWeek, RestaurantSchedules> getSchedulesMap() {
		return schedulesMap;
	}
	public void setSchedulesMap(Map<DayOfWeek, RestaurantSchedules> schedulesMap) {
		this.schedulesMap = schedulesMap;
	}
}
