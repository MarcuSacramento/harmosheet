// 
// Decompiled by Procyon v0.5.30
// 

package harmotab.sound;

import java.util.EventListener;

public interface ScorePlayerListener extends EventListener
{
    void onScorePlayerStateChanged(final ScorePlayerEvent p0);
    
    void onScorePlayerError(final ScorePlayerEvent p0, final Throwable p1);
    
    void onPlaybackStarted(final ScorePlayerEvent p0);
    
    void onPlaybackPaused(final ScorePlayerEvent p0);
    
    void onPlaybackStopped(final ScorePlayerEvent p0, final boolean p1);
    
    void onPlayedSoundItemChanged(final ScorePlayerEvent p0);
}
