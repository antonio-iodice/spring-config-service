package com.iod.app.controllers;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertEquals;
import static org.hamcrest.CoreMatchers.containsString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.iod.app.Application;
import com.iod.app.model.Configuration;
import com.iod.app.services.ConfigurationService;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;

@SpringBootTest(classes = { Application.class })
@AutoConfigureMockMvc
@RunWith(SpringRunner.class)
public class ConfigurationControllerTest {

	@Autowired
	private MockMvc mvc;

	@Autowired
	private ConfigurationService service;

	@Autowired
	private ObjectMapper objectMapper;

	@Before
	public void before() {
		service.deleteAll();
	}

	@Test
	public void givenConfigs_whenGetAll_thenReturnAll() throws Exception {
		service.create("foo", "Foo", "val0");
		service.create("bar", "Bar", "val1");

		mvc.perform(get("/").contentType(MediaType.APPLICATION_JSON)).andExpect(status().isOk())
				.andExpect(jsonPath("$.*", hasSize(2))).andExpect(content().string(containsString("foo")))
				.andExpect(content().string(containsString("bar")));
	}

	@Test
	public void givenConfig_whenGetConfig_thenReturnCorrectConfig() throws Exception {
		service.create("foo", "Foo", "val0");

		mvc.perform(get("/foo").contentType(MediaType.APPLICATION_JSON)).andExpect(status().isOk())
				.andExpect(jsonPath("$.id", is("foo"))).andExpect(content().string(containsString("val0")))
				.andExpect(content().string(containsString("foo")));
	}

	@Test
	public void givenNoConfig_whenGetConfig_thenReturnCorrectConfig() throws Exception {

		mvc.perform(get("/foo").contentType(MediaType.APPLICATION_JSON)).andExpect(status().isOk())
				.andExpect(jsonPath("$.id", is("foo"))).andExpect(jsonPath("$.name", is("")))
				.andExpect(jsonPath("$.value", is("")));
	}

	@Test
	public void givenConfig_whenUpdate_thenReturnCorrectConfig_andUpdate() throws Exception {
		service.create("foo", "Foo", "val0");
		Configuration c0 = new Configuration("foo", "Foo", "val1");

		mvc.perform(put("/foo").contentType(MediaType.APPLICATION_JSON).content(objectMapper.writeValueAsString(c0)))
				.andExpect(status().isOk()).andExpect(jsonPath("$.id", is("foo")))
				.andExpect(content().string(containsString("val1")));

		assertEquals(service.find("foo").getValue(), "val1");
	}

	@Test
	public void givenNoConfig_whenUpdate_thenShouldThrowException() throws Exception {
		Configuration c0 = new Configuration("foo", "Foo", "val1");
		mvc.perform(put("/foo").contentType(MediaType.APPLICATION_JSON).content(objectMapper.writeValueAsString(c0)))
				.andExpect(status().is(404));
	}

	@Test
	public void givenConfigs_whenDelete_thenShouldDelete() throws Exception {
		service.create("foo", "Foo", "val0");
		assertEquals(1, service.findAll().size());
		mvc.perform(delete("/foo").contentType(MediaType.APPLICATION_JSON)).andExpect(status().isOk());
		assertEquals(0, service.findAll().size());
	}

	@Test
	public void whenPostConf_thenConfIsAdded() throws Exception {
		Configuration c0 = new Configuration("foo", "Foo", "val");
		mvc.perform(post("/point").contentType(MediaType.APPLICATION_JSON).content(objectMapper.writeValueAsString(c0)))
				.andExpect(status().isOk()).andExpect(content().string(containsString("value")))
				.andExpect(content().string(containsString("name"))).andExpect(jsonPath("$.name", is("Foo")))
				.andExpect(jsonPath("$.value", is("val")));
	}

	@Test
	public void whenPostConfTwice_thenConfIsNotAdded() throws Exception {
		Configuration c0 = new Configuration("foo", "Foo", "val");
		mvc.perform(
				post("/point").contentType(MediaType.APPLICATION_JSON).content(objectMapper.writeValueAsString(c0)));
		mvc.perform(post("/point").contentType(MediaType.APPLICATION_JSON).content(objectMapper.writeValueAsString(c0)))
				.andExpect(status().is(409));
	}

}
