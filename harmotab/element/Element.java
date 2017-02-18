// 
// Decompiled by Procyon v0.5.30
// 

package harmotab.element;

import harmotab.io.SerializedObject;
import harmotab.io.ObjectSerializer;
import harmotab.throwables.UnhandledCaseError;
import harmotab.core.HarmoTabObject;

public abstract class Element extends HarmoTabObject implements Cloneable
{
    public static final byte EMPTY_AREA = -1;
    public static final byte ELEMENT = 0;
    public static final byte TEXT_ELEMENT = 1;
    public static final byte NOTE = 2;
    public static final byte TAB = 3;
    public static final byte HARMOTAB = 4;
    public static final byte BAR = 5;
    public static final byte STAFF = 6;
    public static final byte KEY = 7;
    public static final byte KEY_SIGNATURE = 8;
    public static final byte TIME_SIGNATURE = 9;
    public static final byte GROUP = 10;
    public static final byte HOOCKED_NOTES = 11;
    public static final byte CHORD = 12;
    public static final byte ACCOMPANIMENT = 13;
    public static final byte TEMPO = 14;
    public static final byte TIED_NOTES = 15;
    public static final byte TRIPLET_GROUP = 16;
    public static final byte SILENCE = 17;
    public static final byte LYRICS = 18;
    public static final byte HARMONICA_PROPERTIES = 19;
    public static final byte TAB_AREA = 20;
    public static final String EMPTY_AREA_TYPESTR = "emptyArea";
    public static final String ELEMENT_TYPESTR = "element";
    public static final String TEXT_ELEMENT_TYPESTR = "textElement";
    public static final String NOTE_TYPESTR = "note";
    public static final String TAB_TYPESTR = "tab";
    public static final String HARMOTAB_TYPESTR = "harmotab";
    public static final String BAR_TYPESTR = "bar";
    public static final String STAFF_TYPESTR = "staff";
    public static final String KEY_TYPESTR = "key";
    public static final String KEY_SIGNATURE_TYPESTR = "keySignature";
    public static final String TIME_SIGNATURE_TYPESTR = "timeSignature";
    public static final String GROUP_TYPESTR = "group";
    public static final String HOOCKED_NOTES_TYPESTR = "hoockedNotes";
    public static final String CHORD_TYPESTR = "chord";
    public static final String ACCOMPANIMENT_TYPESTR = "accompaniment";
    public static final String TEMPO_TYPESTR = "tempo";
    public static final String TIED_NOTES_TYPESTR = "tiedNotes";
    public static final String TRIPLET_GROUP_TYPESTR = "tripletGroup";
    public static final String SILENCE_TYPESTR = "silence";
    public static final String LYRICS_TYPESTR = "lyrics";
    protected byte m_type;
    
    public Element(final byte type) {
        this.m_type = 0;
        this.m_type = type;
    }
    
    public Object clone() {
        return super.clone();
    }
    
    public byte getType() {
        return this.m_type;
    }
    
    public float getWidthUnit() {
        return 1.0f;
    }
    
    public float getDuration() {
        return 0.0f;
    }
    
    public static String getTypeName(final int type) {
        switch (type) {
            case -1: {
                return "emptyArea";
            }
            case 0: {
                return "element";
            }
            case 1: {
                return "textElement";
            }
            case 2: {
                return "note";
            }
            case 3: {
                return "tab";
            }
            case 4: {
                return "harmotab";
            }
            case 5: {
                return "bar";
            }
            case 6: {
                return "staff";
            }
            case 7: {
                return "key";
            }
            case 8: {
                return "keySignature";
            }
            case 9: {
                return "timeSignature";
            }
            case 10: {
                return "group";
            }
            case 11: {
                return "hoockedNotes";
            }
            case 12: {
                return "chord";
            }
            case 13: {
                return "accompaniment";
            }
            case 14: {
                return "tempo";
            }
            case 17: {
                return "silence";
            }
            case 18: {
                return "lyrics";
            }
            default: {
                throw new UnhandledCaseError("Cannot retrieve element type name (#" + type + ")");
            }
        }
    }
    
    public String getTypeName() {
        return getTypeName(this.m_type);
    }
    
    public boolean contains(final Element subElement) {
        return false;
    }
    
    public boolean delete(final Element subElement) {
        return false;
    }
    
    @Override
    public SerializedObject serialize(final ObjectSerializer serializer) {
        final SerializedObject object = serializer.createSerializedObject(this.getTypeName(), this.hashCode());
        return object;
    }
    
    @Override
    public void deserialize(final ObjectSerializer serializer, final SerializedObject object) {
        System.err.println("Element::deserializer: Method should be overloaded for " + this);
    }
}
