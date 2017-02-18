// 
// Decompiled by Procyon v0.5.30
// 

package harmotab.element;

import harmotab.io.SerializedObject;
import harmotab.io.ObjectSerializer;
import harmotab.core.Localizer;
import harmotab.core.HarmoTabObject;
import harmotab.core.undo.RestoreCommand;
import harmotab.core.RepeatAttribute;

public class Bar extends TrackElement
{
    public static final String KEY_ATTR = "key";
    public static final String KEY_SIGNATURE_ATTR = "keySignature";
    public static final String TIME_SIGNATURE_ATTR = "timeSignature";
    public static final String REPEAT_ATTRIBUTE_ATTR = "repeatAttribute";
    protected Key m_key;
    protected KeySignature m_keySignature;
    protected TimeSignature m_timeSignature;
    protected RepeatAttribute m_repeatAttribute;
    
    public Bar() {
        super((byte)5);
        this.m_key = null;
        this.m_keySignature = null;
        this.m_timeSignature = null;
        this.m_repeatAttribute = null;
        this.setKey(null);
        this.setKeySignature(null);
        this.setTimeSignature(null);
        this.setRepeatAttribute(new RepeatAttribute());
    }
    
    public Bar(final Key key, final KeySignature ks, final TimeSignature ts, final RepeatAttribute repeat) {
        super((byte)5);
        this.m_key = null;
        this.m_keySignature = null;
        this.m_timeSignature = null;
        this.m_repeatAttribute = null;
        this.setKey(key);
        this.setKeySignature(ks);
        this.setTimeSignature(ts);
        this.setRepeatAttribute(repeat);
    }
    
    @Override
    public Object clone() {
        final Bar bar = (Bar)super.clone();
        final Key key = (this.m_key != null) ? ((Key)this.m_key.clone()) : null;
        bar.m_key = null;
        bar.setKey(key);
        final KeySignature ks = (this.m_keySignature != null) ? ((KeySignature)this.m_keySignature.clone()) : null;
        bar.m_keySignature = null;
        bar.setKeySignature(ks);
        final TimeSignature ts = (this.m_timeSignature != null) ? ((TimeSignature)this.m_timeSignature.clone()) : null;
        bar.m_timeSignature = null;
        bar.setTimeSignature(ts);
        final RepeatAttribute ra = (this.m_repeatAttribute != null) ? ((RepeatAttribute)this.m_repeatAttribute.clone()) : null;
        bar.m_repeatAttribute = null;
        bar.setRepeatAttribute(ra);
        return bar;
    }
    
    @Override
    public RestoreCommand createRestoreCommand() {
        return new BarRestoreCommand(this);
    }
    
    public Key getKey() {
        return this.m_key;
    }
    
    public void setKey(final Key key) {
        this.removeAttributeChangesObserver(this.m_key, "key");
        this.addAttributeChangesObserver(this.m_key = key, "key");
        this.fireObjectChanged("key");
    }
    
    public KeySignature getKeySignature() {
        return this.m_keySignature;
    }
    
    public void setKeySignature(final KeySignature keySignature) {
        this.removeAttributeChangesObserver(this.m_keySignature, "keySignature");
        this.addAttributeChangesObserver(this.m_keySignature = keySignature, "keySignature");
        this.fireObjectChanged("keySignature");
    }
    
    public TimeSignature getTimeSignature() {
        return this.m_timeSignature;
    }
    
    public void setTimeSignature(final TimeSignature timeSignature) {
        this.removeAttributeChangesObserver(this.m_timeSignature, "timeSignature");
        this.addAttributeChangesObserver(this.m_timeSignature = timeSignature, "timeSignature");
        this.fireObjectChanged("timeSignature");
    }
    
    public RepeatAttribute getRepeatAttribute() {
        return this.m_repeatAttribute;
    }
    
    public void setRepeatAttribute(final RepeatAttribute repeatAttribute) {
        if (repeatAttribute == null) {
            throw new NullPointerException();
        }
        this.removeAttributeChangesObserver(this.m_repeatAttribute, "repeatAttribute");
        this.addAttributeChangesObserver(this.m_repeatAttribute = repeatAttribute, "repeatAttribute");
        this.fireObjectChanged("repeatAttribute");
    }
    
    @Override
    public String getTrackElementLocalizedName() {
        return Localizer.get("N_BAR");
    }
    
    @Override
    public float getWidthUnit() {
        return 1.0f;
    }
    
    @Override
    public boolean contains(final Element subElement) {
        return subElement == this.m_key || subElement == this.m_keySignature || subElement == this.m_timeSignature;
    }
    
    @Override
    public boolean delete(final Element subElement) {
        if (subElement == this.m_key) {
            this.setKey(null);
        }
        else if (subElement == this.m_keySignature) {
            this.setKeySignature(null);
        }
        else {
            if (subElement != this.m_timeSignature) {
                return false;
            }
            this.setTimeSignature(null);
        }
        return true;
    }
    
    @Override
    public SerializedObject serialize(final ObjectSerializer serializer) {
        final SerializedObject object = super.serialize(serializer);
        if (this.getKey() != null) {
            object.setElementAttribute("key", this.getKey());
        }
        if (this.getKeySignature() != null) {
            object.setElementAttribute("keySignature", this.getKeySignature());
        }
        if (this.getTimeSignature() != null) {
            object.setElementAttribute("timeSignature", this.getTimeSignature());
        }
        if (this.getRepeatAttribute() != null) {
            object.setElementAttribute("repeatAttribute", this.getRepeatAttribute());
        }
        return object;
    }
    
    @Override
    public void deserialize(final ObjectSerializer serializer, final SerializedObject object) {
        this.setKey(object.hasAttribute("key") ? ((Key)object.getElementAttribute("key")) : null);
        this.setKeySignature(object.hasAttribute("keySignature") ? ((KeySignature)object.getElementAttribute("keySignature")) : null);
        this.setTimeSignature(object.hasAttribute("timeSignature") ? ((TimeSignature)object.getElementAttribute("timeSignature")) : null);
        this.setRepeatAttribute(object.hasAttribute("repeatAttribute") ? ((RepeatAttribute)object.getElementAttribute("repeatAttribute")) : null);
    }
}
