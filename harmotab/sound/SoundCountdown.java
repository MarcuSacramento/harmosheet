// 
// Decompiled by Procyon v0.5.30
// 

package harmotab.sound;

import java.util.Iterator;
import harmotab.element.Element;
import java.util.ArrayList;
import harmotab.element.Tempo;
import harmotab.element.TimeSignature;

public class SoundCountdown
{
    protected TimeSignature m_timeSignature;
    protected Tempo m_tempo;
    private final ArrayList<SoundCountdownListener> m_listeners;
    
    public SoundCountdown(final TimeSignature timeSignature, final Tempo tempo) {
        this.m_timeSignature = null;
        this.m_tempo = null;
        this.m_listeners = new ArrayList<SoundCountdownListener>();
        this.setTimeSignature(timeSignature);
        this.setTempo(tempo);
    }
    
    public void setTimeSignature(final TimeSignature timeSignature) {
        this.m_timeSignature = timeSignature;
    }
    
    public TimeSignature getTimeSignature() {
        return this.m_timeSignature;
    }
    
    public void setTempo(final Tempo tempo) {
        this.m_tempo = tempo;
    }
    
    public Tempo getTempo() {
        return this.m_tempo;
    }
    
    public SoundSequence getCountdownSequence() {
        final SoundSequence sounds = new SoundSequence();
        final float beatTimeSec = this.m_tempo.getBeatPeriodInSeconds();
        for (float timesPerBar = this.m_timeSignature.getTimesPerBar(), inc = (this.m_timeSignature.getTimesPerBeat() == 1.0f) ? 1.0f : 0.5f, time = 0.0f; time < timesPerBar; time += inc) {
            final SoundItem sound = new SoundItem(null, 9, this.m_timeSignature.isStrongBeat(time % (int)timesPerBar) ? 37 : 42, time * beatTimeSec, beatTimeSec * inc);
            sounds.add(sound);
        }
        return sounds;
    }
    
    public void start() {
        this.fireCountdownStarted();
        try {
            Thread.sleep((long)this.getCountdownSequence().getLastTime() * 1000L);
        }
        catch (InterruptedException ex) {}
        this.fireCountdownStopped(false);
    }
    
    public void addSoundCountdownListener(final SoundCountdownListener listener) {
        this.m_listeners.add(listener);
    }
    
    public void removeSoundCountdownListener(final SoundCountdownListener listener) {
        this.m_listeners.remove(listener);
    }
    
    protected void fireCountdownStarted() {
        for (final SoundCountdownListener listener : this.m_listeners) {
            listener.onSoundCountdownStarted(this);
        }
    }
    
    protected void fireCountdownStopped(final boolean cancelled) {
        for (final SoundCountdownListener listener : this.m_listeners) {
            listener.onSoundCountdownStopped(this, cancelled);
        }
    }
}
