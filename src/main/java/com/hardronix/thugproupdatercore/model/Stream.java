package com.hardronix.thugproupdatercore.model;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Stream {

	@JsonProperty("manifest")
	private String manifest;

	@JsonProperty("version")
	private String version;

	@JsonProperty("release")
	private String release;

	public Stream(String manifest, String version, String release) {
		this.manifest = manifest;
		this.version = version;
		this.release = release;
	}

	public Stream() {
		this.manifest = "unknown";
		this.version = "unknown";
		this.release = "unknown";
	}

	public String getManifest() {
		return manifest;
	}

	public void setManifest(String manifest) {
		this.manifest = manifest;
	}

	public String getVersion() {
		return version;
	}

	public void setVersion(String version) {
		this.version = version;
	}

	public String getRelease() {
		return release;
	}

	public void setRelease(String release) {
		this.release = release;
	}
}

