// 
// Decompiled by Procyon v0.5.30
// 

package harmotab.element;

import harmotab.core.HarmoTabObjectFactory;
import harmotab.io.SerializedObject;
import harmotab.io.ObjectSerializer;
import harmotab.core.Localizer;
import harmotab.throwables.OutOfBoundsError;
import java.util.Collection;
import harmotab.core.HarmoTabObject;
import harmotab.core.undo.RestoreCommand;
import java.util.Iterator;
import harmotab.core.Figure;
import harmotab.core.Duration;
import java.util.Vector;

public class Accompaniment extends TrackElement implements Cloneable, Comparable<Accompaniment>
{
    public static final String CHORD_ATTR = "chord";
    public static final String RHYTHMIC_ATTR = "rhythmic";
    public static final String DURATION_ATTR = "duration";
    public static final int MIN_REPEAT_NUMBER = 1;
    public static final int MAX_REPEAT_NUMBER = 100;
    protected Chord m_chord;
    protected Vector<Duration> m_rhythmic;
    protected boolean m_hasCustomDuration;
    
    public Accompaniment() {
        super((byte)13);
        this.m_chord = null;
        this.m_rhythmic = new Vector<Duration>();
        this.setChord(new Chord());
        this.setCustomDuration(new Duration());
    }
    
    public Accompaniment(final Chord chord) {
        super((byte)13);
        this.m_chord = null;
        this.m_rhythmic = new Vector<Duration>();
        this.setChord(chord);
        this.setCustomDuration(new Duration());
    }
    
    public Accompaniment(final Chord chord, final Figure figure, final int repeat) {
        super((byte)13);
        this.m_chord = null;
        this.m_rhythmic = new Vector<Duration>();
        this.setChord(chord);
        this.setRhythmic(figure, repeat);
    }
    
    public Accompaniment(final Chord chord, final Duration customDuration) {
        super((byte)13);
        this.m_chord = null;
        this.m_rhythmic = new Vector<Duration>();
        this.setChord(chord);
        this.setCustomDuration(customDuration);
    }
    
    @Override
    public int compareTo(final Accompaniment acc) {
        if (acc == null) {
            return 1;
        }
        if (!this.m_chord.equals(acc.m_chord)) {
            return this.m_chord.compareTo(acc.m_chord);
        }
        if (this.m_rhythmic.size() != acc.m_rhythmic.size()) {
            return this.m_rhythmic.size() - acc.m_rhythmic.size();
        }
        for (int i = 0; i < this.m_rhythmic.size(); ++i) {
            final Duration duration = this.m_rhythmic.get(i);
            final Duration accDuration = acc.m_rhythmic.get(i);
            if (duration.getDuration() != accDuration.getDuration()) {
                return (duration.getDuration() > accDuration.getDuration()) ? 1 : -1;
            }
        }
        return 0;
    }
    
    @Override
    public Object clone() {
        final Accompaniment acc = (Accompaniment)super.clone();
        final Chord c = (Chord)this.m_chord.clone();
        acc.m_chord = null;
        acc.setChord(c);
        acc.m_rhythmic = new Vector<Duration>();
        for (final Duration d : this.m_rhythmic) {
            acc.m_rhythmic.add((Duration)d.clone());
        }
        return acc;
    }
    
    @Override
    public RestoreCommand createRestoreCommand() {
        return new AccompanimentRestoreCommand(this);
    }
    
    public void setChord(final Chord chord) {
        if (chord == null) {
            throw new NullPointerException();
        }
        this.removeAttributeChangesObserver(this.m_chord, "chord");
        this.addAttributeChangesObserver(this.m_chord = chord, "chord");
        this.fireObjectChanged("chord");
    }
    
    public Chord getChord() {
        return this.m_chord;
    }
    
    public Collection<Duration> getRhythmic() {
        return this.m_rhythmic;
    }
    
    public void setRhythmic(final Figure figure, int repeat) {
        if (repeat < 1 || repeat > 100) {
            throw new OutOfBoundsError("Bad repeats time value (" + repeat + ")");
        }
        this.m_rhythmic.clear();
        while (repeat-- > 0) {
            this.m_rhythmic.add((Figure)figure.clone());
        }
        this.m_hasCustomDuration = false;
        this.fireObjectChanged("rhythmic");
    }
    
    public void appendRhythmicFigure(final Figure figure) {
        this.m_rhythmic.add((Figure)figure.clone());
        this.fireObjectChanged("rhythmic");
    }
    
    public void setCustomDuration(final Duration duration) {
        if (duration == null) {
            throw new NullPointerException();
        }
        this.m_rhythmic.clear();
        this.m_hasCustomDuration = true;
        this.m_rhythmic.add(duration);
        this.fireObjectChanged("duration");
    }
    
    public void setCustomDuration(final float duration) {
        this.setCustomDuration(new Duration(duration));
    }
    
    @Override
    public String getTrackElementLocalizedName() {
        return Localizer.get("N_ACCOMPANIMENT");
    }
    
    @Override
    public float getDuration() {
        float value = 0.0f;
        for (final Duration duration : this.m_rhythmic) {
            value += duration.getDuration();
        }
        return value;
    }
    
    public boolean hasCustomDuration() {
        return this.m_hasCustomDuration;
    }
    
    public boolean isOneFigureRepeated() {
        if (this.m_hasCustomDuration) {
            return false;
        }
        Duration firstDuration = null;
        if (this.m_rhythmic.size() == 0) {
            return false;
        }
        for (final Duration duration : this.m_rhythmic) {
            if (firstDuration == null) {
                firstDuration = duration;
            }
            if (duration.getDuration() != firstDuration.getDuration()) {
                return false;
            }
        }
        return true;
    }
    
    public int getRepeatTime() {
        return this.isOneFigureRepeated() ? this.m_rhythmic.size() : 0;
    }
    
    public Figure getRepeatedFigure() {
        if (!this.isOneFigureRepeated()) {
            return null;
        }
        return this.m_rhythmic.get(0);
    }
    
    @Override
    public SerializedObject serialize(final ObjectSerializer serializer) {
        final SerializedObject object = super.serialize(serializer);
        object.setElementAttribute("chord", this.m_chord);
        if (this.m_hasCustomDuration) {
            object.setAttribute("duration", new StringBuilder(String.valueOf(this.m_rhythmic.get(0).getDuration())).toString());
        }
        else {
            object.setAttribute("rhythmic", "true");
            for (final Duration duration : this.m_rhythmic) {
                object.addChild(duration.serialize(serializer));
            }
        }
        return object;
    }
    
    @Override
    public void deserialize(final ObjectSerializer serializer, final SerializedObject object) {
        this.setChord((Chord)object.getElementAttribute("chord"));
        if (object.hasAttribute("duration")) {
            this.setCustomDuration(new Duration(Float.parseFloat(object.getAttribute("duration"))));
        }
        else if (object.hasAttribute("rhythmic")) {
            this.m_rhythmic.clear();
            for (int i = 0; i < object.getChildsNumber(); ++i) {
                final SerializedObject child = object.getChild(i);
                if (child != null) {
                    final HarmoTabObject htObj = HarmoTabObjectFactory.create(serializer, child);
                    if (htObj instanceof Figure) {
                        this.appendRhythmicFigure((Figure)htObj);
                    }
                }
            }
        }
    }
}
