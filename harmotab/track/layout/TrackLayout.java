// 
// Decompiled by Procyon v0.5.30
// 

package harmotab.track.layout;

import harmotab.renderer.LocationList;
import harmotab.core.Score;
import harmotab.track.Track;

public abstract class TrackLayout
{
    protected Track m_track;
    
    public TrackLayout(final Track track) {
        this.setTrack(track);
    }
    
    public Track getTrack() {
        return this.m_track;
    }
    
    public void setTrack(final Track track) {
        if (track == null) {
            throw new NullPointerException();
        }
        this.m_track = track;
    }
    
    public Score getScore() {
        return this.m_track.getScore();
    }
    
    public int getTrackId() {
        if (this.getScore() != null) {
            return this.getScore().getTrackId(this.m_track);
        }
        return -1;
    }
    
    public int getLayoutRound() {
        return 0;
    }
    
    public abstract void processElementsPositionning(final LocationList p0, final int p1, final float p2);
    
    public abstract int getTrackHeight();
}
