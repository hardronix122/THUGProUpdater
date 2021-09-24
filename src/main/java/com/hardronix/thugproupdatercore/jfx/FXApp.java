package com.hardronix.thugproupdatercore.jfx;

import com.hardronix.thugproupdatercore.Config;
import com.hardronix.thugproupdatercore.jfx.view.MainView;
import com.hardronix.thugproupdatercore.jfx.view.PathSelectView;
import com.hardronix.thugproupdatercore.jfx.view.SceneController;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.net.URL;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class FXApp extends Application {

	public static SceneController sceneController;
	private double xOffset;
	private double yOffset;

	public static final ExecutorService exec = Executors.newFixedThreadPool(5, r -> {
		Thread t = new Thread(r);
		t.setDaemon(true);
		return t;
	});

	@Override
	public void start(Stage primaryStage) {
		Scene mainScene = new Scene(new Pane(), 516, 212);
		URL cssUrl = this.getClass().getResource("/style/oxygen.css");

		mainScene.setOnMousePressed(event -> {
			xOffset = event.getSceneX();
			yOffset = event.getSceneY();
		});

		mainScene.setOnMouseDragged(event -> {
			primaryStage.setX(event.getScreenX() - xOffset);
			primaryStage.setY(event.getScreenY() - yOffset);
		});

		if(cssUrl != null) {
			mainScene.getStylesheets().add(cssUrl.toString());
		}

		sceneController = new SceneController(mainScene);

		sceneController.addScene("main", new MainView());
		sceneController.addScene("path_select", new PathSelectView());

		if(Config.thug2Directory == null || Config.thugProDirectory == null) {
			sceneController.switchScene("path_select");
		} else {
			sceneController.switchScene("main");
		}

		primaryStage.initStyle(StageStyle.UNDECORATED);
		primaryStage.setScene(mainScene);
		primaryStage.setResizable(false);

		primaryStage.show();
	}

	public void run(String[] args) {
		launch(args);
	}
}
