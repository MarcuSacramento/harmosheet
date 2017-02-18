// 
// Decompiled by Procyon v0.5.30
// 

package harmotab.desktop.actions;

import harmotab.desktop.DesktopController;
import harmotab.desktop.ActionIcon;
import harmotab.core.Localizer;

public class CloseScoreAction extends UserAction
{
    private static final long serialVersionUID = 1L;
    
    public CloseScoreAction() {
        super(Localizer.get("MENU_CLOSE"), ActionIcon.getIcon((byte)1));
    }
    
    @Override
    public void run() {
        DesktopController.getInstance().getScoreController().close();
    }
}
