// 
// Decompiled by Procyon v0.5.30
// 

package harmotab.desktop.actions;

import harmotab.desktop.DesktopController;
import harmotab.desktop.ActionIcon;
import harmotab.core.Localizer;

public class ExportAsImageAction extends UserAction
{
    private static final long serialVersionUID = 1L;
    
    public ExportAsImageAction() {
        super(Localizer.get("MENU_EXPORT_AS_PNG"), ActionIcon.getIcon((byte)26));
    }
    
    @Override
    public void run() {
        DesktopController.getInstance().getScoreController().exportAsImage();
    }
}
