// 
// Decompiled by Procyon v0.5.30
// 

package harmotab.sound;

import harmotab.element.Element;

public class SoundItem implements Cloneable, Comparable<SoundItem>
{
    public static final int NO_SOUND = -1;
    public Element m_element;
    public float m_startTime;
    public float m_endTime;
    public float m_durationTime;
    public int m_trackId;
    public int m_soundId;
    public int m_type;
    
    public SoundItem(final Element element, final int trackId, final int soundId, final float startTime, final float durationTime) {
        this.m_element = element;
        this.m_trackId = trackId;
        this.m_soundId = soundId;
        this.m_startTime = startTime;
        this.m_endTime = startTime + durationTime;
        this.m_durationTime = durationTime;
    }
    
    public Object clone() {
        try {
            return super.clone();
        }
        catch (CloneNotSupportedException e) {
            e.printStackTrace();
            return null;
        }
    }
    
    @Override
    public int compareTo(final SoundItem object) {
        float diff = this.m_startTime - object.m_startTime;
        if (diff == 0.0f) {
            diff = this.m_endTime - object.m_endTime;
            if (diff == 0.0f) {
                return 0;
            }
        }
        return (diff < 0.0f) ? -1 : 1;
    }
    
    public Element getElement() {
        return this.m_element;
    }
    
    public float getStartTime() {
        return this.m_startTime;
    }
    
    public float getEndTime() {
        return this.m_endTime;
    }
    
    public float getDurationTime() {
        return this.m_durationTime;
    }
    
    public int getTrackId() {
        return this.m_trackId;
    }
    
    public int getSoundId() {
        return this.m_soundId;
    }
    
    public boolean isSilence() {
        return this.m_soundId == -1;
    }
    
    public void timeshift(final float time) {
        this.m_startTime += time;
        this.m_endTime += time;
    }
    
    public void extend(final float time) {
        this.m_endTime += time;
        this.m_durationTime += time;
    }
}
