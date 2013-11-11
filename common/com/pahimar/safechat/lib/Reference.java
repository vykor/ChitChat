package com.pahimar.safechat.lib;

/**
 * SafeChat
 * 
 * Reference
 * 
 * @author pahimar
 * @license GNU Public License v3 (http://www.gnu.org/licenses/gpl.html)
 * 
 */
public class Reference {

    /* General @Mod related constants */
    public static final String MOD_ID = "SafeChat";
    public static final String MOD_NAME = "SafeChat";
    public static final String VERSION_NUMBER = "@VERSION@ (build @BUILD_NUMBER@)";
    public static final String FINGERPRINT = "@FINGERPRINT@";
    
    /* Locations of the blacklists */
    public static final String DEFAULT_BLACKLIST_FILE_LOCATION = "/assets/safechat/lang/defaultCurseBlackList.lang";
    public static final String CONFIG_BLACKLIST_FILE_LOCATION = "";
    
    /* Filter mode related constants */
    public static final int FILTER_MODE_NONE = 0;
    public static final int FILTER_MODE_REPLACE = 1;
    public static final int FILTER_MODE_HIDE = 2;
    
    /* Action related constants */
    public static final int ACTION_NOTHING = 0;
    public static final int ACTION_KICK = 1;
    public static final int ACTION_DISABLE_CHAT = 2;
    public static final int ACTION_TIME_OUT = 3;
    public static final int ACTION_BAN = 4;
}