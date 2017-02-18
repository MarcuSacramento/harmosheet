// 
// Decompiled by Procyon v0.5.30
// 

package harmotab.sound;

public class RepeatItem implements Cloneable, Comparable<RepeatItem>
{
    private int m_iterationsNumber;
    private float m_phraseStartTime;
    private float m_phraseEndTime;
    private int m_iterator;
    
    public RepeatItem(final int iterations, final float phraseStartTime, final float phraseEndTime) {
        this.m_iterationsNumber = iterations;
        this.m_phraseStartTime = phraseStartTime;
        this.m_phraseEndTime = phraseEndTime;
        this.m_iterator = this.m_iterationsNumber;
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
    
    public int getIterationsNumber() {
        return this.m_iterationsNumber;
    }
    
    public float getPhraseStartTime() {
        return this.m_phraseStartTime;
    }
    
    public float getPhraseEndTime() {
        return this.m_phraseEndTime;
    }
    
    public void resetIterator() {
        this.m_iterator = this.m_iterationsNumber;
    }
    
    public boolean mustIterate() {
        return this.m_iterator > 1;
    }
    
    public void iterate() {
        --this.m_iterator;
    }
    
    @Override
    public int compareTo(final RepeatItem object) {
        final float diff = this.m_phraseStartTime - object.m_phraseStartTime;
        if (diff == 0.0f) {
            return 0;
        }
        return (diff < 0.0f) ? -1 : 1;
    }
    
    public void timeshift(final float time) {
        this.m_phraseStartTime += time;
        this.m_phraseEndTime += time;
    }
}
