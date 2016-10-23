package org.frangoro.controller;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.mockito.Matchers.isA;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
public class RentalTest {
	
	@Autowired
	 private WebApplicationContext wac;
	
	private MockMvc mockMvc;
	
	@Before 
	public void setUp() {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(this.wac).build();
    }
 
	/**
	 * Rent one game and calculate the price.
	 * 
	 * @throws Exception
	 */
    @Test 
    public void oneGamePrice() throws Exception {
    	
    	
    	
        mockMvc.perform(get("/rental").param("gameId", "1").param("days", "1")
    			.accept(MediaType.parseMediaType("application/json;charset=UTF-8")))
    	.andDo(print())
    	.andExpect(status().isOk())
    	.andExpect(content().contentType("application/json;charset=UTF-8"))
    	.andExpect(jsonPath("$.price").value(9));
    }
    
    /**
     * Rent several games and calculate the price.
     * 
     * @throws Exception
     */
    @Test
    public void multipleGamePrice() throws Exception {
    	
    }
    
    /**
     * Rent one game that not exists.
     * 
     * @throws Exception
     */
    @Test
    public void notExistsGamePrice() throws Exception {
    	
    }

    //TODO: add more tests
}
