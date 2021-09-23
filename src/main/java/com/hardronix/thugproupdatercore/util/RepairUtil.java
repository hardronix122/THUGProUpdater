package com.hardronix.thugproupdatercore.util;

import com.hardronix.thugproupdatercore.Config;
import com.hardronix.thugproupdatercore.model.Update;
import com.hardronix.thugproupdatercore.model.UpdateFile;
import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.zip.CRC32;
import java.util.zip.Checksum;

public class RepairUtil {

	public static boolean hasValidChecksum(File current, String checksum) {
		try {
			Checksum fChecksum = FileUtils.checksum(current, new CRC32());

			return String.format("%08X", fChecksum.getValue()).equalsIgnoreCase(checksum);

		} catch (IOException e) {
			System.out.println("Failed to compare!");
		}

		return true;
	}

	public static String getFileChecksum(File file) {
		try {
			Checksum fChecksum = FileUtils.checksum(file, new CRC32());

			return String.format("%08X", fChecksum.getValue());
		} catch (IOException e) {
			System.out.println("Failed to compare!");
		}

		return null;
	}

	public static void repairFiles(File directory, List<File> invalidFiles) {
		System.out.println("Invalid files: " + invalidFiles.size());

		if(invalidFiles.size() > 0) {
			Update update = UpdateUtil.fetchUpdate(Config.release);

			if(update != null) {
				HashMap<String, UpdateFile> allFiles = new HashMap<>();

				allFiles.putAll(update.getDataFiles());
				allFiles.putAll(update.getOtherFiles());

				for(String filename : allFiles.keySet()) {
					File current = new File(directory.getAbsolutePath() + "/" + filename);

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
