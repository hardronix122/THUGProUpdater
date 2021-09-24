package com.hardronix.thugproupdatercore.jfx.view;

import com.hardronix.thugproupdatercore.Config;
import com.hardronix.thugproupdatercore.jfx.FXApp;
import com.hardronix.thugproupdatercore.jfx.node.TitleBar;
import com.hardronix.thugproupdatercore.util.UpdateUtil;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.*;
import javafx.scene.paint.Paint;
import javafx.scene.text.TextAlignment;
import javafx.stage.DirectoryChooser;

import java.io.File;

public class PathSelectView extends VBox {

	public PathSelectView() {
		this.getStyleClass().add("pane");
		this.setWidth(260);
		this.setHeight(106);
		this.getChildren().add(new TitleBar("THUG Pro Updater $VERSION", 258));

		VBox subContainer = new VBox();
		subContainer.setAlignment(Pos.CENTER);

		Label text = new Label("First we need to know \nwhere THUG Pro and THUG 2 are located :)");
		text.setWrapText(true);
		text.setPrefSize(328, 60);
		text.setAlignment(Pos.CENTER);
		text.setStyle("-fx-font-size: 16");
		text.setTextAlignment(TextAlignment.CENTER);
		subContainer.getChildren().add(text);

		HBox buttonBox = new HBox();
		buttonBox.setAlignment(Pos.CENTER);

		Button thugProBtn = new Button("Select THUG Pro path");
		thugProBtn.setStyle("-fx-font-size: 14");

		thugProBtn.setPrefSize(160, 40);
		thugProBtn.setOnAction(e -> {
			Config.thugProDirectory = selectThugProDirectory();
			thugProBtn.setId("button-activated");
		});
		buttonBox.getChildren().add(thugProBtn);

		Region buttonSpace = new Region();
		buttonSpace.setPrefSize(8, 40);
		buttonBox.getChildren().add(buttonSpace);

		Button thug2Btn = new Button("Select THUG 2 directory");
		thug2Btn.setStyle("-fx-font-size: 14");
		thug2Btn.setPrefSize(160, 40);
		thug2Btn.setOnAction(e -> {
			Config.thug2Directory = selectThug2Directory();
			thug2Btn.setId("button-activated");
		});
		buttonBox.getChildren().add(thug2Btn);

		subContainer.getChildren().add(buttonBox);

		Region buttonSpace2 = new Region();
		buttonSpace2.setPrefSize(80, 8);

		subContainer.getChildren().add(buttonSpace2);

		Button continueButton = new Button("Done");
		continueButton.setStyle("-fx-font-size: 14");
		continueButton.setPrefSize(160, 40);
		continueButton.setOnAction(e -> {
			if(Config.thugProDirectory != null && Config.thug2Directory != null) {
				Config.saveConfig();
				FXApp.sceneController.switchScene("main");
			}
		});

		subContainer.getChildren().add(continueButton);

		this.getChildren().add(subContainer);
	}

	public File selectThugProDirectory() {
		DirectoryChooser dirChooser = new DirectoryChooser();
		dirChooser.setTitle("Select THUG Pro directory");

		File directory = dirChooser.showDialog(this.getScene().getWindow());

		if(directory != Config.thug2Directory) {
			return directory;
		} else {
			selectThugProDirectory();
		}

		return null;
	}

	public File selectThug2Directory() {
		DirectoryChooser dirChooser = new DirectoryChooser();
		dirChooser.setTitle("Select THUG 2 directory");
		File selectedDirectory = dirChooser.showDialog(this.getScene().getWindow());

		if(UpdateUtil.isValidThug2Directory(selectedDirectory) && selectedDirectory != Config.thugProDirectory) {
			return selectedDirectory;
		} else {
			return selectThug2Directory();
		}
	}
}
