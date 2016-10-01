package com.glingo.bundles.fichier.controller;

import com.glingo.marvin.server.request.Request;
import com.glingo.marvin.server.response.Response;

public class FichierController {
	
	public void csvAction(Request request, Response response) {
        response.setContentType("application/octet-stream");
		response.setForward(request.getUri().replaceFirst("/", ""));
	}

	public void pdfAction(Request request, Response response) {
        response.setContentType("application/pdf");
        response.setContentDisposition("attachment;filename=test.pdf");
		response.setForward(request.getUri().replaceFirst("/", ""));
	}
	
	public void cssAction(Request request, Response response) {
        response.setContentType("text/css");
		response.setForward(request.getUri().replaceFirst("/", ""));
	}
	
	public void jsAction(Request request, Response response) {
        response.setContentType("application/download");
        response.setContentDisposition("attachment;filename=test.js");
        response.setForward(request.getUri().replaceFirst("/", ""));
	}
	
}
