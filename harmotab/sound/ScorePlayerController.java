// 
// Decompiled by Procyon v0.5.30
// 

package harmotab.sound;

import java.util.Iterator;
import harmotab.element.Tempo;
import harmotab.element.TimeSignature;
import harmotab.track.Track;
import harmotab.element.Element;
import harmotab.core.GlobalPreferences;
import harmotab.core.Score;

public class ScorePlayerController
{
    private ScorePlayer m_scorePlayer;
    private Score m_score;
    
    public ScorePlayerController(final ScorePlayer player, final Score score) {
        this.m_scorePlayer = null;
        this.m_score = null;
        this.m_scorePlayer = player;
        this.m_score = score;
    }
    
    public void preparePlayer() {
        final TimeSignature timeSignature = this.m_score.getFirstTimeSignature();
        final Tempo tempo = this.m_score.getTempo();
        SoundSequence sounds = this.createSoundSequence();
        if (GlobalPreferences.getMetronomeEnabled()) {
            for (float duration = sounds.getLastTime(), beatDuration = tempo.getBeatPeriodInSeconds() * timeSignature.getTimesPerBeat(), beat = 0.0f; beat < duration; beat += beatDuration) {
                sounds.add(new SoundItem(null, 9, 37, beat, 0.5f));
            }
        }
        sounds = sounds.mergeRepeats();
        this.m_scorePlayer.setSounds(sounds);
        final float globalVolumeFactor = GlobalPreferences.getGlobalVolume() / 100.0f;
        for (final Track track : this.m_score) {
            final int channel = this.m_score.getTrackId(track);
            this.m_scorePlayer.setInstrument(channel, track.getInstrument());
            this.m_scorePlayer.setTrackVolume(channel, (int)(track.getVolume() * globalVolumeFactor));
        }
    }
    
    public SoundSequence createSoundSequence() {
        final SoundSequence sounds = new SoundSequence();
        for (final Track track : this.m_score) {
            track.getSoundLayout().processSoundsPositionning(sounds);
        }
        return sounds;
    }
}
