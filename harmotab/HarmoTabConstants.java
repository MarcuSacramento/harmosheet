// 
// Decompiled by Procyon v0.5.30
// 

package harmotab;

import harmotab.core.GlobalPreferences;

public class HarmoTabConstants
{
    public static final float HT_VERSION = 3.1f;
    public static final String HT_WELCOME_PAGE;
    public static final String LOCALIZATION_FILE_NAME = "localization";
    public static final String LOCALIZATION_FOLDER = "res.i18n.";
    public static final int PLAYER_OBSERVER_REFRESH_PERIOD_MS = 40;
    public static final String SERIALIZATION_ID_ATTR = "id";
    public static final int DEFAULT_SCORE_WIDTH = 900;
    
    static {
        HT_WELCOME_PAGE = "http://www.harmotab.com/infos.php?lang=" + GlobalPreferences.getLanguage() + "&vers=" + getVersionString();
    }
    
    public static String getVersionString() {
        return String.valueOf(3.1f);
    }
}
