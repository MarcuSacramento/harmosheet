// 
// Decompiled by Procyon v0.5.30
// 

package harmotab.harmonica;

import harmotab.core.Localizer;

public class HarmonicaType
{
    public static final String DIATONIC_STR = "diatonic";
    public static final String CHROMATIC_STR = "chromatic";
    public static final String OTHER_STR = "other";
    public static final HarmonicaType DIATONIC;
    public static final HarmonicaType CHROMATIC;
    public static final HarmonicaType OTHER;
    private String m_typeString;
    private String m_localizedName;
    
    static {
        DIATONIC = new HarmonicaType("diatonic");
        CHROMATIC = new HarmonicaType("chromatic");
        OTHER = new HarmonicaType("other");
    }
    
    public static HarmonicaType parseLocalizedName(final String localizedName) {
        if (localizedName.equals(HarmonicaType.DIATONIC.getLocalizedName())) {
            return HarmonicaType.DIATONIC;
        }
        if (localizedName.equals(HarmonicaType.CHROMATIC.getLocalizedName())) {
            return HarmonicaType.CHROMATIC;
        }
        if (localizedName.equals(HarmonicaType.OTHER.getLocalizedName())) {
            return HarmonicaType.OTHER;
        }
        return null;
    }
    
    public static HarmonicaType parseHarmonicaType(final String id) {
        if (id.equals(HarmonicaType.DIATONIC.toString())) {
            return HarmonicaType.DIATONIC;
        }
        if (id.equals(HarmonicaType.CHROMATIC.toString())) {
            return HarmonicaType.CHROMATIC;
        }
        if (id.equals(HarmonicaType.OTHER.toString())) {
            return HarmonicaType.OTHER;
        }
        return null;
    }
    
    public static String[] getLocalizedNamesList() {
        return new String[] { HarmonicaType.DIATONIC.getLocalizedName(), HarmonicaType.CHROMATIC.getLocalizedName(), HarmonicaType.OTHER.getLocalizedName() };
    }
    
    private HarmonicaType(final String typeString) {
        this.m_typeString = null;
        this.m_localizedName = null;
        this.m_typeString = typeString;
        if (typeString.equals("diatonic")) {
            this.m_localizedName = Localizer.get("N_DIATONIC");
        }
        else if (typeString.equals("chromatic")) {
            this.m_localizedName = Localizer.get("N_CHROMATIC");
        }
        else if (typeString.equals("other")) {
            this.m_localizedName = Localizer.get("N_OTHER");
        }
    }
    
    @Override
    public String toString() {
        return this.m_typeString;
    }
    
    public String getLocalizedName() {
        return this.m_localizedName;
    }
    
    public boolean hasPiston() {
        return this == HarmonicaType.CHROMATIC;
    }
}
