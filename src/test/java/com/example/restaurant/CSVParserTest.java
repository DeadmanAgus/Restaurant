package com.example.restaurant;


import java.time.DayOfWeek;
import java.util.List;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import com.example.restaurant.exception.ServiceException;
import com.example.restaurant.model.RestaurantData;
import com.example.restaurant.parser.CSVParser;

public class CSVParserTest {

	private final CSVParser csvParser;
	
	public CSVParserTest() {
		csvParser = new CSVParser();
	}
	
	@Test
	public void testFileNameValidation() {
		String expectedMessage = "The entered file does not exist.";
	    
		Exception exception = Assertions.assertThrows(ServiceException.class, () -> {
			csvParser.parseFile("notExistentFile");
	    });
		
		String actualMessage = exception.getMessage();
		
		Assertions.assertTrue(actualMessage.contains(expectedMessage));
	}
	
	@Test
	public void testLoadCorrectFirstRecord() {
		List<RestaurantData> data;
		
		try {
			data = csvParser.parseFile(getClass().getResource("file1.csv").getPath());
			
			Assertions.assertTrue(data.size() == 2);
			Assertions.assertTrue(data.get(0).getName().equals("Viva Pizza"));
			Assertions.assertTrue(data.get(0).getSchedulesMap().size() == 7);
			Assertions.assertTrue(data.get(0).getSchedulesMap().get(DayOfWeek.MONDAY).getOpenTime().equals("11:30 am"));
			Assertions.assertTrue(data.get(0).getSchedulesMap().get(DayOfWeek.SUNDAY).getCloseTime().equals("9 pm"));
		}catch(ServiceException e) {
			
		}
	}
	
	@Test
	public void testLoadCorrectSecondRecord() {
		List<RestaurantData> data;
		
		try {
			data = csvParser.parseFile(getClass().getResource("file1.csv").getPath());
			
			Assertions.assertTrue(data.size() == 2);
			Assertions.assertTrue(data.get(1).getName().equals("Teriyakis"));
			Assertions.assertTrue(data.get(1).getSchedulesMap().size() == 7);
			Assertions.assertTrue(data.get(1).getSchedulesMap().get(DayOfWeek.MONDAY).getOpenTime().equals("11 am"));
			Assertions.assertTrue(data.get(1).getSchedulesMap().get(DayOfWeek.SUNDAY).getCloseTime().equals("10:30 pm"));
		}catch(ServiceException e) {
			
		}
	}
}
