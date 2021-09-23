package com.hardronix.thugproupdatercore.jfx.view;

import com.hardronix.thugproupdatercore.Config;
import com.hardronix.thugproupdatercore.jfx.FXApp;
import com.hardronix.thugproupdatercore.jfx.node.TitleBar;
import com.hardronix.thugproupdatercore.model.InstallMode;

import com.hardronix.thugproupdatercore.model.Release;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.layout.*;
import javafx.scene.paint.Paint;
import javafx.util.StringConverter;

public class MainView extends VBox {

	public MainView() {
		this.setBackground(new Background(new BackgroundFill(Paint.valueOf("#141414"), CornerRadii.EMPTY, Insets.EMPTY)));
		this.setWidth(260);
		this.setHeight(106);
		this.getChildren().add(new TitleBar("THUG Pro Updater $VERSION", 258));

		VBox subContainer = new VBox();

		subContainer.setAlignment(Pos.CENTER);

		Region emptySpace1 = new Region();
		emptySpace1.setPadding(new Insets(40, 0, 0, 0));

		subContainer.getChildren().add(emptySpace1);

		CheckBox checkBox = new CheckBox("Use test version");
		checkBox.setPadding(new Insets(0, 0, 0, 0));

		checkBox.setOnAction(e -> {
			Config.release = checkBox.isSelected() ? Release.TEST : Release.CURRENT;
			Config.saveConfig();
		});

		subContainer.getChildren().add(checkBox);

		Region emptySpace2 = new Region();
		emptySpace2.setPadding(new Insets(0, 0, 40, 0));

		subContainer.getChildren().add(emptySpace2);

		HBox selectAndContinueBox = new HBox();

		ComboBox<InstallMode> options = new ComboBox<>();

		options.setConverter(new StringConverter<>() {
			@Override
			public String toString(InstallMode mode) {
				if(mode != null) {
					return mode.getName();
				}

				return "";
			}

			@Override
			public InstallMode fromString(String string) {
				return null;
			}
		});

		options.getItems().add(InstallMode.INSTALL);
		options.getItems().add(InstallMode.REPAIR);

		options.getSelectionModel().selectFirst();

		Button continueButton = new Button("Next");

		options.setMinSize(300, 40);
		continueButton.setMinSize(140, 40);

		EventHandler<ActionEvent> buttonPressEvent = e -> FXApp.sceneController.switchScene(new DownloadView(options.getValue()));

		continueButton.setOnAction(buttonPressEvent);

		selectAndContinueBox.getChildren().add(options);
		selectAndContinueBox.getChildren().add(continueButton);

		selectAndContinueBox.setAlignment(Pos.CENTER);
		selectAndContinueBox.setPadding(new Insets(0, 0, 40, 0));

		subContainer.getChildren().add(selectAndContinueBox);

		this.getChildren().add(subContainer);
	}
}
