// 
// Decompiled by Procyon v0.5.30
// 

package harmotab.desktop;

import harmotab.sound.ScorePlayer;
import javax.swing.event.ChangeEvent;
import harmotab.core.ScoreViewSelection;
import javax.swing.event.ChangeListener;
import javax.swing.Box;
import java.awt.Component;
import harmotab.core.Localizer;
import harmotab.core.GlobalPreferences;
import harmotab.core.ScoreView;
import harmotab.desktop.components.PerformancesListControlPane;
import harmotab.desktop.components.PlayerControlPane;
import harmotab.desktop.components.VolumeControl;
import javax.swing.JLabel;
import harmotab.track.Track;
import harmotab.core.ScoreController;
import javax.swing.JToolBar;

public class ScorePlaybackToolBar extends JToolBar implements SelectionListener
{
    private static final long serialVersionUID = 1L;
    private ScoreController m_scoreController;
    private Track m_selectedTrack;
    private JLabel m_trackNameLabel;
    private JLabel m_trackVolumeLabel;
    private VolumeControl m_globalVolume;
    private PlayerControlPane m_playerControlPane;
    private PerformancesListControlPane m_perfsControlPane;
    private GlobalPreferencesObserver m_globalPreferencesObserver;
    
    public ScorePlaybackToolBar(final ScoreController controller, final ScoreView scoreView) {
        this.m_scoreController = null;
        this.m_selectedTrack = null;
        this.m_trackNameLabel = null;
        this.m_trackVolumeLabel = null;
        this.m_globalVolume = null;
        this.m_playerControlPane = null;
        this.m_perfsControlPane = null;
        this.m_globalPreferencesObserver = null;
        this.m_selectedTrack = null;
        this.m_scoreController = controller;
        this.m_globalVolume = new VolumeControl(controller, GlobalPreferences.getGlobalVolume());
        this.m_playerControlPane = new PlayerControlPane(controller);
        this.m_trackNameLabel = new JLabel("");
        this.m_trackVolumeLabel = new JLabel(String.valueOf(Localizer.get("N_VOLUME")) + " : ");
        this.add(this.m_perfsControlPane = new PerformancesListControlPane(controller));
        this.add(this.m_playerControlPane);
        this.addSeparator();
        this.add(Box.createHorizontalGlue());
        this.add(this.m_trackNameLabel);
        this.add(Box.createHorizontalGlue());
        this.add(this.m_trackVolumeLabel);
        this.add(this.m_globalVolume);
        this.m_globalVolume.addChangeListener(new UserActionObserver((UserActionObserver)null));
        GlobalPreferences.addChangeListener(this.m_globalPreferencesObserver = new GlobalPreferencesObserver((GlobalPreferencesObserver)null));
        DesktopController.getInstance().addSelectionListener(this);
    }
    
    public void finalize() {
        GlobalPreferences.removeChangeListener(this.m_globalPreferencesObserver);
        DesktopController.getInstance().removeSelectionListener(this);
    }
    
    @Override
    public void onSelectionChanged(final ScoreViewSelection selection) {
        if (selection == null) {
            this.m_selectedTrack = null;
            this.m_trackNameLabel.setText("");
        }
        else {
            this.m_selectedTrack = selection.getTrack();
            if (this.m_selectedTrack != null) {
                this.m_trackNameLabel.setText(this.m_selectedTrack.getName());
            }
        }
    }
    
    private class UserActionObserver implements ChangeListener
    {
        @Override
        public void stateChanged(final ChangeEvent event) {
            if (event.getSource() == ScorePlaybackToolBar.this.m_globalVolume) {
                final int volumeValue = ScorePlaybackToolBar.this.m_globalVolume.getValue();
                if (volumeValue != GlobalPreferences.getGlobalVolume()) {
                    GlobalPreferences.setMidiGlobalVolume(ScorePlaybackToolBar.this.m_globalVolume.getValue());
                    final ScorePlayer player = ScorePlaybackToolBar.this.m_scoreController.getScorePlayer();
                    if (player != null) {
                        player.setGlobalVolume(volumeValue);
                    }
                }
            }
        }
    }
    
    private class GlobalPreferencesObserver implements ChangeListener
    {
        @Override
        public void stateChanged(final ChangeEvent event) {
            ScorePlaybackToolBar.this.m_globalVolume.setValue(GlobalPreferences.getGlobalVolume());
        }
    }
}
