// 
// Decompiled by Procyon v0.5.30
// 

package ie.debug;

import harmotab.sound.ScorePlayerEvent;
import harmotab.sound.ScorePlayerListener;
import harmotab.sound.PerformanceScorePlayer;
import harmotab.core.Score;
import harmotab.performance.Performance;
import harmotab.sound.RecorderListener;
import java.io.IOException;
import java.io.File;
import harmotab.harmonica.Harmonica;
import harmotab.desktop.ErrorMessenger;
import harmotab.sound.PcmRecorder;

public class IeDebug
{
    PcmRecorder pcmRecorder;
    
    IeDebug() {
        this.pcmRecorder = null;
        ErrorMessenger.setConsoleMode();
        final Harmonica h = new Harmonica();
        File tmpFile = null;
        try {
            tmpFile = File.createTempFile("test", ".pcm");
        }
        catch (IOException e) {
            e.printStackTrace();
        }
        (this.pcmRecorder = new PcmRecorder()).addRecorderListener(new RecorderListener() {
            @Override
            public void onRecorderOpenned() {
                System.out.println("-> onRecorderOpenned");
            }
            
            @Override
            public void onRecorderClosed() {
                System.out.println("-> onRecorderClosed");
            }
            
            @Override
            public void onRecordingStarted() {
                System.out.println("-> onRecordingStarted");
            }
            
            @Override
            public void onRecordingStopped() {
                System.out.println("-> onRecordingStopped");
            }
            
            @Override
            public void onMonitoringStarted() {
                System.out.println("-> onMonitoringStarted");
            }
            
            @Override
            public void onMonitoringStopped() {
                System.out.println("-> onMonitoringStopped");
            }
        });
        try {
            System.out.println("Recording...");
            this.pcmRecorder.open();
            this.pcmRecorder.start();
            Thread.sleep(3000L);
            this.pcmRecorder.stop();
            System.out.println("Saving...");
            this.pcmRecorder.save(tmpFile.getAbsolutePath());
            tmpFile.deleteOnExit();
            this.pcmRecorder.close();
        }
        catch (Exception e2) {
            e2.printStackTrace();
        }
        final PerformanceScorePlayer pl = new PerformanceScorePlayer(new Performance(tmpFile, "test", h), new Score());
        pl.addSoundPlayerListener(new ScorePlayerListener() {
            @Override
            public void onScorePlayerStateChanged(final ScorePlayerEvent event) {
                System.out.println("-> onScorePlayerStateChanged");
            }
            
            @Override
            public void onScorePlayerError(final ScorePlayerEvent event, final Throwable error) {
                System.out.println("-> onScorePlayerError");
            }
            
            @Override
            public void onPlayedSoundItemChanged(final ScorePlayerEvent event) {
                System.out.println("-> onPlayedSoundItemChanged");
            }
            
            @Override
            public void onPlaybackStopped(final ScorePlayerEvent event, final boolean endOfPlayback) {
                System.out.println("-> onPlaybackStopped");
            }
            
            @Override
            public void onPlaybackStarted(final ScorePlayerEvent event) {
                System.out.println("-> onPlaybackStarted");
            }
            
            @Override
            public void onPlaybackPaused(final ScorePlayerEvent event) {
                System.out.println("-> onPlaybackPaused");
            }
        });
        pl.open();
        System.out.println("Playing...");
        pl.play();
        try {
            Thread.sleep(5000L);
        }
        catch (InterruptedException e3) {
            e3.printStackTrace();
        }
        System.out.println("Playing...");
        pl.play();
        try {
            Thread.sleep(2000L);
        }
        catch (InterruptedException e3) {
            e3.printStackTrace();
        }
        System.out.println("Playing...");
        pl.play();
        try {
            Thread.sleep(5000L);
        }
        catch (InterruptedException e3) {
            e3.printStackTrace();
        }
        pl.close();
    }
    
    public static void main(final String[] args) {
        new IeDebug();
        System.out.println("done.");
    }
}
