// 
// Decompiled by Procyon v0.5.30
// 

package harmotab.sound;

import javax.swing.event.EventListenerList;

public class ScorePlayerEventDispatcher
{
    protected ScorePlayer m_player;
    protected boolean m_dispatchingEnabled;
    protected final EventListenerList m_listeners;
    
    public ScorePlayerEventDispatcher(final ScorePlayer player) {
        this.m_listeners = new EventListenerList();
        this.m_player = player;
        this.m_dispatchingEnabled = true;
    }
    
    public boolean isDispatchingEnabled() {
        return this.m_dispatchingEnabled;
    }
    
    public void setDispatchingEnabled(final boolean enabled) {
        this.m_dispatchingEnabled = enabled;
    }
    
    public void addScorePlayerListener(final ScorePlayerListener listener) {
        this.m_listeners.add(ScorePlayerListener.class, listener);
    }
    
    public void removeScorePlayerListener(final ScorePlayerListener listener) {
        this.m_listeners.remove(ScorePlayerListener.class, listener);
    }
    
    public void fireScorePlayerStateChanged(final ScorePlayerEvent event) {
        if (this.m_dispatchingEnabled) {
            ScorePlayerListener[] array;
            for (int length = (array = this.m_listeners.getListeners(ScorePlayerListener.class)).length, i = 0; i < length; ++i) {
                final ScorePlayerListener listener = array[i];
                listener.onScorePlayerStateChanged(event);
            }
        }
    }
    
    public void fireScorePlayerError(final ScorePlayerEvent event, final Throwable error) {
        if (this.m_dispatchingEnabled) {
            ScorePlayerListener[] array;
            for (int length = (array = this.m_listeners.getListeners(ScorePlayerListener.class)).length, i = 0; i < length; ++i) {
                final ScorePlayerListener listener = array[i];
                listener.onScorePlayerError(event, error);
            }
        }
    }
    
    public void firePlaybackStarted(final ScorePlayerEvent event) {
        if (this.m_dispatchingEnabled) {
            ScorePlayerListener[] array;
            for (int length = (array = this.m_listeners.getListeners(ScorePlayerListener.class)).length, i = 0; i < length; ++i) {
                final ScorePlayerListener listener = array[i];
                listener.onPlaybackStarted(event);
            }
        }
    }
    
    public void firePlaybackPaused(final ScorePlayerEvent event) {
        if (this.m_dispatchingEnabled) {
            ScorePlayerListener[] array;
            for (int length = (array = this.m_listeners.getListeners(ScorePlayerListener.class)).length, i = 0; i < length; ++i) {
                final ScorePlayerListener listener = array[i];
                listener.onPlaybackPaused(event);
            }
        }
    }
    
    public void firePlaybackStopped(final ScorePlayerEvent event, final boolean endOfPlayback) {
        if (this.m_dispatchingEnabled) {
            ScorePlayerListener[] array;
            for (int length = (array = this.m_listeners.getListeners(ScorePlayerListener.class)).length, i = 0; i < length; ++i) {
                final ScorePlayerListener listener = array[i];
                listener.onPlaybackStopped(event, endOfPlayback);
            }
        }
    }
    
    public void firePlayedSoundItemChanged(final ScorePlayerEvent event) {
        if (this.m_dispatchingEnabled) {
            ScorePlayerListener[] array;
            for (int length = (array = this.m_listeners.getListeners(ScorePlayerListener.class)).length, i = 0; i < length; ++i) {
                final ScorePlayerListener listener = array[i];
                listener.onPlayedSoundItemChanged(event);
            }
        }
    }
}
