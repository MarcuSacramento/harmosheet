// 
// Decompiled by Procyon v0.5.30
// 

package harmotab.core;

import java.io.File;
import javax.swing.filechooser.FileSystemView;
import java.util.Locale;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.util.prefs.Preferences;
import javax.swing.event.EventListenerList;

public class GlobalPreferences
{
    private static final String LANGUAGE = "HT_LANGUAGE";
    private static final String USE_SYSTEM_APPEARANCE = "HT_USE_SYSTEM_APPEARANCE";
    private static final String AUTO_TAB_ENABLED = "HT_AUTO_TAB_ENABLED";
    private static final String AUTO_NOTE_ENABLED = "HT_AUTO_NOTE_ENABLED";
    private static final String TAB_MAPPING_COMPLETION_ENABLED = "HT_TAB_MAPPING_COMPLETION_ENABLED";
    private static final String BAR_NUMBERS_DISPLAYED = "HT_BAR_NUMBERS_DISPLAYED";
    private static final String EDITING_HELPERS_DISPLAYED = "HT_EDITING_HELPERS_DISPLAYED";
    private static final String TAB_STYLE = "HT_TAB_STYLE";
    private static final String TAB_BLOW_DIRECTION = "HT_TAB_BLOW_DIRECTION";
    private static final String MODELS_FOLDER = "HT_MODELS_FOLDER";
    private static final String MIDI_OUTPUT = "HT_MIDI_OUTPUT";
    private static final String GLOBAL_VOLUME = "HT_GLOBAL_VOLUME";
    private static final String PLAYBACK_COUNTDOWN_ENABLED = "PLAYBACK_COUNTDOWN_ENABLED";
    private static final String NETWORK_ENABLED = "HT_NETWORK_ENABLED";
    private static final String PERFORMANCES_FEATURE_ENABLED = "HT_PERFORMANCES_FEATURE_ENABLED";
    private static final String METRONOME_FEATURE_ENABLED = "HT_METRONOME_FEATURE_ENABLED";
    public static final String DEFAULT_LANGUAGE;
    private static final boolean DEFAULT_USE_SYSTEM_APPEARANCE = true;
    private static final boolean DEFAULT_AUTO_TAB_ENABLED = true;
    private static final boolean DEFAULT_AUTO_NOTE_ENABLED = true;
    private static final boolean DEFAULT_TAB_MAPPING_COMPLETION_ENABLED = true;
    private static final boolean DEFAULT_BAR_NUMBERS_DISPLAYED = false;
    private static final boolean DEFAULT_EDITING_HELPERS_DISPLAYED = true;
    private static final int DEFAULT_TAB_STYLE = 0;
    private static final int DEFAULT_TAB_BLOW_DIRECTION = 0;
    private static final String DEFAULT_MODELS_FOLDER;
    private static final String DEFAULT_MIDI_OUTPUT = "";
    private static final int DEFAULT_GLOBAL_VOLUME = 100;
    private static final boolean DEFAULT_NETWORK_ENABLED = true;
    private static final boolean DEFAULT_BETA_FEATURE_ENABLED = false;
    private static final boolean DEFAULT_PLAYBACK_COUNTDOWN_ENABLED = false;
    private static final String WINDOW_WIDTH = "HT_WINDOW_WIDTH";
    private static final String WINDOW_HEIGHT = "HT_WINDOW_HEIGHT";
    private static final String WINDOW_MAXIMIZED = "HT_WINDOW_MAXIMIZED";
    private static final String SCORES_BROWSING_FOLDER = "HT_SCORES_BROWSING_FOLDER";
    private static final String METRONOME_ENABLED = "HT_METRONOME_ENABLED";
    private static final int DEFAULT_WINDOW_WIDTH = 1024;
    private static final int DEFAULT_WINDOW_HEIGHT = 768;
    private static final boolean DEFAULT_WINDOW_MAXIMIZED = true;
    private static final String DEFAULT_SCORES_BROWSING_FOLDER;
    private static final boolean DEFAULT_METRONOME_ENABLED = false;
    public static final int BLOW_UP = 1;
    public static final int BLOW_DOWN = 2;
    private static GlobalPreferences m_instance;
    private EventListenerList m_listeners;
    private String m_language;
    private boolean m_useSystemAppearance;
    private boolean m_autoTabElabeld;
    private boolean m_autoNoteEnabled;
    private boolean m_tabMappingCompletionEnabled;
    private boolean m_barNumbersDisplayed;
    private boolean m_editingHelpersDisplayed;
    private int m_tabStyle;
    private int m_tabBlowDirection;
    private String m_midiOutput;
    private int m_globalVolume;
    private boolean m_playbackCountdownEnabed;
    private String m_modelsFolder;
    private boolean m_networkEnabled;
    private boolean m_performancesFeatureEnabled;
    private boolean m_metronomeFeatureEnabled;
    
    static {
        DEFAULT_LANGUAGE = getDefaultLanguage();
        DEFAULT_MODELS_FOLDER = getDefaultModelsDirectory();
        DEFAULT_SCORES_BROWSING_FOLDER = getSamplesDirectory();
        GlobalPreferences.m_instance = null;
    }
    
    private static synchronized GlobalPreferences getInstance() {
        if (GlobalPreferences.m_instance == null) {
            GlobalPreferences.m_instance = new GlobalPreferences();
        }
        return GlobalPreferences.m_instance;
    }
    
    private GlobalPreferences() {
        this.m_listeners = new EventListenerList();
        this.read();
    }
    
    public static void restoreDefaultPreferences() {
        final GlobalPreferences prefs = getInstance();
        prefs.m_language = GlobalPreferences.DEFAULT_LANGUAGE;
        prefs.m_useSystemAppearance = true;
        prefs.m_autoTabElabeld = true;
        prefs.m_autoNoteEnabled = true;
        prefs.m_tabMappingCompletionEnabled = true;
        prefs.m_barNumbersDisplayed = false;
        prefs.m_tabStyle = 0;
        prefs.m_midiOutput = "";
        prefs.m_globalVolume = 100;
        prefs.m_playbackCountdownEnabed = false;
        prefs.m_modelsFolder = GlobalPreferences.DEFAULT_MODELS_FOLDER;
        prefs.m_networkEnabled = true;
        prefs.m_performancesFeatureEnabled = false;
        prefs.m_metronomeFeatureEnabled = false;
        save();
    }
    
    public static String getLanguage() {
        return getInstance().m_language;
    }
    
    public static void setLanguage(final String language) {
        getInstance().m_language = language;
    }
    
    public static boolean useSystemAppearance() {
        return getInstance().m_useSystemAppearance;
    }
    
    public static void useSystemAppearance(final boolean useIt) {
        getInstance().m_useSystemAppearance = useIt;
    }
    
    public static boolean isAutoTabEnabled() {
        return getInstance().m_autoTabElabeld;
    }
    
    public static void setAutoTabEnabled(final boolean enabled) {
        getInstance().m_autoTabElabeld = enabled;
    }
    
    public static boolean isAutoNoteEnabled() {
        return getInstance().m_autoNoteEnabled;
    }
    
    public static void setAutoNoteEnabled(final boolean enabled) {
        getInstance().m_autoNoteEnabled = enabled;
    }
    
    public static boolean isTabMappingCompletionEnabled() {
        return getInstance().m_tabMappingCompletionEnabled;
    }
    
    public static void setTabMappingCompletionEnabled(final boolean enabled) {
        getInstance().m_tabMappingCompletionEnabled = enabled;
    }
    
    public static boolean isBarNumbersDisplayed() {
        return getInstance().m_barNumbersDisplayed;
    }
    
    public static void setBarNumbersDisplayed(final boolean displayed) {
        getInstance().m_barNumbersDisplayed = displayed;
    }
    
    public static boolean isEditingHelpersDisplayed() {
        return getInstance().m_editingHelpersDisplayed;
    }
    
    public static void setEditingHelpersDisplayed(final boolean displayed) {
        getInstance().m_editingHelpersDisplayed = displayed;
    }
    
    public static int getTabStyle() {
        return getInstance().m_tabStyle;
    }
    
    public static void setTabStyle(final int tabStyle) {
        getInstance().m_tabStyle = tabStyle;
    }
    
    public static int getTabBlowDirection() {
        return getInstance().m_tabBlowDirection;
    }
    
    public static void setTabBlowDirection(final int direction) {
        getInstance().m_tabBlowDirection = direction;
    }
    
    public static String getMidiOutput() {
        return getInstance().m_midiOutput;
    }
    
    public static void setMidiOutput(final String output) {
        getInstance().m_midiOutput = output;
    }
    
    public static int getGlobalVolume() {
        return getInstance().m_globalVolume;
    }
    
    public static void setMidiGlobalVolume(final int volume) {
        getInstance().m_globalVolume = volume;
    }
    
    public static boolean getPlaybackCountdownEnabled() {
        return getInstance().m_playbackCountdownEnabed;
    }
    
    public static void setPlaybackCountdownEnabeld(final boolean enabled) {
        getInstance().m_playbackCountdownEnabed = enabled;
    }
    
    public static String getModelsFolder() {
        return getInstance().m_modelsFolder;
    }
    
    public static void setModelsFolder(final String path) {
        getInstance().m_modelsFolder = path;
    }
    
    public static boolean isNetworkEnabled() {
        return getInstance().m_networkEnabled;
    }
    
    public static void setNetworkEnabled(final boolean enabled) {
        getInstance().m_networkEnabled = enabled;
    }
    
    public static boolean getPerformancesFeatureEnabled() {
        return getInstance().m_performancesFeatureEnabled;
    }
    
    public static void setPerformancesFeatureEnabled(final boolean enabled) {
        getInstance().m_performancesFeatureEnabled = enabled;
    }
    
    public static boolean getMetronomeFeatureEnabled() {
        return getInstance().m_metronomeFeatureEnabled;
    }
    
    public static void setMetronomeFeatureEnabled(final boolean enabled) {
        if (!(getInstance().m_metronomeFeatureEnabled = enabled)) {
            setMetronomeEnabled(false);
        }
    }
    
    private static Preferences getAutoPrefs() {
        return Preferences.userRoot();
    }
    
    public static int getWindowWidth() {
        return getAutoPrefs().getInt("HT_WINDOW_WIDTH", 1024);
    }
    
    public static int getWindowHeight() {
        return getAutoPrefs().getInt("HT_WINDOW_HEIGHT", 768);
    }
    
    public static void setWindowSize(final int width, final int height) {
        final Preferences prefs = getAutoPrefs();
        prefs.putInt("HT_WINDOW_WIDTH", width);
        prefs.putInt("HT_WINDOW_HEIGHT", height);
    }
    
    public static boolean getWindowMaximized() {
        return getAutoPrefs().getBoolean("HT_WINDOW_MAXIMIZED", true);
    }
    
    public static void setWindowMaximized(final boolean maximized) {
        getAutoPrefs().putBoolean("HT_WINDOW_MAXIMIZED", maximized);
    }
    
    public static String getScoresBrowsingFolder() {
        return getAutoPrefs().get("HT_SCORES_BROWSING_FOLDER", GlobalPreferences.DEFAULT_SCORES_BROWSING_FOLDER);
    }
    
    public static void setScoresBrowsingFolder(final String path) {
        getAutoPrefs().put("HT_SCORES_BROWSING_FOLDER", path);
    }
    
    public static boolean getMetronomeEnabled() {
        return getAutoPrefs().getBoolean("HT_METRONOME_ENABLED", false);
    }
    
    public static void setMetronomeEnabled(final boolean enabled) {
        getAutoPrefs().putBoolean("HT_METRONOME_ENABLED", enabled);
    }
    
    private synchronized void read() {
        final Preferences prefs = Preferences.userRoot();
        this.m_language = prefs.get("HT_LANGUAGE", GlobalPreferences.DEFAULT_LANGUAGE);
        this.m_useSystemAppearance = prefs.getBoolean("HT_USE_SYSTEM_APPEARANCE", true);
        this.m_autoTabElabeld = prefs.getBoolean("HT_AUTO_TAB_ENABLED", true);
        this.m_autoNoteEnabled = prefs.getBoolean("HT_AUTO_NOTE_ENABLED", true);
        this.m_tabMappingCompletionEnabled = prefs.getBoolean("HT_TAB_MAPPING_COMPLETION_ENABLED", true);
        this.m_barNumbersDisplayed = prefs.getBoolean("HT_BAR_NUMBERS_DISPLAYED", false);
        this.m_editingHelpersDisplayed = prefs.getBoolean("HT_EDITING_HELPERS_DISPLAYED", true);
        this.m_tabStyle = prefs.getInt("HT_TAB_STYLE", 0);
        this.m_tabBlowDirection = prefs.getInt("HT_TAB_BLOW_DIRECTION", 0);
        this.m_midiOutput = prefs.get("HT_MIDI_OUTPUT", "");
        this.m_globalVolume = prefs.getInt("HT_GLOBAL_VOLUME", 100);
        this.m_playbackCountdownEnabed = prefs.getBoolean("PLAYBACK_COUNTDOWN_ENABLED", false);
        this.m_modelsFolder = prefs.get("HT_MODELS_FOLDER", GlobalPreferences.DEFAULT_MODELS_FOLDER);
        this.m_networkEnabled = prefs.getBoolean("HT_NETWORK_ENABLED", true);
        this.m_performancesFeatureEnabled = prefs.getBoolean("HT_PERFORMANCES_FEATURE_ENABLED", false);
        this.m_metronomeFeatureEnabled = prefs.getBoolean("HT_METRONOME_FEATURE_ENABLED", false);
    }
    
    private synchronized void write() {
        final Preferences prefs = Preferences.userRoot();
        prefs.put("HT_LANGUAGE", this.m_language);
        prefs.putBoolean("HT_USE_SYSTEM_APPEARANCE", this.m_useSystemAppearance);
        prefs.putBoolean("HT_AUTO_TAB_ENABLED", this.m_autoTabElabeld);
        prefs.putBoolean("HT_AUTO_NOTE_ENABLED", this.m_autoNoteEnabled);
        prefs.putBoolean("HT_TAB_MAPPING_COMPLETION_ENABLED", this.m_tabMappingCompletionEnabled);
        prefs.putBoolean("HT_BAR_NUMBERS_DISPLAYED", this.m_barNumbersDisplayed);
        prefs.putBoolean("HT_EDITING_HELPERS_DISPLAYED", this.m_editingHelpersDisplayed);
        prefs.putInt("HT_TAB_STYLE", this.m_tabStyle);
        prefs.putInt("HT_TAB_BLOW_DIRECTION", this.m_tabBlowDirection);
        prefs.put("HT_MIDI_OUTPUT", this.m_midiOutput);
        prefs.putInt("HT_GLOBAL_VOLUME", this.m_globalVolume);
        prefs.putBoolean("PLAYBACK_COUNTDOWN_ENABLED", this.m_playbackCountdownEnabed);
        prefs.put("HT_MODELS_FOLDER", this.m_modelsFolder);
        prefs.putBoolean("HT_NETWORK_ENABLED", this.m_networkEnabled);
        prefs.putBoolean("HT_PERFORMANCES_FEATURE_ENABLED", this.m_performancesFeatureEnabled);
        prefs.putBoolean("HT_METRONOME_FEATURE_ENABLED", this.m_metronomeFeatureEnabled);
        this.firePreferenceChange();
    }
    
    public static void save() {
        GlobalPreferences.m_instance.write();
    }
    
    public static void addChangeListener(final ChangeListener listener) {
        getInstance().m_listeners.add(ChangeListener.class, listener);
    }
    
    public static void removeChangeListener(final ChangeListener listener) {
        getInstance().m_listeners.remove(ChangeListener.class, listener);
    }
    
    private void firePreferenceChange() {
        ChangeListener[] array;
        for (int length = (array = this.m_listeners.getListeners(ChangeListener.class)).length, i = 0; i < length; ++i) {
            final ChangeListener listener = array[i];
            listener.stateChanged(new ChangeEvent(this));
        }
    }
    
    private static String getDefaultLanguage() {
        final String lang = Locale.getDefault().getLanguage();
        if (lang.equals("en") || lang.equals("fr")) {
            return lang;
        }
        return "en";
    }
    
    public static String getUserDefaultDirectory() {
        return FileSystemView.getFileSystemView().getDefaultDirectory().getAbsolutePath();
    }
    
    private static String getSamplesDirectory() {
        return String.valueOf(System.getProperty("user.dir")) + File.separator + "samples" + File.separator;
    }
    
    private static String getDefaultModelsDirectory() {
        return String.valueOf(System.getProperty("user.dir")) + File.separator + "samples" + File.separator;
    }
}
