package com.marvin;

import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;

import com.marvin.routing.MappingHandler;
import com.marvin.routing.Routeur;
import com.marvin.routing.mapping.Bundle;
import com.marvin.util.classloader.ClassLoaderUtil;
import com.marvin.util.introspection.AnnotationUtils;

public final class Marvin {

    private static final String MAPPING = "ressources/conf/mapping.xml";

    private int port;

    private Routeur routeur = new Routeur();

    private final HashMap<String, Class<?>> bundles = new HashMap<>();

    public Marvin(int port, String... paths) {
        super();
        this.setPort(port);
        registerBundles(paths);
    }

    public void start() {
        loadMapping();
    }

    public void registerBundles(String... paths) {
        for (int i = 0; i < paths.length; i++) {
            registerBundle(paths[i]);
        }
    }

    public void registerBundle(String path) {
        Class<?> clazz = ClassLoaderUtil.loadClass(path);
        if (clazz != null) {
            String name = (String) AnnotationUtils.getAnnotationValue(Bundle.class, clazz, "name");
            if (!bundles.containsKey(name)) {
                bundles.put(name, clazz);
            }
        }
    }

    public void loadMapping() {
        for (Iterator<Class<?>> iterator = bundles.values().iterator(); iterator.hasNext();) {
            loadMapping((Class<?>) iterator.next());
        }
    }

    public void loadMapping(Class<?> bundle) {
        try {
            MappingHandler handler = new MappingHandler(routeur);
            handler.load(MAPPING, bundle);
            routeur = handler.getRouteur();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }
}
