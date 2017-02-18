// 
// Decompiled by Procyon v0.5.30
// 

package rvt.util;

public class Chrono
{
    private long startTime;
    private long intermediaryTime;
    
    public Chrono() {
        this.start();
    }
    
    public void start() {
        this.startTime = System.currentTimeMillis();
        this.intermediaryTime = this.startTime;
    }
    
    public long getElapsedTimeMs() {
        return System.currentTimeMillis() - this.startTime;
    }
    
    public float getElapsedTime() {
        return this.getElapsedTimeMs() / 1000.0f;
    }
    
    public float getTimeAndRestart() {
        final float elapsed = this.getElapsedTime();
        this.start();
        return elapsed;
    }
    
    public float getIntermediaryTime() {
        final long currentTime = System.currentTimeMillis();
        final float elapsed = (currentTime - this.intermediaryTime) / 1000.0f;
        this.intermediaryTime = currentTime;
        return elapsed;
    }
}
