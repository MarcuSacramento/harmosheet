// 
// Decompiled by Procyon v0.5.30
// 

package harmotab.desktop.components;

import harmotab.sound.ScorePlayerEvent;
import harmotab.core.Score;
import java.awt.event.ActionEvent;
import java.awt.Component;
import java.awt.Dimension;
import harmotab.core.GlobalPreferences;
import harmotab.core.Localizer;
import harmotab.desktop.actions.StopAction;
import harmotab.desktop.actions.PauseAction;
import harmotab.desktop.actions.PlayFromAction;
import harmotab.desktop.actions.UserAction;
import harmotab.desktop.actions.PlayAction;
import javax.swing.JCheckBox;
import harmotab.desktop.tools.ToolToggleButton;
import harmotab.desktop.tools.ToolButton;
import harmotab.sound.ScorePlayer;
import harmotab.core.ScoreController;
import java.awt.event.ActionListener;
import harmotab.sound.ScorePlayerListener;
import harmotab.core.ScoreControllerListener;
import javax.swing.JToolBar;

public class PlayerControlPane extends JToolBar implements ScoreControllerListener, ScorePlayerListener, ActionListener
{
    private static final long serialVersionUID = 1L;
    protected ScoreController m_scoreController;
    protected ScorePlayer m_soundPlayer;
    private ToolButton m_playButton;
    private ToolButton m_playFromButton;
    private ToolToggleButton m_pauseButton;
    private ToolButton m_stopButton;
    private JCheckBox m_metronomeCheckBox;
    
    public PlayerControlPane(final ScoreController controller) {
        this.m_scoreController = null;
        this.m_soundPlayer = null;
        this.m_playButton = null;
        this.m_playFromButton = null;
        this.m_pauseButton = null;
        this.m_stopButton = null;
        this.m_metronomeCheckBox = null;
        this.m_scoreController = controller;
        this.setFloatable(false);
        this.m_playButton = new ToolButton(new PlayAction());
        this.m_playFromButton = new ToolButton(new PlayFromAction());
        this.m_pauseButton = new ToolToggleButton(new PauseAction());
        this.m_stopButton = new ToolButton(new StopAction());
        this.m_metronomeCheckBox = new JCheckBox(Localizer.get("N_METRONOME"), GlobalPreferences.getMetronomeEnabled());
        this.m_playButton.setText(null);
        this.m_pauseButton.setText(null);
        this.m_stopButton.setText(null);
        this.m_playFromButton.setText(null);
        this.m_playButton.setEnabled(false);
        this.m_pauseButton.setEnabled(false);
        this.m_stopButton.setEnabled(false);
        this.m_playFromButton.setEnabled(false);
        this.m_metronomeCheckBox.setEnabled(false);
        this.m_playButton.setWide(true);
        this.m_playFromButton.setWide(true);
        this.m_pauseButton.setPreferredSize(null);
        this.m_stopButton.setWide(true);
        this.m_metronomeCheckBox.setOpaque(false);
        this.add(this.m_playButton);
        this.add(this.m_playFromButton);
        this.add(this.m_pauseButton);
        this.add(this.m_stopButton);
        if (GlobalPreferences.getMetronomeFeatureEnabled()) {
            this.add(this.m_metronomeCheckBox);
        }
        this.m_scoreController.addScoreControllerListener(this);
        this.m_metronomeCheckBox.addActionListener(this);
        this.setSoundPlayer(this.m_scoreController.getScorePlayer());
    }
    
    protected void updateButtonsStates() {
        final boolean hasScore = this.m_scoreController.hasScore();
        final boolean opened = this.m_soundPlayer.getState() == 2;
        final boolean playing = this.m_soundPlayer.isPlaying();
        final boolean paused = this.m_soundPlayer.isPaused();
        this.m_playButton.setEnabled(hasScore && opened && !playing && !paused);
        this.m_playFromButton.setEnabled(hasScore && opened && !playing && !paused);
        this.m_pauseButton.setEnabled(opened && (playing || paused));
        this.m_stopButton.setEnabled(opened && (playing || paused));
        this.m_pauseButton.setSelected(hasScore && paused);
        this.m_metronomeCheckBox.setEnabled(hasScore && opened && !playing && !paused);
    }
    
    protected void setSoundPlayer(final ScorePlayer player) {
        if (this.m_soundPlayer != null) {
            this.m_soundPlayer.removeSoundPlayerListener(this);
        }
        (this.m_soundPlayer = player).addSoundPlayerListener(this);
    }
    
    @Override
    public void actionPerformed(final ActionEvent event) {
        if (event.getSource() == this.m_metronomeCheckBox) {
            GlobalPreferences.setMetronomeEnabled(this.m_metronomeCheckBox.isSelected());
        }
    }
    
    @Override
    public void onControlledScoreChanged(final ScoreController controller, final Score scoreControlled) {
        this.updateButtonsStates();
    }
    
    @Override
    public void onScorePlayerChanged(final ScoreController controller, final ScorePlayer soundPlayer) {
        this.setSoundPlayer(soundPlayer);
        this.updateButtonsStates();
    }
    
    @Override
    public void onScorePlayerStateChanged(final ScorePlayerEvent event) {
        this.updateButtonsStates();
    }
    
    @Override
    public void onScorePlayerError(final ScorePlayerEvent event, final Throwable error) {
        error.printStackTrace();
        this.updateButtonsStates();
    }
    
    @Override
    public void onPlaybackStarted(final ScorePlayerEvent event) {
        this.updateButtonsStates();
    }
    
    @Override
    public void onPlaybackPaused(final ScorePlayerEvent event) {
        this.updateButtonsStates();
    }
    
    @Override
    public void onPlaybackStopped(final ScorePlayerEvent event, final boolean endOfPlayback) {
        this.updateButtonsStates();
    }
    
    @Override
    public void onPlayedSoundItemChanged(final ScorePlayerEvent event) {
    }
}
