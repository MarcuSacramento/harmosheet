// 
// Decompiled by Procyon v0.5.30
// 

package harmotab.element;

import harmotab.io.SerializedObject;
import harmotab.io.ObjectSerializer;
import harmotab.core.Localizer;
import harmotab.core.undo.RestoreCommand;
import harmotab.core.Duration;

public class Silence extends TrackElement
{
    public static final String DURATION_ATTR = "duration";
    protected Duration m_duration;
    
    public Silence(final float duration) {
        super((byte)17);
        this.m_duration = new Duration(duration);
    }
    
    public Silence() {
        this(1.0f);
    }
    
    @Override
    public Object clone() {
        final Silence clone = (Silence)super.clone();
        clone.m_duration = (Duration)this.m_duration.clone();
        return clone;
    }
    
    @Override
    public RestoreCommand createRestoreCommand() {
        return new SilenceRestoreCommand(this);
    }
    
    @Override
    public float getDuration() {
        return this.m_duration.getDuration();
    }
    
    public void setDuration(final float duration) {
        this.m_duration.setDuration(duration);
        this.fireObjectChanged("duration");
    }
    
    @Override
    public String getTrackElementLocalizedName() {
        return Localizer.get("N_SILENCE");
    }
    
    @Override
    public SerializedObject serialize(final ObjectSerializer serializer) {
        final SerializedObject object = super.serialize(serializer);
        object.setAttribute("duration", new StringBuilder(String.valueOf(this.getDuration())).toString());
        return object;
    }
    
    @Override
    public void deserialize(final ObjectSerializer serializer, final SerializedObject object) {
        this.setDuration(object.hasAttribute("duration") ? Float.parseFloat(object.getAttribute("duration")) : 1.0f);
    }
}
