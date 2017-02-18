// 
// Decompiled by Procyon v0.5.30
// 

package harmotab.track.layout;

import harmotab.element.Element;
import harmotab.renderer.LocationItem;
import harmotab.renderer.LocationList;
import harmotab.core.Score;

public class HeaderLayout
{
    private static final int HEADER_HEIGHT = 180;
    private Score m_score;
    
    public HeaderLayout(final Score score) {
        this.m_score = score;
    }
    
    public void processHeaderPositionning(final LocationList locations, final int areaWidth) {
        locations.add(LocationItem.newFromArea(this.m_score.getTitle(), 0, 0, areaWidth, 40, -1, 0, -1.0f, 0));
        locations.add(LocationItem.newFromArea(this.m_score.getSongwriter(), 0, 40, areaWidth, 30, -1, 0, -1.0f, 0));
        locations.add(LocationItem.newFromArea(this.m_score.getTempo(), 0, 100, areaWidth / 2, 25, -1, 0, -1.0f, 0));
        locations.add(LocationItem.newFromArea(this.m_score.getComment(), areaWidth / 2, 100, areaWidth / 2, 25, -1, 0, -1.0f, 0));
    }
    
    public int getHeight() {
        return 180;
    }
}
