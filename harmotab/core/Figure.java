// 
// Decompiled by Procyon v0.5.30
// 

package harmotab.core;

import harmotab.io.SerializedObject;
import harmotab.io.ObjectSerializer;
import harmotab.throwables.OutOfSpecificationError;
import harmotab.throwables.UnhandledCaseError;
import harmotab.core.undo.RestoreCommand;
import harmotab.throwables.OutOfBoundsError;
import harmotab.throwables.BrokenImplementationError;

public class Figure extends Duration implements Cloneable
{
    public static final String FIGURE_TYPESTR = "figure";
    public static final String TYPE_ATTR = "type";
    public static final String DOTTED_ATTR = "dotted";
    public static final String TRIPLET_ATTR = "triplet";
    public static final byte WHOLE = 1;
    public static final byte HALF = 2;
    public static final byte QUARTER = 3;
    public static final byte EIGHTH = 4;
    public static final byte SIXTEENTH = 5;
    public static final byte APPOGIATURE = 6;
    public static final byte MIN_FIGURE_ID = 1;
    public static final byte MAX_FIGURE_ID = 6;
    public static final byte FIGURES_NUMBER = 6;
    private static final String WHOLE_STRING = "whole";
    private static final String HALF_STRING = "half";
    private static final String QUARTER_STRING = "quarter";
    private static final String EIGHTH_STRING = "eighth";
    private static final String SIXTEENTH_STRING = "sixteenth";
    private static final String APPOGIATURE_STRING = "appogiature";
    private static final byte DEFAULT_TYPE = 3;
    public static final boolean DEFAULT_DOTTED = false;
    public static final boolean DEFAULT_TRIPLET = false;
    public final float TRIPLET_FACTOR = 0.6666677f;
    public final float DOT_FACTOR = 1.5f;
    protected byte m_type;
    protected boolean m_isDotted;
    protected boolean m_isTriplet;
    
    public Figure() {
        try {
            this.setType((byte)3);
            this.setDotted(false);
            this.setTriplet(false);
        }
        catch (OutOfBoundsError e) {
            throw new BrokenImplementationError("Bad figure default type !");
        }
    }
    
    public Figure(final byte type) throws OutOfBoundsError {
        this.setType(type);
        this.setDotted(false);
        this.setTriplet(false);
    }
    
    public Figure(final float time) {
        if (time >= 4.0f) {
            this.setType((byte)1);
        }
        else if (time >= 3.0f) {
            this.setType((byte)2);
            this.setDotted(true);
        }
        else if (time >= 2.0f) {
            this.setType((byte)2);
        }
        else if (time >= 1.5f) {
            this.setType((byte)3);
            this.setDotted(true);
        }
        else if (time >= 1.0f) {
            this.setType((byte)3);
        }
        else if (time >= 0.75f) {
            this.setType((byte)4);
            this.setDotted(true);
        }
        else if (time >= 0.5f) {
            this.setType((byte)4);
        }
        else if (time >= 0.375f) {
            this.setType((byte)5);
            this.setDotted(true);
        }
        else {
            this.setType((byte)5);
        }
    }
    
    public Figure(final String type) {
        this.setType(type);
        this.setDotted(false);
        this.setTriplet(false);
    }
    
    @Override
    public Object clone() {
        return super.clone();
    }
    
    @Override
    public RestoreCommand createRestoreCommand() {
        return new FigureRestoreCommand(this);
    }
    
    public byte getType() {
        return this.m_type;
    }
    
    public String getFigureTypeStr() {
        switch (this.m_type) {
            case 1: {
                return "whole";
            }
            case 2: {
                return "half";
            }
            case 3: {
                return "quarter";
            }
            case 4: {
                return "eighth";
            }
            case 5: {
                return "sixteenth";
            }
            case 6: {
                return "appogiature";
            }
            default: {
                throw new UnhandledCaseError("Cannot convert figure as a string !");
            }
        }
    }
    
    public void setType(final byte type) throws OutOfBoundsError {
        if (type < 1 || type > 6) {
            throw new OutOfBoundsError("Out of bounds figure's type identifier !");
        }
        switch (this.m_type = type) {
            case 1: {
                this.setDuration(4.0f);
                break;
            }
            case 2: {
                this.setDuration(2.0f);
                break;
            }
            case 3: {
                this.setDuration(1.0f);
                break;
            }
            case 4: {
                this.setDuration(0.5f);
                break;
            }
            case 5: {
                this.setDuration(0.25f);
                break;
            }
            case 6: {
                this.setDuration(0.0f);
                break;
            }
            default: {
                throw new UnhandledCaseError("Cannot get figure's duration !");
            }
        }
        this.fireObjectChanged("type");
    }
    
    public void setType(final String string) throws OutOfSpecificationError {
        try {
            if (string.equals("whole")) {
                this.setType((byte)1);
            }
            else if (string.equals("half")) {
                this.setType((byte)2);
            }
            else if (string.equals("quarter")) {
                this.setType((byte)3);
            }
            else if (string.equals("eighth")) {
                this.setType((byte)4);
            }
            else if (string.equals("sixteenth")) {
                this.setType((byte)5);
            }
            else {
                if (!string.equals("appogiature")) {
                    throw new OutOfSpecificationError("Invalid type string identifier !");
                }
                this.setType((byte)6);
            }
        }
        catch (OutOfBoundsError e) {
            throw new BrokenImplementationError("Hard coded note type does not exists !");
        }
    }
    
    public boolean isDotted() {
        return this.m_isDotted;
    }
    
    public void setDotted(final boolean dotted) {
        this.m_isDotted = dotted;
        this.fireObjectChanged("dotted");
    }
    
    public boolean isTriplet() {
        return this.m_isTriplet;
    }
    
    public void setTriplet(final boolean triplet) {
        this.m_isTriplet = triplet;
        this.fireObjectChanged("triplet");
    }
    
    @Override
    public float getDuration() {
        float duration = super.getDuration();
        if (this.m_isDotted) {
            duration *= 1.5f;
        }
        if (this.m_isTriplet) {
            duration *= 0.6666677f;
        }
        return duration;
    }
    
    public String getLocalizedName() {
        switch (this.m_type) {
            case 1: {
                return Localizer.get("N_NOTE_WHOLE");
            }
            case 2: {
                return Localizer.get("N_NOTE_HALF");
            }
            case 3: {
                return Localizer.get("N_NOTE_QUARTER");
            }
            case 4: {
                return Localizer.get("N_NOTE_EIGHTH");
            }
            case 5: {
                return Localizer.get("N_NOTE_SIXTEENTH");
            }
            case 6: {
                return Localizer.get("N_NOTE_APPOGIATURE");
            }
            default: {
                throw new UnhandledCaseError("Cannot convert figure as a name !");
            }
        }
    }
    
    public float getWidth() {
        float width = 0.0f;
        switch (this.m_type) {
            case 1: {
                width = 3.0f;
                break;
            }
            case 2: {
                width = 1.5f;
                break;
            }
            case 3: {
                width = 1.0f;
                break;
            }
            case 4: {
                width = 0.5f;
                break;
            }
            case 5: {
                width = 0.5f;
                break;
            }
            case 6: {
                width = 0.3f;
                break;
            }
            default: {
                throw new UnhandledCaseError("Cannot get figure's duration !");
            }
        }
        return width;
    }
    
    public boolean isHookable() {
        return this.m_type > 3 && this.m_type != 6;
    }
    
    @Override
    public SerializedObject serialize(final ObjectSerializer serializer) {
        final SerializedObject object = serializer.createSerializedObject("figure", this.hashCode());
        object.setAttribute("type", this.getFigureTypeStr());
        if (this.isDotted()) {
            object.setAttribute("dotted", new StringBuilder(String.valueOf(this.isDotted())).toString());
        }
        if (this.isTriplet()) {
            object.setAttribute("triplet", new StringBuilder(String.valueOf(this.isTriplet())).toString());
        }
        return object;
    }
    
    @Override
    public void deserialize(final ObjectSerializer serializer, final SerializedObject object) {
        this.setType(object.getAttribute("type"));
        this.setDotted(object.hasAttribute("dotted") && Boolean.parseBoolean(object.getAttribute("dotted")));
        this.setTriplet(object.hasAttribute("triplet") && Boolean.parseBoolean(object.getAttribute("triplet")));
    }
}
