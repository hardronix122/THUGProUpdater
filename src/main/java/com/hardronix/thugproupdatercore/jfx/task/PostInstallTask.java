package com.hardronix.thugproupdatercore.jfx.task;

import com.hardronix.thugproupdatercore.model.InstallConfig;
import javafx.concurrent.Task;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

public class PostInstallTask extends Task<Object> {

	private final InstallConfig installConfig;

	public PostInstallTask(InstallConfig installConfig) {
		this.installConfig = installConfig;
	}

	@Override
	protected Object call() throws Exception {
		if(this.isCancelled()) {
			updateTitle("Cancelled");
			updateMessage("Post-install cancelled");
			return null;
		}

		updateTitle("Post-Install...");
		updateMessage("Copying binkw32.dll from THUG2 to THUG Pro directory...");

		Files.copy(
				Paths.get(installConfig.getThug2Path().getAbsolutePath() + "/binkw32.dll"),
				Paths.get(installConfig.getPath().getAbsolutePath() + "/binkw32.dll"),
				StandardCopyOption.REPLACE_EXISTING
		);

		return null;
	}
}
