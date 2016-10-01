/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.marvin.component.service.loader;

import com.marvin.component.resource.Resource;
import com.marvin.component.resource.locator.FileLocator;
import com.marvin.component.service.ContainerBuilder;
import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import org.w3c.dom.Document;
import org.xml.sax.SAXException;

/**
 *
 * @author Dr.Who
 */
public class XmlFileLoader extends ContainerLoader {

    protected final DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();

    protected Document document;

    public XmlFileLoader(ContainerBuilder builder, FileLocator locator) {
        super(builder, locator);
    }
    
    @Override
    public boolean supports(Resource ressource, String type) {
        return super.supports(ressource, type) || "xml".equals(type);
    }

    @Override
    public void load(Resource resource) throws Exception {
        File file = this.locate(resource);
        this.parseFile(file);
        this.parseServices();
    }
    
    protected void parseFile(File file){
        try {
            this.document = factory.newDocumentBuilder().parse(file);
        } catch (ParserConfigurationException | SAXException | IOException ex) {
            Logger.getLogger(XmlFileLoader.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    protected void parseServices(){
        
    }
}
