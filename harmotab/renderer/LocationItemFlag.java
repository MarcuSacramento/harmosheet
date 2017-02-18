// 
// Decompiled by Procyon v0.5.30
// 

package harmotab.renderer;

import harmotab.core.Localizer;

public class LocationItemFlag
{
    public static final int ERRORNOUS_ITEM = 0;
    public static final int STAND_ALONE = 1;
    public static final int FORCE_QUEUE_UP = 2;
    public static final int FORCE_QUEUE_DOWN = 3;
    public static final int TEMPORARY_ELEMENT = 4;
    public static final int IMPLICIT_ELEMENT = 5;
    public static final int EXPLICIT_ALTERATION = 6;
    public static final int IMPLICIT_PHRASE_START = 7;
    public static final int IMPLICIT_PHRASE_END = 8;
    public static final int DRAW_REPEAT_SYMBOL = 9;
    public static final int NOT_FILLED_BAR = 10;
    public static final int BAR_EXCEEDED = 11;
    public static final int DEBUG_MARK = 28;
    public static final int RED_MARK = 29;
    public static final int GREEN_MARK = 30;
    
    public static String getErrorFlagToolTip(final LocationItem item) {
        if (item.getFlag(11)) {
            return Localizer.get("M_BAR_EXCEEDED");
        }
        if (item.getFlag(10)) {
            return Localizer.get("M_NOT_FILLED_BAR");
        }
        return "";
    }
}
