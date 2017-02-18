// 
// Decompiled by Procyon v0.5.30
// 

package harmotab.element;

import harmotab.io.SerializedObject;
import harmotab.io.ObjectSerializer;
import harmotab.throwables.UnhandledCaseError;
import harmotab.core.Localizer;
import harmotab.core.undo.RestoreCommand;
import harmotab.throwables.OutOfBoundsError;
import harmotab.core.Height;

public class KeySignature extends TrackElement
{
    public static final String INDEX_ATTR = "index";
    public static final byte MAX_KEY_SIGNATURE = 6;
    public static final byte MIN_KEY_SIGNATURE = -6;
    public static final byte G_FLAT_MAJOR = -6;
    public static final byte D_FLAT_MAJOR = -5;
    public static final byte A_FLAT_MAJOR = -4;
    public static final byte E_FLAT_MAJOR = -3;
    public static final byte B_FLAT_MAJOR = -2;
    public static final byte F_MAJOR = -1;
    public static final byte C_MAJOR = 0;
    public static final byte G_MAJOR = 1;
    public static final byte D_MAJOR = 2;
    public static final byte A_MAJOR = 3;
    public static final byte E_MAJOR = 4;
    public static final byte B_MAJOR = 5;
    public static final byte F_SHARP_MAJOR = 6;
    public static final byte E_FLAT_MINOR = -6;
    public static final byte B_FLAT_MINOR = -5;
    public static final byte F_MINOR = -4;
    public static final byte C_MINOR = -3;
    public static final byte G_MINOR = -2;
    public static final byte D_MINOR = -1;
    public static final byte A_MINOR = 0;
    public static final byte E_MINOR = 1;
    public static final byte B_MINOR = 2;
    public static final byte F_SHARP_MINOR = 3;
    public static final byte C_SHARP_MINOR = 4;
    public static final byte G_SHARP_MINOR = 5;
    public static final byte D_SHARP_MINOR = 6;
    public static final Height[] SHARP_ORDER;
    public static final Height[] FLAT_ORDER;
    public static final byte DEFAULT_KEY_SIGNATURE = 0;
    protected byte m_keySignature;
    
    static {
        SHARP_ORDER = new Height[] { null, new Height((byte)3, 5), new Height((byte)0, 5), new Height((byte)4, 5), new Height((byte)1, 5), new Height((byte)5, 4), new Height((byte)2, 5), new Height((byte)6, 4) };
        FLAT_ORDER = new Height[] { null, new Height((byte)6, 4), new Height((byte)2, 5), new Height((byte)5, 4), new Height((byte)1, 5), new Height((byte)4, 4), new Height((byte)0, 5), new Height((byte)3, 4) };
    }
    
    public KeySignature() {
        super((byte)8);
        this.setIndex((byte)0);
    }
    
    public KeySignature(final byte index) throws OutOfBoundsError {
        super((byte)8);
        this.setIndex(index);
    }
    
    @Override
    public Object clone() {
        return super.clone();
    }
    
    @Override
    public RestoreCommand createRestoreCommand() {
        return new KeySignatureRestoreCommand(this);
    }
    
    public byte getValue() {
        return this.m_keySignature;
    }
    
    public void setIndex(final byte keySignature) throws OutOfBoundsError {
        if (keySignature < -6 || keySignature > 6) {
            throw new OutOfBoundsError("Invalid key signature index (" + keySignature + ") !");
        }
        this.m_keySignature = keySignature;
        this.fireObjectChanged("index");
    }
    
    @Override
    public String getTrackElementLocalizedName() {
        return Localizer.get("N_KEY_SIGNATURE");
    }
    
    public boolean isSharp(final Height height) {
        switch (height.getNote()) {
            case 3: {
                return this.m_keySignature > 0;
            }
            case 0: {
                return this.m_keySignature > 1;
            }
            case 4: {
                return this.m_keySignature > 2;
            }
            case 1: {
                return this.m_keySignature > 3;
            }
            case 5: {
                return this.m_keySignature > 4;
            }
            case 2: {
                return this.m_keySignature > 5;
            }
            case 6: {
                return this.m_keySignature > 6;
            }
            default: {
                throw new UnhandledCaseError("Invalid note height !");
            }
        }
    }
    
    public boolean isFlat(final Height height) {
        switch (height.getNote()) {
            case 6: {
                return this.m_keySignature < 0;
            }
            case 2: {
                return this.m_keySignature < -1;
            }
            case 5: {
                return this.m_keySignature < -2;
            }
            case 1: {
                return this.m_keySignature < -3;
            }
            case 4: {
                return this.m_keySignature < -4;
            }
            case 0: {
                return this.m_keySignature < -5;
            }
            case 3: {
                return this.m_keySignature < -6;
            }
            default: {
                throw new UnhandledCaseError("Invalid note height !");
            }
        }
    }
    
    public static Height getHeight(final byte index) {
        if (index > 0) {
            return KeySignature.SHARP_ORDER[index];
        }
        if (index < 0) {
            return KeySignature.FLAT_ORDER[-index];
        }
        return null;
    }
    
    @Override
    public float getWidthUnit() {
        final float width = this.getValue() * 0.3f;
        return ((width >= 0.0f) ? width : (-width)) + 0.25f;
    }
    
    public static String getTonalityName(final int value) {
        switch (value) {
            case -6: {
                return "Gb " + Localizer.get("N_MAJOR") + " / Eb " + Localizer.get("N_MINOR");
            }
            case -5: {
                return "Db " + Localizer.get("N_MAJOR") + " / Bb " + Localizer.get("N_MINOR");
            }
            case -4: {
                return "Ab " + Localizer.get("N_MAJOR") + " / F " + Localizer.get("N_MINOR");
            }
            case -3: {
                return "Eb " + Localizer.get("N_MAJOR") + " / C " + Localizer.get("N_MINOR");
            }
            case -2: {
                return "Bb " + Localizer.get("N_MAJOR") + " / G " + Localizer.get("N_MINOR");
            }
            case -1: {
                return "F " + Localizer.get("N_MAJOR") + " / D " + Localizer.get("N_MINOR");
            }
            case 0: {
                return "C " + Localizer.get("N_MAJOR") + " / A " + Localizer.get("N_MINOR");
            }
            case 1: {
                return "G " + Localizer.get("N_MAJOR") + " / E " + Localizer.get("N_MINOR");
            }
            case 2: {
                return "D " + Localizer.get("N_MAJOR") + " / B " + Localizer.get("N_MINOR");
            }
            case 3: {
                return "A " + Localizer.get("N_MAJOR") + " / F# " + Localizer.get("N_MINOR");
            }
            case 4: {
                return "E " + Localizer.get("N_MAJOR") + " / C# " + Localizer.get("N_MINOR");
            }
            case 5: {
                return "B " + Localizer.get("N_MAJOR") + " / G# " + Localizer.get("N_MINOR");
            }
            case 6: {
                return "F# " + Localizer.get("N_MAJOR") + " / D# " + Localizer.get("N_MINOR");
            }
            default: {
                return "ERROR";
            }
        }
    }
    
    @Override
    public SerializedObject serialize(final ObjectSerializer serializer) {
        final SerializedObject object = super.serialize(serializer);
        object.setAttribute("index", new StringBuilder(String.valueOf(this.getValue())).toString());
        return object;
    }
    
    @Override
    public void deserialize(final ObjectSerializer serializer, final SerializedObject object) {
        this.setIndex((byte)(object.hasAttribute("index") ? ((byte)Byte.decode(object.getAttribute("index"))) : 0));
    }
}
