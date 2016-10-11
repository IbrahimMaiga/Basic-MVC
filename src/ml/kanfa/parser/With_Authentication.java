package ml.kanfa.parser;

/**
 * @author Ibrahim Maïga.
 */
public interface With_Authentication {

    /**
     *
     * With Authentication parser config path
     *
     */

    String CONFIG_PATH = "/ml/kanfa/config/database/config.xml";

    String get(String type);
}
