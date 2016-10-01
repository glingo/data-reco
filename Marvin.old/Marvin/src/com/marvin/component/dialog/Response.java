package com.marvin.component.dialog;

import com.marvin.util.StringUtils;
import com.marvin.util.classloader.ClassLoaderUtil;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;

public class Response {

    private String protocol;
    private String status;
    private String server;
    private String contentType;
    private String contentDisposition;
    private String content;
    private int code;
    private PrintWriter writer;

    private HashMap<String, Object> ressources = new HashMap<>();

    private HashMap<String, ArrayList<String>> flash = new HashMap<>();

    public Response(String protocol, String status, String contentType, int code) {
        super();
        this.protocol = protocol;
        this.status = status;
        this.contentType = contentType;
        this.code = code;
    }

    public Response(String protocol, String status, String server, String contentType, int code) {
        super();
        this.protocol = protocol;
        this.status = status;
        this.server = server;
        this.contentType = contentType;
        this.code = code;
    }

    public static Response HTTPResponse() {
        return new Response("HTTP/1.1", "OK", "text/html", 200);
    }

    public void flush(OutputStream out) {
        writer = new PrintWriter(out);

        writer.println(getProtocol() + " " + getCode() + " " + getStatus());
        writer.println("Content-Type : " + getContentType());

        if (content != null) {
            writer.println("Content-Length : " + content.getBytes().length);
        }

        if (getContentDisposition() != null && !"".equals(getContentDisposition())) {
            writer.println("Content-Disposition : " + getContentDisposition());
        }

        if (getServer() != null && !"".equals(getServer())) {
            writer.println("Server : " + getServer());
        }

        writer.println();
        writer.println(getContent());

        writer.close();
    }

    public void renderFromString(String rendered) {
        if (content == null) {
            content = "";
        }

        content += rendered + StringUtils.NEWLINE;
    }

    public void render(String path) throws IOException {

        boolean parse = path.endsWith(".marvin");

        InputStream in = ClassLoaderUtil.getResourceAsStream(path);

        if (in != null) {
            BufferedReader buffer = new BufferedReader(new InputStreamReader(in));

            String line = buffer.readLine();

            while (line != null) {
                if (parse) {
                    renderFromString(line);
                } else {
                    content += line + StringUtils.NEWLINE;
                }
                line = buffer.readLine();
            }
        }
    }

    public void addFlash(String key, String value) {
        if (this.flash == null) {
            this.flash = new HashMap<>();
        }

        ArrayList<String> values = this.flash.get(key);

        if (values == null) {
            values = new ArrayList<>();
        }

        values.add(value);

        this.flash.put(key, values);
    }

    public void addRessource(String key, Object value) {
        if (this.ressources == null) {
            this.ressources = new HashMap<>();
        }
        this.ressources.put(key, value);
    }

    public HashMap<String, Object> getRessources() {
        return ressources;
    }

    public void setRessources(HashMap<String, Object> ressources) {
        this.ressources = ressources;
    }

    public String getProtocol() {
        return protocol;
    }

    public void setProtocol(String protocol) {
        this.protocol = protocol;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getServer() {
        return server;
    }

    public void setServer(String server) {
        this.server = server;
    }

    public String getContentType() {
        return contentType;
    }

    public void setContentType(String contentType) {
        this.contentType = contentType;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getContentDisposition() {
        return contentDisposition;
    }

    public void setContentDisposition(String contentDisposition) {
        this.contentDisposition = contentDisposition;
    }

    public HashMap<String, ArrayList<String>> getFlash() {
        return flash;
    }

    public void setFlash(HashMap<String, ArrayList<String>> flash) {
        this.flash = flash;
    }
}
