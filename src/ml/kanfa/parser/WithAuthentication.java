package ml.kanfa.parser;

/**
 * @author Ibrahim Maïga.
 */
public interface WithAuthentication extends ParserInterface{

    /**
     * With Authentication parser config path
     */

    String get(String type);

    default String getPath(){
        return getProperties().getProperty("auth");
    }
}
