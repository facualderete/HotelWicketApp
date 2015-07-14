package ar.edu.itba.it.paw.helpers;

import org.apache.commons.configuration.Configuration;
import org.apache.commons.configuration.ConfigurationException;
import org.apache.commons.configuration.XMLConfiguration;
import org.springframework.stereotype.Component;

@Component
public class AppConfigurationHelper {

    private static final String DEFAULT_HOST = "smtp.gmail.commmm";
    private static final String DEFAULT_PORT = "587";
    private static final String DEFAULT_USERNAME = "pawemailsender@gmail.com";
    private static final String DEFAULT_PASSWORD = "Paw1234567890";
    private static final Integer DEFAULT_REQUIRED_COMMENTS_DISTINCTION = 3;

    private final Configuration config;

    private String host;
    private String port;
    private String username;
    private String password;
    private Integer requiredCommentsDistinction;

    public AppConfigurationHelper() {
        try {
            config = new XMLConfiguration("hotelApp.conf");
            try {
                host = config.getString("smtp.host", DEFAULT_HOST);
            } catch (Exception e) {
                host = DEFAULT_HOST;
            }
            try {
                port = config.getString("smtp.port", DEFAULT_PORT);
            } catch (Exception e) {
                port = DEFAULT_PORT;
            }
            try {
                username = config.getString("smtp.username", DEFAULT_USERNAME);
            } catch (Exception e) {
                username = DEFAULT_USERNAME;
            }
            try {
                password = config.getString("smtp.password", DEFAULT_PASSWORD);
            } catch (Exception e) {
                password = DEFAULT_PASSWORD;
            }
            try {
                requiredCommentsDistinction = config.getInteger("general.distinction", DEFAULT_REQUIRED_COMMENTS_DISTINCTION);
            } catch (Exception e) {
                requiredCommentsDistinction = DEFAULT_REQUIRED_COMMENTS_DISTINCTION;
            }
        }catch (ConfigurationException e) {
            throw new ExceptionInInitializerError(e);
        }
    }

    public String getHost() {
        return host;
    }

    public String getPort() {
        return port;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public Integer getRequiredCommentsDistinction() {
        return requiredCommentsDistinction;
    }
}
