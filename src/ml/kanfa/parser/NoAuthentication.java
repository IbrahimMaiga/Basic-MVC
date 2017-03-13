package ml.kanfa.parser;

/**
 * @author Ibrahim Maïga.
 */
public interface NoAuthentication {

    /**
     * No Authentication parser config path
     */
    String CONFIG_PATH = "/ml/kanfa/database/na-config.xml";

    String get();
}
