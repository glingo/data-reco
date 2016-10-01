package com.marvin.dialog;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.net.Socket;

import com.marvin.routing.Route;
import com.marvin.routing.Routeur;
import com.marvin.util.classloader.ClassLoaderUtil;

public class Controller extends Thread {

    private Socket client;
    private Routeur routeur;

    public Controller(Socket client, Routeur routeur) {
        super();
        this.client = client;
        this.routeur = routeur;
    }

    public void run() {
        try {
            ResponseFilter outStream = new ResponseFilter(client.getOutputStream());
            RequestFilter inStream = new RequestFilter(client.getInputStream());
            processRequest(inStream, outStream);
        } catch (IOException ex) {
            System.exit(0);
        }
    }

    public void flush(Response reponse, Request request, Route route) {
        try {
            Class<?> bundle = route.getBundle();
            File file = ClassLoaderUtil.getResourceAsFile(reponse.getPath(), bundle);
//            JtwigTemplate template = new JtwigTemplate(file, new JtwigConfiguration());
//            JtwigModelMap ressources = reponse.getRessources();
//            String output = template.output(ressources);
//            reponse.setText(output);
            reponse.setText("SALUT");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void processRequest(RequestFilter in, ResponseFilter out) throws IOException {
        try {
            Response reponse = out.getResponse();
            Request request = in.getRequest();

            if (request.getUri() != null) {
                Route route = routeur.find(request.getUri());

                if (route != null && route.getAction() != null && route.getController() != null) {
                    route.getAction().setAccessible(true);
                    route.getAction().invoke(route.getController().newInstance(), request, reponse);
                    route.getAction().setAccessible(false);
                }

                if (reponse.getPath() == null || reponse.getPath().isEmpty()) {
                    reponse.setPath("com/marvin/dialog/ressources/templates/defaut.twig");
                }

                flush(reponse, request, route);
            }

            out.write(reponse.getText().getBytes());
            out.flush();
        } catch (IOException io) {
//            logger.info("Controller interrupted.");
            io.printStackTrace();
        } catch (IllegalAccessException e) {
//            logger.info("Controller interrupted.");
            e.printStackTrace();
        } catch (IllegalArgumentException e) {
//            logger.info("Controller interrupted.");
            e.printStackTrace();
        } catch (InvocationTargetException e) {
//            logger.info("Controller interrupted.");
            e.printStackTrace();
        } catch (InstantiationException e) {
//            logger.info("Controller interrupted.");
            e.printStackTrace();
        } finally {
//            logger.info("Client closed.");
            client.close();
        }
    }
}
