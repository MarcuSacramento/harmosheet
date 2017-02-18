// 
// Decompiled by Procyon v0.5.30
// 

package harmotab.desktop.actions;

import java.io.File;
import harmotab.core.ScoreController;
import harmotab.throwables.ScoreIoException;
import harmotab.desktop.ErrorMessenger;
import harmotab.desktop.RecentFilesManager;
import java.awt.Component;
import javax.swing.JOptionPane;
import harmotab.desktop.DesktopController;
import harmotab.desktop.ActionIcon;
import harmotab.core.Localizer;

public class RecordPerformanceFromHt3Action extends UserAction
{
    private static final long serialVersionUID = 1L;
    
    public RecordPerformanceFromHt3Action() {
        super(Localizer.get("MENU_RECORD"), ActionIcon.getIcon((byte)28));
    }
    
    @Override
    public void run() {
        final ScoreController controller = DesktopController.getInstance().getScoreController();
        final int res = JOptionPane.showConfirmDialog(null, Localizer.get("M_RECORD_FROM_SCORE_CONFIRMATION"), Localizer.get("N_RECORDING"), 0);
        if (res == 0) {
            if (controller.saveScore()) {
                final File exportedFile = controller.exportAsExportedScore();
                if (exportedFile != null) {
                    RecentFilesManager.getInstance().addRecentFile(exportedFile.getAbsolutePath());
                    if (controller.close()) {
                        try {
                            controller.open(exportedFile.getAbsolutePath());
                        }
                        catch (ScoreIoException e) {
                            ErrorMessenger.showErrorMessage("Error opening ht3x file.");
                            controller.close();
                        }
                    }
                    else {
                        System.err.println("Error closing score.");
                    }
                }
                else {
                    ErrorMessenger.showErrorMessage("Error exporting score as ht3x file.");
                }
            }
            else {
                ErrorMessenger.showErrorMessage("Error saving score file.");
            }
        }
    }
}
