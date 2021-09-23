package com.hardronix.thugproupdatercore.jfx.view;

import javafx.scene.Scene;
import javafx.scene.layout.Pane;

import java.util.HashMap;

public class SceneController {
	private final HashMap<String, Pane> scenes = new HashMap<>();
	private final Scene mainScene;

	public SceneController(Scene mainScene) {
		this.mainScene = mainScene;
	}

	public void addScene(String name, Pane scene) {
		scenes.put(name, scene);
	}

	public void removeScene(String name) {
		scenes.remove(name);
	}

	public void switchScene(String name) {
		mainScene.setRoot(scenes.get(name));
	}

	public void switchScene(Pane scene) {
		mainScene.setRoot(scene);
	}
}
