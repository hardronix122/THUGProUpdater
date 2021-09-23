package com.hardronix.thugproupdatercore.model;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Version {

	@JsonProperty("release")
	private String release;

	@JsonProperty("version")
	private String version;

	public Version(String release, String version) {
		this.release = release;
		this.version = version;
	}

	public Version() {
		this.release = "unknown";
		this.version = "unknown";
	}

	public String getRelease() {
		return release;
	}

	public String getVersion() {
		return version;
	}
}
