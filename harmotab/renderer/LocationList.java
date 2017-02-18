// 
// Decompiled by Procyon v0.5.30
// 

package harmotab.renderer;

import java.util.ListIterator;
import harmotab.element.Element;
import java.util.Iterator;
import java.util.ConcurrentModificationException;
import java.util.Collection;
import java.util.Vector;

public class LocationList implements Iterable<LocationItem>
{
    private Vector<LocationItem> m_list;
    
    public LocationList() {
        this.m_list = new Vector<LocationItem>();
    }
    
    public void add(final LocationItem item) {
        this.m_list.add(item);
    }
    
    public void add(final LocationList list) {
        this.m_list.addAll(list.m_list);
    }
    
    public void add(final int index, final LocationList list) {
        this.m_list.addAll(index, list.m_list);
    }
    
    public void add(final int index, final LocationItem item) {
        this.m_list.add(index, item);
    }
    
    public void addBefore(final LocationItem beforeThis, final LocationItem item) {
        if (beforeThis == null) {
            throw new NullPointerException();
        }
        final int index = this.m_list.indexOf(beforeThis);
        if (index == -1) {
            throw new IllegalArgumentException("Element not found");
        }
        this.m_list.add(index, item);
    }
    
    public LocationItem at(final int x, final int y) {
        try {
            for (final LocationItem e : this.m_list) {
                if (e.m_y1 <= y && e.m_y2 >= y && e.m_x1 <= x && e.m_x2 >= x) {
                    return e;
                }
            }
        }
        catch (ConcurrentModificationException e2) {
            System.err.println("LocationItem.at(x, y): ConcurrentModificationException, return null.");
            e2.printStackTrace();
        }
        return null;
    }
    
    public LocationItem get(final Element element) {
        try {
            for (final LocationItem e : this.m_list) {
                if (e.getElement() == element) {
                    return e;
                }
            }
        }
        catch (ConcurrentModificationException e2) {
            System.err.println("LocationItem.get(element): ConcurrentModificationException, return null.");
            e2.printStackTrace();
        }
        return null;
    }
    
    public LocationItem get(final int index) {
        return this.m_list.get(index);
    }
    
    public void addVerticalOffset(final int trackId, final int lineHeight, final int trackOffset) {
        for (final LocationItem e : this.m_list) {
            if (e.m_trackId == trackId) {
                final int verticalOffset = trackOffset + (e.m_line - 1) * lineHeight;
                final LocationItem locationItem = e;
                locationItem.m_y1 += verticalOffset;
                final LocationItem locationItem2 = e;
                locationItem2.m_y2 += verticalOffset;
                final LocationItem locationItem3 = e;
                locationItem3.m_poiY += verticalOffset;
            }
        }
    }
    
    public void addOffset(final int marginX, final int marginY) {
        for (final LocationItem locationItem : this.m_list) {
            final LocationItem e = locationItem;
            locationItem.m_x1 += marginX;
            final LocationItem locationItem2 = e;
            locationItem2.m_x2 += marginX;
            final LocationItem locationItem3 = e;
            locationItem3.m_y1 += marginY;
            final LocationItem locationItem4 = e;
            locationItem4.m_y2 += marginY;
            final LocationItem locationItem5 = e;
            locationItem5.m_poiX += marginX;
            final LocationItem locationItem6 = e;
            locationItem6.m_poiY += marginY;
        }
    }
    
    public void addVerticalScrolling(final int scollY) {
        for (final LocationItem locationItem : this.m_list) {
            final LocationItem e = locationItem;
            locationItem.m_y1 -= scollY;
            final LocationItem locationItem2 = e;
            locationItem2.m_y2 -= scollY;
            final LocationItem locationItem3 = e;
            locationItem3.m_poiY -= scollY;
        }
    }
    
    public int getBottomOrdinate() {
        int maxY = 0;
        for (final LocationItem item : this.m_list) {
            if (item.m_y2 > maxY) {
                maxY = item.m_y2;
            }
        }
        return maxY;
    }
    
    public int getRightOrdinate() {
        int maxX1 = 0;
        LocationItem maxX1Item = null;
        for (final LocationItem item : this.m_list) {
            if (item.m_x2 > maxX1 && item.m_x2 < 2147482647) {
                maxX1Item = item;
                maxX1 = item.m_x1;
            }
        }
        return maxX1Item.m_x2;
    }
    
    public void reset() {
        this.m_list.clear();
    }
    
    public boolean hasElementOfType(final byte type) {
        for (final LocationItem item : this.m_list) {
            if (item.getElement().getType() == type) {
                return true;
            }
        }
        return false;
    }
    
    @Override
    public Iterator<LocationItem> iterator() {
        return this.m_list.iterator();
    }
    
    public Iterator<LocationItem> getIterator() {
        return this.m_list.iterator();
    }
    
    public ListIterator<LocationItem> getListIterator() {
        return this.m_list.listIterator();
    }
    
    public ListIterator<LocationItem> getListIterator(final int index) {
        return this.m_list.listIterator(index);
    }
    
    public ElementLocationIterator getElementLocationIterator(final Class elementClass) {
        return new ElementLocationIterator(this.getListIterator(), elementClass);
    }
    
    public ElementLocationIterator getElementLocationIterator(final int index, final Class elementClass) {
        return new ElementLocationIterator(this.getListIterator(index), elementClass);
    }
    
    public int getItemIndex(final LocationItem item) {
        return this.m_list.indexOf(item);
    }
    
    public int getSize() {
        return this.m_list.size();
    }
    
    public void printStackTrace() {
        final Iterator<LocationItem> it = this.getIterator();
        System.out.println("Location list (" + this.m_list.size() + " items) : " + this);
        int index = 0;
        while (it.hasNext()) {
            final LocationItem item = it.next();
            System.out.println(String.valueOf(index) + ". " + item.getElement() + "\t\t" + "t=" + item.m_trackId + ", l=" + item.m_line + ", x1=" + item.m_x1 + ", y1=" + item.m_y1 + ", w=" + item.m_width + ", h=" + item.m_height);
            ++index;
        }
    }
}
