// 
// Decompiled by Procyon v0.5.30
// 

package harmotab.track.soundlayout;

import harmotab.element.Tempo;
import harmotab.sound.SoundSequence;
import harmotab.core.Score;
import harmotab.track.Track;

public abstract class SoundLayout
{
    private Track m_track;
    private Score m_score;
    
    public SoundLayout(final Track track) {
        this.m_track = track;
        this.m_score = track.getScore();
    }
    
    public abstract void processSoundsPositionning(final SoundSequence p0);
    
    public int getTrackId() {
        return this.m_track.getScore().getTrackId(this.m_track);
    }
    
    public Track getTrack() {
        return this.m_track;
    }
    
    public int getTempo() {
        return (this.m_score != null) ? this.m_score.getTempoValue() : new Tempo().getValue();
    }
    
    public Score getScore() {
        return this.m_score;
    }
}
