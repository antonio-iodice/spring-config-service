package com.iod.app.services;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.iod.app.model.Configuration;
import com.iod.app.utils.exceptions.ItemAlreadyExistsException;
import com.iod.app.utils.exceptions.ItemNotFoundException;

@Service
public class ConfigurationServiceImpl implements ConfigurationService {
	
	private final static Map<String, Configuration> configurations = new HashMap<>();
	private final static Configuration VOID_CONFIGURATION = new Configuration("", "", "");
	
	@Override
	public final Map<String, Configuration> findAll() {
		return configurations;
	}
	
	@Override
	public Configuration find(String id) {
		return Optional.ofNullable(configurations.get(id)).orElseGet(() -> {
			VOID_CONFIGURATION.setId(id);
			return VOID_CONFIGURATION;
		});
	}
	
	@Override
	public Configuration create(String id, String name, String value) throws ItemAlreadyExistsException {
		Objects.requireNonNull(id);
		Objects.requireNonNull(name);
		if (configurations.containsKey(id)) {
			throw new ItemAlreadyExistsException();
		}
		Configuration conf = new Configuration(id, name, value);
		configurations.put(id, conf);
		return conf;
	}
	
	@Override
	public Configuration update(String id, String name, String value) throws ItemNotFoundException {
		Objects.requireNonNull(id);
		if (!configurations.containsKey(id)) {
			throw new ItemNotFoundException();
		}
		Configuration conf = configurations.get(id);
		conf.setName(Optional.ofNullable(name).orElse(conf.getName()));
		conf.setValue(value);
		return conf;
	}
	
	@Override
	public void delete(String id) {
		Objects.requireNonNull(id);
		configurations.remove(id);
	}
	
	@Override
	public void deleteAll() {
		configurations.clear();
	}

}
