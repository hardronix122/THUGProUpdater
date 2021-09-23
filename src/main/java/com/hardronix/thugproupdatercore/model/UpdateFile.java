package com.hardronix.thugproupdatercore.model;

import com.fasterxml.jackson.annotation.JsonProperty;

public class UpdateFile {

	@JsonProperty("crc32")
	String crc32Hash;

	@JsonProperty("link")
	String link;

	@JsonProperty("version")
	String version;

	public UpdateFile(String crc32Hash, String link, String version) {
		this.crc32Hash = crc32Hash;
		this.link = link;
		this.version = version;
	}

	public UpdateFile() {
		this.crc32Hash = "unknown";
		this.link = "unknown";
		this.version = "unknown";
	}

	public String getCrc32Hash() {
		return crc32Hash;
	}

	public void setCrc32Hash(String crc32Hash) {
		this.crc32Hash = crc32Hash;
	}

	public String getLink() {
		return link;
	}

	public void setLink(String link) {
		this.link = link;
	}

	public String getVersion() {
		return version;
	}

	public void setVersion(String version) {
		this.version = version;
	}
}
