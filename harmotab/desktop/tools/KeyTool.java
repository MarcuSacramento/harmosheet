// 
// Decompiled by Procyon v0.5.30
// 

package harmotab.desktop.tools;

import harmotab.track.Track;
import java.awt.Window;
import harmotab.desktop.setupdialog.TrackSetupDialog;
import harmotab.core.undo.UndoManager;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.awt.event.ActionListener;
import java.awt.Component;
import harmotab.core.Localizer;
import harmotab.renderer.LocationItem;
import harmotab.core.Score;
import java.awt.Container;
import javax.swing.JButton;

public class KeyTool extends Tool
{
    private static final long serialVersionUID = 1L;
    private JButton m_trackPropertiesButton;
    
    public KeyTool(final Container container, final Score score, final LocationItem item) {
        super(container, score, item);
        this.m_trackPropertiesButton = null;
        this.add(this.m_trackPropertiesButton = new ToolButton(Localizer.get("ET_TRACK_SETUP"), (byte)16, Localizer.get("ET_TRACK_SETUP")));
        final UserActionObserver listener = new UserActionObserver();
        this.m_trackPropertiesButton.addActionListener(listener);
    }
    
    @Override
    public void keyTyped(final KeyEvent event) {
    }
    
    private class UserActionObserver implements ActionListener
    {
        @Override
        public void actionPerformed(final ActionEvent event) {
            if (event.getSource() == KeyTool.this.m_trackPropertiesButton) {
                final Track track = KeyTool.this.getTrack();
                UndoManager.getInstance().addUndoCommand(track.createRestoreCommand(), Localizer.get("ET_TRACK_SETUP"));
                TrackSetupDialog.create(null, track).setVisible(true);
            }
        }
    }
}
