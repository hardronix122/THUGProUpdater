package com.hardronix.thugproupdatercore.console.command;

import com.hardronix.thugproupdatercore.console.Console;

public abstract class Command {

	private final String name;
	private final String description;

	public Command(String name, String description) {
		this.name = name;
		this.description = description;
	}

	public abstract void execute(Console console);

	public String getName() {
		return name;
	}

	public String getDescription() {
		return description;
	}
}
