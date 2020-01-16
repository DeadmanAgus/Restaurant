package com.example.restaurant.model;

/**
 * Stores the open and close time
 *
 */
public class RestaurantSchedules {

	private String openTime;
	private String closeTime;
	
	public String getOpenTime() {
		return openTime;
	}
	public void setOpenTime(String openTime) {
		this.openTime = openTime;
	}
	public String getCloseTime() {
		return closeTime;
	}
	public void setCloseTime(String closeTime) {
		this.closeTime = closeTime;
	}
}
