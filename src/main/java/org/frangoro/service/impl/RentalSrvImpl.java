package org.frangoro.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.frangoro.dao.GameDao;
import org.frangoro.dao.RentalDao;
import org.frangoro.dao.UserDao;
import org.frangoro.domain.Game;
import org.frangoro.domain.Rental;
import org.frangoro.domain.User;
import org.frangoro.dto.InvoiceItemDto;
import org.frangoro.dto.RentInvoiceDto;
import org.frangoro.dto.ReturnInvoiceDto;
import org.frangoro.enums.TypeOfGame.Type;
import org.frangoro.service.RentalSrv;
import org.frangoro.util.Constants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RentalSrvImpl implements RentalSrv {

	@Autowired
	private RentalDao rentalDao;
	
	@Autowired
	private GameDao gameDao;

	@Autowired
	private UserDao userDao;
	
	@Override
	public List<RentInvoiceDto> rentGame(Long[] gameId, Integer[] days, Long userId) {
		
		RentInvoiceDto invoice;
		List<RentInvoiceDto> invoices = new ArrayList<RentInvoiceDto>();
		Game game;
		User user = userDao.findById(userId);
		Rental rental;
		Double price;
		
		
		// Para cada gameId
		for (int i = 0; i < gameId.length; i++) {
			
			// Obtener el precio del game (tipo * days)
			// Get game
			game = gameDao.findById(gameId[i]);
			// If not exists TODO Exception handling
			if (game == null || user == null) {
				return null;
			}
			if (Type.NEW_RELEASE.name().equals(game.getType())) {
				price = Constants.PREMIUM_PRICE * days[i];
			} else if (Type.STANDARD.name().equals(game.getType())) {
				if (Constants.STANDARD_GAME_DAYS >= days[i]) {
					price = Constants.BASIC_PRICE;
				} else {
					price = Constants.BASIC_PRICE * (days[i] - Constants.STANDARD_GAME_DAYS + 1);
				}
			} else {
				if (Constants.CLASSIC_GAME_DAYS >= days[i]) {
					price = Constants.BASIC_PRICE;
				} else {
					price = Constants.BASIC_PRICE * (days[i] - Constants.CLASSIC_GAME_DAYS + 1);
				}
			}
			rental = new Rental();
			rental.setPrice(price);
			
			// Set rent attributes
			rental.setGame(game);
			rental.setUser(user);
			rental.setDays(days[i]);
			rental.setRentDate(new Date());
			
			// Crear registro en rent
			rentalDao.save(rental);
			
			// Create rent invoice
			invoice = new RentInvoiceDto();
			invoice.setPrice(price);
			invoice.setGameName(game.getName());
			//TODO game type in natural lenguage
			invoice.setDays(days[i]);
			invoices.add(invoice);
			//TODO se puede usar patron strategy para tener varios tipos de invoice
		}
		
		return invoices;
	}
	
	@Override
	public ReturnInvoiceDto returnGame(Long[] gameId, Long userId) {
		
		Rental rental;
		Game game;
		Integer extraDays;
		Long extraDaysDiff;
		Double surcharge = new Double(0);
		Double totalLateCharge = new Double(0);
		User user;
		Long points = new Long(0);
		ReturnInvoiceDto invoice = new ReturnInvoiceDto();
		InvoiceItemDto invoiceItem;
		List<InvoiceItemDto> invoiceItems = new ArrayList<InvoiceItemDto>();
		
		for (int i = 0; i < gameId.length; i++) {
		
			// Get rental
			rental = rentalDao.findRentedGameByUser(gameId[i], userId);
			// Get extra days
			extraDaysDiff = (new Date()).getTime() - rental.getRentDate().getTime();
			extraDays = (int)(long)TimeUnit.DAYS.convert(extraDaysDiff, TimeUnit.MILLISECONDS);
			extraDays = extraDays - rental.getDays();
			
			// Get game
			game = gameDao.findById(gameId[i]);
			
			// Get surcharge
			if (extraDays > 0) {
				if (Type.NEW_RELEASE.name().equals(game.getType())) {
					surcharge = Constants.PREMIUM_PRICE * extraDays;
				} else {
					surcharge = Constants.BASIC_PRICE * extraDays;
				}
				totalLateCharge = totalLateCharge + surcharge;
			} else {
				// No extra days if it was return before return date
				extraDays = 0;
			}
			
			// Get loyality points
			if (Type.NEW_RELEASE.name().equals(game.getType())) {
				points = points + Constants.NEW_RELEASE_GAME_POINTS;
			} else {
				points = points + Constants.OTHER_GAME_POINTS;
			}
		
			// Mark rentals as returned by returnDate
			rental.setReturnDate(new Date());
			
			// Set extra days and surcharge
			rental.setExtraDays(extraDays);
			rental.setSurcharge(surcharge);
			
			rentalDao.update(rental);
			
			// Add item to return invoice
			invoiceItem = new InvoiceItemDto();
			invoiceItem.setExtraDays(extraDays);
			invoiceItem.setGameName(game.getName());
			invoiceItem.setSurcharge(surcharge);
			invoiceItems.add(invoiceItem);
		
		}
		
		// Add loyality points 
		user = userDao.findById(userId);
		points = points + user.getPoints();
		user.setPoints(points);
		userDao.update(user);
		
		// Complete return invoice
		invoice.setTotalLateCharge(totalLateCharge);
		invoice.setInvoiceItems(invoiceItems);
		
		return invoice;
	}

}
