// 
// Decompiled by Procyon v0.5.30
// 

package harmotab.core;

import harmotab.sound.ScorePlayer;

public interface ScoreControllerListener
{
    void onControlledScoreChanged(final ScoreController p0, final Score p1);
    
    void onScorePlayerChanged(final ScoreController p0, final ScorePlayer p1);
}
