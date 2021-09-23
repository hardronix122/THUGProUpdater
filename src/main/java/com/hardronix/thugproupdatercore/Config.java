package com.hardronix.thugproupdatercore;

import com.hardronix.thugproupdatercore.model.Release;
import org.apache.commons.io.FileUtils;
import org.json.JSONObject;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;

public class Config {

	public static String baseUrl = "http://dl.thugpro.com";
	public static File thugProDirectory = null;
	public static File thug2Directory = null;
	public static Release release = Release.CURRENT;

	private static final String CONFIG_FILE = "updaterconfig.json";

	public static void loadConfig() {
		System.out.println("Loading config...");
		File config = new File(CONFIG_FILE);
		if(config.exists()) {
			try {
				JSONObject configObject = new JSONObject(Files.readString(Paths.get(CONFIG_FILE)));

				baseUrl = configObject.has("base_url") ? configObject.getString("base_url") : "http://dl.thugpro.com";
				thugProDirectory = configObject.has("thug_pro_directory") ? new File(configObject.getString("thug_pro_directory")) : null;
				thug2Directory = configObject.has("thug_2_directory") ? new File(configObject.getString("thug_2_directory")) : null;
				release = configObject.has("release") ? configObject.getEnum(Release.class, "release") : Release.CURRENT;
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	public static void saveConfig() {
		JSONObject configObject = new JSONObject();

		configObject.put("base_url", baseUrl);
		configObject.put("thug_pro_directory", thugProDirectory);
		configObject.put("thug_2_directory", thug2Directory);
		configObject.put("release", release);

		try {
			FileUtils.write(new File(CONFIG_FILE), configObject.toString(), Charset.defaultCharset());
			System.out.println("Saved!");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
