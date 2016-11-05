package org.frangoro.controller;

import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;
import java.util.List;

import org.frangoro.dto.InvoiceItemDto;
import org.frangoro.dto.RentInvoiceDto;
import org.frangoro.dto.ReturnInvoiceDto;
import org.frangoro.service.RentalSrv;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

@RunWith(SpringRunner.class)
@WebMvcTest
public class RentalCtrlTest {

	@Autowired
	MockMvc mockMvc;

	@MockBean
	private RentalSrv rentalSrvMock;

	static RentInvoiceDto rentInvoice1, rentInvoice2, rentInvoice3, rentInvoice4;
	static ReturnInvoiceDto returnInvoice;
	static InvoiceItemDto invoiceItem1, invoiceItem2;
	List<RentInvoiceDto> result;
	Long[] gameId;
	Integer[] days;
	static Long userId;


	/**
	 * Initialize common information for all tests at the beginning.
	 */
	@BeforeClass
	public static void setupAll() {

		rentInvoice1 = new RentInvoiceDto();
		rentInvoice1.setDays(1);
		rentInvoice1.setGameName("No Man's Sky");
		rentInvoice1.setPrice(4d);

		rentInvoice2 = new RentInvoiceDto();
		rentInvoice2.setDays(1);
		rentInvoice2.setGameName("Resident Evil 6");
		rentInvoice2.setPrice(4d);

		rentInvoice3 = new RentInvoiceDto();
		rentInvoice3.setDays(1);
		rentInvoice3.setGameName("Fallout 5");
		rentInvoice3.setPrice(4d);

		rentInvoice4 = new RentInvoiceDto();
		rentInvoice4.setDays(1);
		rentInvoice4.setGameName("Fallout 3");
		rentInvoice4.setPrice(4d);

		returnInvoice = new ReturnInvoiceDto();
		returnInvoice.setTotalLateCharge(11d);

		invoiceItem1 = new InvoiceItemDto();
		invoiceItem1.setExtraDays(2);
		invoiceItem1.setGameName("No Man's Sky");
		invoiceItem1.setSurcharge(8d);
		invoiceItem2 = new InvoiceItemDto();
		invoiceItem2.setExtraDays(1);
		invoiceItem2.setGameName("Resident Evil 6");
		invoiceItem2.setSurcharge(3d);

		List<InvoiceItemDto> invoiceItems = new ArrayList<InvoiceItemDto>();
		invoiceItems.add(invoiceItem1);
		invoiceItems.add(invoiceItem2);

		returnInvoice.setInvoiceItems(invoiceItems);

		userId = 1l;
	}

	/**
	 * Restart information for every test.
	 */
	@Before
	public void setup() {
		result = new ArrayList<RentInvoiceDto>();
	}

	/**
	 * Rent one game and calculate the price.
	 * @throws Exception 
	 */
	@Test
	public void rentOneGameSuccess() throws Exception {

		// Input data
		gameId = new Long[] { 1l };
		days = new Integer[] { 1 };

		// Output data
		result.add(rentInvoice1);

		// Given
		given(this.rentalSrvMock.rentGame(gameId, days, userId)).willReturn(result);

		// When
		mockMvc.perform(get("/rental/rent?gameId={gameId}&days={days}&userId={userId}", 
				gameId[0], days[0], userId)
				.contentType(MediaType.APPLICATION_JSON))
				.andDo(print())
		// Then
				.andExpect(status().isOk())
				.andExpect(content().contentType("application/json;charset=UTF-8"))
				.andExpect(jsonPath("$.length()").value(1))
				.andExpect(jsonPath("$[0].days").value(1))
				.andExpect(jsonPath("$[0].gameName").value("No Man's Sky"))
				.andExpect(jsonPath("$[0].price").value(4));

	}
	
	/**
	 * Rent several games and calculate the price.
	 * @throws Exception 
	 */
	@Test
	public void rentMultipleGamesSuccess() throws Exception {
		
		// Input data
		gameId = new Long[]{1l,2l,3l,4l};
		days = new Integer[]{1,5,2,7};
		
		// Output data
		result.add(rentInvoice1);
		result.add(rentInvoice2);
		result.add(rentInvoice3);
		result.add(rentInvoice4);
				
		// Given
		given(this.rentalSrvMock.rentGame(gameId, days, userId)).willReturn(result);

		// When
		mockMvc.perform(get("/rental/rent?gameId={gameId}&days={days}&userId={userId}", 
				gameId, days, userId)
				.contentType(MediaType.APPLICATION_JSON))
				.andDo(print())
		// Then
				.andExpect(status().isOk())
				.andExpect(content().contentType("application/json;charset=UTF-8"))
				.andExpect(jsonPath("$.length()").value(4))
				.andExpect(jsonPath("$[0].days").value(1))
				.andExpect(jsonPath("$[0].gameName").value("No Man's Sky"))
				.andExpect(jsonPath("$[0].price").value(4));
		// Others expectations...

	}

	/**
	 * Rent one game that not exists.
	 * @throws Exception 
	 */
	@Test
	public void rentNotExistsGame() throws Exception {
		
		// Input data
		gameId = new Long[]{0l};
		days = new Integer[]{1};
		
		// Output data
		result = null;
		
		// Given
		given(this.rentalSrvMock.rentGame(gameId, days, userId)).willReturn(result);

		// When
		mockMvc.perform(get("/rental/rent?gameId={gameId}&days={days}&userId={userId}", 
				gameId, days, userId)
				.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isBadRequest());

	}

	/**
	 * Returns a previous rented game.
	 * @throws Exception 
	 */
	@Test
	public void returnGameSuccess() throws Exception {
		
		// Input data
		gameId = new Long[]{1l,2l,3l,4l};
		
		// Given
		given(this.rentalSrvMock.returnGame(gameId, userId)).willReturn(returnInvoice);

		// When
		mockMvc.perform(get("/rental/return?gameId={gameId}&userId={userId}", 
				gameId, userId)
				.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.totalLateCharge").value(11))
				.andExpect(jsonPath("$.invoiceItems.length()").value(2))
				.andExpect(jsonPath("$.invoiceItems[0].gameName").value("No Man's Sky"))
				.andExpect(jsonPath("$.invoiceItems[0].surcharge").value(8))
				.andExpect(jsonPath("$.invoiceItems[0].extraDays").value(2))
				.andExpect(jsonPath("$.invoiceItems[1].extraDays").value(1))
				.andExpect(jsonPath("$.invoiceItems[1].surcharge").value(3))
				.andExpect(jsonPath("$.invoiceItems[1].gameName").value("Resident Evil 6"));

	}


}
