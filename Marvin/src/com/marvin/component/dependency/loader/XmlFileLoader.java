/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.marvin.component.dependency.loader;

import com.marvin.component.dependency.ContainerBuilder;
import com.marvin.component.dependency.Definition;
import com.marvin.component.dependency.Reference;
import com.marvin.component.filter.XmlNodeFilter;
import com.marvin.component.loader.FileLoader;
import com.marvin.component.resource.locator.LocatorInterface;
import com.marvin.component.util.classloader.ClassLoaderUtil;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.traversal.DocumentTraversal;
import org.w3c.dom.traversal.NodeFilter;
import org.w3c.dom.traversal.TreeWalker;
import org.xml.sax.SAXException;

/**
 *
 * @author Dr.Who
 */
public class XmlFileLoader extends FileLoader {

    protected final DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
    protected DocumentTraversal traversal;
    protected Element root;
    protected ContainerBuilder builder;

    public XmlFileLoader(LocatorInterface locator, ContainerBuilder builder) {
        super(locator);
        this.builder = builder;
    }

    @Override
    public boolean supports(String resource) {
        return resource != null && resource.endsWith("xml");
    }
    
    @Override
    protected final void load(File file) {
        try {
            Document document = factory.newDocumentBuilder().parse(file);
            this.load(document);
        } catch (ParserConfigurationException | SAXException | IOException ex) {
            Logger.getLogger(XmlFileLoader.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    protected void load(Document document) {
        this.traversal = (DocumentTraversal) document;
        this.root = document.getDocumentElement();
        parseServices(root);
    }
    
    private List<Object> getArguments(Element service) {
        List<Object> args = new ArrayList<>();

        NodeFilter filter = new XmlNodeFilter("argument", Arrays.asList(new String[]{}));
        TreeWalker walker = traversal.createTreeWalker(service, NodeFilter.SHOW_ELEMENT, filter, true);

        // describe current node:
        Element parent = (Element) walker.getCurrentNode();
        Element argument = (Element) walker.firstChild();

        // traverse children:
        while (argument != null) {
            try {
                args.add(this.getValue(argument));
            } catch (Exception ex) {
                Logger.getLogger(XmlFileLoader.class.getName()).log(Level.SEVERE, null, ex);
            }
            argument = (Element) walker.nextSibling();
        }

        // return position to the current (level up):
        walker.setCurrentNode(parent);
        return args;
    }

    private Object[] getArgumentsAsArray(Element service) {
        return this.getArguments(service).toArray();
    }
    
    private Collection<Object> getArgumentsAsCollection(Element service) {
        return this.getArguments(service);
    }

    private Object getValue(Element argument) {
        String type = argument.getAttribute("type");
        String value = argument.getTextContent();

        if (type == null || "".equals(type)) {
            return null;
        }
        
        switch (type) {
            case "collection":
                return this.getArgumentsAsCollection(argument);
                
            case "array":
                return this.getArgumentsAsArray(argument);

            case "service":
                String id = argument.getAttribute("id");
//                return this.builder.resolve(id);
                return new Reference(id);
                
            case "number":
                return Integer.parseInt(value);
                
            default:
                switch (value) {
                    case "null":
                        return null;
                    case "true":
                        return true;
                    case "false":
                        return false;
                    default:
                        return value;
                }
        }
    }
    
    private void parseService(Element service) {
        String id = service.getAttribute("id");
        String name = service.getAttribute("class");
        
        if (name == null || "".equals(name)) {
            return;
        }
        
        if (id == null || "".equals(id)) {
            id = name;
        }
        
        Class cl = ClassLoaderUtil.loadClass(name);
        List<Object> args = this.getArguments(service);
        Definition def = new Definition(id, cl, new Class[args.size()], args.toArray());
        this.builder.addDefinition(def);
    }

    private void parseServices(Element root) {
        NodeFilter filter = new XmlNodeFilter("service", Arrays.asList("container", "services"));
        TreeWalker walker = this.traversal.createTreeWalker(root, NodeFilter.SHOW_ELEMENT, filter, true);

        // describe current node:
        Element parent = (Element) walker.getCurrentNode();
        Element child = (Element) walker.firstChild();

        // traverse children:
        while (child != null) {
            this.parseService(child);
            child = (Element) walker.nextSibling();
        }

        // return position to the current (level up):
        walker.setCurrentNode(parent);

    }
}
