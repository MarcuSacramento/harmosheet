// 
// Decompiled by Procyon v0.5.30
// 

package harmotab.desktop.actions;

import harmotab.sound.ScorePlayer;
import harmotab.desktop.DesktopController;
import harmotab.desktop.tools.ToolIcon;
import harmotab.core.Localizer;

public class StopAction extends UserAction
{
    private static final long serialVersionUID = 1L;
    
    public StopAction() {
        super(Localizer.get("TT_STOP"), ToolIcon.getIcon((byte)13));
        this.setLittleIcon(ToolIcon.getIcon((byte)14));
    }
    
    @Override
    public void run() {
        final ScorePlayer player = DesktopController.getInstance().getScoreController().getScorePlayer();
        player.stop();
    }
}
