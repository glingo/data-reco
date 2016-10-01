package com.marvin.dialog;

public final class Request {

//    private JtwigModelMap ressources;
    private String method;
    private String uri;
    private String protocol;

    public Request() {
    }

    public Request(String uri) {
        super();
        this.setUri(uri);
    }

//    public Request(String uri, JtwigModelMap ressources) {
//        super();
//        this.setUri(uri);
//        this.ressources = ressources;
//    }

//    public JtwigModelMap getRessources() {
//        return ressources;
//    }
//
//    public void setRessources(JtwigModelMap ressources) {
//        this.ressources = ressources;
//    }

    public String getMethod() {
        return method;
    }

    public void setMethod(String method) {
        this.method = method;
    }

    public String getUri() {
        return uri;
    }

    public void setUri(String uri) {
        this.uri = uri;
    }

    public String getProtocol() {
        return protocol;
    }

    public void setProtocol(String protocol) {
        this.protocol = protocol;
    }

}
