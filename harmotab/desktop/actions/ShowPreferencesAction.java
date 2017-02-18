// 
// Decompiled by Procyon v0.5.30
// 

package harmotab.desktop.actions;

import java.awt.Window;
import harmotab.desktop.setupdialog.PreferencesSetupDialog;
import harmotab.desktop.ActionIcon;
import harmotab.core.Localizer;

public class ShowPreferencesAction extends UserAction
{
    private static final long serialVersionUID = 1L;
    
    public ShowPreferencesAction() {
        super(Localizer.get("MENU_PREFERENCES"), ActionIcon.getIcon((byte)7));
    }
    
    @Override
    public void run() {
        new PreferencesSetupDialog((Window)null).setVisible(true);
    }
}
