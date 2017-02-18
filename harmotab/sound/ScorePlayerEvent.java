// 
// Decompiled by Procyon v0.5.30
// 

package harmotab.sound;

public class ScorePlayerEvent
{
    protected ScorePlayer m_player;
    protected SoundItem m_played;
    protected float m_position;
    protected byte m_state;
    
    public ScorePlayerEvent(final ScorePlayer player) {
        this.m_player = null;
        this.m_position = 0.0f;
        this.m_state = 0;
        this.m_player = player;
        this.m_played = player.getPlayedItem();
        this.m_position = player.getPosition();
        this.m_state = player.getState();
    }
    
    public ScorePlayerEvent(final ScorePlayer player, final byte state, final SoundItem played, final float position) {
        this.m_player = null;
        this.m_position = 0.0f;
        this.m_state = 0;
        this.m_player = player;
        this.m_played = played;
        this.m_position = position;
        this.m_state = state;
    }
    
    public ScorePlayer getScorePlayer() {
        return this.m_player;
    }
    
    public SoundItem getPlayedItem() {
        return this.m_played;
    }
    
    public float getPosition() {
        return this.m_position;
    }
    
    public byte getState() {
        return this.m_state;
    }
}
