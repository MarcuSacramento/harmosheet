// 
// Decompiled by Procyon v0.5.30
// 

package harmotab.desktop.components;

import harmotab.core.HarmoTabObjectEvent;
import java.awt.event.MouseEvent;
import harmotab.element.Note;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Iterator;
import java.awt.Graphics2D;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Dimension;
import harmotab.core.HarmoTabObjectListener;
import java.awt.event.MouseMotionListener;
import java.awt.event.MouseListener;
import harmotab.element.Element;
import harmotab.renderer.LocationItem;
import harmotab.renderer.LocationList;
import harmotab.renderer.ElementRendererBundle;
import harmotab.track.Track;
import javax.swing.Action;
import javax.swing.event.EventListenerList;
import javax.swing.JPanel;

class TrackPane extends JPanel
{
    private static final long serialVersionUID = 1L;
    private static final int WIDTH = 2048;
    private static final int HEIGHT = 180;
    private static final int XOFFSET = 15;
    private final EventListenerList m_listeners;
    private Action m_popupTriggerAction;
    private Track m_track;
    private ElementRendererBundle m_elementRenderer;
    private LocationList m_locations;
    private LocationItem m_over;
    private LocationItem m_selected;
    private Element m_requestedElementSelection;
    
    public TrackPane(final Track track, final ElementRendererBundle renderer) {
        this.m_listeners = new EventListenerList();
        this.m_popupTriggerAction = null;
        this.m_track = null;
        this.m_elementRenderer = null;
        this.m_locations = null;
        this.m_over = null;
        this.m_selected = null;
        this.m_requestedElementSelection = null;
        this.m_locations = new LocationList();
        this.m_over = null;
        this.m_track = track;
        this.m_elementRenderer = renderer;
        this.m_popupTriggerAction = null;
        final MouseObserver mouseObserver = new MouseObserver();
        this.addMouseListener(mouseObserver);
        this.addMouseMotionListener(mouseObserver);
        this.m_track.addObjectListener(new TrackChangesObserver());
        final Dimension size = new Dimension(2048, 180);
        this.setPreferredSize(size);
        this.setMinimumSize(size);
        this.m_track.getTrackLayout().processElementsPositionning(this.m_locations, 2048, this.m_track.getDuration());
    }
    
    public Track getTrack() {
        return this.m_track;
    }
    
    public Element getSelectedItem() {
        if (this.m_selected == null) {
            return null;
        }
        return this.m_selected.getElement();
    }
    
    public LocationList getCurrentLocations() {
        return this.m_locations;
    }
    
    public void setPopupTriggerAction(final Action action) {
        this.m_popupTriggerAction = action;
    }
    
    public void setSelectedItem(final LocationItem item) {
        this.m_selected = item;
        this.repaint();
    }
    
    public void setSelectedElement(final Element element) {
        this.m_selected = this.m_locations.get(this.m_requestedElementSelection);
        this.repaint();
    }
    
    @Override
    public void paint(final Graphics g) {
        g.setColor(Color.WHITE);
        g.fillRect(0, 0, 2048, 180);
        g.setColor(Color.BLACK);
        if (this.m_selected != null) {
            g.setColor(new Color(16777152));
            g.fillRect(this.m_selected.getX1() - 15, 0, this.m_selected.getWidth(), 180);
        }
        this.m_locations.reset();
        this.m_track.getTrackLayout().processElementsPositionning(this.m_locations, 2048, this.m_track.getDuration());
        for (final LocationItem item : this.m_locations) {
            this.m_elementRenderer.paintElement((Graphics2D)g, item);
        }
        if (this.m_over != null && this.m_over != this.m_selected) {
            g.setColor(Color.GRAY);
            g.drawRect(this.m_over.getX1() - 15, 0, this.m_over.getWidth(), 180);
        }
    }
    
    public void addActionListener(final ActionListener listener) {
        this.m_listeners.add(ActionListener.class, listener);
    }
    
    public void removeActionListener(final ActionListener listener) {
        this.m_listeners.remove(ActionListener.class, listener);
    }
    
    private void fireActionPerformed() {
        ActionListener[] array;
        for (int length = (array = this.m_listeners.getListeners(ActionListener.class)).length, i = 0; i < length; ++i) {
            final ActionListener listener = array[i];
            listener.actionPerformed(new ActionEvent(this, 0, ""));
        }
    }
    
    static /* synthetic */ void access$1(final TrackPane trackPane, final LocationItem selected) {
        trackPane.m_selected = selected;
    }
    
    static /* synthetic */ void access$2(final TrackPane trackPane, final LocationItem over) {
        trackPane.m_over = over;
    }
    
    class MouseObserver implements MouseListener, MouseMotionListener
    {
        private boolean trigeringPopupOnPressed;
        
        MouseObserver() {
            this.trigeringPopupOnPressed = false;
        }
        
        public void updateSelection(final int mouseX, final int mouseY) {
            final LocationItem selected = TrackPane.this.m_locations.at(mouseX + 15, mouseY);
            if (selected != null && selected.getElement() instanceof Note) {
                TrackPane.access$1(TrackPane.this, selected);
            }
            else {
                TrackPane.access$1(TrackPane.this, null);
            }
            TrackPane.this.repaint();
        }
        
        @Override
        public void mouseMoved(final MouseEvent event) {
            final LocationItem selected = TrackPane.this.m_locations.at(event.getX() + 15, event.getY());
            if (selected != null && selected.getElement() instanceof Note) {
                TrackPane.access$2(TrackPane.this, selected);
            }
            else {
                TrackPane.access$2(TrackPane.this, null);
            }
            TrackPane.this.repaint();
        }
        
        @Override
        public void mouseEntered(final MouseEvent event) {
            this.mouseMoved(event);
        }
        
        @Override
        public void mouseExited(final MouseEvent event) {
            TrackPane.access$2(TrackPane.this, null);
            TrackPane.this.repaint();
        }
        
        @Override
        public void mousePressed(final MouseEvent event) {
            if (event.isPopupTrigger() && TrackPane.this.m_popupTriggerAction != null) {
                this.updateSelection(event.getX(), event.getY());
                TrackPane.this.m_popupTriggerAction.actionPerformed(new ActionEvent(event.getSource(), event.getID(), ""));
                this.trigeringPopupOnPressed = true;
            }
        }
        
        @Override
        public void mouseReleased(final MouseEvent event) {
            this.updateSelection(event.getX(), event.getY());
            if (event.isPopupTrigger() && TrackPane.this.m_popupTriggerAction != null) {
                TrackPane.this.m_popupTriggerAction.actionPerformed(new ActionEvent(event.getSource(), event.getID(), ""));
            }
            else if (!this.trigeringPopupOnPressed) {
                TrackPane.this.fireActionPerformed();
            }
            this.trigeringPopupOnPressed = false;
        }
        
        @Override
        public void mouseClicked(final MouseEvent event) {
        }
        
        @Override
        public void mouseDragged(final MouseEvent event) {
        }
    }
    
    class TrackChangesObserver implements HarmoTabObjectListener
    {
        @Override
        public void onObjectChanged(final HarmoTabObjectEvent event) {
            TrackPane.this.repaint();
        }
    }
}
