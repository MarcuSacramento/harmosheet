// 
// Decompiled by Procyon v0.5.30
// 

package harmotab.desktop.actions;

import harmotab.sound.SoundCountdown;
import harmotab.sound.ScorePlayer;
import harmotab.sound.ScorePlayerController;
import harmotab.core.Score;
import harmotab.sound.SoundCountdownListener;
import harmotab.sound.MidiCountDown;
import harmotab.desktop.DesktopController;
import harmotab.core.GlobalPreferences;
import harmotab.desktop.tools.ToolIcon;
import harmotab.core.Localizer;

public class PlayAction extends UserAction
{
    private static final long serialVersionUID = 1L;
    
    public PlayAction() {
        super(Localizer.get("TT_PLAY"), Localizer.get("TT_PLAY"), ToolIcon.getIcon((byte)9));
        this.setLittleIcon(ToolIcon.getIcon((byte)10));
    }
    
    @Override
    public void run() {
        if (GlobalPreferences.getPlaybackCountdownEnabled()) {
            try {
                final Score score = DesktopController.getInstance().getScoreController().getScore();
                final MidiCountDown midiCountDown = new MidiCountDown(score.getFirstTimeSignature(), score.getTempo());
                midiCountDown.addSoundCountdownListener(new SoundCountdownObserver((SoundCountdownObserver)null));
                midiCountDown.start();
                return;
            }
            catch (Exception e) {
                e.printStackTrace();
            }
        }
        this.doPlay();
    }
    
    private void doPlay() {
        final Score score = DesktopController.getInstance().getScoreController().getScore();
        final ScorePlayer player = DesktopController.getInstance().getScoreController().getScorePlayer();
        final ScorePlayerController playerController = new ScorePlayerController(player, score);
        playerController.preparePlayer();
        player.play();
    }
    
    private class SoundCountdownObserver implements SoundCountdownListener
    {
        @Override
        public void onSoundCountdownStopped(final SoundCountdown soundCountDown, final boolean cancelled) {
            if (!cancelled) {
                PlayAction.this.doPlay();
            }
        }
        
        @Override
        public void onSoundCountdownStarted(final SoundCountdown soundCountDown) {
        }
    }
}
