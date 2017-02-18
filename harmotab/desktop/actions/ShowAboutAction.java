// 
// Decompiled by Procyon v0.5.30
// 

package harmotab.desktop.actions;

import harmotab.desktop.AboutDialog;
import harmotab.desktop.ActionIcon;
import harmotab.core.Localizer;

public class ShowAboutAction extends UserAction
{
    private static final long serialVersionUID = 1L;
    
    public ShowAboutAction() {
        super(Localizer.get("MENU_ABOUT"), ActionIcon.getIcon((byte)11));
    }
    
    @Override
    public void run() {
        new AboutDialog();
    }
}
