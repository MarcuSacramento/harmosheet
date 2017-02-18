// 
// Decompiled by Procyon v0.5.30
// 

package harmotab.core;

import harmotab.io.SerializedObject;
import harmotab.io.ObjectSerializer;
import harmotab.core.undo.RestoreCommand;

public class Effect extends HarmoTabObject
{
    public static final String EFFECT_TYPESTR = "effect";
    public static final String EFFECT_ATTR = "effect";
    public static final byte NONE = 0;
    public static final byte WAHWAH = 1;
    public static final byte SLIDE = 2;
    public static final byte NUMBER_OF_EFFETS = 3;
    public static final String NONE_STR = "none";
    public static final String WAHWAH_STR = "wahwah";
    public static final String SLIDE_STR = "slide";
    protected byte m_effect;
    
    public Effect(final byte effect) {
        this.setType(effect);
    }
    
    public Effect(final String effect) {
        this.setType(effect);
    }
    
    public Effect() {
        this((byte)0);
    }
    
    public Object clone() {
        return super.clone();
    }
    
    @Override
    public RestoreCommand createRestoreCommand() {
        return new EffectRestoreCommand(this);
    }
    
    public void setType(final byte effect) {
        switch (effect) {
            case 0:
            case 1:
            case 2: {
                this.m_effect = effect;
                this.fireObjectChanged("effect");
            }
            default: {
                throw new IllegalArgumentException("Unhandled effect " + effect + ".");
            }
        }
    }
    
    public void setType(final String effect) {
        if (effect.equals("none")) {
            this.setType((byte)0);
        }
        else if (effect.equals("wahwah")) {
            this.setType((byte)1);
        }
        else {
            if (!effect.equals("slide")) {
                throw new IllegalArgumentException("Unhandled effect '" + effect + "'.");
            }
            this.setType((byte)2);
        }
    }
    
    public byte getType() {
        return this.m_effect;
    }
    
    public String getEffectTypeStr() {
        switch (this.m_effect) {
            case 0: {
                return "none";
            }
            case 1: {
                return "wahwah";
            }
            case 2: {
                return "slide";
            }
            default: {
                return "UNKNOWN EFFECT";
            }
        }
    }
    
    public static String getEffectLocalizedName(final byte effect) {
        switch (effect) {
            case 0: {
                return Localizer.get("N_EFFECT_NONE");
            }
            case 1: {
                return Localizer.get("N_EFFECT_WAHWAH");
            }
            case 2: {
                return Localizer.get("N_EFFECT_SLIDE");
            }
            default: {
                return "UNKNOWN EFFECT";
            }
        }
    }
    
    @Override
    public SerializedObject serialize(final ObjectSerializer serializer) {
        final SerializedObject object = serializer.createSerializedObject("effect", this.hashCode());
        object.setAttribute("effect", new StringBuilder(String.valueOf(this.getType())).toString());
        return null;
    }
    
    @Override
    public void deserialize(final ObjectSerializer serializer, final SerializedObject object) {
        this.setType(Byte.parseByte(object.getAttribute("effect")));
    }
}
