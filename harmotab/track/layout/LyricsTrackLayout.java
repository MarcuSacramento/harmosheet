// 
// Decompiled by Procyon v0.5.30
// 

package harmotab.track.layout;

import java.util.Iterator;
import harmotab.renderer.renderingelements.EmptyArea;
import harmotab.renderer.LocationItem;
import harmotab.element.Element;
import harmotab.renderer.LocationList;
import harmotab.track.Track;
import harmotab.track.LyricsTrack;

public class LyricsTrackLayout extends TrackLayout
{
    protected static final int LYRICS_TRACK_HEIGHT = 25;
    private LyricsTrack m_lyricsTrack;
    
    public LyricsTrackLayout(final Track track) {
        super(track);
        this.m_lyricsTrack = null;
        this.m_lyricsTrack = (LyricsTrack)track;
    }
    
    @Override
    public int getTrackHeight() {
        return 25;
    }
    
    @Override
    public int getLayoutRound() {
        return this.m_lyricsTrack.getLinkedTrack().getTrackLayout().getLayoutRound() + 1;
    }
    
    @Override
    public void processElementsPositionning(final LocationList locations, final int areaWidth, final float scoreDuration) {
        final LocationList localLocations = new LocationList();
        final int linkedTrackId = this.m_track.getScore().getTrackId(this.m_lyricsTrack.getLinkedTrack());
        final TimeScale timeScale = new TimeScale(locations, linkedTrackId);
        final int trackId = this.getTrackId();
        float currentTime = 0.0f;
        int currentLine = 1;
        for (final Element element : this.m_track) {
            final TimeScale.TimePoint startTimePoint = timeScale.getLastPointAt(currentTime);
            final TimeScale.TimePoint endTimePoint = timeScale.getFirstPointAt(currentTime + element.getDuration());
            if (startTimePoint != null) {
                final int startTimeLine = startTimePoint.getLine();
                final int endTimeLine = (endTimePoint != null) ? endTimePoint.getLine() : startTimeLine;
                final int x1 = startTimePoint.getX();
                int width = areaWidth - x1;
                if (endTimePoint != null && startTimeLine == endTimeLine) {
                    width = endTimePoint.getX() - x1;
                }
                final LocationItem lyricsItem = LocationItem.newFromArea(element, x1, 0, width, this.getTrackHeight(), trackId, startTimeLine, currentTime, 0);
                localLocations.add(lyricsItem);
                if (endTimePoint != null) {
                    for (int line = startTimeLine + 1; line <= endTimeLine; ++line) {
                        width = ((line == endTimeLine) ? endTimePoint.getX() : areaWidth);
                        final LocationItem tempItem = LocationItem.newFromArea(element, 0, 0, width, this.getTrackHeight(), trackId, line, currentTime, 0);
                        tempItem.setFlag(4, true);
                        localLocations.add(tempItem);
                    }
                }
                currentLine = endTimeLine;
            }
            else {
                System.err.println("LyricsTrackLayout::processElementsPositionning: no location found for " + element);
            }
            currentTime += element.getDuration();
        }
        final TimeScale.TimePoint endPoint = timeScale.getFirstPointAt(currentTime);
        final int emptyAreaX = (endPoint != null) ? endPoint.getX() : 0;
        LocationItem emptyArea = LocationItem.newFromArea(new EmptyArea(this.m_track), emptyAreaX, 0, areaWidth - emptyAreaX, this.getTrackHeight(), trackId, currentLine, currentTime, 0);
        emptyArea.setFlag(4, true);
        localLocations.add(emptyArea);
        final int maxLine = timeScale.getNumberOfLines();
        ++currentLine;
        while (currentLine <= maxLine) {
            currentTime = timeScale.getLineStart(currentLine).getTime();
            emptyArea = LocationItem.newFromArea(new EmptyArea(this.m_track), 0, 0, areaWidth, this.getTrackHeight(), trackId, currentLine, currentTime, 0);
            emptyArea.setFlag(4, true);
            localLocations.add(emptyArea);
            ++currentLine;
        }
        locations.add(localLocations);
    }
}
