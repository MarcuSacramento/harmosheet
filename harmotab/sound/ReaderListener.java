// 
// Decompiled by Procyon v0.5.30
// 

package harmotab.sound;

public interface ReaderListener
{
    void onReaderOpenned();
    
    void onPlaybackStarted();
    
    void onPlaybackStopped();
    
    void onReaderClosed();
    
    void onPlaybackGoesOn(final float p0);
}
