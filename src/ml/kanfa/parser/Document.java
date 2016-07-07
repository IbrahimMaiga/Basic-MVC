package ml.kanfa.parser;

import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.File;
import java.io.IOException;

/**
 * @author Kanfa.
 */

public class Document {

    private static org.w3c.dom.Document document = null;

    private static String currentPath = "";

    private Document(){}

    public static org.w3c.dom.Document getInstance(String path){
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder;

        try {
            builder = factory.newDocumentBuilder();
            if(document == null && !currentPath.contains(path)) {
                document = builder.parse(new File(path));
                currentPath = document.getBaseURI();
            }
        } catch (ParserConfigurationException e) {
            e.printStackTrace();
        } catch (SAXException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return document;
    }
}