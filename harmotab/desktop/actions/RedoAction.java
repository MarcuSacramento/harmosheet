// 
// Decompiled by Procyon v0.5.30
// 

package harmotab.desktop.actions;

import harmotab.desktop.DesktopController;
import harmotab.core.undo.UndoManager;
import harmotab.desktop.ActionIcon;
import harmotab.core.Localizer;

public class RedoAction extends UserAction
{
    private static final long serialVersionUID = 1L;
    
    public RedoAction() {
        super(Localizer.get("MENU_REDO"), ActionIcon.getIcon((byte)32));
        this.setLittleIcon(ActionIcon.getIcon((byte)33));
    }
    
    @Override
    public void run() {
        UndoManager.getInstance().redo();
        DesktopController.getInstance().getScoreView().refresh();
    }
}
