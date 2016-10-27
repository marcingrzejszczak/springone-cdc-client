package com.example;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.json.AutoConfigureJsonTesters;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.cloud.contract.stubrunner.spring.AutoConfigureStubRunner;
import org.springframework.cloud.contract.wiremock.AutoConfigureWireMock;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

/**
 * @author Marcin Grzejszczak
 */
@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
@AutoConfigureJsonTesters
@AutoConfigureWireMock
@AutoConfigureStubRunner(workOffline = true,
		ids = "com.example:beer-api-producer:+:stubs:8090")
public class BeerControllerTest extends AbstractTest {

	@Autowired  MockMvc mockMvc;

	@Test public void should_give_me_a_beer_when_im_old_enough() throws Exception {
		mockMvc.perform(MockMvcRequestBuilders.post("/beer")
				.contentType(MediaType.APPLICATION_JSON)
				.content(json.write(new Person("marcin", 22)).getJson()))
				.andExpect(MockMvcResultMatchers.status().isOk())
				.andExpect(MockMvcResultMatchers.content().string("THERE YOU GO"));
	}

	@Test public void should_reject_a_beer_when_im_too_young() throws Exception {
		mockMvc.perform(MockMvcRequestBuilders.post("/beer")
				.contentType(MediaType.APPLICATION_JSON)
				.content(json.write(new Person("marcin", 17)).getJson()))
				.andExpect(MockMvcResultMatchers.status().isOk())
				.andExpect(MockMvcResultMatchers.content().string("GET LOST"));
	}
}

/*



	@Test public void should_give_me_a_beer_when_im_old_enough() throws Exception {
		mockMvc.perform(MockMvcRequestBuilders.post("/beer")
					.contentType(MediaType.APPLICATION_JSON)
					.content(json.write(new Person("marcin", 22)).getJson()))
				.andExpect(MockMvcResultMatchers.status().isOk())
				.andExpect(MockMvcResultMatchers.content().string("THERE YOU GO"));
	}

	@Test public void should_reject_a_beer_when_im_too_young() throws Exception {
		mockMvc.perform(MockMvcRequestBuilders.post("/beer")
					.contentType(MediaType.APPLICATION_JSON)
					.content(json.write(new Person("marcin", 17)).getJson()))
				.andExpect(MockMvcResultMatchers.status().isOk())
				.andExpect(MockMvcResultMatchers.content().string("GET LOST"));
	}

 */

