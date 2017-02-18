// 
// Decompiled by Procyon v0.5.30
// 

package harmotab.core;

import harmotab.element.Element;
import harmotab.track.Track;
import harmotab.renderer.LocationItem;
import harmotab.desktop.tools.Tool;

public class ScoreViewSelection
{
    private Tool m_tool;
    private LocationItem m_locationItem;
    private Track m_track;
    private Element m_element;
    private int m_elementIndex;
    private int m_locationItemIndex;
    
    public ScoreViewSelection(final Tool tool, final int itemIndex) {
        this.m_tool = tool;
        this.m_locationItem = this.m_tool.getLocationItem();
        this.m_track = tool.getTrack();
        this.m_element = this.m_locationItem.getElement();
        if (this.m_track != null) {
            this.m_elementIndex = this.m_track.indexOf(this.m_element);
        }
        else {
            this.m_elementIndex = -1;
        }
        this.m_locationItemIndex = itemIndex;
        if (itemIndex == -1) {
            System.err.println("ScoreViewSelection::ScoreViewSelection: itemIndex == -1");
        }
    }
    
    public Tool getTool() {
        return this.m_tool;
    }
    
    public LocationItem getLocationItem() {
        return this.m_locationItem;
    }
    
    public Track getTrack() {
        return this.m_track;
    }
    
    public Element getElement() {
        return this.m_element;
    }
    
    public int getElementIndex() {
        return this.m_elementIndex;
    }
    
    public int getLocationItemIndex() {
        return this.m_locationItemIndex;
    }
    
    public int getTrackId() {
        return this.m_locationItem.getTrackId();
    }
}
