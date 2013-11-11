package com.pahimar.safechat;

import java.io.File;

import com.pahimar.safechat.blacklist.BlackList;
import com.pahimar.safechat.chat.ChatListener;
import com.pahimar.safechat.command.CommandHandler;
import com.pahimar.safechat.configuration.ConfigurationHandler;
import com.pahimar.safechat.helper.LogHelper;
import com.pahimar.safechat.lib.Reference;

import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.EventHandler;
import cpw.mods.fml.common.Mod.Instance;
import cpw.mods.fml.common.event.FMLFingerprintViolationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.event.FMLServerStartingEvent;
import cpw.mods.fml.common.network.NetworkMod;
import cpw.mods.fml.common.network.NetworkRegistry;

/**
 * SafeChat
 * 
 * SafeChat
 * 
 * @author pahimar
 * @license GNU Public License v3 (http://www.gnu.org/licenses/gpl.html)
 * 
 */
@Mod(modid = Reference.MOD_ID, name = Reference.MOD_NAME, version = Reference.VERSION_NUMBER, certificateFingerprint = Reference.FINGERPRINT)
@NetworkMod(clientSideRequired = false, serverSideRequired = false)
public class SafeChat {
    
    @Instance(com.pahimar.safechat.lib.Reference.MOD_ID)
    public static SafeChat instance;
    
    @EventHandler
    public void invalidFingerprint(FMLFingerprintViolationEvent event) {
        
        if (Reference.FINGERPRINT.equals("@FINGERPRINT@")) {
            LogHelper.warning("The copy of SafeChat that you are running is a development version of the mod, and as such may be unstable and/or incomplete.");
        }
        else {
            LogHelper.severe("The copy of SafeChat that you are running has been modified from the original, and unpredictable things may happen. Please consider re-downloading the original version of the mod.");
        }
    }
    
    @EventHandler
    public void serverStarting(FMLServerStartingEvent event) {
        
        CommandHandler.initCommands(event);
    }
    
    @EventHandler
    public void preInit(FMLPreInitializationEvent event) {
        
        // Initialize the log helper
        LogHelper.init();
        
        // Initialize the configuration 
        ConfigurationHandler.init(event.getModConfigurationDirectory().getAbsolutePath() + File.separator + Reference.MOD_ID.toLowerCase() + File.separator);
        
        // TODO Read in the blacklist from file
        for (String string : BlackList.getBlackList()) {
            LogHelper.debug(string);
        }
        
        NetworkRegistry.instance().registerChatListener(new ChatListener());
    }
}