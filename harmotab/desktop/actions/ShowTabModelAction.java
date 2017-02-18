// 
// Decompiled by Procyon v0.5.30
// 

package harmotab.desktop.actions;

import java.awt.Window;
import harmotab.desktop.setupdialog.TrackSetupDialog;
import harmotab.desktop.DesktopController;
import harmotab.desktop.ActionIcon;
import harmotab.core.Localizer;

public class ShowTabModelAction extends UserAction
{
    private static final long serialVersionUID = 1L;
    
    public ShowTabModelAction() {
        super(Localizer.get("MENU_TAB_MODEL"), ActionIcon.getIcon((byte)7));
    }
    
    @Override
    public void run() {
        final TrackSetupDialog dlg = TrackSetupDialog.create(null, DesktopController.getInstance().getCurrentSelection().getTrack());
        dlg.setVisible(true);
    }
}
