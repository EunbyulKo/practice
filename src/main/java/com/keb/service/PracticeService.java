package com.keb.service;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.Properties;

import javax.annotation.PostConstruct;

import org.springframework.stereotype.Service;

@Service
public class PracticeService {
	private static Properties keys;

	@PostConstruct
	public void init() throws Exception {
		keys = loadProperties("files/keys.properties");
	}

	private Properties loadProperties(String pathname) throws Exception {
		Properties properties = new Properties();
		properties.load(new BufferedReader(new InputStreamReader(new FileInputStream(pathname), "UTF-8"), 8 * 1024));
		return properties;
	}
	
	public static Properties getKeys() {
		return keys;
	}
}
