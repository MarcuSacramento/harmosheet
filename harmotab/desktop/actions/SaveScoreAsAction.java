// 
// Decompiled by Procyon v0.5.30
// 

package harmotab.desktop.actions;

import java.io.File;
import harmotab.core.ScoreController;
import harmotab.desktop.RecentFilesManager;
import harmotab.desktop.DesktopController;
import harmotab.desktop.ActionIcon;
import harmotab.core.Localizer;

public class SaveScoreAsAction extends UserAction
{
    private static final long serialVersionUID = 1L;
    
    public SaveScoreAsAction() {
        super(Localizer.get("MENU_SAVE_AS"), ActionIcon.getIcon((byte)3));
    }
    
    @Override
    public void run() {
        DesktopController.getInstance().getScoreController().saveScoreAs();
        try {
            final ScoreController scoreController = DesktopController.getInstance().getScoreController();
            final File file = scoreController.getCurrentScoreWriter().getFile();
            RecentFilesManager.getInstance().addRecentFile(file.getAbsolutePath());
        }
        catch (Throwable t) {}
    }
}
