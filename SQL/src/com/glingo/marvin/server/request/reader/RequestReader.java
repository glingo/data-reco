package com.glingo.marvin.server.request.reader;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.glingo.marvin.server.request.Request;

public class RequestReader {
	
	protected static final Pattern pattern = Pattern.compile("^([A-Z]+) (\\p{Graph}+) ?((HTTP/[0-9\\.]+)?)$");

	public static Request read(BufferedReader buffer) throws IOException {
		Request request = new Request();
		
		String line = buffer.readLine();
		
		while (line != null && !"".equals(line)) {
			Matcher matcher = pattern.matcher(line);
			if(matcher.matches()) {
				request.setMethod(matcher.group(1));
				request.setUri(matcher.group(2));
				request.setProtocol(matcher.group(3));
			}
			line = buffer.readLine();
		}
		
		return request;
	}
}
