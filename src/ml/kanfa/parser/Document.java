package ml.kanfa.parser;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.File;

/**
 * @author Ibrahim Maïga.
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
                document = builder.parse(new File(Document.class.getResource(path).toURI()));
                currentPath = document.getBaseURI();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return document;
    }
}