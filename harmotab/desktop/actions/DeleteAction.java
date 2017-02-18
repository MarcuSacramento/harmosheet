// 
// Decompiled by Procyon v0.5.30
// 

package harmotab.desktop.actions;

import harmotab.track.Track;
import harmotab.element.Element;
import harmotab.core.ScoreViewSelection;
import harmotab.core.undo.UndoManager;
import harmotab.desktop.DesktopController;
import harmotab.desktop.ActionIcon;
import harmotab.core.Localizer;

public class DeleteAction extends UserAction
{
    private static final long serialVersionUID = 1L;
    
    public DeleteAction() {
        super(Localizer.get("MENU_DELETE_ELEMENT"), ActionIcon.getIcon((byte)18));
        this.setLittleIcon(ActionIcon.getIcon((byte)19));
    }
    
    @Override
    public void run() {
        final ScoreViewSelection selection = DesktopController.getInstance().getCurrentSelection();
        if (selection != null) {
            final Element element = selection.getLocationItem().getRootElement();
            final Track track = selection.getTrack();
            UndoManager.getInstance().addUndoCommand(track.createRestoreCommand(), Localizer.get("MENU_DELETE_ELEMENT"));
            track.remove(element);
        }
    }
}
