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
		List<File> invalidFiles = new ArrayList<>();

		if(update != null) {
			HashMap<String, UpdateFile> allFiles = new HashMap<>();

			allFiles.putAll(update.getDataFiles());
			allFiles.putAll(update.getOtherFiles());

			for(String filename : allFiles.keySet()) {
				File current = new File(Config.thugProDirectory + "/" + filename);
				String checksum = allFiles.get(filename).getCrc32Hash();

				if(current.exists() && RepairUtil.hasValidChecksum(current, checksum)) {
					updateMessage(String.format("%s - ok!%n", filename));
				} else {
					invalidFiles.add(current);
				}
			}


			if(invalidFiles.size() > 0) {
				updateMessage("Repairing invalid files...");

				for(String filename : allFiles.keySet()) {
					File current = new File(Config.thugProDirectory + "/" + filename);

					if(invalidFiles.contains(current)) {
						updateMessage(String.format("Repairing %s...%n", filename));

						UpdateUtil.downloadFile(Config.baseUrl + allFiles.get(filename).getLink(), current);

						updateProgress(getProgress() + 1, invalidFiles.size());
					}
				}
			}
		}


		return null;
	}
}
