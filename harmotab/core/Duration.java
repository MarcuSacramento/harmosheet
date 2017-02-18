// 
// Decompiled by Procyon v0.5.30
// 

package harmotab.core;

import harmotab.io.SerializedObject;
import harmotab.io.ObjectSerializer;
import harmotab.core.undo.RestoreCommand;

public class Duration extends HarmoTabObject implements Cloneable
{
    public static final String DURATION_TYPESTR = "duration";
    public static final String DURATION_ATTR = "duration";
    public static final float DEFAULT_DURATION = 1.0f;
    public static final float DURATION_GRANULARITY = 0.25f;
    public static final float MIN_DURATION_VALUE = 0.0f;
    public static final float MAX_DURATION_VALUE = 100.0f;
    protected float m_duration;
    
    public Duration() {
        this.setDuration(1.0f);
    }
    
    public Duration(final float value) {
        this.setDuration(value);
    }
    
    public Object clone() {
        return super.clone();
    }
    
    @Override
    public RestoreCommand createRestoreCommand() {
        return new DurationRestoreCommand(this);
    }
    
    public float getDuration() {
        return this.m_duration;
    }
    
    public void setDuration(final float value) {
        if (value < 0.0f || value > 100.0f) {
            throw new IllegalArgumentException("Bad duration value " + value);
        }
        this.m_duration = value;
        this.fireObjectChanged("duration");
    }
    
    @Override
    public SerializedObject serialize(final ObjectSerializer serializer) {
        final SerializedObject object = serializer.createSerializedObject("duration", this.hashCode());
        object.setAttribute("duration", new StringBuilder(String.valueOf(this.getDuration())).toString());
        return object;
    }
    
    @Override
    public void deserialize(final ObjectSerializer serializer, final SerializedObject object) {
        this.setDuration(object.hasAttribute("duration") ? Float.parseFloat(object.getAttribute("duration")) : 1.0f);
    }
}
