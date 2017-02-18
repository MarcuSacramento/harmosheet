// 
// Decompiled by Procyon v0.5.30
// 

package harmotab.renderer;

import java.util.ListIterator;

public class ElementLocationIterator
{
    private ListIterator<LocationItem> m_iterator;
    private Class<?> m_class;
    
    public ElementLocationIterator(final ListIterator<LocationItem> i, final Class<?> elementClass) {
        this.m_iterator = null;
        this.m_class = null;
        this.m_iterator = i;
        this.m_class = elementClass;
    }
    
    public LocationItem next() {
        while (this.m_iterator.hasNext()) {
            final LocationItem item = this.m_iterator.next();
            if (this.m_class.isInstance(item.getElement())) {
                return item;
            }
        }
        return null;
    }
    
    public LocationItem previous() {
        while (this.m_iterator.hasPrevious()) {
            final LocationItem item = this.m_iterator.previous();
            if (this.m_class.isInstance(item.getElement())) {
                return item;
            }
        }
        return null;
    }
}
