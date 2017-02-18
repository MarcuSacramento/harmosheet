// 
// Decompiled by Procyon v0.5.30
// 

package harmotab.io.score;

import java.awt.Component;
import javax.swing.JOptionPane;
import harmotab.core.Localizer;
import java.io.IOException;
import java.io.File;
import harmotab.core.Score;

public abstract class ScoreWriter extends ScoreIO
{
    public ScoreWriter(final Score score, final String path) {
        super(score, path);
    }
    
    protected abstract void write(final Score p0, final File p1) throws IOException;
    
    public void saveAs(final String path) throws IOException {
        final ScoreWriter writer = (ScoreWriter)this.clone();
        final File outputFile = new File(path);
        if (outputFile.exists()) {
            final int res = JOptionPane.showConfirmDialog(null, Localizer.get("M_FILE_ALREADY_EXISTS_QUESTION").replace("%FILE%", path), Localizer.get("MENU_SAVE_AS"), 0);
            if (res != 0) {
                return;
            }
        }
        writer.setFile(outputFile);
        writer.save();
    }
    
    public void save() throws IOException {
        this.write(this.m_score, this.m_file);
    }
}
