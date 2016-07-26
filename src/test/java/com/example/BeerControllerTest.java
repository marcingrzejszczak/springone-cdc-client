package com.example;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.cloud.contract.stubrunner.spring.AutoConfigureStubRunner;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * @author Marcin Grzejszczak
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(webEnvironment = WebEnvironment.NONE)
@AutoConfigureStubRunner
public class BeerControllerTest {

	TestRestTemplate template = new TestRestTemplate();
	MockMvc mockMvc;

	@Before
	public void setup() {
		mockMvc = MockMvcBuilders.standaloneSetup(new BeerController(template.getRestTemplate())).build();
	}

	@Test public void should_give_me_a_beer_when_im_old_enough() throws Exception {
		mockMvc.perform(MockMvcRequestBuilders.post("/beer")
					.contentType(MediaType.APPLICATION_JSON)
					.content(asJson(new Person("marcin", 22))))
				.andExpect(MockMvcResultMatchers.status().isOk())
				.andExpect(MockMvcResultMatchers.content().string("THERE YOU GO"));
	}

	@Test public void should_reject_a_beer_when_im_too_young() throws Exception {
		mockMvc.perform(MockMvcRequestBuilders.post("/beer")
					.contentType(MediaType.APPLICATION_JSON)
					.content(asJson(new Person("marcin", 17))))
				.andExpect(MockMvcResultMatchers.status().isOk())
				.andExpect(MockMvcResultMatchers.content().string("GET LOST"));
	}

	private String asJson(Person person) {
		try {
			return new ObjectMapper().writeValueAsString(person);
		} catch (Exception e) {
			throw new IllegalStateException(e);
		}
	}
}


/*



	@Test public void should_give_me_a_beer_when_im_old_enough() throws Exception {
		mockMvc.perform(MockMvcRequestBuilders.post("/beer")
					.contentType(MediaType.APPLICATION_JSON)
					.content(asJson(new Person("marcin", 22))))
				.andExpect(status().isOk())
				.andExpect(content().string("THERE YOU GO"));
	}

	@Test public void should_reject_a_beer_when_im_too_young() throws Exception {
		mockMvc.perform(MockMvcRequestBuilders.post("/beer")
					.contentType(MediaType.APPLICATION_JSON)
					.content(asJson(new Person("marcin", 17))))
				.andExpect(status().isOk())
				.andExpect(content().string("GET LOST"));
	}

 */

