package ml.kanfa.parser;

/**
 * @author Ibrahim Maïga.
 */
public interface NoAuthentication extends ParserInterface{

    /**
     * No Authentication parser config path
     */
    String get();

    default String getPath(){
        return getProperties().getProperty("no_auth");
    }
}
