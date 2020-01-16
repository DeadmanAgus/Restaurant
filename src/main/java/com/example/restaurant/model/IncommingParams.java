package com.example.restaurant.model;

import java.time.LocalDate;
import java.time.LocalTime;

/**
 * Stores the input form the user.
 *
 */
public class IncommingParams {

	private String filePath;
	private LocalDate date;
	private LocalTime time;
	
	public String getFilePath() {
		return filePath;
	}
	public void setFilePath(String filePath) {
		this.filePath = filePath;
	}
	public LocalDate getDate() {
		return date;
	}
	public void setDate(LocalDate date) {
		this.date = date;
	}
	public LocalTime getTime() {
		return time;
	}
	public void setTime(LocalTime time) {
		this.time = time;
	}
}
