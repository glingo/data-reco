package com.marvin.dialog;

import java.io.IOException;
import java.io.OutputStream;

public class Response {

    private String path = "";
    private String text = "";
//    private JtwigModelMap ressources;

    public Response() {
        super();
//        setRessources(new JtwigModelMap());
    }

    public Response(String path) {
        super();
        setPath(path);
    }

//    public Response(String path, JtwigModelMap ressources) {
//        super();
//        setPath(path);
//        setRessources(ressources);
//    }

    public void flush(OutputStream output) {
        try {
            if (getText() != null) {
                output.write(getText().getBytes());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

//    public JtwigModelMap getRessources() {
//        return ressources;
//    }
//
//    public void addRessource(String key, Object ressource) {
//        this.ressources.add(key, ressource);
//    }
//
//    private void setRessources(JtwigModelMap ressources) {
//        this.ressources = ressources;
//    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

}
