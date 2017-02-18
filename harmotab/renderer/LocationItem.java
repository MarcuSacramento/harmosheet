// 
// Decompiled by Procyon v0.5.30
// 

package harmotab.renderer;

import harmotab.element.Element;

public class LocationItem implements Cloneable
{
    public Element m_element;
    public int m_x1;
    public int m_y1;
    public int m_x2;
    public int m_y2;
    public int m_width;
    public int m_height;
    public int m_trackId;
    public int m_line;
    public int m_poiX;
    public int m_poiY;
    public float m_time;
    public int m_extra;
    public int m_flag;
    public boolean m_isSelection;
    public Element m_parent;
    public int m_elementIndex;
    
    public LocationItem(final Element element, final int poiX, final int poiY, final int x, final int y, final int width, final int height, final int trackId, final int line, final float time, final int extra) {
        this.m_element = element;
        this.m_x1 = x;
        this.m_y1 = y;
        this.m_width = width;
        this.m_height = height;
        this.m_x2 = x + width;
        this.m_y2 = y + height;
        this.m_trackId = trackId;
        this.m_line = line;
        this.m_poiX = poiX;
        this.m_poiY = poiY;
        this.m_time = time;
        this.m_extra = extra;
        this.m_isSelection = false;
        this.m_flag = 0;
        this.m_parent = null;
        this.m_elementIndex = -1;
    }
    
    public LocationItem(final LocationItem item) {
        this.m_element = item.m_element;
        this.m_x1 = item.m_x1;
        this.m_y1 = item.m_y1;
        this.m_width = item.m_width;
        this.m_height = item.m_height;
        this.m_x2 = item.m_x1 + item.m_width;
        this.m_y2 = item.m_y1 + item.m_height;
        this.m_trackId = item.m_trackId;
        this.m_line = item.m_line;
        this.m_poiX = item.m_poiX;
        this.m_poiY = item.m_poiY;
        this.m_extra = item.m_extra;
        this.m_isSelection = item.m_isSelection;
        this.m_flag = item.m_flag;
        this.m_parent = item.m_parent;
        this.m_elementIndex = item.m_elementIndex;
    }
    
    public Object clone() {
        try {
            return super.clone();
        }
        catch (CloneNotSupportedException e) {
            e.printStackTrace();
            return new LocationItem(this);
        }
    }
    
    public static LocationItem newFromArea(final Element element, final int x, final int y, final int width, final int height, final int trackId, final int line, final float time, final int extra) {
        return new LocationItem(element, x + width / 2, y + height / 2, x, y, width, height, trackId, line, time, extra);
    }
    
    public static LocationItem newFromPoi(final Element element, final int poiX, final int poiY, final int width, final int height, final int trackId, final int line, final float time, final int extra) {
        return new LocationItem(element, poiX, poiY, poiX - width / 2, poiY - height / 2, width, height, trackId, line, time, extra);
    }
    
    public static LocationItem newFromOrdinate(final Element element, final int x, final int y, final int width, final int height, final int ordinate, final int trackId, final int line, final float time, final int extra) {
        return new LocationItem(element, x + width / 2, ordinate, x, y, width, height, trackId, line, time, extra);
    }
    
    public int getX1() {
        return this.m_x1;
    }
    
    public int getY1() {
        return this.m_y1;
    }
    
    public int getX2() {
        return this.m_x2;
    }
    
    public int getY2() {
        return this.m_y2;
    }
    
    public int getPointOfInterestX() {
        return this.m_poiX;
    }
    
    public int getPointOfInterestY() {
        return this.m_poiY;
    }
    
    public int getWidth() {
        return this.m_width;
    }
    
    public int getHeight() {
        return this.m_height;
    }
    
    public int getTrackId() {
        return this.m_trackId;
    }
    
    public int getLine() {
        return this.m_line;
    }
    
    public float getTime() {
        return this.m_time;
    }
    
    public Element getElement() {
        return this.m_element;
    }
    
    public int getExtra() {
        return this.m_extra;
    }
    
    public void setFlag(final int flag, final boolean value) {
        if (value) {
            this.m_flag |= 1 << flag;
        }
        else {
            this.m_flag &= ~(1 << flag);
        }
    }
    
    public boolean getFlag(final int flag) {
        return (0x1 & this.m_flag >> flag) == 0x1;
    }
    
    public void setElementIndex(final int index) {
        this.m_elementIndex = index;
    }
    
    public int getElementIndex() {
        return this.m_elementIndex;
    }
    
    public void setParent(final Element parent) {
        this.m_parent = parent;
    }
    
    public Element getParent() {
        return this.m_parent;
    }
    
    public Element getRootElement() {
        return (this.m_parent != null) ? this.m_parent : this.m_element;
    }
    
    public void resize(final int width, final int height) {
        this.m_width = width;
        this.m_height = height;
        this.m_x2 = this.m_x1 + this.m_width;
        this.m_y2 = this.m_y1 + this.m_height;
    }
    
    public void reduceLeft(final int dx) {
        this.m_width -= dx;
        this.m_x1 += dx;
    }
    
    public void translate(final int dx, final int dy) {
        this.m_x1 += dx;
        this.m_x2 += dx;
        this.m_y1 += dy;
        this.m_y2 += dy;
        this.m_poiX += dx;
        this.m_poiY += dy;
    }
    
    public void moveTo(final int x, final int y) {
        this.m_poiX -= x - this.m_x1;
        this.m_poiY -= y - this.m_y1;
        this.m_x1 = x;
        this.m_x2 = x + this.m_width;
        this.m_y1 = y;
        this.m_y2 = y + this.m_height;
    }
    
    public void moveToX(final int x) {
        this.m_poiX += x - this.m_x1;
        this.m_x1 = x;
        this.m_x2 = x + this.m_width;
    }
}
