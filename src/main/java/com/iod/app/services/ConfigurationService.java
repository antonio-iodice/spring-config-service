package com.iod.app.services;

import java.util.Map;

import com.iod.app.model.Configuration;
import com.iod.app.utils.exceptions.ItemAlreadyExistsException;
import com.iod.app.utils.exceptions.ItemNotFoundException;

public interface ConfigurationService {

	Configuration find(String id);
	void delete(String id);
	void deleteAll();
	Configuration create(String id, String name, String value) throws ItemAlreadyExistsException;
	Configuration update(String id, String name, String value) throws ItemNotFoundException;
	Map<String, Configuration> findAll();

}
