package com.marvin.routing;

import java.io.IOException;
import java.io.InputStream;

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import com.marvin.routing.strategies.AnnotationRoutingStrategy;
import com.marvin.routing.strategies.IRoutingStrategy;
import com.marvin.routing.strategies.XMLRoutingStrategy;
import com.marvin.util.StringUtils;
import com.marvin.util.classloader.ClassLoaderUtil;

public class MappingHandler extends DefaultHandler {

    private IRoutingStrategy strategy;
    private Class<?> container;
    private Routeur routeur;

    public MappingHandler(Routeur routeur) {
        super();
        this.routeur = routeur;
    }

    @Override
    public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {

        super.startElement(uri, localName, qName, attributes);

        switch (qName) {
            case "route":
                String type = StringUtils.toString(attributes.getValue("type"));
                switch (type) {
                    case "annotation":
                        strategy = new AnnotationRoutingStrategy(routeur);
                        break;

                    default:
                        strategy = new XMLRoutingStrategy(routeur);
                        break;
                }

                strategy.register(container, attributes);

                break;

            default:
                break;
        }

    }

    public void load(String path, Class<?> ref) throws IOException {
        this.container = ref;
        try (InputStream in = ClassLoaderUtil.getResourceAsStream(path, this.container)) {
            SAXParser saxParser = SAXParserFactory.newInstance().newSAXParser();
            saxParser.parse(in, this);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public IRoutingStrategy getStrategy() {
        return strategy;
    }

    public void setStrategy(IRoutingStrategy strategy) {
        this.strategy = strategy;
    }

    public Class<?> getContainer() {
        return container;
    }

    public void setContainer(Class<?> container) {
        this.container = container;
    }

    public Routeur getRouteur() {
        return routeur;
    }

    public void setRouteur(Routeur routeur) {
        this.routeur = routeur;
    }
}
