// 
// Decompiled by Procyon v0.5.30
// 

package harmotab.sound;

import javax.sound.sampled.LineEvent;
import java.util.Iterator;
import java.io.IOException;
import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.Line;
import javax.sound.sampled.DataLine;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Control;
import javax.sound.sampled.FloatControl;
import harmotab.core.GlobalPreferences;
import harmotab.performance.Performance;
import java.util.ArrayList;
import java.io.File;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.Clip;
import harmotab.core.Score;
import javax.sound.sampled.LineListener;

public class PerformanceScorePlayer extends ScorePlayer implements LineListener
{
    protected Score m_score;
    protected Clip m_clip;
    protected AudioInputStream m_audioInputStream;
    protected boolean m_openned;
    protected File m_filePath;
    protected boolean m_stoppedByUser;
    protected int m_volume;
    protected final ArrayList<ScorePlayerListener> m_listeners;
    
    public PerformanceScorePlayer(final Performance performance, final Score score) {
        this.m_score = null;
        this.m_clip = null;
        this.m_audioInputStream = null;
        this.m_openned = false;
        this.m_filePath = null;
        this.m_stoppedByUser = false;
        this.m_volume = 100;
        this.m_listeners = new ArrayList<ScorePlayerListener>();
        this.m_score = score;
        this.m_filePath = new File(performance.getFile().getAbsolutePath());
        this.m_openned = false;
        this.m_clip = null;
        this.m_volume = GlobalPreferences.getGlobalVolume();
    }
    
    @Override
    public byte getState() {
        if (this.m_clip == null) {
            return 1;
        }
        return (byte)(this.m_clip.isOpen() ? 2 : 1);
    }
    
    @Override
    public void setInstrument(final int channel, final int instrument) {
    }
    
    @Override
    public void setTrackVolume(final int channel, final int volume) {
    }
    
    @Override
    public void setGlobalVolume(final int volume) {
        this.m_volume = volume;
        if (this.m_openned) {
            try {
                final float value = this.m_volume / 100.0f;
                final FloatControl volCtrl = (FloatControl)this.m_clip.getControl(FloatControl.Type.MASTER_GAIN);
                volCtrl.setValue(value);
            }
            catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
    
    @Override
    public boolean isPlaying() {
        return this.m_clip.isRunning();
    }
    
    @Override
    public boolean isPaused() {
        return false;
    }
    
    @Override
    public float getPosition() {
        return this.m_clip.getMicrosecondPosition() / 1000000.0f;
    }
    
    @Override
    public void setPosition(final SoundItem item) {
        this.m_clip.setMicrosecondPosition((long)(item.getStartTime() * 1000000.0f));
    }
    
    @Override
    public float getDuration() {
        return this.m_clip.getMicrosecondLength() / 1000000.0f;
    }
    
    @Override
    public void open() {
        if (this.m_openned) {
            throw new IllegalStateException("Reader already openned");
        }
        try {
            this.m_audioInputStream = AudioSystem.getAudioInputStream(this.m_filePath);
            final AudioFormat audioFormat = this.m_audioInputStream.getFormat();
            final DataLine.Info info = new DataLine.Info(Clip.class, audioFormat);
            (this.m_clip = (Clip)AudioSystem.getLine(info)).open(this.m_audioInputStream);
            this.m_openned = true;
            this.setGlobalVolume(this.m_volume);
            this.m_clip.addLineListener(this);
        }
        catch (Exception e) {
            this.fireSoundPlayerError(e);
        }
        this.fireStateChanged();
    }
    
    @Override
    public void close() {
        if (!this.m_openned) {
            throw new IllegalStateException("Reader not openned");
        }
        try {
            this.m_clip.close();
            this.m_audioInputStream.close();
        }
        catch (IOException e) {
            this.fireSoundPlayerError(e);
        }
        this.m_openned = false;
        this.fireStateChanged();
    }
    
    @Override
    public void play() {
        if (!this.m_openned) {
            throw new IllegalStateException("Reader not openned");
        }
        if (this.m_clip.isActive()) {
            return;
        }
        this.m_stoppedByUser = false;
        this.setGlobalVolume(this.m_volume);
        this.m_clip.start();
        new ReadingObserver((ReadingObserver)null).start();
        this.firePlaybackStarted();
    }
    
    @Override
    public void pause() {
        this.m_clip.stop();
        this.firePlaybackPaused();
    }
    
    @Override
    public void stop() {
        if (!this.m_clip.isActive()) {
            throw new IllegalStateException("Reader not reading");
        }
        this.m_stoppedByUser = true;
        this.m_clip.stop();
        this.m_clip.setFramePosition(0);
    }
    
    @Override
    public void addSoundPlayerListener(final ScorePlayerListener listener) {
        this.m_listeners.add(listener);
    }
    
    @Override
    public void removeSoundPlayerListener(final ScorePlayerListener listener) {
        this.m_listeners.remove(listener);
    }
    
    private void fireStateChanged() {
        final ScorePlayerEvent event = new ScorePlayerEvent(this);
        for (final ScorePlayerListener listener : this.m_listeners) {
            listener.onScorePlayerStateChanged(event);
        }
    }
    
    private void firePlaybackStarted() {
        final ScorePlayerEvent event = new ScorePlayerEvent(this);
        for (final ScorePlayerListener listener : this.m_listeners) {
            listener.onPlaybackStarted(event);
        }
    }
    
    private void firePlaybackStopped(final boolean endOfPlayback) {
        final ScorePlayerEvent event = new ScorePlayerEvent(this);
        for (final ScorePlayerListener listener : this.m_listeners) {
            listener.onPlaybackStopped(event, endOfPlayback);
        }
    }
    
    private void firePlaybackPaused() {
        final ScorePlayerEvent event = new ScorePlayerEvent(this);
        for (final ScorePlayerListener listener : this.m_listeners) {
            listener.onPlaybackPaused(event);
        }
    }
    
    private void fireSoundItemChanged() {
        final ScorePlayerEvent event = new ScorePlayerEvent(this);
        for (final ScorePlayerListener listener : this.m_listeners) {
            listener.onPlayedSoundItemChanged(event);
        }
    }
    
    private void fireSoundPlayerError(final Throwable error) {
        final ScorePlayerEvent event = new ScorePlayerEvent(this);
        for (final ScorePlayerListener listener : this.m_listeners) {
            listener.onScorePlayerError(event, error);
        }
    }
    
    @Override
    public void update(final LineEvent event) {
        if (event.getType() == LineEvent.Type.STOP) {
            this.firePlaybackStopped(!this.m_stoppedByUser);
            this.m_clip.setFramePosition(0);
        }
    }
    
    private class ReadingObserver extends Thread
    {
        @Override
        public void run() {
            while (PerformanceScorePlayer.this.m_clip.isActive()) {
                PerformanceScorePlayer.this.fireSoundItemChanged();
                try {
                    Thread.sleep(40L);
                }
                catch (InterruptedException ex) {}
            }
        }
    }
}
