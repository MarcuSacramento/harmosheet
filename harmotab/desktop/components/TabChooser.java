// 
// Decompiled by Procyon v0.5.30
// 

package harmotab.desktop.components;

import java.awt.event.ActionEvent;
import javax.swing.event.ChangeEvent;
import java.awt.event.ActionListener;
import javax.swing.event.ChangeListener;
import java.awt.Component;
import java.awt.LayoutManager;
import java.awt.FlowLayout;
import harmotab.element.Tab;
import javax.swing.event.EventListenerList;
import javax.swing.JPanel;

public class TabChooser extends JPanel
{
    private static final long serialVersionUID = 1L;
    private EventListenerList m_listeners;
    private Tab m_tab;
    private HoleChooser m_holeChooser;
    private DirectionChooser m_directionChooser;
    private PushedChooser m_pushedChooser;
    
    public TabChooser(final Tab tab, final boolean showPushButton) {
        this.m_listeners = new EventListenerList();
        this.m_tab = null;
        this.m_holeChooser = null;
        this.m_directionChooser = null;
        this.m_pushedChooser = null;
        this.m_tab = ((tab != null) ? new Tab(tab) : new Tab());
        this.m_holeChooser = new HoleChooser((byte)0);
        this.m_directionChooser = new DirectionChooser();
        this.m_pushedChooser = (showPushButton ? new PushedChooser(this.m_tab.isPushed()) : null);
        if (this.m_tab != null) {
            this.m_holeChooser.setValue(this.m_tab.getHole());
            this.m_directionChooser.setTab(this.m_tab);
        }
        this.setLayout(new FlowLayout(3, 5, 0));
        this.add(this.m_holeChooser);
        this.add(this.m_directionChooser);
        if (this.m_pushedChooser != null) {
            this.add(this.m_pushedChooser);
        }
        final HarmoTabChangeAction listener = new HarmoTabChangeAction();
        this.m_holeChooser.addChangeListener(listener);
        this.m_directionChooser.addActionListener(listener);
        if (this.m_pushedChooser != null) {
            this.m_pushedChooser.addActionListener(listener);
        }
    }
    
    public TabChooser() {
        this((Tab)null, false);
    }
    
    @Override
    public void setEnabled(final boolean enabled) {
        this.m_directionChooser.setEnabled(enabled);
        this.m_holeChooser.setEnabled(enabled);
    }
    
    public void setTab(final Tab tab) {
        this.m_tab = ((tab != null) ? new Tab(tab) : new Tab());
        this.m_holeChooser.setValue(this.m_tab.getHole());
        this.m_directionChooser.setTab(this.m_tab);
    }
    
    public Tab getTab() {
        return new Tab(this.m_tab);
    }
    
    public void addChangeListener(final ChangeListener listener) {
        this.m_listeners.add(ChangeListener.class, listener);
    }
    
    public void removeChangeListener(final ChangeListener listener) {
        this.m_listeners.remove(ChangeListener.class, listener);
    }
    
    public void fireTabChanged() {
        ChangeListener[] array;
        for (int length = (array = this.m_listeners.getListeners(ChangeListener.class)).length, i = 0; i < length; ++i) {
            final ChangeListener listener = array[i];
            listener.stateChanged(new ChangeEvent(this));
        }
    }
    
    static /* synthetic */ void access$2(final TabChooser tabChooser, final Tab tab) {
        tabChooser.m_tab = tab;
    }
    
    private class HarmoTabChangeAction implements ChangeListener, ActionListener
    {
        @Override
        public void stateChanged(final ChangeEvent event) {
            this.updateHole();
        }
        
        @Override
        public void actionPerformed(final ActionEvent event) {
            this.updateTab();
        }
        
        private void updateHole() {
            final int hole = (int)TabChooser.this.m_holeChooser.getValue();
            if (TabChooser.this.m_tab == null) {
                TabChooser.access$2(TabChooser.this, new Tab(hole));
            }
            else {
                TabChooser.this.m_tab.setHole(hole);
            }
            TabChooser.this.fireTabChanged();
        }
        
        private void updateTab() {
            final Tab selected = TabChooser.this.m_directionChooser.getTab(0);
            if (TabChooser.this.m_tab == null) {
                TabChooser.access$2(TabChooser.this, selected);
            }
            else {
                TabChooser.this.m_tab.setDirection(selected.getDirection());
                TabChooser.this.m_tab.setBend(selected.getBend());
                TabChooser.this.m_tab.setPushed(TabChooser.this.m_pushedChooser != null && TabChooser.this.m_pushedChooser.isSelected());
            }
            TabChooser.this.fireTabChanged();
        }
    }
}
