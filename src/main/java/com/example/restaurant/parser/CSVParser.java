package com.example.restaurant.parser;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.time.DayOfWeek;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.example.restaurant.exception.ServiceException;
import com.example.restaurant.model.RestaurantData;
import com.example.restaurant.model.RestaurantSchedules;
import com.opencsv.CSVReader;

/**
 * Utility class to parse a CSV file
 *
 */
public class CSVParser {

	/* Stores the file names as they appear in the CSV file */
	private final Map<String, Integer> daysMap;
	
	public CSVParser() {
		daysMap = new HashMap<String, Integer>();
		
		daysMap.put("Mon", 1);
		daysMap.put("Tue", 2);
		daysMap.put("Wed", 3);
		daysMap.put("Thu", 4);
		daysMap.put("Fri", 5);
		daysMap.put("Sat", 6);
		daysMap.put("Sun", 7);
	}
	
	/**
	 * Parses the CSV file, it will validate that the file exists first.
	 * 
	 * @param csvFile
	 * @return
	 * @throws ServiceException
	 */
	public List<RestaurantData> parseFile(String csvFile) throws ServiceException {
		List<RestaurantData> restaurantData = new ArrayList<RestaurantData>();
		CSVReader reader = null;
        String[] line;
		
        try {
        	File file = new File(csvFile);
        	
        	if(!file.exists() || !file.isFile()) {
        		throw new ServiceException("The entered file does not exist.");
        	}
        	
        	FileReader fileReader = new FileReader(file);
            reader = new CSVReader(fileReader);
            
            while ((line = reader.readNext()) != null) {
            	if(line.length != 2) {
            		throw new ServiceException("There was an issue while parsing the CSV please try with a different file.");
            	}
            	
                restaurantData.add(parseColumns(line));
            }
        } catch (IOException e) {
            throw new ServiceException("An error has occurred while parsing the file.", e);
        }finally {
        	try {
        		if(reader != null) {
        			reader.close();
        		}
			} catch (IOException e) {
			}
        }
        
        return restaurantData;
	}
	
	/**
	 * Fills the Restaurant name and calls the method that will fill days and schedules. 
	 * 
	 * @param lines
	 * @return
	 * @throws ServiceException 
	 */
	private RestaurantData parseColumns(String[] lines) throws ServiceException {
		RestaurantData restaurantData  = new RestaurantData();
		
		restaurantData.setName(lines[0]);
		restaurantData.setSchedulesMap(parseSchedules(lines[1]));
		
		return restaurantData;
	}
	
	/**
	 * Parses the Schedules per Restaurant.
	 * 
	 * @param line
	 * @return
	 * @throws ServiceException 
	 */
	private Map<DayOfWeek, RestaurantSchedules> parseSchedules(String line) throws ServiceException{
		Matcher matcher;
		Pattern pattern = Pattern.compile("\\d+");
		Map<DayOfWeek, RestaurantSchedules> restaurantSchedules = new HashMap<DayOfWeek, RestaurantSchedules>();
		
		String[] data = line.split("/");
		
		for(String schedule: data) {
		    matcher = pattern.matcher(schedule);
		    
		    if(matcher.find()){
		    	String[] schedules = schedule.subSequence(matcher.start(), schedule.length()).toString().trim().split("-");
		    	
		    	if(schedules.length != 2 || schedules[0] == null || schedules[1] == null) {
		    		throw new ServiceException("There was an issue while parsing the CSV please try with a different file.");
		    	}
		    	
		    	extractDays((String) schedule.subSequence(0, matcher.start()), schedules, restaurantSchedules);
		    }else {
		    	throw new ServiceException("There was an issue while parsing the CSV please try with a different file.");
		    }
		}
		
		return restaurantSchedules;
	}
	
	/**
	 * Extracts the days fills the Map with the schedules per day.
	 * 
	 * @param scheduleDays
	 * @param schedules
	 * @param restaurantSchedulesMap
	 * @throws ServiceException 
	 */
	private void extractDays(String scheduleDays, String[] schedules, Map<DayOfWeek, RestaurantSchedules> restaurantSchedulesMap) throws ServiceException{
		RestaurantSchedules restaurantSchedules;
		String[] days = scheduleDays.trim().split(",");
		
		for(String day: days) {
			if(day.indexOf("-") != -1) {
				String initialDay = day.substring(0, day.indexOf("-"));
				String endDay = day.substring(day.indexOf("-") + 1, day.length());
				
				if(initialDay == null || initialDay.isEmpty() || endDay == null || endDay.isEmpty() 
						|| daysMap.get(initialDay.trim()) == null || daysMap.get(endDay.trim()) == null) {
					throw new ServiceException("There was an issue while parsing the CSV please try with a different file.");
				}
				
				for(int i = daysMap.get(initialDay.trim()); i <= daysMap.get(endDay.trim()); i++) {
					restaurantSchedules = new RestaurantSchedules();
					restaurantSchedules.setOpenTime(schedules[0].trim());
					restaurantSchedules.setCloseTime(schedules[1].trim());
					
					restaurantSchedulesMap.put(DayOfWeek.of(i), restaurantSchedules);	
				}
			}else {
				restaurantSchedules = new RestaurantSchedules();
				restaurantSchedules.setOpenTime(schedules[0].trim());
				restaurantSchedules.setCloseTime(schedules[1].trim());
				
				restaurantSchedulesMap.put(DayOfWeek.of(daysMap.get(day.trim())), restaurantSchedules);
			}
		}
	}
}
