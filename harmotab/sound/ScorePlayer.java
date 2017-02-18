// 
// Decompiled by Procyon v0.5.30
// 

package harmotab.sound;

import harmotab.element.Element;

public abstract class ScorePlayer
{
    public static final byte CLOSED = 1;
    public static final byte OPENED = 2;
    public static final int NO_TRACK_HIGHLIGHTED = -1;
    private int m_highlightedTrack;
    private Element m_playFromElement;
    protected SoundSequence m_sounds;
    
    public ScorePlayer() {
        this.m_playFromElement = null;
        this.m_sounds = null;
        this.setHighlightedTrack(-1);
        this.setPlayFromElement(null);
    }
    
    public void setHighlightedTrack(final int trackId) {
        this.m_highlightedTrack = trackId;
    }
    
    public int getHighlightedTrack() {
        return this.m_highlightedTrack;
    }
    
    public void setPlayFromElement(final Element element) {
        this.m_playFromElement = element;
    }
    
    public Element getPlayFromElement() {
        return this.m_playFromElement;
    }
    
    public void setSounds(final SoundSequence sounds) {
        this.m_sounds = sounds;
    }
    
    public SoundSequence getSounds() {
        return this.m_sounds;
    }
    
    public abstract void setInstrument(final int p0, final int p1);
    
    public abstract void setTrackVolume(final int p0, final int p1);
    
    public abstract void setGlobalVolume(final int p0);
    
    public abstract void open();
    
    public abstract void close();
    
    public abstract byte getState();
    
    public abstract void play();
    
    public abstract void pause();
    
    public abstract void stop();
    
    public abstract boolean isPlaying();
    
    public abstract boolean isPaused();
    
    public abstract float getPosition();
    
    public abstract void setPosition(final SoundItem p0);
    
    public abstract float getDuration();
    
    public abstract void addSoundPlayerListener(final ScorePlayerListener p0);
    
    public abstract void removeSoundPlayerListener(final ScorePlayerListener p0);
    
    public synchronized void asynchronousOpen() {
        new Thread() {
            @Override
            public void run() {
                ScorePlayer.this.open();
            }
        }.start();
    }
    
    public synchronized void asynchronousClose() {
        new Thread() {
            @Override
            public void run() {
                ScorePlayer.this.close();
            }
        }.start();
    }
    
    public void playFrom() {
        if (this.m_playFromElement != null) {
            final SoundItem item = this.m_sounds.get(this.m_playFromElement);
            if (item != null) {
                this.play();
                this.setPosition(item);
                return;
            }
        }
        this.play();
    }
    
    public boolean isOpenned() {
        return this.getState() == 2;
    }
    
    public SoundItem getPlayedItem() {
        if (!this.isOpenned() || this.m_sounds == null) {
            return null;
        }
        if (this.getHighlightedTrack() != -1) {
            return this.m_sounds.at(this.getHighlightedTrack(), this.getPosition());
        }
        return this.m_sounds.at(1, this.getPosition());
    }
}
