package com.hardronix.thugproupdatercore.net;

import org.apache.commons.io.FileUtils;
import org.apache.http.HttpResponse;
import org.apache.http.client.ResponseHandler;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;

public class FileDownloadResponseHandler implements ResponseHandler<File> {

	private final File file;

	public FileDownloadResponseHandler(File file) {
		this.file = file;
	}


	@Override
	public File handleResponse(HttpResponse response) throws IOException {
		InputStream stream = response.getEntity().getContent();
		FileUtils.copyInputStreamToFile(stream, file);

		return file;
	}
}
