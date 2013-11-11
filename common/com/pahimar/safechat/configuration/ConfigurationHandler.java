package com.pahimar.safechat.configuration;

import static net.minecraftforge.common.Configuration.CATEGORY_GENERAL;

import java.io.File;
import java.util.logging.Level;

import net.minecraftforge.common.Configuration;
import net.minecraftforge.common.Property;

import com.pahimar.safechat.lib.Reference;

import cpw.mods.fml.common.FMLLog;

/**
 * SafeChat
 * 
 * ConfigurationHandler
 * 
 * @author pahimar
 * @license GNU Public License v3 (http://www.gnu.org/licenses/gpl.html)
 * 
 */
public class ConfigurationHandler {

    private static Configuration configuration;
    
    private static final String CATEGORY_STRIKE_SYSTEM = "strike_system";

    public static void init(String configPath) {

        configuration = new Configuration(new File(configPath + "general.properties"));

        try {
            configuration.load();

            Settings.FILTER_MODE = configuration.get(CATEGORY_GENERAL, Settings.FILTER_MODE_CONFIGNAME, "" + Settings.FILTER_MODE_DEFAULT, Settings.FILTER_MODE_COMMENT, Property.Type.INTEGER).getInt(Settings.FILTER_MODE_DEFAULT);

            Settings.STRIKE_SYSTEM_ENABLED = configuration.get(CATEGORY_GENERAL, Settings.STRIKE_SYSTEM_ENABLED_CONFIGNAME, "" + Settings.STRIKE_SYSTEM_ENABLED_DEFAULT, Settings.STRIKE_SYSTEM_ENABLED_COMMENT, Property.Type.BOOLEAN).getBoolean(Settings.STRIKE_SYSTEM_ENABLED_DEFAULT);

            Settings.STRIKE_TIME_TO_EXPIRATION = configuration.get(CATEGORY_STRIKE_SYSTEM, Settings.STRIKE_TIME_TO_EXPIRATION_CONFIGNAME, "" + Settings.STRIKE_TIME_TO_EXPIRATION_DEFAULT, Settings.STRIKE_TIME_TO_EXPIRATION_COMMENT, Property.Type.INTEGER).getInt(Settings.STRIKE_TIME_TO_EXPIRATION_DEFAULT);
            
            Settings.MAX_STRIKES_ALLOWED = configuration.get(CATEGORY_STRIKE_SYSTEM, Settings.MAX_STRIKES_ALLOWED_CONFIGNAME, "" + Settings.MAX_STRIKES_ALLOWED_DEFAULT, Settings.MAX_STRIKES_ALLOWED_COMMENT, Property.Type.INTEGER).getInt(Settings.MAX_STRIKES_ALLOWED_DEFAULT);

            Settings.EXCEEDS_MAX_STRIKES_ACTION = configuration.get(CATEGORY_STRIKE_SYSTEM, Settings.EXCEEDS_MAX_STRIKES_ACTION_CONFIGNAME, "" + Settings.EXCEEDS_MAX_STRIKES_ACTION_DEFAULT, Settings.EXCEEDS_MAX_STRIKES_ACTION_COMMENT, Property.Type.INTEGER).getInt(Settings.EXCEEDS_MAX_STRIKES_ACTION_DEFAULT);

            Settings.EXCEEDS_MAX_STRIKES_ACTION_DURATION = configuration.get(CATEGORY_STRIKE_SYSTEM, Settings.EXCEEDS_MAX_STRIKES_ACTION_DURATION_CONFIGNAME, "" + Settings.EXCEEDS_MAX_STRIKES_ACTION_DURATION_DEFAULT, Settings.EXCEEDS_MAX_STRIKES_ACTION_DURATION_COMMENT, Property.Type.INTEGER).getInt(Settings.EXCEEDS_MAX_STRIKES_ACTION_DURATION_DEFAULT);
        }
        catch (Exception e) {
            FMLLog.log(Level.SEVERE, e, Reference.MOD_NAME + " has had a problem loading its general configuration");
        }
        finally {
            configuration.save();
        }
    }

    public static void set(String categoryName, String propertyName, String newValue) {

        configuration.load();
        if (configuration.getCategoryNames().contains(categoryName)) {
            if (configuration.getCategory(categoryName).containsKey(propertyName)) {
                configuration.getCategory(categoryName).get(propertyName).set(newValue);
            }
        }
        configuration.save();
    }
}