package org.frangoro.service;

import java.util.List;

import org.frangoro.dto.RentInvoiceDto;
import org.frangoro.dto.ReturnInvoiceDto;

public interface RentalSrv {

		public List<RentInvoiceDto> rentGame(Long[] gameId, Integer[] days, Long userId);

		public ReturnInvoiceDto returnGame(Long[] gameId, Long userId);
}
