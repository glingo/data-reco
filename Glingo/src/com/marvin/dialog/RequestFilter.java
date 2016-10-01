package com.marvin.dialog;

import java.io.BufferedReader;
import java.io.FilterInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Iterator;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RequestFilter extends FilterInputStream {

	private static final Pattern pattern = Pattern.compile("^([A-Z]+) (\\p{Graph}+) ?((HTTP/[0-9\\.]+)?)$");
	protected int maxLineLen = 1024;

	public RequestFilter(InputStream in) {
		super(in);
	}

	public Request getRequest() {
		Request request = new Request();

		BufferedReader inFromServer = new BufferedReader(new InputStreamReader(in));

		for (Iterator<String> iterator = inFromServer.lines().iterator(); iterator.hasNext();) {
			String line = (String) iterator.next();
			Matcher matcher = pattern.matcher(line);

			if (matcher.find()) {
				request.setMethod(matcher.group(1));
				request.setUri(matcher.group(2));
				request.setProtocol(matcher.group(3));
			}
		}
		
		return request;
	}

}
