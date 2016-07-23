package com.example;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.cloud.contract.stubrunner.spring.AutoConfigureStubRunner;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * @author Marcin Grzejszczak
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = ClientApplication.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureStubRunner
public class BeerControllerTest {

	@Autowired WebApplicationContext webApplicationContext;
	ObjectMapper objectMapper = new ObjectMapper();
	MockMvc mockMvc;

	@Before
	public void setup() {
		mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
	}

	@Test public void should_give_me_a_beer_when_im_old_enough() throws Exception {
		mockMvc.perform(MockMvcRequestBuilders.post("/beer")
					.header("Content-Type", "application/json")
					.content(objectMapper.writeValueAsString(new Person("josh", 22))))
				.andExpect(MockMvcResultMatchers.status().isOk())
				.andExpect(MockMvcResultMatchers.content().string("THERE YOU GO"));
	}

	@Test public void should_reject_a_beer_when_im_too_young() throws Exception {
		mockMvc.perform(MockMvcRequestBuilders.post("/beer")
				.header("Content-Type", "application/json")
				.content(objectMapper.writeValueAsString(new Person("josh", 17))))
				.andExpect(MockMvcResultMatchers.status().isOk())
				.andExpect(MockMvcResultMatchers.content().string("GET LOST"));
	}
}