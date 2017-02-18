// 
// Decompiled by Procyon v0.5.30
// 

package harmotab.core;

import harmotab.element.Bar;
import harmotab.renderer.LocationItem;
import harmotab.track.HarmoTabTrack;
import java.util.Iterator;
import harmotab.desktop.DesktopController;
import harmotab.track.Track;
import harmotab.renderer.LocationList;
import harmotab.sound.SoundSequence;

public class ScoreStatistics
{
    private Score m_score;
    private SoundSequence m_soundSequence;
    private LocationList m_locations;
    
    public ScoreStatistics(final Score score) {
        this.m_score = null;
        this.m_soundSequence = null;
        this.m_locations = null;
        this.m_score = score;
        this.m_soundSequence = new SoundSequence();
        for (final Track track : this.m_score) {
            track.getSoundLayout().processSoundsPositionning(this.m_soundSequence);
        }
        this.m_soundSequence.mergeRepeats();
        this.m_locations = new LocationList();
        DesktopController.getInstance().getScoreView().getScoreRenderer().layout(this.m_locations);
    }
    
    public int getTracksCount() {
        return this.m_score.getTracksCount();
    }
    
    public float getPlaybackDurationSec() {
        return this.m_soundSequence.getLastTime();
    }
    
    public int getBarsCount() {
        int result = 0;
        final Track track = this.m_score.getTrack(HarmoTabTrack.class, 0);
        final int trackIndex = track.getTrackIndex();
        for (final LocationItem item : this.m_locations) {
            if (item.getTrackId() == trackIndex && item.getElement() instanceof Bar) {
                result = Math.max(result, item.getExtra());
            }
        }
        return result;
    }
    
    public int getItemsCount() {
        int result = 0;
        for (final Track track : this.m_score) {
            result += track.size();
        }
        return result;
    }
    
    public int getDisplayedItemsCount() {
        return this.m_locations.getSize();
    }
}
