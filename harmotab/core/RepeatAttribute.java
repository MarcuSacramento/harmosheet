// 
// Decompiled by Procyon v0.5.30
// 

package harmotab.core;

import harmotab.io.SerializedObject;
import harmotab.io.ObjectSerializer;
import harmotab.throwables.OutOfBoundsError;
import harmotab.core.undo.RestoreCommand;

public class RepeatAttribute extends HarmoTabObject implements Cloneable
{
    public static final String REPEAT_ATTRIBUTE_TYPESTR = "repeatAttribute";
    public static final String BEGINNING_ATTR = "beginning";
    public static final String ENDING_ATTR = "ending";
    public static final String ALTERNATE_ENDING_ATTR = "alternateEnding";
    public static final String REPEAT_TIMES_ATTR = "repeats";
    public static final int MIN_ALTERNATE_ENDING = 0;
    public static final int MAX_ALTERNATE_ENDING = 32;
    public static final int MIN_REPEAT_TIMES = 1;
    public static final int MAX_REPEAT_TIMES = 32;
    public static final byte DEFAULT_ALTERNAT_ENDING = 0;
    public static final boolean DEFAULT_ENDING = false;
    public static final boolean DEFAULT_BEGINNING = false;
    public static final int DEFAULT_REPEAT_TIMES = 1;
    protected byte m_alternateEnding;
    protected boolean m_isBeginning;
    protected boolean m_isEnd;
    protected int m_repeatTimes;
    
    public RepeatAttribute(final boolean isBeginning, final boolean isEnd, final int repeatTimes, final byte alternateEnding) {
        this.m_alternateEnding = 0;
        this.m_isBeginning = false;
        this.m_isEnd = false;
        this.m_repeatTimes = 0;
        this.setBeginning(isBeginning);
        this.setEnd(isEnd);
        this.setAlternateEnding(alternateEnding);
        this.setRepeatTimes(repeatTimes);
    }
    
    public RepeatAttribute(final boolean isBeginning, final boolean isEnd, final int repeatTimes) {
        this(isBeginning, isEnd, repeatTimes, (byte)0);
    }
    
    public RepeatAttribute(final boolean isBeginning, final boolean isEnd) {
        this(isBeginning, isEnd, 1);
    }
    
    public RepeatAttribute() {
        this(false, false, 1, (byte)0);
    }
    
    public RepeatAttribute(final byte alternateEnding) {
        this(false, false, 1, alternateEnding);
    }
    
    public RepeatAttribute(final int repeatTimes) {
        this(false, true, repeatTimes);
    }
    
    public Object clone() {
        return super.clone();
    }
    
    @Override
    public RestoreCommand createRestoreCommand() {
        return new RepeatAttributeRestoreCommand(this);
    }
    
    public boolean isBeginning() {
        return this.m_isBeginning;
    }
    
    public void setBeginning(final boolean isBeginning) {
        this.m_isBeginning = isBeginning;
        this.fireObjectChanged("beginning");
    }
    
    public boolean isEnd() {
        return this.m_isEnd;
    }
    
    public void setEnd(final boolean isEnd) {
        if (!(this.m_isEnd = isEnd)) {
            this.setRepeatTimes(1);
        }
        this.fireObjectChanged("ending");
    }
    
    public boolean isAlternateEnding() {
        return this.m_alternateEnding > 0;
    }
    
    public byte getAlternateEnding() {
        return this.m_alternateEnding;
    }
    
    public void setAlternateEnding(final byte alternateEnding) throws OutOfBoundsError {
        if (alternateEnding < 0 || alternateEnding > 32) {
            throw new OutOfBoundsError("Invalid alternate ending value !");
        }
        this.m_alternateEnding = alternateEnding;
        this.fireObjectChanged("alternateEnding");
    }
    
    public int getRepeatTimes() {
        return this.m_repeatTimes;
    }
    
    public void setRepeatTimes(final int repeatTimes) {
        if (repeatTimes < 0 || repeatTimes > 32) {
            throw new OutOfBoundsError("Invalid repeat times value (" + repeatTimes + ")");
        }
        this.m_repeatTimes = repeatTimes;
        this.fireObjectChanged("repeats");
    }
    
    public boolean isSingle() {
        return !this.m_isBeginning && !this.m_isEnd;
    }
    
    @Override
    public SerializedObject serialize(final ObjectSerializer serializer) {
        final SerializedObject object = serializer.createSerializedObject("repeatAttribute", this.hashCode());
        if (this.getAlternateEnding() != 0) {
            object.setAttribute("alternateEnding", new StringBuilder(String.valueOf(this.getAlternateEnding())).toString());
        }
        if (this.isBeginning()) {
            object.setAttribute("beginning", new StringBuilder(String.valueOf(this.isBeginning())).toString());
        }
        if (this.isEnd()) {
            object.setAttribute("ending", new StringBuilder(String.valueOf(this.isEnd())).toString());
        }
        if (this.getRepeatTimes() != 1) {
            object.setAttribute("repeats", new StringBuilder(String.valueOf(this.getRepeatTimes())).toString());
        }
        return object;
    }
    
    @Override
    public void deserialize(final ObjectSerializer serializer, final SerializedObject object) {
        this.setAlternateEnding((byte)(object.hasAttribute("alternateEnding") ? ((byte)Byte.decode(object.getAttribute("alternateEnding"))) : 0));
        this.setBeginning(object.hasAttribute("beginning") && Boolean.parseBoolean(object.getAttribute("beginning")));
        this.setEnd(object.hasAttribute("ending") && Boolean.parseBoolean(object.getAttribute("ending")));
        this.setRepeatTimes(object.hasAttribute("repeats") ? Integer.parseInt(object.getAttribute("repeats")) : 1);
    }
}
