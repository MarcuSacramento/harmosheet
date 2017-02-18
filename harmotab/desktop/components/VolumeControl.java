// 
// Decompiled by Procyon v0.5.30
// 

package harmotab.desktop.components;

import javax.swing.event.ChangeEvent;
import harmotab.sound.ScorePlayerEvent;
import harmotab.core.Score;
import harmotab.core.ScoreController;
import java.awt.Dimension;
import javax.swing.event.ChangeListener;
import java.awt.Component;
import java.awt.LayoutManager;
import java.awt.BorderLayout;
import harmotab.sound.ScorePlayer;
import javax.swing.JLabel;
import javax.swing.JSlider;
import javax.swing.event.EventListenerList;
import harmotab.sound.ScorePlayerListener;
import harmotab.core.ScoreControllerListener;
import javax.swing.JPanel;

public class VolumeControl extends JPanel implements ScoreControllerListener, ScorePlayerListener
{
    private static final long serialVersionUID = 1L;
    private static final int DEFAULT_WIDTH = 110;
    private EventListenerList m_listeners;
    private JSlider m_slider;
    private JLabel m_valueLabel;
    private VolumeControl m_instance;
    private ScorePlayer m_scorePlayer;
    
    public VolumeControl(final int value) {
        this.m_listeners = new EventListenerList();
        this.m_slider = null;
        this.m_valueLabel = null;
        this.m_instance = null;
        this.m_scorePlayer = null;
        this.m_instance = this;
        (this.m_slider = new JSlider(0, 100, value)).setOpaque(false);
        this.m_valueLabel = new JLabel(String.valueOf(value) + " %");
        this.setLayout(new BorderLayout());
        this.add(this.m_slider, "Center");
        final Dimension volumeSliderSize = this.getPreferredSize();
        volumeSliderSize.width = 110;
        this.setPreferredSize(volumeSliderSize);
        this.setMaximumSize(volumeSliderSize);
        this.setSize(volumeSliderSize);
        this.m_slider.addChangeListener(new SliderValueObserver((SliderValueObserver)null));
        this.setOpaque(false);
    }
    
    public VolumeControl(final ScoreController controller, final int value) {
        this(value);
        this.setScorePlayer(controller.getScorePlayer());
        controller.addScoreControllerListener(this);
        this.updateEnabledStatus();
    }
    
    public int getValue() {
        return this.m_slider.getValue();
    }
    
    public void setValue(final int value) {
        this.m_slider.setValue(value);
    }
    
    @Override
    public void setEnabled(final boolean enabled) {
        this.m_slider.setEnabled(enabled);
    }
    
    private void setScorePlayer(final ScorePlayer player) {
        if (this.m_scorePlayer != null) {
            this.m_scorePlayer.removeSoundPlayerListener(this);
        }
        this.m_scorePlayer = player;
        if (this.m_scorePlayer != null) {
            this.m_scorePlayer.addSoundPlayerListener(this);
        }
        this.updateEnabledStatus();
    }
    
    private void updateEnabledStatus() {
        this.setEnabled(this.m_scorePlayer != null && !this.m_scorePlayer.isPlaying());
    }
    
    public void addChangeListener(final ChangeListener listener) {
        this.m_listeners.add(ChangeListener.class, listener);
    }
    
    public void removeChangeListener(final ChangeListener listener) {
        this.m_listeners.remove(ChangeListener.class, listener);
    }
    
    @Override
    public void onControlledScoreChanged(final ScoreController controller, final Score scoreControlled) {
    }
    
    @Override
    public void onScorePlayerChanged(final ScoreController controller, final ScorePlayer scorePlayer) {
        this.setScorePlayer(scorePlayer);
    }
    
    @Override
    public void onScorePlayerStateChanged(final ScorePlayerEvent event) {
        this.updateEnabledStatus();
    }
    
    @Override
    public void onPlaybackStarted(final ScorePlayerEvent event) {
        this.updateEnabledStatus();
    }
    
    @Override
    public void onPlaybackStopped(final ScorePlayerEvent event, final boolean endOfPlayback) {
        this.updateEnabledStatus();
    }
    
    @Override
    public void onScorePlayerError(final ScorePlayerEvent event, final Throwable error) {
    }
    
    @Override
    public void onPlaybackPaused(final ScorePlayerEvent event) {
    }
    
    @Override
    public void onPlayedSoundItemChanged(final ScorePlayerEvent event) {
    }
    
    private class SliderValueObserver implements ChangeListener
    {
        @Override
        public void stateChanged(final ChangeEvent event) {
            VolumeControl.this.m_valueLabel.setText(String.valueOf(VolumeControl.this.getValue()) + " %");
            ChangeListener[] array;
            for (int length = (array = VolumeControl.this.m_listeners.getListeners(ChangeListener.class)).length, i = 0; i < length; ++i) {
                final ChangeListener listener = array[i];
                listener.stateChanged(new ChangeEvent(VolumeControl.this.m_instance));
            }
        }
    }
}
