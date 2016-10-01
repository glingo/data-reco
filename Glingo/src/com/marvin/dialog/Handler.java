package com.marvin.dialog;

import java.io.IOException;

import javax.xml.ws.spi.http.HttpExchange;
import javax.xml.ws.spi.http.HttpHandler;

import com.marvin.routing.Route;
import com.marvin.routing.Routeur;
import java.lang.reflect.InvocationTargetException;

public class Handler extends HttpHandler {

    private Routeur routeur;

    @Override
    public void handle(HttpExchange ex) throws IOException {
        try {
            Route route = routeur.find(ex.getContextPath());
            
            if (route == null) {
                return;
            }
            
            route.getAction().setAccessible(true);
            route.getAction().invoke(route.getController().newInstance(), null, null);
            route.getAction().setAccessible(false);
            
        } catch (SecurityException | InstantiationException | IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
            e.printStackTrace();
        }

    }

}
