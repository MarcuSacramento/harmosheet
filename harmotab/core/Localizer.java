// 
// Decompiled by Procyon v0.5.30
// 

package harmotab.core;

import javax.swing.event.ChangeEvent;
import harmotab.desktop.ErrorMessenger;
import java.util.Locale;
import javax.swing.event.ChangeListener;
import java.util.ResourceBundle;

public class Localizer
{
    private static ResourceBundle m_resourceBundle;
    
    static {
        loadLocale();
        GlobalPreferences.addChangeListener(new PreferencesObserver());
        Localizer.m_resourceBundle = null;
    }
    
    private static synchronized void loadLocale() {
        try {
            Localizer.m_resourceBundle = ResourceBundle.getBundle("res.i18n.localization", new Locale(GlobalPreferences.getLanguage()));
        }
        catch (Exception e1) {
            e1.printStackTrace();
            try {
                Localizer.m_resourceBundle = ResourceBundle.getBundle("res.i18n.localization", new Locale(GlobalPreferences.DEFAULT_LANGUAGE));
            }
            catch (Exception e2) {
                e2.printStackTrace();
                ErrorMessenger.showErrorMessage("Cannot load resource file res.i18n.localization.");
                System.exit(1);
            }
        }
    }
    
    public static String get(final String key) {
        if (Localizer.m_resourceBundle == null) {
            loadLocale();
        }
        try {
            return Localizer.m_resourceBundle.getString(key);
        }
        catch (Exception e) {
            return "<error>";
        }
    }
    
    private static class PreferencesObserver implements ChangeListener
    {
        @Override
        public void stateChanged(final ChangeEvent event) {
            loadLocale();
        }
    }
}
