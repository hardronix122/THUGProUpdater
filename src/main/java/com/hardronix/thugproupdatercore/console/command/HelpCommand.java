package com.hardronix.thugproupdatercore.console.command;

import com.hardronix.thugproupdatercore.console.Console;

public class HelpCommand extends Command {

	public HelpCommand() {
		super("help", "Show command list");
	}

	@Override
	public void execute(Console console) {
		for(Command cmd : console.commands) {
			System.out.printf("%s - %s%n", cmd.getName(), cmd.getDescription());
		}
	}
}
