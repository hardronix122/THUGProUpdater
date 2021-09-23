package com.hardronix.thugproupdatercore.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.HashMap;

public class Update {

	@JsonProperty("version")
	private String version;

	@JsonProperty("release")
	private String release;

	@JsonProperty("data_files")
	private HashMap<String, UpdateFile> dataFiles;

	@JsonProperty("other_files")
	private HashMap<String, UpdateFile> otherFiles;

	@JsonProperty("streams")
	private Stream streams;

	public Update(String version, String release, HashMap<String, UpdateFile> dataFiles, HashMap<String, UpdateFile> otherFiles, Stream streams) {
		this.version = version;
		this.release = release;
		this.dataFiles = dataFiles;
		this.otherFiles = otherFiles;
		this.streams = streams;
	}

	public Update() {
		this.version = "unknown";
		this.release = "unknown";
		this.dataFiles = new HashMap<>();
		this.otherFiles = new HashMap<>();
		this.streams = new Stream();
	}

	public Version getVersion() {
		return new Version(release, version);
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

	public HashMap<String, UpdateFile> getDataFiles() {
		return dataFiles;
	}

	public void setDataFiles(HashMap<String, UpdateFile> dataFiles) {
		this.dataFiles = dataFiles;
	}

	public HashMap<String, UpdateFile> getOtherFiles() {
		return otherFiles;
	}

	public void setOtherFiles(HashMap<String, UpdateFile> otherFiles) {
		this.otherFiles = otherFiles;
	}

	public Stream getStreams() {
		return streams;
	}

	public void setStreams(Stream streams) {
		this.streams = streams;
	}
}
