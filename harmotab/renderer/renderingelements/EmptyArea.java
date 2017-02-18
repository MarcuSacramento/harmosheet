// 
// Decompiled by Procyon v0.5.30
// 

package harmotab.renderer.renderingelements;

import harmotab.track.Track;

public class EmptyArea extends RenderingElement
{
    private Track m_track;
    
    public EmptyArea(final Track track) {
        super((byte)(-1));
        this.setAssociatedTrack(track);
    }
    
    public void setAssociatedTrack(final Track track) {
        this.m_track = track;
    }
    
    public Track getAssociatedTrack() {
        return this.m_track;
    }
}
