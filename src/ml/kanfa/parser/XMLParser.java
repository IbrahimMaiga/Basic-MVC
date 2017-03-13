package ml.kanfa.parser;

import ml.kanfa.utils.dbutils.Database;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

/**
 * @author Ibrahim Maïga.
 */
public abstract class XMLParser {

    private static final String TAG_NAME = "database";
    private static final String ATTRIBUTE = "name";

    private Database database;
    private NodeList nodeList;

    public XMLParser(Database database){
        this.database = database;
        final Document document = ml.kanfa.parser.Document.getInstance(getConfigPath());
        final Element root = document.getDocumentElement();
        this.nodeList = root.getElementsByTagName(TAG_NAME);
    }

    protected String get(String dbName, String elementName){
        Element database;
        for(int i = 0; i < this.nodeList.getLength(); i++){
            database = (Element)this.nodeList.item(i);
            if (database.getAttribute(ATTRIBUTE).equals(dbName)){
                return (database.getElementsByTagName(elementName).item(0)).getTextContent();
            }
        }
        return null;
    }

    protected abstract String getConfigPath();

    public Database getDatabase(){
        return this.database;
    }
}
