// 
// Decompiled by Procyon v0.5.30
// 

package harmotab.renderer.renderingelements;

import java.util.Collection;
import java.util.ListIterator;
import harmotab.element.Note;
import harmotab.core.Figure;
import java.util.Iterator;
import java.util.LinkedList;
import harmotab.renderer.LocationItem;
import java.util.List;

public abstract class NoteGroup extends RenderingElement implements List<LocationItem>
{
    public static final int UP = 1;
    public static final int DOWN = -1;
    public static final int NOTE_ROUND_WIDTH = 8;
    private LinkedList<LocationItem> m_list;
    
    public NoteGroup(final byte type) {
        super(type);
        this.m_list = new LinkedList<LocationItem>();
    }
    
    public float getGroupDuration() {
        float duration = 0.0f;
        for (final LocationItem item : this) {
            duration += item.getElement().getDuration();
        }
        return duration;
    }
    
    public Figure getGroupFigure() {
        if (this.m_list.size() < 1) {
            return null;
        }
        final byte figureType = ((Note)this.m_list.get(0).getElement()).getFigure().getType();
        for (final LocationItem item : this.m_list) {
            final int itemType = ((Note)item.getElement()).getFigure().getType();
            if (itemType != figureType) {
                return null;
            }
        }
        return new Figure(figureType);
    }
    
    public int getAverageHeight() {
        int sum = 0;
        final ListIterator<LocationItem> it = this.listIterator();
        while (it.hasNext()) {
            sum += ((Note)it.next().getElement()).getHeight().getOrdinate();
        }
        return sum / this.size();
    }
    
    public int getAverageDirection() {
        return (this.getAverageHeight() >= 85) ? 1 : -1;
    }
    
    public boolean allHaveSameDirection() {
        if (this.m_list.size() < 1) {
            return true;
        }
        final int direction = (((Note)this.m_list.get(0).getElement()).getHeight().getOrdinate() >= 85) ? 1 : -1;
        for (final LocationItem item : this.m_list) {
            final int itemDirection = (((Note)item.getElement()).getHeight().getOrdinate() >= 85) ? 1 : -1;
            if (itemDirection != direction) {
                return false;
            }
        }
        return true;
    }
    
    public int getX1() {
        if (this.size() > 1) {
            return this.get(0).getPointOfInterestX();
        }
        return this.get(0).getPointOfInterestX() - 8;
    }
    
    public int getX2() {
        if (this.size() > 1) {
            return this.get(this.size() - 1).getPointOfInterestX();
        }
        return this.get(this.size() - 1).getPointOfInterestX() + 8;
    }
    
    public GroupLine getGroupLine() {
        final int LINE_SIZE = 22;
        final float MAX_SLOPE = 0.25f;
        final LocationItem first = this.get(0);
        final LocationItem last = this.get(this.size() - 1);
        int x1 = this.getX1();
        int x2 = this.getX2();
        final int y1 = first.getPointOfInterestY() - 22;
        final int y2 = last.getPointOfInterestY() - 22;
        float m = (y2 - y1) / (x2 - x1);
        if (m > 0.25f) {
            m = 0.25f;
        }
        if (m < -0.25f) {
            m = -0.25f;
        }
        int direction = 0;
        final Figure groupFigure = this.getGroupFigure();
        if (groupFigure != null && groupFigure.isHookable()) {
            direction = this.getAverageDirection();
        }
        else if (this.allHaveSameDirection() && this.getAverageDirection() == -1) {
            direction = -1;
        }
        else {
            direction = 1;
        }
        final Iterator<LocationItem> i = this.iterator();
        int p = i.next().getPointOfInterestY() - 22 * direction;
        int y3 = 0;
        while (i.hasNext()) {
            final LocationItem item = i.next();
            y3 = (int)(m * (item.getPointOfInterestX() - x1) + p);
            if (direction == 1) {
                if (y3 + 22 * direction <= item.getPointOfInterestY()) {
                    continue;
                }
                p -= y3 + 22 - item.getPointOfInterestY();
            }
            else {
                if (y3 + 22 * direction >= item.getPointOfInterestY()) {
                    continue;
                }
                p -= y3 + 22 * direction - item.getPointOfInterestY();
            }
        }
        if (direction == -1) {
            x1 -= 5;
            x2 -= 5;
        }
        else {
            x1 += 3;
            x2 += 3;
        }
        return new GroupLine(m, p, direction, x1, x2);
    }
    
    @Override
    public boolean add(final LocationItem item) {
        return this.m_list.add(item);
    }
    
    @Override
    public void add(final int index, final LocationItem item) {
        this.m_list.add(index, item);
    }
    
    @Override
    public boolean addAll(final Collection<? extends LocationItem> collection) {
        return this.m_list.addAll(collection);
    }
    
    @Override
    public boolean addAll(final int index, final Collection<? extends LocationItem> collection) {
        return this.m_list.addAll(index, collection);
    }
    
    @Override
    public void clear() {
        this.m_list.clear();
    }
    
    @Override
    public boolean contains(final Object element) {
        return this.m_list.contains(element);
    }
    
    @Override
    public boolean containsAll(final Collection<?> collection) {
        return this.m_list.containsAll(collection);
    }
    
    @Override
    public LocationItem get(final int index) {
        return this.m_list.get(index);
    }
    
    @Override
    public int indexOf(final Object element) {
        return this.m_list.indexOf(element);
    }
    
    @Override
    public boolean isEmpty() {
        return this.m_list.isEmpty();
    }
    
    @Override
    public Iterator<LocationItem> iterator() {
        return this.m_list.iterator();
    }
    
    @Override
    public int lastIndexOf(final Object element) {
        return this.m_list.lastIndexOf(element);
    }
    
    @Override
    public ListIterator<LocationItem> listIterator() {
        return this.m_list.listIterator();
    }
    
    @Override
    public ListIterator<LocationItem> listIterator(final int index) {
        return this.m_list.listIterator(index);
    }
    
    @Override
    public boolean remove(final Object element) {
        return this.m_list.remove(element);
    }
    
    @Override
    public LocationItem remove(final int index) {
        return this.m_list.remove(index);
    }
    
    @Override
    public boolean removeAll(final Collection<?> collection) {
        return this.m_list.removeAll(collection);
    }
    
    @Override
    public boolean retainAll(final Collection<?> collection) {
        return this.m_list.retainAll(collection);
    }
    
    @Override
    public LocationItem set(final int index, final LocationItem item) {
        return this.m_list.set(index, item);
    }
    
    @Override
    public int size() {
        return this.m_list.size();
    }
    
    @Override
    public List<LocationItem> subList(final int fromIndex, final int toIndex) {
        return this.m_list.subList(fromIndex, toIndex);
    }
    
    @Override
    public Object[] toArray() {
        return this.m_list.toArray();
    }
    
    @Override
    public <T> T[] toArray(final T[] array) {
        return this.m_list.toArray(array);
    }
}
