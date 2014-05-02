package com.manta.akismet;

import java.io.IOException;
import java.util.Properties;

public final class Key {

  public static final String AKISMET_API_KEY = "AKISMET_API_KEY";
  public static final String CONFIG_PROPERTIES = "config.properties";
  private static String key = null;

  public static String get() throws IOException {
    if (key == null) {
      key = System.getenv(AKISMET_API_KEY);
      if (key == null || key.length() == 0) {
    	Properties properties = new Properties();
    	properties.load(Thread.currentThread().getContextClassLoader().getResourceAsStream(CONFIG_PROPERTIES));
    	key = properties.getProperty(AKISMET_API_KEY);
    	if (key == null || key.length() == 0) {
    		throw new RuntimeException("akismet api key needed please update config.properties or use environment variable " + AKISMET_API_KEY);
    	}
      }
    }
    return key;
  }

  public static void set (String in) {
    key = in;
  }

}
