package com.hardronix.thugproupdatercore.jfx.task;

import com.hardronix.thugproupdatercore.Config;
import com.hardronix.thugproupdatercore.model.InstallConfig;
import com.hardronix.thugproupdatercore.model.Update;
import com.hardronix.thugproupdatercore.model.UpdateFile;
import com.hardronix.thugproupdatercore.util.UpdateUtil;
import javafx.concurrent.Task;

import java.io.File;
import java.util.HashMap;

public class DownloadTask extends Task<Object> {

	private final InstallConfig installConfig;
	private final Update update;

	public DownloadTask(InstallConfig installConfig, Update update) {
		this.installConfig = installConfig;
		this.update = update;
	}

	@Override
	protected Object call() {
		updateMessage("Preparing to download THUG Pro into " + installConfig.getPath().toString());

		if(update != null) {
			updateTitle("Downloading data...");


			HashMap<String, UpdateFile> allFiles = new HashMap<>();
			allFiles.putAll(update.getDataFiles());
			allFiles.putAll(update.getOtherFiles());

			long fileCount = 0;

			for(String filename : allFiles.keySet()) {
				if(this.isCancelled()) {
					System.out.println("Cancelled!!!!");
					updateTitle("Cancelled");
					updateMessage("Download cancelled");
					break;
				}

				updateMessage(filename);

				System.out.printf("Downloading %s...%n", filename);

				UpdateFile current = allFiles.get(filename);

				File downloaded = UpdateUtil.downloadFile(Config.baseUrl + current.getLink(), new File(installConfig.getPath() + "/" + filename));

				if(downloaded != null && downloaded.exists()) {

					fileCount++;

					updateMessage(String.format("%s - Ok!%n", filename));
					updateProgress(fileCount, allFiles.size());
				} else {
					updateMessage(String.format("%s - Failed!%n", filename));
				}
			}
		}

		return null;
	}
}
