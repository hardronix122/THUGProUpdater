package com.hardronix.thugproupdatercore.console.command;

import com.hardronix.thugproupdatercore.Config;
import com.hardronix.thugproupdatercore.console.Console;
import com.hardronix.thugproupdatercore.model.*;
import com.hardronix.thugproupdatercore.util.UpdateUtil;

public class DownloadCommand extends Command {

	public DownloadCommand() {
		super("download", "Download THUG Pro");
	}

	@Override
	public void execute(Console console) {
		Version version = askVersion(console);

		InstallMode installMode = askInstallMode(console);

		System.out.printf("Going to download THUG Pro %s [%s]%n%n", version.getVersion(), version.getRelease());

		Update update = UpdateUtil.fetchUpdate(Config.release);

		if(update != null) {
			UpdateUtil.download(new InstallConfig(
					update,
					Config.thugProDirectory,
					Config.thug2Directory,
					installMode,
					Config.release));
		} else {
			System.out.println("Damn it, server seems to be dead!");
		}
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
			System.out.println("Damn it, server seems to be dead!");
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
