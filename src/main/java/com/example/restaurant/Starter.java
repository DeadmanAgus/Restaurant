package com.example.restaurant;

import java.util.List;

import com.example.restaurant.exception.ServiceException;
import com.example.restaurant.model.IncommingParams;
import com.example.restaurant.model.RestaurantData;
import com.example.restaurant.parser.CSVParser;
import com.example.restaurant.util.ConsoleReader;
import com.example.restaurant.util.RestaurantsAvailability;

/**
 * Starter (main) Class
 */
public class Starter{
	
	/**
	 * Starts all the process and prints the results.
	 * 
	 * @param args
	 */
    public static void main(String[] args){
    	try {
    		/* Read inputs from console. */
	    	ConsoleReader consoleReader = new ConsoleReader();
	    	IncommingParams incommingParams = consoleReader.readFromConsole();
	    	
	    	/* Load and parse the CSV file. */
	    	CSVParser parser = new CSVParser();
	    	List<RestaurantData> restaurantData = parser.parseFile(incommingParams.getFilePath());
	    	
	    	/* Get the restaurants availability */
	    	RestaurantsAvailability restaurantsAvailability = new RestaurantsAvailability();
	    	List<String> openedRestaurants = restaurantsAvailability.getOpenedRestaurants(incommingParams, restaurantData);
	    	
	    	/* Print the results */
	    	System.out.println("\nList of Opened Restaurants for the date:\n");
	    	openedRestaurants.forEach(System.out::println);
	    	
	    	/* Also print those restaurants that are opened because they remained that way form the day before.*/
	    	System.out.println("\nList of Restaurants that remain opened from the day before:\n");
	    	openedRestaurants = restaurantsAvailability.getRemainingOpenedRestaurants(incommingParams, restaurantData);
	    	openedRestaurants.forEach(System.out::println);
    	}catch(ServiceException e) {
	    	System.out.println("\nThe following error has happened: \n" + e.getMessage());
    	}catch(Exception e) {
	    	e.printStackTrace();
	    }
    }
}
