package org.frangoro.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.frangoro.config.DatabaseConfigTest;
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
import org.frangoro.service.impl.RentalSrvImpl;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest(classes={DatabaseConfigTest.class, RentalSrvImpl.class})
public class RentalSrvTest {

	@Autowired
	private RentalSrv rentalSrv;
	
	@MockBean
	private RentalDao rentalDao;
	
	@MockBean
	private GameDao gameDao;

	@MockBean
	private UserDao userDao;
	
	static RentInvoiceDto rentInvoice1, rentInvoice2, rentInvoice3, rentInvoice4;
	static ReturnInvoiceDto returnInvoice;
	static InvoiceItemDto invoiceItem1, invoiceItem2;
	List<RentInvoiceDto> result;
	Long[] gameId;
	Integer[] days;
	static Long userId;
	User user;
	Game game;
	Rental rental;

	/**
	 * Initialize common information for all tests at the beginning.
	 */
	@Before
	public void setup() {

		// Service inputs
		userId = 1l;
		gameId = new Long[]{1l};
		days = new Integer[]{1};
		
		// DAO mocks data
		user = new User();
		user.setId(1l);
		user.setPoints(2l);
		
		game = new Game();
		game.setId(1l);
		game.setName("No Man's Sky");
		game.setType(Type.NEW_RELEASE.name());
		
		rental = new Rental();
		rental.setId(1l);
		Calendar cal = Calendar.getInstance();
		cal.setTime(new Date());
		cal.add(Calendar.DATE, -3);
		Date dateBefore3Days = cal.getTime();
		rental.setRentDate(dateBefore3Days);
		rental.setDays(1);
	}
	
	/**
	 * Rent games successfully
	 */
	@Test
	public void rentGameSuccess() {
		
		// Given
		given(userDao.findById(userId)).willReturn(user);
		given(gameDao.findById(gameId[0])).willReturn(game);
		given(rentalDao.save(rental)).willReturn(true);
		
		// When
		List<RentInvoiceDto> dto = rentalSrv.rentGame(gameId, days, userId);
		
		// Then
		assertThat(dto.size()).isEqualTo(1);
		assertThat(dto.get(0).getDays()).isEqualTo(1);
		assertThat(dto.get(0).getGameName()).isEqualTo("No Man's Sky");
		assertThat(dto.get(0).getPrice()).isEqualTo(4);
	}
	
	/**
	 * Returns a previous rented game.
	 */
	@Test
	public void returnGameSuccess() {
		
		// Given
		given(userDao.findById(userId)).willReturn(user);
		given(gameDao.findById(gameId[0])).willReturn(game);
		given(rentalDao.findRentedGameByUser(gameId[0], userId)).willReturn(rental);
		
		// When
		ReturnInvoiceDto dto = rentalSrv.returnGame(gameId, userId);
		
		// Then
		assertThat(dto.getTotalLateCharge()).isEqualTo(8);
		InvoiceItemDto item = dto.getInvoiceItems().get(0);
		assertThat(item.getGameName()).isEqualTo("No Man's Sky");
		assertThat(item.getSurcharge()).isEqualTo(8);
		assertThat(item.getExtraDays()).isEqualTo(2);
	}
}
