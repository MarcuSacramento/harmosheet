// 
// Decompiled by Procyon v0.5.30
// 

package harmotab.desktop.actions;

import harmotab.sound.ScorePlayer;
import harmotab.core.ScoreController;
import harmotab.desktop.DesktopController;
import harmotab.desktop.tools.ToolIcon;
import harmotab.core.Localizer;

public class PauseAction extends UserAction
{
    private static final long serialVersionUID = 1L;
    
    public PauseAction() {
        super(Localizer.get("TT_PAUSE"), ToolIcon.getIcon((byte)11));
        this.setLittleIcon(ToolIcon.getIcon((byte)12));
    }
    
    @Override
    public void run() {
        final ScoreController controller = DesktopController.getInstance().getScoreController();
        final ScorePlayer player = controller.getScorePlayer();
        if (player != null && player.isPaused()) {
            player.play();
        }
        else {
            player.pause();
        }
    }
}
