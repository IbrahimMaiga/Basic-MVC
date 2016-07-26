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

    private Document document;

    private NodeList nodeList;

    private Element root;


    public XMLParser(Database database){
        this.database = database;
        this.document = ml.kanfa.parser.Document.getInstance(getConfigPath());
        this.root = this.document.getDocumentElement();
        this.nodeList = this.root.getElementsByTagName(TAG_NAME);
    }

    protected String get(String dbname, String elementname){
        String value = "";
        Element database;
        for(int i = 0; i < nodeList.getLength(); i++){
            database = (Element)nodeList.item(i);
            if (database.getAttribute(ATTRIBUTE).equals(dbname)){
                value = (database.getElementsByTagName(elementname).item(0)).getTextContent();
                break;
            }
        }
        return value;
    }

    protected abstract String getConfigPath();
    public Database getDatabase(){
        return this.database;
    }
}
