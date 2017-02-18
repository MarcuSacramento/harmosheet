// 
// Decompiled by Procyon v0.5.30
// 

package harmotab.sound;

public class MidiPlayerObserver extends ScorePlayerEventDispatcher
{
    private ObserverThread m_observer;
    
    public MidiPlayerObserver(final MidiScorePlayer player) {
        super(player);
    }
    
    @Override
    public void firePlaybackStarted(final ScorePlayerEvent event) {
        super.firePlaybackStarted(event);
        (this.m_observer = new ObserverThread()).start();
    }
    
    @Override
    public void firePlaybackPaused(final ScorePlayerEvent event) {
        this.m_observer.haveToStop = true;
        super.firePlaybackPaused(event);
    }
    
    @Override
    public void firePlaybackStopped(final ScorePlayerEvent event, final boolean endOfPlayback) {
        this.m_observer.haveToStop = true;
        super.firePlaybackStopped(event, endOfPlayback);
    }
    
    private class ObserverThread extends Thread
    {
        public boolean haveToStop;
        
        public ObserverThread() {
            this.haveToStop = false;
        }
        
        @Override
        public void run() {
            SoundItem currentPlayed = null;
            SoundItem lastPlayed = null;
            while (!this.haveToStop) {
                try {
                    currentPlayed = MidiPlayerObserver.this.m_player.getPlayedItem();
                    if (currentPlayed != lastPlayed) {
                        MidiPlayerObserver.this.firePlayedSoundItemChanged(new ScorePlayerEvent(MidiPlayerObserver.this.m_player, MidiPlayerObserver.this.m_player.getState(), currentPlayed, MidiPlayerObserver.this.m_player.getPosition()));
                    }
                    lastPlayed = currentPlayed;
                    Thread.sleep(40L);
                }
                catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
