package com.hardronix.thugproupdatercore.model;

public enum InstallMode {
	INSTALL("Install/Reinstall"), REPAIR("Repair");

	private final String name;

	InstallMode(String name) {
		this.name = name;
	}

	public String getName() {
		return name;
	}
}
