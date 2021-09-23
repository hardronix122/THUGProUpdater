package com.hardronix.thugproupdatercore.model;

import java.io.File;

public class InstallConfig {

	final Version version;
	private final File path;
	private final File thug2Path;
	private final InstallMode downloadMode;
	private final Release release;

	public InstallConfig(Version version, File path, File thug2path, InstallMode downloadMode, Release release) {
		this.version = version;
		this.path = path;
		this.thug2Path = thug2path;
		this.downloadMode = downloadMode;
		this.release = release;
	}

	public InstallConfig(Version version, File path, File thug2path, InstallMode downloadMode) {
		this.version = version;
		this.path = path;
		this.thug2Path = thug2path;
		this.downloadMode = downloadMode;
		this.release = Release.CURRENT;
	}

	public InstallConfig(Version version, File path, InstallMode downloadMode) {
		this.version = version;
		this.path = path;
		this.thug2Path = null;
		this.downloadMode = downloadMode;
		this.release = Release.CURRENT;
	}

	public Version getVersion() {
		return version;
	}

	public File getPath() {
		return path;
	}

	public File getThug2Path() {
		return thug2Path;
	}

	public Release getRelease() {
		return release;
	}

	public InstallMode getDownloadMode() {
		return downloadMode;
	}
}
