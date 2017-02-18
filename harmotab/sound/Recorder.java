// 
// Decompiled by Procyon v0.5.30
// 

package harmotab.sound;

import java.util.Iterator;
import java.util.ArrayList;

public abstract class Recorder
{
    protected final ArrayList<RecorderListener> m_listeners;
    
    public Recorder() {
        this.m_listeners = new ArrayList<RecorderListener>();
    }
    
    public abstract void open() throws RecorderException;
    
    public abstract void close() throws RecorderException;
    
    public abstract void start() throws RecorderException;
    
    public abstract void startMonitoring() throws RecorderException;
    
    public abstract void stop() throws RecorderException;
    
    public abstract void save(final String p0) throws RecorderException;
    
    public abstract float getLevel() throws RecorderException;
    
    public abstract float getPositionSec() throws RecorderException;
    
    public abstract boolean isOpenned() throws RecorderException;
    
    public abstract boolean isRunning() throws RecorderException;
    
    protected void fireRecorderOpened() {
        for (final RecorderListener listener : this.m_listeners) {
            listener.onRecorderOpenned();
        }
    }
    
    protected void fireRecorderClosed() {
        for (final RecorderListener listener : this.m_listeners) {
            listener.onRecorderClosed();
        }
    }
    
    protected void fireRecordingStarted() {
        for (final RecorderListener listener : this.m_listeners) {
            listener.onRecordingStarted();
        }
    }
    
    protected void fireRecordingStopped() {
        for (final RecorderListener listener : this.m_listeners) {
            listener.onRecordingStopped();
        }
    }
    
    protected void fireMonitoringStarted() {
        for (final RecorderListener listener : this.m_listeners) {
            listener.onMonitoringStarted();
        }
    }
    
    protected void fireMonitoringStopped() {
        for (final RecorderListener listener : this.m_listeners) {
            listener.onMonitoringStopped();
        }
    }
    
    public void addRecorderListener(final RecorderListener listener) {
        this.m_listeners.add(listener);
    }
    
    public void removeRecorderListener(final RecorderListener listener) {
        this.m_listeners.remove(listener);
    }
}
