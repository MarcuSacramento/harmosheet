// 
// Decompiled by Procyon v0.5.30
// 

package harmotab.io.score;

import harmotab.performance.PerformancesList;
import harmotab.core.GlobalPreferences;
import harmotab.throwables.FileFormatException;
import java.io.IOException;
import java.io.File;
import harmotab.core.Score;

public abstract class ScoreReader extends ScoreIO
{
    public ScoreReader(final Score score, final String path) {
        super(score, path);
    }
    
    protected abstract void read(final Score p0, final File p1) throws IOException, FileFormatException;
    
    public void open() throws IOException, FileFormatException {
        IOException exception = null;
        final boolean autoTabEnabled = GlobalPreferences.isAutoTabEnabled();
        GlobalPreferences.setAutoTabEnabled(false);
        try {
            this.read(this.m_score, this.m_file);
        }
        catch (IOException x) {
            exception = new IOException(x);
        }
        GlobalPreferences.setAutoTabEnabled(autoTabEnabled);
        if (exception != null) {
            throw exception;
        }
    }
    
    public boolean isExportedScore() {
        return false;
    }
    
    public PerformancesList getPerformancesList() {
        return null;
    }
}
