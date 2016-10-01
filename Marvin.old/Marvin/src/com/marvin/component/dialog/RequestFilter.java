package com.marvin.component.dialog;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RequestFilter extends BufferedReader {

    private static final Pattern pattern = Pattern.compile("^([A-Z]+) (\\p{Graph}+) ?((HTTP/[0-9\\.]+)?)$");
    protected Request request;

    public RequestFilter(InputStream in) {
        super(new InputStreamReader(in));
        this.request = new Request();
    }

    public Request getRequest() throws IOException {
        String line = this.readLine();
		
        while (line != null && !"".equals(line)) {
            Matcher matcher = pattern.matcher(line);

            if (matcher.find()) {
                request.setMethod(matcher.group(1));
                request.setUri(matcher.group(2));
                request.setProtocol(matcher.group(3));
            }

            line = this.readLine();
        }
        
        System.err.println(this.request);
        
        return this.request;
    }

}
