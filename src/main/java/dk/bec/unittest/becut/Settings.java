package dk.bec.unittest.becut;

import java.io.IOException;
import java.io.InputStream;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Properties;

public class Settings {

	private Settings() {
	}

	private static final String PROPERTIES_FILENAME = "becut.properties";
	private static final String RESOURCES_ROOT = "/";
	
	public static String FTP_HOST = "localhost";
	public static String USERNAME = "";
	public static String PASSWORD = "";

	public static String COMPILE_STEP_NAME = "";
	public static String COMPILELIST_DD_NAME= "";

	public static Integer JOB_POLLING_RATE = 2;
	public static List<String> STEPLIB = new ArrayList<String>();

	public static final int OUTPUTSTREAM_BUFFER_INITIAL_CAPACITY = 1000000;

	static {
		Properties properties = new Properties();
		// TODO lookup properties file based on machine name
		String hostname = getHostname();
		InputStream inputStream = Settings.class.getResourceAsStream(RESOURCES_ROOT + hostname + "_" + PROPERTIES_FILENAME);
		if (inputStream == null) {
			inputStream = Settings.class.getResourceAsStream(RESOURCES_ROOT + PROPERTIES_FILENAME);
		}
		if (inputStream != null) {
			try {
				properties.load(inputStream);
				FTP_HOST = properties.getProperty("FTP_HOST", FTP_HOST);
				USERNAME = properties.getProperty("USERNAME", USERNAME);
				if (USERNAME.isEmpty()) {
					USERNAME = System.getProperty("user.name", "");
				}
				PASSWORD = properties.getProperty("PASSWORD", "");
				COMPILE_STEP_NAME = properties.getProperty("COMPILE_STEP_NAME", COMPILE_STEP_NAME);
				COMPILELIST_DD_NAME = properties.getProperty("COMPILELIST_DD_NAME", COMPILELIST_DD_NAME);
				JOB_POLLING_RATE = Integer.parseInt(properties.getProperty("JOB_POLLING_RATE", JOB_POLLING_RATE.toString())); 
				STEPLIB.addAll(Arrays.asList(properties.getProperty("STEPLIB", "").split("\\s*,\\s*")));
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	private static String getHostname() {
		String hostname = "Unknown";

		try {
			InetAddress addr;
			addr = InetAddress.getLocalHost();
			hostname = addr.getHostName();
		} catch (UnknownHostException ex) {
			System.out.println("Hostname can not be resolved");
		}
		return hostname;
	}

}
