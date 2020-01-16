package com.example.restaurant;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import com.example.restaurant.exception.ServiceException;
import com.example.restaurant.model.IncommingParams;
import com.example.restaurant.model.RestaurantData;
import com.example.restaurant.model.RestaurantSchedules;
import com.example.restaurant.util.RestaurantsAvailability;

public class RestaurantsAvailabilityTest {

	private final RestaurantsAvailability restaurantsAvailability;
	private List<RestaurantData> restaurantDataList;
	
	public RestaurantsAvailabilityTest() {
		restaurantsAvailability = new RestaurantsAvailability();
		fillRestaurantData();		
	}
	
	@Test
	public void testOpenedRestaurantsFound() {
		IncommingParams incommingParams =  new IncommingParams();
		incommingParams.setDate(LocalDate.of(2020, 01, 13));
		incommingParams.setTime(LocalTime.of(12, 30, 00));
		
		try {
			List<String> restaurants = restaurantsAvailability.getOpenedRestaurants(incommingParams, restaurantDataList);
		
			Assertions.assertTrue(restaurants.size() == 1);
			Assertions.assertTrue(restaurants.get(0).equalsIgnoreCase("Funny Pizza"));
		}catch(ServiceException e) {
		}
	}
	
	@Test
	public void testOpenedRestaurantsFromPreviousDay() {
		IncommingParams incommingParams =  new IncommingParams();
		incommingParams.setDate(LocalDate.of(2020, 01, 15));
		incommingParams.setTime(LocalTime.of(2, 30, 00));
		
		try {
			List<String> restaurants = restaurantsAvailability.getRemainingOpenedRestaurants(incommingParams, restaurantDataList);
		
			Assertions.assertTrue(restaurants.size() == 1);
		}catch(ServiceException e) {
		}
	}
	
	@Test
	public void testOpenedRestaurantsNotFound() {
		IncommingParams incommingParams =  new IncommingParams();
		incommingParams.setDate(LocalDate.of(2020, 01, 15));
		incommingParams.setTime(LocalTime.of(12, 30, 00));
		
		try {
			List<String> restaurants = restaurantsAvailability.getOpenedRestaurants(incommingParams, restaurantDataList);
		
			Assertions.assertFalse(restaurants.size() == 1);
		}catch(ServiceException e) {
		}
	}
	
	@Test
	public void testOpenedRestaurantsEmptyData() {
		IncommingParams incommingParams =  new IncommingParams();
		incommingParams.setDate(LocalDate.of(2020, 01, 15));
		incommingParams.setTime(LocalTime.of(12, 30, 00));
		
		try {
			List<String> restaurants = restaurantsAvailability.getOpenedRestaurants(incommingParams, new ArrayList<RestaurantData>());
		
			Assertions.assertTrue(restaurants.size() == 0);
		}catch(ServiceException e) {
		}
	}
	
	@Test
	public void testOpenedRestaurantsNoData() {
		String expectedMessage = "No data to process has been found.";
	    
		Exception exception = Assertions.assertThrows(ServiceException.class, () -> {
			restaurantsAvailability.getOpenedRestaurants(null, null);
	    });
		
		String actualMessage = exception.getMessage();
		
		Assertions.assertTrue(actualMessage.contains(expectedMessage));
	}
	
	@Test
	public void testOpenedRestaurantsPartialData() {
		String expectedMessage = "No data to process has been found.";
	    
		Exception exception = Assertions.assertThrows(ServiceException.class, () -> {
			restaurantsAvailability.getOpenedRestaurants(new IncommingParams(), null);
	    });
		
		String actualMessage = exception.getMessage();
		
		Assertions.assertTrue(actualMessage.contains(expectedMessage));
	}
	
	/**
	 * Fills testing data;
	 */
	private void fillRestaurantData() {
		RestaurantData restaurantData = new RestaurantData();
		Map<DayOfWeek, RestaurantSchedules> schedulesMap = new HashMap<DayOfWeek, RestaurantSchedules>();
		RestaurantSchedules restaurantSchedules;
		
		restaurantDataList = new ArrayList<RestaurantData>();
		
		restaurantSchedules = new RestaurantSchedules();
		restaurantSchedules.setOpenTime("10 am");
		restaurantSchedules.setCloseTime("10 pm");
		schedulesMap.put(DayOfWeek.MONDAY, restaurantSchedules);
		
		restaurantSchedules = new RestaurantSchedules();	
		restaurantSchedules.setOpenTime("5 pm");
		restaurantSchedules.setCloseTime("5 am");
		schedulesMap.put(DayOfWeek.TUESDAY, restaurantSchedules);
		
		restaurantData.setName("Funny Pizza");
		restaurantData.setSchedulesMap(schedulesMap);
		
		restaurantDataList.add(restaurantData);
	}
}
