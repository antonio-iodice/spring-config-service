package com.iod.app.controllers;

import java.util.Collections;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.iod.app.model.Configuration;
import com.iod.app.services.ConfigurationService;
import com.iod.app.utils.exceptions.ItemAlreadyExistsException;
import com.iod.app.utils.exceptions.ItemNotFoundException;

@RestController
public class ConfigurationController {
	
	@Autowired
	private ConfigurationService configurationService;
	
	@GetMapping(value = "/")
    public Map<String, Configuration> getAllConfigurations() {
    	return configurationService.findAll();
    }
	
	@GetMapping(value = "/{id}")
    public Configuration getConfiguration(@PathVariable String id) {
    	return configurationService.find(id.toLowerCase());
    }
	
	@PostMapping(value = "/{id}")
    public Configuration createConfiguration(@PathVariable String id, @RequestBody Configuration conf) {
		try {
			return configurationService.create(id.toLowerCase(), conf.getName(), conf.getValue());
		} catch(ItemAlreadyExistsException e) {
			throw new ResponseStatusException(HttpStatus.CONFLICT, "Already exists", e);
		}
    }
	
	@PutMapping(value = "/{id}")
    public Configuration updateConfiguration(@PathVariable String id, @RequestBody Configuration conf) {
    	try {
			return configurationService.update(id.toLowerCase(), conf.getName(), conf.getValue());
		} catch (ItemNotFoundException e) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Item not found", e);
		}
    }
	
	@DeleteMapping(value = "/{id}")
	public Map<String, Integer> deleteConfiguration(@PathVariable String id) {
		configurationService.delete(id.toLowerCase());
		return Collections.singletonMap("result", 200);
	}
}
