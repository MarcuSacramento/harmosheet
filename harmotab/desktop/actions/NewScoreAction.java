// 
// Decompiled by Procyon v0.5.30
// 

package harmotab.desktop.actions;

import harmotab.core.ScoreController;
import harmotab.core.undo.UndoManager;
import java.awt.Window;
import harmotab.desktop.setupdialog.ScoreSetupDialog;
import harmotab.desktop.DesktopController;
import harmotab.desktop.ActionIcon;
import harmotab.core.Localizer;

public class NewScoreAction extends UserAction
{
    private static final long serialVersionUID = 1L;
    
    public NewScoreAction() {
        super(Localizer.get("MENU_NEW_SCORE"), ActionIcon.getIcon((byte)4));
    }
    
    @Override
    public void run() {
        final ScoreController controller = DesktopController.getInstance().getScoreController();
        controller.createNewDefaultScore();
        final ScoreSetupDialog setupDlg = new ScoreSetupDialog(null, controller);
        setupDlg.setVisible(true);
        UndoManager.getInstance().reset();
    }
}
