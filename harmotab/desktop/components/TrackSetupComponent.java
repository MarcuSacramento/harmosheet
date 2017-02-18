// 
// Decompiled by Procyon v0.5.30
// 

package harmotab.desktop.components;

import harmotab.desktop.setupdialog.TrackSetupDialog;
import harmotab.desktop.tools.ToolIcon;
import harmotab.core.Localizer;
import harmotab.core.HarmoTabObjectEvent;
import javax.swing.event.ChangeEvent;
import java.awt.Component;
import java.awt.LayoutManager;
import java.awt.BorderLayout;
import harmotab.desktop.actions.UserAction;
import harmotab.desktop.actions.ActionButton;
import java.awt.Window;
import javax.swing.JLabel;
import harmotab.track.Track;
import harmotab.core.HarmoTabObjectListener;
import javax.swing.event.ChangeListener;
import javax.swing.JPanel;

public class TrackSetupComponent extends JPanel implements ChangeListener, HarmoTabObjectListener
{
    private static final long serialVersionUID = 1L;
    private Track m_track;
    private JLabel m_trackNameLabel;
    private VolumeControl m_volumeControl;
    private Window m_parentWindow;
    
    public TrackSetupComponent(final Window parent, final Track track) {
        this.m_track = null;
        this.m_trackNameLabel = null;
        this.m_volumeControl = null;
        this.m_parentWindow = null;
        this.m_parentWindow = parent;
        this.m_track = track;
        (this.m_trackNameLabel = new JLabel(track.getName())).setOpaque(false);
        this.m_volumeControl = new VolumeControl(this.m_track.getVolume());
        final ActionButton trackSetupButton = new ActionButton(new TrackSetupAction());
        trackSetupButton.setText(null);
        this.setLayout(new BorderLayout(10, 10));
        this.add(this.m_trackNameLabel, "Center");
        this.add(trackSetupButton, "West");
        this.add(this.m_volumeControl, "East");
        this.m_volumeControl.addChangeListener(this);
        this.m_track.addObjectListener(this);
        this.setOpaque(false);
        this.update();
    }
    
    private void update() {
        this.m_trackNameLabel.setText(this.m_track.getName());
        this.m_volumeControl.setValue(this.m_track.getVolume());
    }
    
    public void finalize() {
        this.m_track.removeObjectListener(this);
    }
    
    @Override
    public void stateChanged(final ChangeEvent event) {
        if (event.getSource() == this.m_volumeControl && this.m_volumeControl.getValue() != this.m_track.getVolume()) {
            this.m_track.setVolume(this.m_volumeControl.getValue());
        }
    }
    
    @Override
    public void onObjectChanged(final HarmoTabObjectEvent event) {
        this.update();
    }
    
    private class TrackSetupAction extends UserAction
    {
        private static final long serialVersionUID = 1L;
        
        public TrackSetupAction() {
            super(Localizer.get("TT_SETUP"), ToolIcon.getIcon((byte)16));
        }
        
        @Override
        public void run() {
            TrackSetupDialog.create(TrackSetupComponent.this.m_parentWindow, TrackSetupComponent.this.m_track).setVisible(true);
        }
    }
}
