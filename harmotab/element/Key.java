// 
// Decompiled by Procyon v0.5.30
// 

package harmotab.element;

import harmotab.io.SerializedObject;
import harmotab.io.ObjectSerializer;
import harmotab.core.Localizer;
import harmotab.core.Height;
import harmotab.core.undo.RestoreCommand;
import harmotab.throwables.UnhandledCaseError;
import harmotab.throwables.OutOfBoundsError;

public class Key extends TrackElement
{
    public static final String KEY_ATTR = "value";
    public static final byte G2 = 0;
    public static final byte F4 = 1;
    public static final byte C3 = 2;
    public static final byte C4 = 3;
    public static final String G2_STR = "G2";
    public static final String F4_STR = "F4";
    public static final String C3_STR = "C3";
    public static final String C4_STR = "C4";
    private static final String DEFAULT_KEY = "G2";
    protected byte m_key;
    protected int m_ordinate;
    
    public Key() {
        super((byte)7);
        this.setValue("G2");
    }
    
    public Key(final byte key) throws OutOfBoundsError {
        super((byte)7);
        this.setValue(key);
    }
    
    public void setValue(final String key) {
        if (key.equals("G2")) {
            this.setValue((byte)0);
        }
        else if (key.equals("F4")) {
            this.setValue((byte)1);
        }
        else if (key.equals("C3")) {
            this.setValue((byte)2);
        }
        else {
            if (!key.equals("C4")) {
                throw new UnhandledCaseError("Invalid key '" + key + "'");
            }
            this.setValue((byte)3);
        }
    }
    
    public String getValueStr() {
        switch (this.m_key) {
            case 0: {
                return "G2";
            }
            case 1: {
                return "F4";
            }
            case 2: {
                return "C3";
            }
            case 3: {
                return "C4";
            }
            default: {
                throw new UnhandledCaseError("Unhandled key conversion for '" + this.m_key + "'");
            }
        }
    }
    
    @Override
    public Object clone() {
        return super.clone();
    }
    
    @Override
    public RestoreCommand createRestoreCommand() {
        return new KeyRestoreCommand(this);
    }
    
    public void setValue(final byte key) throws OutOfBoundsError {
        switch (key) {
            case 0: {
                this.m_ordinate = new Height((byte)4, 4).getOrdinate();
                break;
            }
            case 1: {
                this.m_ordinate = new Height((byte)3, 2).getOrdinate();
                break;
            }
            case 2: {
                this.m_ordinate = new Height((byte)0, 3).getOrdinate();
                break;
            }
            case 3: {
                this.m_ordinate = new Height((byte)0, 3).getOrdinate();
                break;
            }
            default: {
                throw new OutOfBoundsError("Invalid key value (" + key + ") !");
            }
        }
        this.m_key = key;
        this.fireObjectChanged("value");
    }
    
    public byte getValue() {
        return this.m_key;
    }
    
    @Override
    public String getTrackElementLocalizedName() {
        return Localizer.get("N_KEY");
    }
    
    public int getOrdinate() {
        return this.m_ordinate;
    }
    
    @Override
    public float getWidthUnit() {
        return 0.75f;
    }
    
    @Override
    public SerializedObject serialize(final ObjectSerializer serializer) {
        final SerializedObject object = super.serialize(serializer);
        object.setAttribute("value", this.getValueStr());
        return object;
    }
    
    @Override
    public void deserialize(final ObjectSerializer serializer, final SerializedObject object) {
        this.setValue(object.hasAttribute("value") ? object.getAttribute("value") : "G2");
    }
}
