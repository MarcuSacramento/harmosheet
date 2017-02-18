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

public class ExportAsHt3xAction extends UserAction
{
    private static final long serialVersionUID = 1L;
    
    public ExportAsHt3xAction() {
        super(Localizer.get("MENU_EXPORT_AS_HT3X"), ActionIcon.getIcon((byte)24));
    }
    
    @Override
    public void run() {
        final ScoreController scoreController = DesktopController.getInstance().getScoreController();
        final File exportedFile = scoreController.exportAsExportedScore();
        if (exportedFile != null) {
            RecentFilesManager.getInstance().addRecentFile(exportedFile.getAbsolutePath());
        }
    }
}
