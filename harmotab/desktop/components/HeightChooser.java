// 
// Decompiled by Procyon v0.5.30
// 

package harmotab.desktop.components;

import java.awt.event.ActionEvent;
import harmotab.core.HarmoTabObjectEvent;
import harmotab.renderer.LocationItem;
import java.awt.Graphics2D;
import harmotab.renderer.AwtPrintingElementRendererBundle;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import harmotab.track.Track;
import java.awt.Dimension;
import harmotab.core.HarmoTabObjectListener;
import java.awt.event.ActionListener;
import java.awt.LayoutManager;
import java.awt.BorderLayout;
import java.awt.Component;
import javax.swing.Box;
import harmotab.desktop.tools.ToolButton;
import harmotab.core.Localizer;
import harmotab.renderer.ElementRendererBundle;
import harmotab.element.Note;
import harmotab.element.Element;
import harmotab.element.Bar;
import harmotab.core.RepeatAttribute;
import harmotab.element.TimeSignature;
import harmotab.element.KeySignature;
import harmotab.element.Key;
import harmotab.track.StaffTrack;
import harmotab.core.Score;
import javax.swing.event.EventListenerList;
import harmotab.core.Height;
import javax.swing.JButton;
import javax.swing.JPanel;

public class HeightChooser extends JPanel
{
    private static final long serialVersionUID = 1L;
    private TrackPane m_trackPane;
    private JButton m_upButton;
    private JButton m_downButton;
    private AlterationChooser m_alterationChooser;
    private Height m_height;
    private EventListenerList m_listeners;
    
    public HeightChooser(final Height height) {
        this.m_trackPane = null;
        this.m_upButton = null;
        this.m_downButton = null;
        this.m_alterationChooser = null;
        this.m_height = null;
        this.m_listeners = new EventListenerList();
        this.m_height = new Height(height);
        final Track track = new StaffTrack(new Score());
        track.add(new Bar(new Key(), new KeySignature(), new TimeSignature(), new RepeatAttribute()));
        track.add(new Note(this.m_height));
        this.m_trackPane = new TrackPane(track, new CustomElementRenderer());
        this.m_upButton = new ToolButton(Localizer.get("TT_MOVE_UP"), (byte)5);
        this.m_downButton = new ToolButton(Localizer.get("TT_MOVE_DOWN"), (byte)6);
        this.m_alterationChooser = new AlterationChooser(this.m_height.getAlteration());
        final Box buttonsBox = new Box(1);
        buttonsBox.add(this.m_upButton);
        buttonsBox.add(this.m_downButton);
        final JPanel controlPanel = new JPanel(new BorderLayout());
        controlPanel.setOpaque(false);
        controlPanel.add(buttonsBox, "Center");
        controlPanel.add(this.m_alterationChooser, "South");
        this.setLayout(new BorderLayout(10, 10));
        this.add(this.m_trackPane, "Center");
        this.add(controlPanel, "East");
        final UserActionObserver listener = new UserActionObserver();
        this.m_alterationChooser.addActionListener(listener);
        this.m_upButton.addActionListener(listener);
        this.m_downButton.addActionListener(listener);
        this.m_height.addObjectListener(new HeightChangesObserver());
        this.setOpaque(false);
        this.setPreferredSize(new Dimension(190, 110));
    }
    
    public Height getSelectedHeight() {
        return new Height(this.m_height);
    }
    
    public void addChangeListener(final ChangeListener listener) {
        this.m_listeners.add(ChangeListener.class, listener);
    }
    
    public void removeChangeListener(final ChangeListener listener) {
        this.m_listeners.remove(ChangeListener.class, listener);
    }
    
    protected void fireHeightChanged() {
        ChangeListener[] array;
        for (int length = (array = this.m_listeners.getListeners(ChangeListener.class)).length, i = 0; i < length; ++i) {
            final ChangeListener listener = array[i];
            listener.stateChanged(new ChangeEvent(this));
        }
    }
    
    class CustomElementRenderer extends AwtPrintingElementRendererBundle
    {
        @Override
        protected void paintTimeSignature(final Graphics2D g, final TimeSignature ts, final LocationItem l) {
        }
    }
    
    private class HeightChangesObserver implements HarmoTabObjectListener
    {
        @Override
        public void onObjectChanged(final HarmoTabObjectEvent event) {
            HeightChooser.this.fireHeightChanged();
        }
    }
    
    private class UserActionObserver implements ActionListener
    {
        @Override
        public void actionPerformed(final ActionEvent event) {
            if (event.getSource() == HeightChooser.this.m_upButton) {
                HeightChooser.this.m_height.moveUp();
            }
            else if (event.getSource() == HeightChooser.this.m_downButton) {
                HeightChooser.this.m_height.moveDown();
            }
            else if (event.getSource() == HeightChooser.this.m_alterationChooser) {
                HeightChooser.this.m_height.setAlteration(HeightChooser.this.m_alterationChooser.getSelectedAlteration());
            }
        }
    }
}
