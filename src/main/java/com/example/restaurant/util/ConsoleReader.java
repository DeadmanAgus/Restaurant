package com.example.restaurant.util;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeParseException;
import java.util.Scanner;

import com.example.restaurant.exception.ServiceException;
import com.example.restaurant.model.IncommingParams;

/**
 * Reads data from console and performs some validations.
 *
 */
public class ConsoleReader {
	
	/**
	 * Reads the input data from the console (date, time and CSV path).
	 * It will validate the Date and Time, file will be validated later on the parser.
	 *  
	 * @return
	 * @throws ServiceException
	 */
	public IncommingParams readFromConsole() throws ServiceException{
		IncommingParams incommingParams = new IncommingParams();
		Scanner in = new Scanner(System.in); 
		
        System.out.print("Enter the date, use format yyyy-MM-dd > "); 
        LocalDate localDate = getDate(in.nextLine());
        incommingParams.setDate(localDate);
        
        System.out.print("Enter the time, use format HH:mm (24 Hr)> ");  
        LocalTime localTime = getTime(in.nextLine());
        incommingParams.setTime(localTime);
        
        System.out.print("Enter the Excel (CSV) path: ");  
        incommingParams.setFilePath(in.nextLine());
        
        in.close();
		
        return incommingParams;
	}
	
	/**
	 * Creates the LocalDate object
	 * 
	 * @param dateString
	 * @return
	 * @throws ServiceException
	 */
	private LocalDate getDate(String dateString) throws ServiceException {
		LocalDate localDate;
		
		try {
			String[] fields = dateString.split("-");
			
			localDate = LocalDate.of(Integer.parseInt(fields[0].trim()), Integer.parseInt(fields[1].trim()), Integer.parseInt(fields[2].trim()));
		}catch(DateTimeParseException e) {
			throw new ServiceException("The date or time entered are incorrect, please start again.");
		}
		
		return localDate;
	}
	
	/**
	 * Creates the LocalTime object
	 * 
	 * @param timeString
	 * @return
	 * @throws ServiceException
	 */
	private LocalTime getTime(String timeString) throws ServiceException {
		LocalTime localTime;
		String time = timeString;
		
		try {
			if (time.indexOf(":") == time.lastIndexOf(":")) {
	        	time += ":00";
	        }
			String[] fields = time.split(":");
			
	        localTime = LocalTime.of(Integer.parseInt(fields[0].trim()), Integer.parseInt(fields[1].trim()), Integer.parseInt(fields[2].trim()));
		}catch(DateTimeParseException e) {
			throw new ServiceException("The date or time entered are incorrect, please start again.");
		}
		
		return localTime;
	}
}
