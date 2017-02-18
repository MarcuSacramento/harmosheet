// 
// Decompiled by Procyon v0.5.30
// 

package harmotab.desktop;

import java.util.Iterator;
import java.util.prefs.Preferences;
import java.util.ArrayList;

public class RecentFilesManager
{
    public static final int MAX_NUMBER_OF_RECENT_FILES = 10;
    private static final String RECENT_FILE = "HT_RECENT_FILE_";
    private static RecentFilesManager m_instance;
    private ArrayList<String> m_list;
    
    static {
        RecentFilesManager.m_instance = null;
    }
    
    private RecentFilesManager() {
        this.m_list = null;
        this.m_list = new ArrayList<String>();
        this.load();
    }
    
    public static synchronized RecentFilesManager getInstance() {
        if (RecentFilesManager.m_instance == null) {
            RecentFilesManager.m_instance = new RecentFilesManager();
        }
        return RecentFilesManager.m_instance;
    }
    
    public void addRecentFile(final String filepath) {
        if (this.m_list.contains(filepath)) {
            this.m_list.remove(filepath);
        }
        this.m_list.add(0, filepath);
        if (this.m_list.size() > 10) {
            this.m_list.remove(this.m_list.size() - 1);
        }
        this.save();
    }
    
    public ArrayList<String> getRecentFiles() {
        return this.m_list;
    }
    
    private void save() {
        final Preferences prefs = Preferences.userRoot();
        int fileIndex = 0;
        for (final String path : this.m_list) {
            prefs.put("HT_RECENT_FILE_" + fileIndex, path);
            ++fileIndex;
        }
    }
    
    private void load() {
        final Preferences prefs = Preferences.userRoot();
        this.m_list.clear();
        for (int i = 0; i < 10; ++i) {
            final String current = prefs.get("HT_RECENT_FILE_" + i, null);
            if (current != null) {
                this.m_list.add(current);
            }
        }
    }
    
    public void reset() {
        final Preferences prefs = Preferences.userRoot();
        for (int i = 0; i < 10; ++i) {
            final String current = prefs.get("HT_RECENT_FILE_" + i, null);
            prefs.remove(current);
        }
        this.m_list.clear();
    }
}
