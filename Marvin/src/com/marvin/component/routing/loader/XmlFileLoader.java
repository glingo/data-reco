package com.marvin.component.routing.loader;

import com.marvin.component.builder.BuilderInterface;
import com.marvin.component.resource.locator.FileLocator;
import com.marvin.component.routing.RouteCollection;
import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.w3c.dom.Node;
import org.xml.sax.SAXException;

public class XmlFileLoader extends RouteLoader {

    protected final DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();

    public XmlFileLoader(FileLocator locator, BuilderInterface builder) {
        super(locator, builder);
    }

    protected Document buildDocument(File file) throws ParserConfigurationException, SAXException, IOException{
        return factory.newDocumentBuilder().parse(file);
    }
    
    @Override
    protected void parseFile(File file){
        try {
            Document document = this.buildDocument(file);
            NodeList childs = document.getDocumentElement().getChildNodes();
            
            int length = childs.getLength();
            for (int i = 0; i<length; i++) {
                this.parseNode(routes, childs.item(i), document.getDocumentURI(), document.getNodeName());
            }
        } catch (ParserConfigurationException | SAXException | IOException ex) {
            Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    protected void parseNode(RouteCollection collection, Node node, String path, String file){
        
        System.out.println(node.getNodeName());
        System.out.println(path);
        System.out.println(file);
    }
}
