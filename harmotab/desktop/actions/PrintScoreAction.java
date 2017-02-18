// 
// Decompiled by Procyon v0.5.30
// 

package harmotab.desktop.actions;

import harmotab.desktop.DesktopController;
import harmotab.desktop.ActionIcon;
import harmotab.core.Localizer;

public class PrintScoreAction extends UserAction
{
    private static final long serialVersionUID = 1L;
    
    public PrintScoreAction() {
        super(Localizer.get("MENU_PRINT"), ActionIcon.getIcon((byte)9));
    }
    
    @Override
    public void run() {
        DesktopController.getInstance().getScoreController().print();
    }
}
