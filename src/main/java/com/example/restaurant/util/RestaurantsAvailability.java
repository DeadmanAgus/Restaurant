package com.example.restaurant.util;

import java.time.DayOfWeek;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

import com.example.restaurant.exception.ServiceException;
import com.example.restaurant.model.IncommingParams;
import com.example.restaurant.model.RestaurantData;
import com.example.restaurant.model.RestaurantSchedules;

/**
 * Utility class to verify the availability of a restaurant.
 *
 */
public class RestaurantsAvailability {

	/**
	 * Retrieves the list of Opened Restaurants per date-time
	 * 
	 * @param incommingParams
	 * @param restaurantDataList
	 * @return
	 * @throws ServiceException 
	 */
	public List<String> getOpenedRestaurants(IncommingParams incommingParams, List<RestaurantData> restaurantDataList) throws ServiceException{
		List<String> openedRestaurants = new ArrayList<String>();
		DayOfWeek dayOfWeek;
		
		if(incommingParams == null || incommingParams.getDate() == null || incommingParams.getTime() == null || restaurantDataList == null) {
			throw new ServiceException("No data to process has been found.");
		}
		
		dayOfWeek = incommingParams.getDate().getDayOfWeek();
		
		for(RestaurantData restaurantData: restaurantDataList) {
			RestaurantSchedules restaurantSchedules = restaurantData.getSchedulesMap().get(dayOfWeek);
			
			if(restaurantSchedules != null) {
				if(isOpened(restaurantSchedules, incommingParams)) {
					openedRestaurants.add(restaurantData.getName());
				}
			}
		}
		
		return openedRestaurants;
	}
	
	/**
	 * Verifies if a restaurant is opened based on the schedule and the data entered by the user.
	 * 
	 * @param schedules
	 * @param incommingParams
	 * @return
	 */
	private boolean isOpened(RestaurantSchedules schedules, IncommingParams incommingParams) {
		boolean targetInSchedule;
		
		targetInSchedule = !incommingParams.getTime().isBefore(getLocalTime(schedules.getOpenTime(), true))  &&  
				!incommingParams.getTime().isAfter(getLocalTime(schedules.getCloseTime(), false)); 
		
		return targetInSchedule;
	}
	
	/**
	 * Transforms times in string to LocalTime based on am/pm times.
	 * 
	 * @param openCloseTime
	 * @param isOpenTime
	 * @return
	 */
	private LocalTime getLocalTime(String openCloseTime, boolean isOpenTime) {
		int hours;
		int minutes = 0;
		int seconds = 0;
		String[] strings = openCloseTime.trim().split(" ");
		
		if(strings[0].indexOf(":") != -1) {
			hours = Integer.parseInt(strings[0].trim().split(":")[0]);
			minutes = Integer.parseInt(strings[0].trim().split(":")[1]);
		}else {
			hours = Integer.parseInt(strings[0]);
		}
		
		if(strings[1].trim().equalsIgnoreCase("pm") && hours != 12) {
			hours += 12;
		}else if(strings[1].trim().equalsIgnoreCase("am")){
			/* If it's an "am" close time and it's after midnight then just cut it since it's already within the current day */
			if(!isOpenTime) { 
				hours = 23;
				minutes = 59;
				seconds = 59;
			}else { /* In case the time is 12am then it must be fixed to 0*/
				if(hours == 12) {
					hours = 0;
				}
			}
		}
		
		return LocalTime.of(hours, minutes, seconds);
	}
	
	/**
	 * Retrieves that list of restaurants that are opened because they remain that way from the day before.
	 * 
	 * @param incommingParams
	 * @param restaurantDataList
	 * @return
	 * @throws ServiceException 
	 */
	public List<String> getRemainingOpenedRestaurants(IncommingParams incommingParams, List<RestaurantData> restaurantDataList) throws ServiceException{
		List<String> openedRestaurants = new ArrayList<String>();
		DayOfWeek dayOfWeek;
		
		if(incommingParams == null || incommingParams.getDate() == null || incommingParams.getTime() == null || restaurantDataList == null) {
			throw new ServiceException("No data to process has been found.");
		}
		
		dayOfWeek = incommingParams.getDate().getDayOfWeek().minus(1);
		
		for(RestaurantData restaurantData: restaurantDataList) {
			RestaurantSchedules restaurantSchedules = restaurantData.getSchedulesMap().get(dayOfWeek);
			
			if(restaurantSchedules != null && restaurantSchedules.getCloseTime().endsWith("am")) {
				if(remainsOpened(restaurantSchedules.getCloseTime(), incommingParams)) {
					openedRestaurants.add(restaurantData.getName());
				}
			}
		}
		
		return openedRestaurants;
	}
	
	/**
	 * Verifies if a restaurant remains opened based on the closing time.
	 * 
	 * @param closeTime
	 * @param incommingParams
	 * @return
	 */
	private boolean remainsOpened(String closeTime, IncommingParams incommingParams) {
		boolean targetInSchedule;
		
		targetInSchedule = !incommingParams.getTime().isBefore(getLocalTime("00:00 am", true))  &&  
				!incommingParams.getTime().isAfter(getLocalTime(closeTime, true)); 
		
		return targetInSchedule;
	}
}
