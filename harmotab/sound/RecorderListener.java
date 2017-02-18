// 
// Decompiled by Procyon v0.5.30
// 

package harmotab.sound;

public interface RecorderListener
{
    void onRecorderOpenned();
    
    void onRecorderClosed();
    
    void onRecordingStarted();
    
    void onRecordingStopped();
    
    void onMonitoringStarted();
    
    void onMonitoringStopped();
}
