// 
// Decompiled by Procyon v0.5.30
// 

package harmotab.desktop.actions;

import harmotab.desktop.DesktopController;
import harmotab.core.undo.UndoManager;
import harmotab.desktop.ActionIcon;
import harmotab.core.Localizer;

public class UndoAction extends UserAction
{
    private static final long serialVersionUID = 1L;
    
    public UndoAction() {
        super(Localizer.get("MENU_UNDO"), ActionIcon.getIcon((byte)30));
        this.setLittleIcon(ActionIcon.getIcon((byte)31));
    }
    
    @Override
    public void run() {
        UndoManager.getInstance().undo();
        DesktopController.getInstance().getScoreView().refresh();
    }
}
