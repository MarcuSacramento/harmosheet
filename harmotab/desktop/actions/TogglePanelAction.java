// 
// Decompiled by Procyon v0.5.30
// 

package harmotab.desktop.actions;

import harmotab.desktop.Gui;
import harmotab.desktop.DesktopController;
import javax.swing.ImageIcon;
import harmotab.core.Localizer;

public class TogglePanelAction extends UserAction
{
    private static final long serialVersionUID = 1L;
    
    public TogglePanelAction() {
        super(Localizer.get("MENU_SHOW_NAVIGATION_PANEL"), null);
    }
    
    @Override
    public void run() {
        final Gui gui = DesktopController.getInstance().getGuiWindow();
        gui.setNavigationPanelVisible(!gui.isBrowsersPaneVisible());
    }
}
