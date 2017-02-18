// 
// Decompiled by Procyon v0.5.30
// 

package harmotab.io.score;

import harmotab.performance.Ht3XPerformanceWriter;
import harmotab.performance.PerformancesList;
import harmotab.performance.Ht3XScoreReader;
import harmotab.core.Score;
import harmotab.desktop.ErrorMessenger;
import javax.swing.JOptionPane;
import harmotab.core.Localizer;
import java.io.File;
import javax.swing.filechooser.FileFilter;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.JFileChooser;
import harmotab.core.ScoreController;
import java.awt.Component;

public class ScoreIOUtilities
{
    public static ScoreWriter saveScore(final Component parent, final ScoreController controller, ScoreWriter writer) {
        final JFileChooser chooser = new JFileChooser();
        chooser.setFileFilter(new FileNameExtensionFilter("HarmoTab3 score", new String[] { "ht3" }));
        chooser.setSelectedFile(new File(controller.getScore().getScoreName()));
        if (writer == null && chooser.showSaveDialog(parent) == 0) {
            File file = chooser.getSelectedFile();
            if (!file.getPath().endsWith(".ht3")) {
                file = new File(String.valueOf(file.getPath()) + ".ht3");
            }
            if (file.exists()) {
                final int res = JOptionPane.showConfirmDialog(parent, Localizer.get("M_FILE_ALREADY_EXISTS_QUESTION").replace("%FILE%", file.getPath()), Localizer.get("MENU_SAVE_AS"), 0);
                if (res != 0) {
                    return null;
                }
            }
            writer = new HT3ScoreWriter(controller.getScore(), file.getPath());
        }
        if (writer != null) {
            try {
                writer.save();
                return writer;
            }
            catch (Exception e) {
                e.printStackTrace();
                ErrorMessenger.showErrorMessage(Localizer.get("M_ERROR_CREATING_FILE").replace("%FILE%", writer.getFile().getPath()));
            }
        }
        return null;
    }
    
    public static ScoreReader createScoreReader(final Score score, final String path) {
        if (path.endsWith(".htb")) {
            return new HTBScoreReader(score, path);
        }
        if (path.endsWith(".ht3")) {
            return new HT3ScoreReader(score, path);
        }
        if (path.endsWith(".ht3x")) {
            return new Ht3XScoreReader(score, path);
        }
        return new HT3ScoreReader(score, path);
    }
    
    public static ScoreWriter createScoreWriter(final Score score, final String path) {
        if (path.endsWith(".ht3")) {
            return new HT3ScoreWriter(score, path);
        }
        return null;
    }
    
    public static ScoreWriter createScoreWriter(final Score score, final String path, final PerformancesList perfs) {
        if (path.endsWith(".ht3x") && perfs != null) {
            return new Ht3XPerformanceWriter(score, path, perfs);
        }
        return createScoreWriter(score, path);
    }
    
    public static class ReadableScoreFileFilter implements FileFilter
    {
        
        public boolean accept( File file) {
            final String name = file.getName();
            return name.endsWith(".ht3") || name.endsWith(".htb") || name.endsWith(".ht3x");
        }
    }
}
