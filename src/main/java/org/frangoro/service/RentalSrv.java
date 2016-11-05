package org.frangoro.service;

import java.util.List;

import org.frangoro.dto.RentInvoiceDto;
import org.frangoro.dto.ReturnInvoiceDto;

public interface RentalSrv {

	/**
	 * Rent games.
	 * 
	 * @param gameId
	 * @param days
	 * @param userId
	 * @return
	 */
	public List<RentInvoiceDto> rentGame(Long[] gameId, Integer[] days, Long userId);

	/**
	 * Return games.
	 * 
	 * @param gameId
	 * @param userId
	 * @return
	 */
	public ReturnInvoiceDto returnGame(Long[] gameId, Long userId);
}
