// 
// Decompiled by Procyon v0.5.30
// 

package harmotab.performance;

import java.util.Iterator;
import harmotab.sound.ScorePlayerEvent;
import harmotab.element.TimeSignature;
import harmotab.core.Score;
import java.io.File;
import harmotab.desktop.ErrorMessenger;
import harmotab.desktop.components.CountDownDialog;
import harmotab.core.Localizer;
import harmotab.sound.ScorePlayerController;
import harmotab.sound.SoundCountdown;
import harmotab.core.GlobalPreferences;
import harmotab.sound.MidiCountDown;
import harmotab.sound.RecorderException;
import java.util.ArrayList;
import harmotab.sound.Recorder;
import harmotab.sound.ScorePlayer;
import harmotab.core.ScoreController;
import harmotab.sound.ScorePlayerListener;

public class RecordingWorker extends Thread implements ScorePlayerListener
{
    protected ScoreController m_scoreController;
    protected ScorePlayer m_soundPlayer;
    protected Recorder m_recorder;
    protected Performance m_performance;
    protected boolean m_recording;
    protected boolean m_aborted;
    protected boolean m_errorOccured;
    protected final ArrayList<RecordingListener> m_listeners;
    
    public RecordingWorker(final ScoreController controller, final Recorder recorder, final Performance performance) throws RecorderException {
        this.m_scoreController = null;
        this.m_soundPlayer = null;
        this.m_recorder = null;
        this.m_performance = null;
        this.m_recording = false;
        this.m_aborted = false;
        this.m_errorOccured = false;
        this.m_listeners = new ArrayList<RecordingListener>();
        if (controller.getPerformancesList() == null) {
            throw new IllegalArgumentException("Cannot create a RecordingWorker for a non exported score.");
        }
        this.m_scoreController = controller;
        this.m_soundPlayer = controller.getScorePlayer();
        this.m_recorder = recorder;
        this.m_performance = performance;
    }
    
    public boolean hasBeenAborted() {
        return this.m_aborted;
    }
    
    public boolean hasAbortedOnError() {
        return this.m_errorOccured;
    }
    
    @Override
    public void run() {
        this.m_recording = true;
        this.m_aborted = false;
        this.m_errorOccured = false;
        ScorePlayer player = null;
        final Score score = this.m_scoreController.getScore();
        final TimeSignature timeSignature = score.getFirstTimeSignature();
        SoundCountdown soundCountDown = null;
        try {
            soundCountDown = new MidiCountDown(score.getFirstTimeSignature(), score.getTempo());
            if (GlobalPreferences.getPlaybackCountdownEnabled()) {
                ((MidiCountDown)soundCountDown).start();
            }
        }
        catch (Exception e) {
            e.printStackTrace();
            soundCountDown = new SoundCountdown(timeSignature, score.getTempo());
        }
        try {
            this.m_scoreController.setDefaultSoundPlayer();
            player = this.m_scoreController.getScorePlayer();
            final ScorePlayerController playerController = new ScorePlayerController(player, score);
            playerController.preparePlayer();
            this.m_recorder.open();
        }
        catch (RecorderException e2) {
            e2.printStackTrace();
            this.m_recording = false;
            this.m_aborted = true;
            this.m_errorOccured = true;
            return;
        }
        final CountDownDialog countDownDialog = new CountDownDialog(Localizer.get("N_RECORDING"), Localizer.get("M_RECORDING_COUNTDOWN"));
        final float countDownDuration = soundCountDown.getCountdownSequence().getLastTime();
        if (!countDownDialog.countDown(countDownDuration, 1.0f)) {
            try {
                player.stop();
                this.m_recorder.close();
            }
            catch (RecorderException ex) {}
            ErrorMessenger.showErrorMessage(Localizer.get("M_RECORDING_ABORTED_ERR"));
            return;
        }
        this.fireRecordingStarting();
        try {
            player.addSoundPlayerListener(this);
            player.play();
            this.m_recorder.start();
            while (this.m_recording && !this.m_aborted) {
                yield();
            }
            this.m_recorder.stop();
            if (!this.m_aborted) {
                final PerformancesList perfs = this.m_scoreController.getPerformancesList();
                final File outFile = File.createTempFile("perf_", ".pcm");
                outFile.deleteOnExit();
                this.m_recorder.save(outFile.getAbsolutePath());
                this.m_performance.setFile(outFile);
                perfs.add(this.m_performance);
            }
        }
        catch (Exception e3) {
            e3.printStackTrace();
            this.m_errorOccured = true;
            return;
        }
        finally {
            this.m_scoreController.getScorePlayer().removeSoundPlayerListener(this);
            try {
                this.m_recorder.close();
            }
            catch (RecorderException e4) {
                e4.printStackTrace();
            }
            if (this.hasAbortedOnError()) {
                ErrorMessenger.showErrorMessage(Localizer.get("M_RECORDING_ERROR_ERR"));
            }
            else if (this.hasBeenAborted()) {
                ErrorMessenger.showErrorMessage(Localizer.get("M_RECORDING_ABORTED_ERR"));
            }
            this.fireRecordingStopped();
        }
        this.m_scoreController.getScorePlayer().removeSoundPlayerListener(this);
        try {
            this.m_recorder.close();
        }
        catch (RecorderException e4) {
            e4.printStackTrace();
        }
        if (this.hasAbortedOnError()) {
            ErrorMessenger.showErrorMessage(Localizer.get("M_RECORDING_ERROR_ERR"));
        }
        else if (this.hasBeenAborted()) {
            ErrorMessenger.showErrorMessage(Localizer.get("M_RECORDING_ABORTED_ERR"));
        }
        this.fireRecordingStopped();
    }
    
    @Override
    public void onPlaybackStopped(final ScorePlayerEvent event, final boolean endOfPlayback) {
        this.m_recording = false;
        this.m_aborted = !endOfPlayback;
    }
    
    @Override
    public void onScorePlayerError(final ScorePlayerEvent event, final Throwable error) {
        this.m_aborted = true;
        this.m_errorOccured = true;
    }
    
    @Override
    public void onPlaybackPaused(final ScorePlayerEvent event) {
        this.m_aborted = true;
        this.m_errorOccured = true;
    }
    
    @Override
    public void onScorePlayerStateChanged(final ScorePlayerEvent event) {
    }
    
    @Override
    public void onPlaybackStarted(final ScorePlayerEvent event) {
    }
    
    @Override
    public void onPlayedSoundItemChanged(final ScorePlayerEvent event) {
    }
    
    public void addRecordingListener(final RecordingListener listener) {
        this.m_listeners.add(listener);
    }
    
    public void removeRecordingListener(final RecordingListener listener) {
        this.m_listeners.remove(listener);
    }
    
    public void fireRecordingStarting() {
        for (final RecordingListener listener : this.m_listeners) {
            listener.onRecordingStarted(this);
        }
    }
    
    public void fireRecordingStopped() {
        for (final RecordingListener listener : this.m_listeners) {
            listener.onRecordingStopped(this);
        }
    }
}
