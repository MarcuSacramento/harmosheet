// 
// Decompiled by Procyon v0.5.30
// 

package harmotab.desktop.actions;

import harmotab.track.Track;
import java.awt.Window;
import harmotab.desktop.setupdialog.ScoreSetupDialog;
import harmotab.core.undo.UndoManager;
import harmotab.desktop.DesktopController;
import harmotab.desktop.tools.ToolIcon;
import harmotab.core.Localizer;

public class ShowHarmonicaPropertiesAction extends UserAction
{
    private static final long serialVersionUID = 1L;
    
    public ShowHarmonicaPropertiesAction() {
        super(Localizer.get("ET_HARMONICA_SETUP"), ToolIcon.getIcon((byte)16));
    }
    
    @Override
    public void run() {
        final DesktopController dsk = DesktopController.getInstance();
        final Track track = dsk.getCurrentSelection().getTrack();
        UndoManager.getInstance().addUndoCommand(track.createRestoreCommand(), "ET_HARMONICA_SETUP");
        final ScoreSetupDialog dlg = new ScoreSetupDialog(dsk.getGuiWindow(), dsk.getScoreController());
        dlg.setSelectedTab(1);
        dlg.setVisible(true);
    }
}
