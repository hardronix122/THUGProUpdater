package com.hardronix.thugproupdatercore.util;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.hardronix.thugproupdatercore.Config;
import com.hardronix.thugproupdatercore.model.*;
import com.hardronix.thugproupdatercore.net.FileDownloadResponseHandler;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import java.io.File;
import java.util.HashMap;

public class UpdateUtil {

	static final ObjectMapper objectMapper = new ObjectMapper();

	public static File downloadFile(String url, File destFile) {
		try {
			CloseableHttpClient httpClient = HttpClients.createDefault();
			HttpGet request = new HttpGet(url);

			File response = httpClient.execute(request, new FileDownloadResponseHandler(destFile));

			httpClient.close();

			return response;
		} catch (Exception e) {
			System.out.println("Failed to download file!");
		}

		return null;
	}

	public static Update fetchUpdate(Release release) {
		try {
			Version version = getVersion(release);

			if(version != null) {
				CloseableHttpClient httpClient = HttpClients.createDefault();
				HttpGet request = new HttpGet(String.format("%s/update/%s/%s/update.json", Config.baseUrl, version.getRelease(), version.getVersion()));

				CloseableHttpResponse httpResponse = httpClient.execute(request);
				String response = EntityUtils.toString(httpResponse.getEntity());

				Update update = objectMapper.readValue(response, Update.class);

				httpClient.close();

				return update;
			}
		} catch (Exception e) {
			System.out.println("Failed to fetch update!");
		}

		return null;
	}

	public static void download(InstallConfig dlConfig) {
		System.out.println("Preparing to download THUG Pro into " + dlConfig.getPath().toString());

		System.out.println("Fetching update data...");

		if(dlConfig.getUpdate() != null) {
			System.out.printf("Fetched, got %s files!%n", dlConfig.getUpdate().getDataFiles().size() + dlConfig.getUpdate().getOtherFiles().size());

			System.out.println("Downloading Data...");

			HashMap<String, UpdateFile> allFiles = new HashMap<>();
			allFiles.putAll(dlConfig.getUpdate().getDataFiles());
			allFiles.putAll(dlConfig.getUpdate().getOtherFiles());

			for(String filename : allFiles.keySet()) {
				System.out.printf("Downloading %s...%n", filename);

				UpdateFile current = allFiles.get(filename);

				File downloaded = UpdateUtil.downloadFile(Config.baseUrl + current.getLink(), new File(dlConfig.getPath() + "/" + filename));

				if(downloaded != null && downloaded.exists()) {
					System.out.println("OK");
				} else {
					System.out.println("FAILED");
				}
			}
		}
	}

	public static Version getVersion(Release release) {

		try {
			CloseableHttpClient httpClient = HttpClients.createDefault();
			HttpGet request;

			if(release == Release.TEST) {
				request = new HttpGet(Config.baseUrl + "/testver.json");
			} else {
				request = new HttpGet(Config.baseUrl + "/current.json");
			}

			return objectMapper.readValue(EntityUtils.toString(httpClient.execute(request).getEntity()), Version.class);
		} catch (Exception e) {
			e.printStackTrace();
		}

		return null;
	}

	public static boolean isValidThug2Directory(File directory) {
		if(directory != null) {
			File binkw32dll = new File(directory.getAbsolutePath() + "/binkw32.dll");
			File thug2exe = new File(directory.getAbsolutePath() + "/THUG2.exe");

			return binkw32dll.exists() && thug2exe.exists();
		}

		return false;
	}
}
