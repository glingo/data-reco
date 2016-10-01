package com.marvin.component.dialog;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Request {

    public static final String METHOD_HEAD = "HEAD";
    public static final String METHOD_GET = "GET";
    public static final String METHOD_POST = "POST";
    public static final String METHOD_PUT = "PUT";
    public static final String METHOD_PATCH = "PATCH";
    public static final String METHOD_DELETE = "DELETE";
    public static final String METHOD_PURGE = "PURGE";
    public static final String METHOD_OPTIONS = "OPTIONS";
    public static final String METHOD_TRACE = "TRACE";
    public static final String METHOD_CONNECT = "CONNECT";

    private String method;
    private String uri;
    private String protocol;
    private HashMap<String, Object> ressources;

    public Request() {
        super();
        this.method = METHOD_GET;
        this.uri = "/";
        this.protocol = null;
        this.ressources = new HashMap<>();
    }
    
    public Request(String method, String uri, String protocol) {
        super();
        this.method = method;
        this.uri = uri;
        this.protocol = protocol;
        this.ressources = new HashMap<>();
    }

    public String getUri() {
        return uri;
    }

    public void setUri(String uri) {
        this.uri = uri;
    }

    public void addRessource(String key, Object ressource) {
        getRessources().put(key, ressource);
    }

    public Object getRessource(String key) {
        return getRessources().get(key);
    }

    public HashMap<String, Object> getRessources() {
        return ressources;
    }

    public void setRessources(HashMap<String, Object> ressources) {
        this.ressources = ressources;
    }

    public String getMethod() {
        return method;
    }

    public void setMethod(String method) {
        this.method = method;
    }

    public String getProtocol() {
        return protocol;
    }

    public void setProtocol(String protocol) {
        this.protocol = protocol;
    }

//    public static Request fromStream(InputStream in) throws IOException {
//        Request request = null;
//
//        InputStreamReader reader = new InputStreamReader(in);
//
//        BufferedReader buffer = new BufferedReader(reader);
//
//        String line = buffer.readLine();
//
//        while (line != null && !"".equals(line)) {
//            Matcher matcher = Pattern.compile("^([A-Z]+) (\\p{Graph}+) ?((HTTP/[0-9\\.]+)?)$").matcher(line);
//
//            if (matcher.find()) {
//                request = new Request(matcher.group(1), matcher.group(2), matcher.group(3));
//            }
//
//            line = buffer.readLine();
//        }
//
//        return request;
//    }
}
