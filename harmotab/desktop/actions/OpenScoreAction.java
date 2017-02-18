// 
// Decompiled by Procyon v0.5.30
// 

package harmotab.desktop.actions;

import harmotab.core.ScoreController;
import harmotab.throwables.ScoreIoException;
import harmotab.desktop.ErrorMessenger;
import harmotab.throwables.FileFormatException;
import harmotab.desktop.RecentFilesManager;
import harmotab.desktop.DesktopController;
import harmotab.desktop.ActionIcon;
import harmotab.core.Localizer;

public class OpenScoreAction extends UserAction
{
    private static final long serialVersionUID = 1L;
    private String m_filepath;
    
    public OpenScoreAction() {
        super(Localizer.get("MENU_OPEN_SCORE"), ActionIcon.getIcon((byte)0));
        this.m_filepath = null;
    }
    
    public OpenScoreAction(final String path) {
        this();
        this.m_filepath = path;
    }
    
    @Override
    public void run() {
        final ScoreController controller = DesktopController.getInstance().getScoreController();
        try {
            boolean openned = false;
            if (this.m_filepath != null) {
                openned = controller.open(this.m_filepath);
            }
            else {
                openned = controller.open();
            }
            if (openned) {
                RecentFilesManager.getInstance().addRecentFile(controller.getCurrentScoreWriter().getFile().getAbsolutePath());
            }
        }
        catch (ScoreIoException exception) {
            if (exception.getCause() != null && exception.getCause() instanceof FileFormatException) {
                ErrorMessenger.showErrorMessage(Localizer.get("M_FILE_FORMAT_ERROR"));
            }
            else {
                ErrorMessenger.showErrorMessage(Localizer.get("M_FILE_OPENING_ERROR").replace("%FILE%", exception.getFilePath()));
            }
        }
    }
}
