package org.frangoro.dto;

import java.io.Serializable;

public class RentInvoiceDto implements Serializable {

	/**
	 * Serial version
	 */
	private static final long serialVersionUID = 1L;
	
	private Double price;
	private String gameName;
	private Integer days;
	
	public Double getPrice() {
		return price;
	}

	public void setPrice(Double price) {
		this.price = price;
	}

	public String getGameName() {
		return gameName;
	}

	public void setGameName(String gameName) {
		this.gameName = gameName;
	}

	public Integer getDays() {
		return days;
	}

	public void setDays(Integer days) {
		this.days = days;
	}

}
