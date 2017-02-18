// 
// Decompiled by Procyon v0.5.30
// 

package harmotab.track.soundlayout;

import java.util.Iterator;
import harmotab.sound.SoundItem;
import harmotab.element.Element;
import harmotab.sound.SoundSequence;
import harmotab.track.Track;

public class LyricsTrackSoundLayout extends SoundLayout
{
    public LyricsTrackSoundLayout(final Track track) {
        super(track);
    }
    
    @Override
    public void processSoundsPositionning(final SoundSequence sounds) {
        float currentTime = 0.0f;
        final int trackId = this.getTrackId();
        final float tempo = this.getTempo();
        for (final Element element : this.getTrack()) {
            final float startTime = currentTime;
            final float durationTime = 60.0f / tempo * element.getDuration();
            final SoundItem item = new SoundItem(element, trackId, -1, startTime, durationTime);
            sounds.add(item);
            currentTime += durationTime;
        }
    }
}
