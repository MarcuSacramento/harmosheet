// 
// Decompiled by Procyon v0.5.30
// 

package harmotab.desktop.actions;

import java.io.File;
import harmotab.core.ScoreController;
import harmotab.desktop.RecentFilesManager;
import harmotab.throwables.ScoreIoException;
import harmotab.desktop.ErrorMessenger;
import harmotab.desktop.DesktopController;
import harmotab.desktop.ActionIcon;
import harmotab.core.Localizer;

public class SaveAction extends UserAction
{
    private static final long serialVersionUID = 1L;
    
    public SaveAction() {
        super(Localizer.get("MENU_SAVE"), ActionIcon.getIcon((byte)2));
    }
    
    @Override
    public void run() {
        try {
            DesktopController.getInstance().getScoreController().save();
        }
        catch (ScoreIoException e) {
            ErrorMessenger.showErrorMessage(Localizer.get("M_FILE_WRITE_ERROR").replace("%FILE%", e.getFilePath()));
        }
        try {
            final ScoreController scoreController = DesktopController.getInstance().getScoreController();
            final File file = scoreController.getCurrentScoreWriter().getFile();
            RecentFilesManager.getInstance().addRecentFile(file.getAbsolutePath());
        }
        catch (Throwable t) {}
    }
}
