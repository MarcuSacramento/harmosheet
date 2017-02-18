// 
// Decompiled by Procyon v0.5.30
// 

package harmotab.element;

import harmotab.io.SerializedObject;
import harmotab.io.ObjectSerializer;
import harmotab.throwables.UnhandledCaseError;
import harmotab.core.Localizer;
import harmotab.throwables.OutOfSpecificationError;
import harmotab.throwables.OutOfBoundsError;
import harmotab.core.undo.RestoreCommand;

public class TimeSignature extends TrackElement
{
    public static final String NUMBER_ATTR = "number";
    public static final String REFERENCE_ATTR = "reference";
    public static final byte MIN_NUMBER = 1;
    public static final byte MAX_NUMBER = 16;
    public static final byte DEFAULT_NUMBER = 4;
    public static final byte MIN_REFERENCE = 1;
    public static final byte MAX_REFERENCE = 16;
    public static final byte DEFAULT_REFERENCE = 4;
    protected byte m_number;
    protected byte m_reference;
    
    public TimeSignature() {
        super((byte)9);
        this.m_number = 4;
        this.m_reference = 4;
    }
    
    public TimeSignature(final byte number, final byte reference) {
        super((byte)9);
        this.m_number = 4;
        this.m_reference = 4;
        this.setNumber(number);
        this.setReference(number);
    }
    
    @Override
    public Object clone() {
        return super.clone();
    }
    
    @Override
    public RestoreCommand createRestoreCommand() {
        return new TimeSignatureRestoreCommand(this);
    }
    
    public byte getNumber() {
        return this.m_number;
    }
    
    public void setNumber(final byte number) throws OutOfBoundsError {
        if (number < 1 || number > 16) {
            throw new OutOfBoundsError("Invalid time signature number !");
        }
        this.m_number = number;
        this.fireObjectChanged("number");
    }
    
    public byte getReference() {
        return this.m_reference;
    }
    
    public void setReference(final byte reference) throws OutOfBoundsError, OutOfSpecificationError {
        if (reference < 1 || reference > 16) {
            throw new OutOfBoundsError("Invalid time signature reference !");
        }
        if ((reference & reference - 1) != 0x0) {
            throw new OutOfSpecificationError("Time signature reference must be 1, 2, 4, 8, 16 or 32");
        }
        this.m_reference = reference;
        this.fireObjectChanged("reference");
    }
    
    @Override
    public String getTrackElementLocalizedName() {
        return Localizer.get("N_TIME_SIGNATURE");
    }
    
    public static boolean isReferenceValid(final byte reference) {
        return reference >= 1 && reference <= 16 && (reference & reference - 1) == 0x0;
    }
    
    public float getTimesPerBar() {
        float factor = 0.0f;
        switch (this.m_reference) {
            case 1: {
                factor = 4.0f;
                break;
            }
            case 2: {
                factor = 2.0f;
                break;
            }
            case 4: {
                factor = 1.0f;
                break;
            }
            case 8: {
                factor = 0.5f;
                break;
            }
            case 16: {
                factor = 0.25f;
                break;
            }
        }
        return factor * this.m_number;
    }
    
    public int getBeatsPerBar() {
        return (int)(this.getTimesPerBar() / this.getTimesPerBeat());
    }
    
    public boolean isStrongBeat(final float time) {
        return time - (float)(Math.floor(time) * this.getTimesPerBeat()) == 0.0f;
    }
    
    public float getTimesPerBeat() {
        switch (this.m_reference) {
            case 1:
            case 2:
            case 4: {
                return 1.0f;
            }
            case 8:
            case 12:
            case 16: {
                return 1.5f;
            }
            default: {
                throw new UnhandledCaseError("TimeSignature::getBeatPeriod: Reference not handled !");
            }
        }
    }
    
    @Override
    public float getWidthUnit() {
        return 0.5f;
    }
    
    @Override
    public SerializedObject serialize(final ObjectSerializer serializer) {
        final SerializedObject object = super.serialize(serializer);
        object.setAttribute("reference", new StringBuilder(String.valueOf(this.getReference())).toString());
        object.setAttribute("number", new StringBuilder(String.valueOf(this.getNumber())).toString());
        return object;
    }
    
    @Override
    public void deserialize(final ObjectSerializer serializer, final SerializedObject object) {
        this.setReference((byte)(object.hasAttribute("reference") ? ((byte)Byte.decode(object.getAttribute("reference"))) : 4));
        this.setNumber((byte)(object.hasAttribute("number") ? ((byte)Byte.decode(object.getAttribute("number"))) : 4));
    }
}
