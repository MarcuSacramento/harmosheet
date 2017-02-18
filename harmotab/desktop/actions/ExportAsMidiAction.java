// 
// Decompiled by Procyon v0.5.30
// 

package harmotab.desktop.actions;

import harmotab.desktop.DesktopController;
import harmotab.desktop.ActionIcon;
import harmotab.core.Localizer;

public class ExportAsMidiAction extends UserAction
{
    private static final long serialVersionUID = 1L;
    
    public ExportAsMidiAction() {
        super(Localizer.get("MENU_EXPORT_AS_MIDI"), ActionIcon.getIcon((byte)25));
    }
    
    @Override
    public void run() {
        DesktopController.getInstance().getScoreController().exportAsMidi();
    }
}
