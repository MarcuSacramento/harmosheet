// 
// Decompiled by Procyon v0.5.30
// 

package harmotab.performance;

import harmotab.core.HarmoTabObjectEvent;
import java.util.Iterator;
import java.util.ArrayList;
import java.util.LinkedList;
import harmotab.core.HarmoTabObjectListener;

public class PerformancesList implements Iterable<Performance>, HarmoTabObjectListener
{
    protected int m_defaultIndex;
    private final LinkedList<Performance> m_list;
    private final ArrayList<PerformanceListListener> m_listeners;
    
    public PerformancesList() {
        this.m_list = new LinkedList<Performance>();
        this.m_listeners = new ArrayList<PerformanceListListener>();
        this.setDefaultPerformance(-1);
    }
    
    public int size() {
        return this.m_list.size();
    }
    
    public void add(final Performance perf) {
        this.m_list.add(perf);
        perf.addObjectListener(this);
        this.firePerformanceListChanged();
    }
    
    public boolean remove(final Performance perf) {
        final boolean res = this.m_list.remove(perf);
        if (res) {
            perf.removeObjectListener(this);
            this.firePerformanceListChanged();
        }
        return res;
    }
    
    @Override
    public Iterator<Performance> iterator() {
        return this.m_list.iterator();
    }
    
    public int indexOf(final Performance performance) {
        return this.m_list.indexOf(performance);
    }
    
    public Performance get(final int index) {
        return this.m_list.get(index);
    }
    
    public int getDefaultPerformance() {
        return (this.size() > 0) ? this.m_defaultIndex : -1;
    }
    
    public void setDefaultPerformance(final int index) {
        this.m_defaultIndex = index;
        this.fireDefaultPerformanceChanged();
    }
    
    @Override
    public void onObjectChanged(final HarmoTabObjectEvent event) {
        this.firePerformanceListItemChanged((Performance)event.getSource());
    }
    
    public void addPerformanceListListener(final PerformanceListListener listener) {
        this.m_listeners.add(listener);
    }
    
    public void removePerformanceListListener(final PerformanceListListener listener) {
        this.m_listeners.remove(listener);
    }
    
    protected void firePerformanceListChanged() {
        for (final PerformanceListListener listener : this.m_listeners) {
            listener.onPerformanceListChanged(this);
        }
    }
    
    protected void fireDefaultPerformanceChanged() {
        for (final PerformanceListListener listener : this.m_listeners) {
            listener.onDefaultPerformanceChanged(this);
        }
    }
    
    protected void firePerformanceListItemChanged(final Performance perf) {
        for (final PerformanceListListener listener : this.m_listeners) {
            listener.onPerformanceListItemChanged(this, perf);
        }
    }
}
