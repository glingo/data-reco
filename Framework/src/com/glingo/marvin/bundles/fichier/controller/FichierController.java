package com.glingo.marvin.bundles.fichier.controller;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.glingo.marvin.kernel.Request;
import com.glingo.marvin.kernel.Response;

public class FichierController {
	
	public void csvAction(Request request, Response response) {
        response.setContentType("application/octet-stream");
		response.render(request.getUri().replaceFirst("/", "") + ".marvin");
	}

	public void pdfAction(Request request, Response response) {
        response.setContentType("application/pdf");
        response.setContentDisposition("attachment;filename=test.pdf");
		response.render(request.getUri().replaceFirst("/", ""));
	}
	
	public void cssAction(Request request, Response response) {
        response.setContentType("text/css");
		response.render(request.getUri().replaceFirst("/", "") + ".marvin");
	}
	
	public void jsAction(Request request, Response response) {
        response.setContentType("application/download");
        response.setContentDisposition("attachment;filename=test.js");
        response.render(request.getUri().replaceFirst("/", "") + ".marvin");
	}
	
	public static void main(String[] args) {
		String line = "{{ name }} {{ router }}";
		
		String[] lines = line.split("\\{\\{ [a-zA-Z]* \\}\\}");
		
		for (int i = 0; i < lines.length; i++) {
			String group = lines[i];
			System.out.println("line : " + group);
		}
		
		Matcher matcher = Pattern.compile("(\\{\\{[a-zA-Z]*\\}\\}) ").matcher(line);
		
		boolean find = matcher.find();
		
		System.out.println(find);
		
		System.out.println(matcher.groupCount());
		for (int i = 0; i < matcher.groupCount(); i++) {
			String group = matcher.group(i);
			System.out.println("group : " + group);
		}
			
	
	}
}
