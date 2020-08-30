package com.iod.app.services;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.Objects;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.iod.app.Application;
import com.iod.app.utils.exceptions.ItemAlreadyExistsException;
import com.iod.app.utils.exceptions.ItemNotFoundException;

@SpringBootTest(classes = {Application.class})
@AutoConfigureMockMvc
@RunWith(SpringRunner.class)
public class ConfigurationServiceTest {
	
	@Autowired
	private ConfigurationService configurationService;
	
	@Before
	public void deleteEverythingBefore() {
		configurationService.deleteAll();
	}

	@Test
	public void configurationIsAddedAndRetrieved() throws ItemAlreadyExistsException {
		assertEquals(0, configurationService.findAll().size());
		configurationService.create("foo", "Foo", "value");
		assertEquals(1, configurationService.findAll().size());
		assertTrue(Objects.equals(configurationService.findAll().get("foo").getId(), "foo"));
	}
	
	@Test(expected = ItemAlreadyExistsException.class)
	public void configurationIsNotAddedTwice() throws ItemAlreadyExistsException {
		assertEquals(0, configurationService.findAll().size());
		configurationService.create("foo", "Foo", "value");
		configurationService.create("foo", "Foo", "value");
	}
	
	@Test
	public void allConfigurationsAreRetrieved() throws ItemAlreadyExistsException {
		assertEquals(0, configurationService.findAll().size());
		configurationService.create("foo", "Foo", "value");
		configurationService.create("bar", "Boo", "value");
		assertEquals(2, configurationService.findAll().size());
		assertNotNull(configurationService.findAll().get("foo"));
		assertNotNull(configurationService.findAll().get("bar"));
	}
	
	@Test
	public void savedConfigurationsAreFound() throws ItemAlreadyExistsException {
		assertEquals(0, configurationService.findAll().size());
		configurationService.create("foo", "Foo", "value");
		configurationService.create("bar", "Bar", "value");
		assertEquals(configurationService.find("foo").getName(), "Foo");
		assertEquals(configurationService.find("bar").getName(), "Bar");
	}
	
	@Test
	public void savedConfigurationsAreUpdated() throws ItemAlreadyExistsException, ItemNotFoundException {
		assertEquals(0, configurationService.findAll().size());
		configurationService.create("foo", "Foo", "first");
		assertEquals(configurationService.find("foo").getValue(), "first");
		configurationService.update("foo", "Foo", "second");
		assertEquals(configurationService.find("foo").getValue(), "second");
	}
	
	@Test(expected = ItemNotFoundException.class)
	public void shouldNotUpdateNotExistingConfiguration() throws ItemNotFoundException {
		assertEquals(0, configurationService.findAll().size());
		configurationService.update("foo", "Foo", "second");
	}
	
	@Test
	public void shouldDeleteConfigurations() throws ItemAlreadyExistsException {
		assertEquals(0, configurationService.findAll().size());
		configurationService.create("foo", "Foo", "first");
		assertEquals(1, configurationService.findAll().size());
		configurationService.delete("bar");
		assertEquals(1, configurationService.findAll().size());
		configurationService.delete("foo");
		assertEquals(0, configurationService.findAll().size());
	}
}
