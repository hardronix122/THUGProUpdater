package com.hardronix.thugproupdatercore.jfx.node;

import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Paint;
import javafx.scene.text.Font;

import java.io.InputStream;

public class TitleBar extends VBox {

	public TitleBar(String title, int width) {
		this.setBackground(new Background(new BackgroundFill(Paint.valueOf("#181818"), CornerRadii.EMPTY, Insets.EMPTY)));
		this.setMinWidth(width);
		this.setMinHeight(48);
		this.setAlignment(Pos.CENTER);

		Label label = new Label(title);
		label.setAlignment(Pos.CENTER);
		label.setMinSize(width, 48);

		InputStream is = this.getClass().getResourceAsStream("/style/font/Sansation_Light.ttf");
		label.setFont(Font.loadFont(is, 24));
		label.setId("window-title");

		this.getChildren().add(label);
	}
}
