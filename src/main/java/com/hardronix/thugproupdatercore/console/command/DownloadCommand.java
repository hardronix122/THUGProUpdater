package com.hardronix.thugproupdatercore.console.command;

import com.hardronix.thugproupdatercore.console.Console;
import com.hardronix.thugproupdatercore.model.InstallConfig;
import com.hardronix.thugproupdatercore.model.InstallMode;
import com.hardronix.thugproupdatercore.model.Release;
import com.hardronix.thugproupdatercore.model.Version;
import com.hardronix.thugproupdatercore.util.UpdateUtil;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.InvalidPathException;

public class DownloadCommand extends Command {

	public DownloadCommand() {
		super("download", "Download THUG Pro");
	}

	@Override
	public void execute(Console console) {
		Version version = askVersion(console);

		InstallMode installMode = askInstallMode(console);
		File path = askPath(console);

		System.out.printf("Going to download THUG Pro %s [%s]%n%n", version.getVersion(), version.getRelease());

		UpdateUtil.download(new InstallConfig(version, path, installMode));
	}

	private File askPath(Console c) {
		System.out.println("Where do you want to install THUG Pro?");
		System.out.println("Type 'here' or specify full path");
		String input = c.readLine();

		try {
			if(input.equalsIgnoreCase("here") || input.isEmpty()) {
				return new File(new File(".").getCanonicalPath() + "/THUG Pro");
			} else {
				File baseDir = new File(input);

				if(baseDir.exists() && !baseDir.isDirectory()) {
					System.out.println("Not a directory! Please, set another path!");
					askPath(c);
				}

				if(!baseDir.exists()) {
					System.out.println("Directory does not exist, will be created...");

					Files.createDirectories(baseDir.toPath());
				}
				return baseDir;
			}
		} catch (InvalidPathException ex1) {
			System.out.println("Invalid path! Please, set another path!");
		} catch (IOException ex2) {
			System.out.println("Failed to create a directory!");
		}

		return askPath(c);
	}

	private InstallMode askInstallMode(Console c) {
		System.out.println("What install mode do you need?");
		System.out.println("1 - install");
		System.out.println("2 - repair");

		String input = c.readLine();

		if(input.equals("2")) {
			return InstallMode.REPAIR;
		} else {
			return InstallMode.INSTALL;
		}
	}

	private Version askVersion(Console c) {
		Version currentVersion = UpdateUtil.getVersion(Release.CURRENT);
		Version testVersion = UpdateUtil.getVersion(Release.TEST);

		System.out.println("What version do you want?");

		if(currentVersion == null && testVersion == null) {
			System.out.println("Damn it, repo seems to be dead!");
		}

		if(currentVersion != null) {
			System.out.printf("1 - Current [%s | %s] [default]%n", currentVersion.getRelease(), currentVersion.getVersion());
		}

		if(testVersion != null) {
			System.out.printf("2 - TestVer [%s | %s]%n", testVersion.getRelease(), testVersion.getVersion());
		}


		String input = c.readLine();

		if(input.equals("2")) {
			return testVersion;
		} else {
			return currentVersion;
		}
	}
}
