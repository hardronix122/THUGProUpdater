package com.hardronix.thugproupdatercore.jfx.task;

import com.hardronix.thugproupdatercore.Config;
import com.hardronix.thugproupdatercore.model.Update;
import com.hardronix.thugproupdatercore.model.UpdateFile;
import com.hardronix.thugproupdatercore.util.RepairUtil;
import com.hardronix.thugproupdatercore.util.UpdateUtil;
import javafx.concurrent.Task;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class RepairTask extends Task<Object> {

	private final Update update;

	public RepairTask(Update update) {
		this.update = update;
	}

	@Override
	protected Object call() {
		List<String> invalidFiles = new ArrayList<>();

		if(update != null) {
			updateTitle("Checking files");

			HashMap<String, UpdateFile> allFiles = new HashMap<>();

			allFiles.putAll(update.getDataFiles());
			allFiles.putAll(update.getOtherFiles());

			for(String filename : allFiles.keySet()) {
				if(this.isCancelled()) {
					updateTitle("Cancelled");
					updateMessage("Repair cancelled");
					return null;
				}

				File current = new File(Config.thugProDirectory + "/" + filename);
				String checksum = allFiles.get(filename).getCrc32Hash();

				if(current.exists() && RepairUtil.hasValidChecksum(current, checksum)) {
					updateMessage(String.format("%s - ok!%n", filename));
				} else {
					updateMessage(String.format("%s - bad!%n", filename));
					invalidFiles.add(filename);
				}
			}

			if(invalidFiles.size() > 1) {
				updateTitle("Repairing");

				for(int i = 0; i < invalidFiles.size(); i++) {
					String filename = invalidFiles.get(i);

					if(this.isCancelled()) {
						updateTitle("Cancelled");
						updateMessage("Repair cancelled");
						return null;
					}

					File current = new File(Config.thugProDirectory + "/" + filename);

					updateMessage(String.format("Repairing %s!%n", filename));
					updateProgress(i, invalidFiles.size());
					System.out.println(allFiles.get(filename).getLink());
					UpdateUtil.downloadFile(Config.baseUrl + allFiles.get(filename).getLink(), current);
				}
			}
		}

		return null;
	}
}
