package com.sales;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.log4j.Logger;
import org.json.JSONObject;
import org.json.JSONTokener;

import java.io.IOException;
import java.io.InputStream;

/**
 * This class load settings used to run the receipt calculator
 */
public class Settings {

    private static final Logger logger = Logger.getLogger(Settings.class);

    private static final String SETTINGS = "settings.json";
    private static Object SYNC = new Object();
    private SettingsBean settingsBean;
    private static volatile Settings instance;

    public static Settings getInstance() {
        if (instance == null) {
            synchronized (SYNC) {
                if (instance == null) {
                    instance = new Settings();
                }
            }
        }
        return instance;
    }

    private Settings () {
        this.settingsBean = loadSettings();
    }

    /**
     * load settings file
     * @return bean that map settings file
     */
    private SettingsBean loadSettings () {
        InputStream stream = Settings.class.getResourceAsStream(SETTINGS);
        if (stream == null) {
            logger.error("Impossible to read settings file");
            return new SettingsBean();
        }

        JSONObject settingsJson = new JSONObject(new JSONTokener(stream));
        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        SettingsBean settingsBean;
        try {
            settingsBean = mapper.readValue (settingsJson.toString(), SettingsBean.class);
        } catch (IOException e) {
            logger.error("Impossible to initialize settings", e);
            return new SettingsBean();
        }

        return settingsBean;
    }

    public SettingsBean getSettingsBean() {
        return settingsBean;
    }
}
