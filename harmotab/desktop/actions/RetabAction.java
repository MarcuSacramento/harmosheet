// 
// Decompiled by Procyon v0.5.30
// 

package harmotab.desktop.actions;

import harmotab.harmonica.TabModel;
import harmotab.harmonica.TabModelController;
import harmotab.core.undo.UndoManager;
import harmotab.track.HarmoTabTrack;
import java.awt.Window;
import harmotab.desktop.setupdialog.TabModelWizard;
import harmotab.desktop.DesktopController;
import harmotab.desktop.ActionIcon;
import harmotab.core.Localizer;

public class RetabAction extends UserAction
{
    private static final long serialVersionUID = 1L;
    
    public RetabAction() {
        super(Localizer.get("MENU_RETAB"), ActionIcon.getIcon((byte)5));
    }
    
    @Override
    public void run() {
        final TabModelWizard tabModelWizzard = new TabModelWizard((Window)DesktopController.getInstance().getGuiWindow());
        tabModelWizzard.setModal(true);
        tabModelWizzard.setVisible(true);
        final TabModel tabModel = tabModelWizzard.getTabModel();
        if (tabModel != null) {
            final HarmoTabTrack htTrack = (HarmoTabTrack)DesktopController.getInstance().getScoreController().getScore().getTrack(HarmoTabTrack.class, 0);
            UndoManager.getInstance().addUndoCommand(htTrack.createRestoreCommand(), Localizer.get("MENU_RETAB"));
            htTrack.setTabModel(tabModel);
            htTrack.setHarmonica(tabModelWizzard.getHarmonica());
            final TabModelController tabModelController = new TabModelController(tabModel);
            tabModelController.updateTabs(htTrack);
        }
    }
}
