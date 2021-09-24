package com.hardronix.thugproupdatercore.console;

import com.hardronix.thugproupdatercore.Config;
import com.hardronix.thugproupdatercore.console.command.*;
import com.hardronix.thugproupdatercore.util.UpdateUtil;

import java.io.File;
import java.io.IOException;
import java.nio.file.InvalidPathException;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Console {

	private final Scanner sc;
	public final List<Command> commands = new ArrayList<>();

	public Console() {
		sc = new Scanner(System.in);

		commands.add(new HelpCommand());
		commands.add(new FetchCommand());
		commands.add(new DownloadCommand());
		commands.add(new RepairCommand());
	}

	public void init() {

		askPathsIfNecessary();

		while(true) {
			String input = readLine();

			for(Command cmd : commands) {
				if(cmd.getName().equalsIgnoreCase(input)) {
					cmd.execute(this);
				}
			}

			if(input.equals("exit")) {
				System.exit(0);
			}
		}
	}

	private void askPathsIfNecessary() {
		if(Config.thug2Directory == null || Config.thugProDirectory == null) {
			System.out.println("First we need to know where THUG Pro and THUG 2 are located :)");
			File thugProDirectory = null;
			
			while(Config.thugProDirectory == null) {
				System.out.println("Enter THUG Pro path or type 'here':");
				String input = readLine();
				
				if(input.equalsIgnoreCase("here")) {
					try {
						thugProDirectory = new File(new File(".").getCanonicalPath() + "/THUG Pro");
					} catch (IOException e) {
						System.out.println("Failed to get path");
					}
				} else {
					thugProDirectory = Paths.get(input).toFile();
				}

				try {
					if(thugProDirectory != null && thugProDirectory != Config.thug2Directory) {
						Config.thugProDirectory = thugProDirectory;
						break;
					}
				} catch (InvalidPathException | NullPointerException ignored) {
				}

				System.out.println("Path is invalid! Please enter a valid path!");
			}

			while(Config.thug2Directory == null) {
				System.out.println("Enter THUG 2 path or type 'here':");
				String input = readLine();

				File directory = Paths.get(input).toFile();

				try {
					if(UpdateUtil.isValidThug2Directory(directory) && Config.thugProDirectory != directory) {
						Config.thug2Directory = directory;
						break;
					}
				} catch (InvalidPathException | NullPointerException ignored) {
				}

				System.out.println("Path is invalid! Please enter a valid path!");
			}

			Config.saveConfig();
		}
	}

	public String readLine() {
		System.out.print("\n>> ");
		return sc.nextLine();
	}
}
