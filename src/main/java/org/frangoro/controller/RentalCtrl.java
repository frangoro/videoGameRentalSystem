package org.frangoro.controller;

import java.util.List;

import org.frangoro.dto.RentInvoiceDto;
import org.frangoro.dto.ReturnInvoiceDto;
import org.frangoro.service.RentalSrv;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/rental")
public class RentalCtrl {

	@Autowired
	RentalSrv rentalSrv;

	@RequestMapping(value="/rent", method = RequestMethod.GET)
	@ResponseBody
	public ResponseEntity<List<RentInvoiceDto>> rentGame(@RequestParam Long[] gameId, @RequestParam Integer[] days,
			@RequestParam Long userId) {
		
		// Check params number
		if (gameId.length != days.length) {
			return new ResponseEntity<List<RentInvoiceDto>>(HttpStatus.BAD_REQUEST);
		}
		
		List<RentInvoiceDto> dto = rentalSrv.rentGame(gameId, days, userId);
		
		// rentGame exception
		if (dto == null) {
			return new ResponseEntity<List<RentInvoiceDto>>(HttpStatus.BAD_REQUEST);
		}
		
		return new ResponseEntity<List<RentInvoiceDto>>(dto, HttpStatus.OK);
	}

	@RequestMapping(value="/return", method = RequestMethod.GET)
	@ResponseBody
	public ResponseEntity<ReturnInvoiceDto> returnGame(@RequestParam Long[] gameId, @RequestParam Long userId) {

		ReturnInvoiceDto dto = rentalSrv.returnGame(gameId, userId);
		
		// rentGame exception
		if (dto == null) {
			return new ResponseEntity<ReturnInvoiceDto>(HttpStatus.BAD_REQUEST);
		}
		
		return new ResponseEntity<ReturnInvoiceDto>(dto, HttpStatus.OK);
	}

}
