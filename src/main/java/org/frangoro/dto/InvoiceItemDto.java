package org.frangoro.dto;

public class InvoiceItemDto {

	private Double surcharge;
	private String gameName;
	private Integer extraDays;

	public String getGameName() {
		return gameName;
	}

	public void setGameName(String gameName) {
		this.gameName = gameName;
	}

	public Double getSurcharge() {
		return surcharge;
	}

	public void setSurcharge(Double surcharge) {
		this.surcharge = surcharge;
	}

	public Integer getExtraDays() {
		return extraDays;
	}

	public void setExtraDays(Integer extraDays) {
		this.extraDays = extraDays;
	}

}
