package com.hardronix.thugproupdatercore.jfx.task;

import com.hardronix.thugproupdatercore.Config;
import com.hardronix.thugproupdatercore.model.Update;
import com.hardronix.thugproupdatercore.util.UpdateUtil;
import javafx.concurrent.Task;

public class FetchUpdateTask extends Task<Update> {

	@Override
	protected Update call() {
		updateTitle("Fetching update...");

		Update update = UpdateUtil.fetchUpdate(Config.release);

		if(this.isCancelled()) {
			updateTitle("Cancelled");
			updateMessage("Update fetch cancelled");
			return null;
		}

		return update;
	}
}
