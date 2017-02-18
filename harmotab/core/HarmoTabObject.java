// 
// Decompiled by Procyon v0.5.30
// 

package harmotab.core;

import java.util.Iterator;
import harmotab.core.undo.UndoManager;
import java.util.LinkedList;
import harmotab.core.undo.Restoreable;
import harmotab.io.SerializableObject;

public abstract class HarmoTabObject implements Cloneable, SerializableObject, Restoreable
{
    private LinkedList<HarmoTabObjectListener> m_listeners;
    private boolean m_dispatchEvents;
    protected UndoManager m_undoManager;
    
    public HarmoTabObject() {
        this.m_listeners = null;
        this.m_undoManager = null;
        this.m_undoManager = UndoManager.getInstance();
        this.m_dispatchEvents = true;
        this.m_listeners = new LinkedList<HarmoTabObjectListener>();
    }
    
    @Override
    protected Object clone() {
        try {
            final HarmoTabObject clone = (HarmoTabObject)super.clone();
            clone.m_listeners = new LinkedList<HarmoTabObjectListener>();
            return clone;
        }
        catch (CloneNotSupportedException e) {
            e.printStackTrace();
            return null;
        }
    }
    
    public void addObjectListener(final HarmoTabObjectListener listener) {
        this.m_listeners.add(listener);
    }
    
    public void removeObjectListener(final HarmoTabObjectListener listener) {
        this.m_listeners.remove(listener);
    }
    
    protected synchronized void dispatchEvent(final HarmoTabObjectEvent event) {
        if (this.m_dispatchEvents) {
            for (final HarmoTabObjectListener listener : this.m_listeners) {
                listener.onObjectChanged(event);
            }
        }
    }
    
    protected synchronized void fireObjectChanged(final String propertyName) {
        if (this.m_dispatchEvents) {
            final HarmoTabObjectEvent event = new HarmoTabObjectEvent(this, propertyName);
            for (final HarmoTabObjectListener listener : this.m_listeners) {
                listener.onObjectChanged(event);
            }
        }
    }
    
    protected void addAttributeChangesObserver(final HarmoTabObject attribute, final String name) {
        if (attribute != null) {
            attribute.addObjectListener(new AttributeChangesObserver(this, name));
        }
    }
    
    protected void removeAttributeChangesObserver(final HarmoTabObject attribute, final String name) {
        if (attribute != null) {
            for (final HarmoTabObjectListener listener : attribute.m_listeners) {
                if (listener instanceof AttributeChangesObserver && ((AttributeChangesObserver)listener).m_attribute.equals(name)) {
                    attribute.m_listeners.remove(listener);
                }
            }
        }
    }
    
    public synchronized void setDispachEvents(final boolean dispatchEvents, final String notificationEvent) {
        this.m_dispatchEvents = dispatchEvents;
        if (notificationEvent != null) {
            this.fireObjectChanged(notificationEvent);
        }
    }
}
