// 
// Decompiled by Procyon v0.5.30
// 

package harmotab.desktop.actions;

import harmotab.core.ScoreController;
import harmotab.core.Height;
import java.awt.Window;
import harmotab.desktop.setupdialog.ScoreSetupDialog;
import harmotab.desktop.DesktopController;
import harmotab.desktop.ActionIcon;
import harmotab.core.Localizer;

public class ShowScorePropertiesAction extends UserAction
{
    private static final long serialVersionUID = 1L;
    
    public ShowScorePropertiesAction() {
        super(Localizer.get("MENU_SCORE_PROPERTIES"), ActionIcon.getIcon((byte)22));
        this.setLittleIcon(ActionIcon.getIcon((byte)23));
    }
    
    @Override
    public void run() {
        final ScoreController controller = DesktopController.getInstance().getScoreController();
        final ScoreSetupDialog setupDlg = new ScoreSetupDialog(null, controller);
        setupDlg.setSelectedTabMappingHeight(new Height());
        setupDlg.setVisible(true);
    }
}
