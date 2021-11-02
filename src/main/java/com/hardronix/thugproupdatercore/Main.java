package com.hardronix.thugproupdatercore;

import com.hardronix.thugproupdatercore.console.Console;
import com.hardronix.thugproupdatercore.jfx.FXApp;

public class Main {

	public static void main(String[] args) {
		System.out.println("THUG Pro Updater $VERSION by Hardronix");

		Config.loadConfig();

		if(args.length >= 1 && args[0].equals("--nogui")) {
			new Console().init();
		}
		else {
			new FXApp().run(args);
		}
	}
}
