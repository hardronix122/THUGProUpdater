package com.hardronix.thugproupdatercore.console.command;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.hardronix.thugproupdatercore.Config;
import com.hardronix.thugproupdatercore.console.Console;
import com.hardronix.thugproupdatercore.model.Update;
import com.hardronix.thugproupdatercore.model.Version;
import com.hardronix.thugproupdatercore.util.UpdateUtil;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import java.io.IOException;

public class FetchCommand extends Command {

	private final ObjectMapper objectMapper;

	public FetchCommand() {
		super("fetch", "Fetches some base info");

		objectMapper = new ObjectMapper();
	}

	@Override
	public void execute(Console console) {

		try {
			CloseableHttpClient httpClient = HttpClients.createDefault();

			Version version = UpdateUtil.getVersion(Config.release);

			if(version != null) {
				HttpGet request = new HttpGet(String.format("%s/update/%s/%s/update.json", Config.baseUrl, version.getRelease(), version.getVersion()));

				CloseableHttpResponse httpResponse = httpClient.execute(request);
				String response = EntityUtils.toString(httpResponse.getEntity());

				Update update = objectMapper.readValue(response, Update.class);

				System.out.println("Release: " + update.getRelease());
				System.out.println("Version: " + update.getVersion());

				for(String updatePath : update.getDataFiles().keySet()) {
					System.out.printf("%s - %s%n", updatePath, update.getDataFiles().get(updatePath).getCrc32Hash());
				}

				httpClient.close();

			} else {
				System.out.println("Can't fetch version!");
			}

		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
