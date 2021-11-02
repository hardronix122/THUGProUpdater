package com.hardronix.thugproupdatercore.jfx.node;

import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.*;
import javafx.scene.paint.Paint;
import javafx.scene.text.Font;

import java.io.InputStream;

public class TitleBar extends StackPane {

	public TitleBar(String title, int width) {
		this.setBackground(new Background(new BackgroundFill(Paint.valueOf("#181818"), CornerRadii.EMPTY, Insets.EMPTY)));
		this.setPrefSize(width, 48);
		this.setAlignment(Pos.CENTER);

		HBox closeButtonBox = new HBox();
		closeButtonBox.setAlignment(Pos.TOP_RIGHT);
		closeButtonBox.setPadding(new Insets(4, 4, 4, 4));

		Button closeButton = new Button();
		closeButton.setId("close-button");
		closeButton.setOnAction(e -> Platform.exit());

		closeButtonBox.getChildren().add(closeButton);
		this.getChildren().add(closeButtonBox);


		Label label = new Label(title);
		label.setAlignment(Pos.CENTER);
		label.setMinSize(width, 48);
		label.setId("window-title");

		this.getChildren().add(label);
	}
}
