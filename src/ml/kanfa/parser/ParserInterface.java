package ml.kanfa.parser;

import java.io.IOException;
import java.util.Properties;

/**
 * @author Ibrahim Maïga.
 */
public interface ParserInterface {


    default Properties getProperties() {
        final Properties properties = new Properties();
        try {
            properties.load(ParserInterface.class.getResourceAsStream("/ml/kanfa/config/parser_config.properties"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return properties;
    }
}
