package ml.kanfa.parser;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import ml.kanfa.utils.dbutils.Database;

/**
 * @author Ibrahim Maïga.
 */

public class XMLParser {

    private static final String CONFIG_PATH = "resource/config/database/config.xml";
    private static final String TAG_NAME = "database";
    private static final String ATTRIBUTE = "name";

    private Database database;

    private Document document;

    private NodeList nodeList;

    private Element root;

    public XMLParser(Database database){
        this.database = database;
        this.document = ml.kanfa.parser.Document.getInstance(CONFIG_PATH);
        this.root = this.document.getDocumentElement();
        this.nodeList = this.root.getElementsByTagName(TAG_NAME);
    }

    private String get(String dbname, String elementname){
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

    public String getHost(){
        return this.get(this.database.getName(), "host");
    }

    public String getUsername(){
        return this.get(this.database.getName(), "user");
    }

    public String getPassword(){
        return this.get(this.database.getName(), "password");
    }

    public Database getDatabase(){
        return this.database;
    }
}
