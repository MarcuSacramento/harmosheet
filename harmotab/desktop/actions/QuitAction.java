// 
// Decompiled by Procyon v0.5.30
// 

package harmotab.desktop.actions;

import harmotab.core.ScoreController;
import harmotab.desktop.DesktopController;
import harmotab.desktop.ActionIcon;
import harmotab.core.Localizer;

public class QuitAction extends UserAction
{
    private static final long serialVersionUID = 1L;
    
    public QuitAction() {
        super(Localizer.get("MENU_QUIT"), ActionIcon.getIcon((byte)1));
    }
    
    @Override
    public void run() {
        final ScoreController controller = DesktopController.getInstance().getScoreController();
        if (controller.close()) {
            System.exit(0);
        }
    }
}
