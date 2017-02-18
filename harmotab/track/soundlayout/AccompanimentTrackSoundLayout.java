// 
// Decompiled by Procyon v0.5.30
// 

package harmotab.track.soundlayout;

import harmotab.core.Height;
import harmotab.core.Duration;
import java.util.Iterator;
import harmotab.sound.SoundItem;
import java.util.Collection;
import harmotab.element.Silence;
import harmotab.element.Accompaniment;
import harmotab.element.Element;
import harmotab.track.Track;
import harmotab.sound.SoundSequence;

public class AccompanimentTrackSoundLayout extends SoundLayout
{
    private SoundSequence m_sounds;
    private float m_currentTime;
    private int m_trackId;
    private float m_tempo;
    
    public AccompanimentTrackSoundLayout(final Track track) {
        super(track);
        this.m_sounds = new SoundSequence();
    }
    
    @Override
    public void processSoundsPositionning(final SoundSequence sounds) {
        this.m_sounds.clear();
        this.m_currentTime = 0.0f;
        this.m_trackId = this.getTrackId();
        this.m_tempo = this.getTempo();
        for (final Element element : this.getTrack()) {
            if (element instanceof Accompaniment) {
                this.add((Accompaniment)element);
            }
            else {
                if (!(element instanceof Silence)) {
                    continue;
                }
                this.add((Silence)element);
            }
        }
        sounds.addAll(this.m_sounds);
    }
    
    private void add(final Accompaniment acc) {
        for (final Duration duration : acc.getRhythmic()) {
            final float startTime = this.m_currentTime;
            final float durationTime = 60.0f / this.m_tempo * duration.getDuration();
            for (final Height height : acc.getChord().getHeights()) {
                final SoundItem item = new SoundItem(acc, this.m_trackId, height.getSoundId(), startTime, durationTime);
                this.m_sounds.add(item);
            }
            this.m_currentTime += durationTime;
        }
    }
    
    private void add(final Silence silence) {
        final float durationTime = 60.0f / this.m_tempo * silence.getDuration();
        this.m_currentTime += durationTime;
    }
}
