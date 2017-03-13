package ml.kanfa.parser;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

/**
 * @author Ibrahim Maïga.
 */
public class Document {

    private static String currentPath = "";

    private Document(){}

    public static org.w3c.dom.Document getInstance(String path){
        return LazyInitialisation.getInstance(path);
    }

    private final static class LazyInitialisation {

        private static org.w3c.dom.Document document;

        private static org.w3c.dom.Document getInstance(final String path){
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder;
            try {
                builder = factory.newDocumentBuilder();
                if(document == null && !currentPath.contains(path)) {
                    document = builder.parse(LazyInitialisation.class.getResourceAsStream(path));
                    currentPath = document.getBaseURI();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            return document;
        }
    }
}