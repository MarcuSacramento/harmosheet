// 
// Decompiled by Procyon v0.5.30
// 

package harmotab.element;

import harmotab.io.SerializedObject;
import harmotab.io.ObjectSerializer;
import harmotab.core.Localizer;
import harmotab.core.HarmoTabObject;
import harmotab.core.undo.RestoreCommand;
import harmotab.core.Duration;

public class Lyrics extends TrackElement
{
    public static final String TEXT_ATTR = "text";
    public static final String DURATION_ATTR = "duration";
    protected String m_text;
    protected Duration m_duration;
    
    public Lyrics(final String text, final Duration duration) {
        super((byte)18);
        this.m_text = null;
        this.m_duration = null;
        this.setText(text);
        this.setDurationObject(duration);
    }
    
    public Lyrics(final String text) {
        this(text, new Duration());
    }
    
    public Lyrics() {
        this("");
    }
    
    @Override
    public Object clone() {
        final Lyrics lyrics = (Lyrics)super.clone();
        lyrics.setText(this.getText());
        lyrics.setDurationObject((Duration)this.getDurationObject().clone());
        return lyrics;
    }
    
    @Override
    public RestoreCommand createRestoreCommand() {
        return new LyricsRestoreCommand(this);
    }
    
    public void setText(final String text) {
        this.m_text = text;
        this.fireObjectChanged("text");
    }
    
    public String getText() {
        return this.m_text;
    }
    
    public void setDurationObject(final Duration duration) {
        this.removeAttributeChangesObserver(this.m_duration, "duration");
        this.addAttributeChangesObserver(this.m_duration = duration, "duration");
        this.fireObjectChanged("duration");
    }
    
    public Duration getDurationObject() {
        return this.m_duration;
    }
    
    @Override
    public String getTrackElementLocalizedName() {
        return Localizer.get("N_LYRICS");
    }
    
    @Override
    public float getDuration() {
        return this.m_duration.getDuration();
    }
    
    @Override
    public SerializedObject serialize(final ObjectSerializer serializer) {
        final SerializedObject object = super.serialize(serializer);
        object.setAttribute("text", this.getText());
        object.setElementAttribute("duration", this.getDurationObject());
        return object;
    }
    
    @Override
    public void deserialize(final ObjectSerializer serializer, final SerializedObject object) {
        this.setText(object.getAttribute("text"));
        this.setDurationObject((Duration)object.getElementAttribute("duration"));
    }
}
