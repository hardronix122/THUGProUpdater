package com.hardronix.thugproupdatercore.jfx.view;

import com.hardronix.thugproupdatercore.Config;
import com.hardronix.thugproupdatercore.jfx.FXApp;
import com.hardronix.thugproupdatercore.jfx.node.TitleBar;
import com.hardronix.thugproupdatercore.jfx.task.DownloadTask;
import com.hardronix.thugproupdatercore.jfx.task.FetchUpdateTask;
import com.hardronix.thugproupdatercore.jfx.task.PostInstallTask;
import com.hardronix.thugproupdatercore.jfx.task.RepairTask;
import com.hardronix.thugproupdatercore.model.InstallConfig;
import com.hardronix.thugproupdatercore.model.InstallMode;
import com.hardronix.thugproupdatercore.model.Update;
import javafx.concurrent.Task;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;

public class DownloadView extends VBox {
	private final ProgressBar currentStepProgress;
	private final ProgressBar stepProgress;
	private final Label title;
	private final Label message;
	private Task<?> currentTask;

	public DownloadView(InstallMode installMode) {
		this.getStyleClass().add("pane");
		this.setWidth(258);
		this.setHeight(106);
		this.getChildren().add(new TitleBar("THUG Pro Updater $VERSION", 258));

		VBox subContainer = new VBox();
		subContainer.setAlignment(Pos.TOP_CENTER);

		title = new Label();
		title.setId("title");
		title.setMinSize(this.getWidth(), 20);
		title.setStyle("-fx-font-size: 24px");
		title.setAlignment(Pos.CENTER);
		title.setPadding(new Insets(10, 0, 10, 0));
		title.setPrefWidth(10);

		subContainer.getChildren().add(title);

		stepProgress = new ProgressBar();
		stepProgress.setId("step_progress");
		stepProgress.setPrefSize(420, 8);
		stepProgress.setProgress(0.25);
		subContainer.getChildren().add(stepProgress);

		Region spacer = new Region();
		spacer.setPadding(new Insets(16, 0, 0, 0));
		VBox.setVgrow(spacer, Priority.ALWAYS);
		subContainer.getChildren().add(spacer);

		currentStepProgress = new ProgressBar();
		currentStepProgress.setId("current_step_progress");
		currentStepProgress.setPrefSize(420, 8);
		currentStepProgress.setProgress(0.75);
		subContainer.getChildren().add(currentStepProgress);

		Region spacer2 = new Region();
		spacer2.setPadding(new Insets(0, 0, 0, 0));
		VBox.setVgrow(spacer2, Priority.ALWAYS);
		subContainer.getChildren().add(spacer2);

		message = new Label();
		message.setId("message");
		message.setAlignment(Pos.CENTER);

		message.setStyle("-fx-font-size: 12px");
		message.setMinWidth(this.getWidth());
		message.setAlignment(Pos.CENTER);
		message.setPadding(new Insets(8, 0, 8, 0));

		subContainer.getChildren().add(message);

		Button cancelButton = new Button("Cancel");
		cancelButton.setId("cancel_button");
		cancelButton.setPrefSize(160, 40);
		cancelButton.setOnAction(event -> currentTask.cancel());
		subContainer.getChildren().add(cancelButton);

		this.getChildren().add(subContainer);

		if(installMode.equals(InstallMode.INSTALL)) {
			initDownload(installMode);
		} else if(installMode.equals(InstallMode.REPAIR)) {
			initRepair();
		}
	}

	private void initDownload(InstallMode installMode) {
		FetchUpdateTask fetchUpdateTask = new FetchUpdateTask();
		currentTask = fetchUpdateTask;

		bindNodesToTask(fetchUpdateTask);

		fetchUpdateTask.setOnSucceeded(event -> {
			Update update = fetchUpdateTask.getValue();

			InstallConfig installConfig = new InstallConfig(
					update.getVersion(),
					Config.thugProDirectory,
					Config.thug2Directory,
					installMode,
					Config.release);

			stepProgress.setProgress(0.35);

			downloadFiles(installConfig, update);
		});

		fetchUpdateTask.setOnFailed(event -> {
			title.textProperty().unbind();
			message.textProperty().unbind();
			title.setText("Failed to update :<");
			message.setText("Failed on fetch step :<");
		});

		FXApp.exec.submit(fetchUpdateTask);
	}

	private void initRepair() {
		FetchUpdateTask fetchUpdateTask = new FetchUpdateTask();
		currentTask = fetchUpdateTask;

		bindNodesToTask(fetchUpdateTask);

		fetchUpdateTask.setOnSucceeded(event -> {
			Update update = fetchUpdateTask.getValue();

			stepProgress.setProgress(0.50);

			repairFiles(update);
		});

		fetchUpdateTask.setOnFailed(event -> {
			title.textProperty().unbind();
			message.textProperty().unbind();
			title.setText("Failed to update :<");
			message.setText("Failed on fetch step :<");
		});

		FXApp.exec.submit(fetchUpdateTask);
	}

	private void repairFiles(Update update) {
		RepairTask repairTask = new RepairTask(update);
		currentTask = repairTask;

		bindNodesToTask(repairTask);

		repairTask.setOnSucceeded(event -> {
			System.out.println("Finished!");

			title.textProperty().unbind();
			message.textProperty().unbind();
			stepProgress.progressProperty().unbind();
			currentStepProgress.progressProperty().unbind();

			title.setText("Finished!");
			message.setText("Repair finished!");
			stepProgress.setProgress(1);
			currentStepProgress.setProgress(1);
		});

		repairTask.setOnFailed(event -> {

			title.textProperty().unbind();
			message.textProperty().unbind();
			title.setText("Failed to repair :<");
			message.setText("Failed on repair step :<");
		});

		FXApp.exec.submit(repairTask);
	}

	private void downloadFiles(InstallConfig installConfig, Update update) {
		DownloadTask downloadTask = new DownloadTask(installConfig, update);
		currentTask = downloadTask;

		bindNodesToTask(downloadTask);

		downloadTask.setOnSucceeded(event -> {
			stepProgress.setProgress(0.70);
			postInstall(installConfig);
		});

		downloadTask.setOnFailed(event -> {
			title.textProperty().unbind();
			message.textProperty().unbind();
			title.setText("Failed to update :<");
			message.setText("Failed on download step :<");
		});

		FXApp.exec.submit(downloadTask);
	}

	private void postInstall(InstallConfig installConfig) {
		PostInstallTask postInstallTask = new PostInstallTask(installConfig);
		currentTask = postInstallTask;

		bindNodesToTask(postInstallTask);

		postInstallTask.setOnSucceeded(event -> {
			stepProgress.progressProperty().unbind();
			stepProgress.setProgress(1);

			title.textProperty().unbind();
			title.setText("Finished!");
		});

		postInstallTask.setOnFailed(event -> {
			title.textProperty().unbind();
			message.textProperty().unbind();
			title.setText("Failed to update :<");
			message.setText("Failed on post-install step :<");
		});

		FXApp.exec.submit(postInstallTask);
	}

	private void bindNodesToTask(Task<?> task) {
		title.textProperty().bind(task.titleProperty());
		message.textProperty().bind(task.messageProperty());
		currentStepProgress.progressProperty().bind(task.progressProperty());
	}
}
