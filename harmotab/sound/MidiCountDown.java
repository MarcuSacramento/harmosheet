// 
// Decompiled by Procyon v0.5.30
// 

package harmotab.sound;

import java.awt.Component;
import harmotab.desktop.ErrorMessenger;
import harmotab.core.Localizer;
import harmotab.element.Tempo;
import harmotab.element.TimeSignature;

public class MidiCountDown extends SoundCountdown implements ScorePlayerListener
{
    private MidiScorePlayer player;
    
    public MidiCountDown(final TimeSignature timeSignature, final Tempo tempo) throws Exception {
        super(timeSignature, tempo);
        this.player = null;
        this.player = MidiScorePlayer.getInstance();
    }
    
    @Override
    protected void finalize() throws Throwable {
        this.player.removeSoundPlayerListener(this);
    }
    
    @Override
    public void start() {
        final int trackId = 0;
        if (this.player.getState() == 2 && !this.player.isPlaying()) {
            this.player.setSounds(this.getCountdownSequence());
            this.player.setInstrument(0, 0);
            this.player.setTrackVolume(0, 100);
            this.player.removeSoundPlayerListener(this);
            this.player.addSoundPlayerListener(this);
            this.player.play();
            this.fireCountdownStarted();
        }
        else {
            ErrorMessenger.showErrorMessage(null, Localizer.get("M_MIDI_OUTPUT_ERROR"));
        }
    }
    
    @Override
    public void onPlaybackStopped(final ScorePlayerEvent event, final boolean endOfPlayback) {
        this.fireCountdownStopped(!endOfPlayback);
        this.player.removeSoundPlayerListener(this);
    }
    
    @Override
    public void onScorePlayerError(final ScorePlayerEvent event, final Throwable error) {
    }
    
    @Override
    public void onPlaybackPaused(final ScorePlayerEvent event) {
    }
    
    @Override
    public void onScorePlayerStateChanged(final ScorePlayerEvent event) {
    }
    
    @Override
    public void onPlaybackStarted(final ScorePlayerEvent event) {
    }
    
    @Override
    public void onPlayedSoundItemChanged(final ScorePlayerEvent event) {
    }
}
