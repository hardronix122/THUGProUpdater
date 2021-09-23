package com.hardronix.thugproupdatercore.console.command;

import com.hardronix.thugproupdatercore.Config;
import com.hardronix.thugproupdatercore.console.Console;
import com.hardronix.thugproupdatercore.model.Update;
import com.hardronix.thugproupdatercore.model.UpdateFile;
import com.hardronix.thugproupdatercore.util.RepairUtil;
import com.hardronix.thugproupdatercore.util.UpdateUtil;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class RepairCommand extends Command {
	public RepairCommand() {
		super("repair", "Verifies integrity of game files and repairs them");
	}

	@Override
	public void execute(Console console) {

		System.out.println("Enter full path to THUG Pro or type 'here'");
		String path = console.readLine();

		if(path.equalsIgnoreCase("here") || path.isEmpty()) {
			try {
				path = new File(".").getCanonicalPath() + "/THUG Pro";
			} catch (Exception e) {
				System.out.println("Failed to get path of updater!");
			}
		}

		List<File> invalidFiles = new ArrayList<>();

		Update update = UpdateUtil.fetchUpdate(Config.release);

		if(update != null) {
			HashMap<String, UpdateFile> allFiles = new HashMap<>();

			allFiles.putAll(update.getDataFiles());
			allFiles.putAll(update.getOtherFiles());

			for(String filename : allFiles.keySet()) {
				File current = new File(path + "/" + filename);
				String checksum = allFiles.get(filename).getCrc32Hash();

				if(current.exists() && RepairUtil.hasValidChecksum(current, checksum)) {
					System.out.printf("%s - ok!%n", filename);
				} else {
					invalidFiles.add(current);
				}
			}

			System.out.println("Invalid files: " + invalidFiles.size());

			if(invalidFiles.size() > 0) {
				System.out.println("Do you want to repair them? [y/N]");

				String input = console.readLine();

				if(input.equalsIgnoreCase("y")) {
					for(String filename : allFiles.keySet()) {
						File current = new File(path + "/" + filename);

						if(invalidFiles.contains(current)) {
							System.out.printf("Repairing %s...%n", filename);
							System.out.println(allFiles.get(filename).getLink());
							UpdateUtil.downloadFile(Config.baseUrl + allFiles.get(filename).getLink(), current);
						}
					}
				}
			}
		}
	}
}
