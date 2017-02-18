// 
// Decompiled by Procyon v0.5.30
// 

package harmotab.desktop;

import res.ResourceLoader;
import javax.swing.ImageIcon;
import java.util.Vector;

public class GuiIcon
{
    public static final byte HARMONICA_START = 0;
    public static final byte HARMONICA_END_DIATO = 1;
    public static final byte HARMONICA_END_CHROMA_NATURAL = 2;
    public static final byte HARMONICA_END_CHROMA_PUSHED = 3;
    public static final byte HARMONICA_HOLE = 4;
    public static final byte ALTERATION_NATURAL = 5;
    public static final byte ALTERATION_SHARP = 6;
    public static final byte ALTERATION_FLAT = 7;
    public static final byte ADD = 8;
    public static final byte HARMOTAB_ICON_16 = 9;
    public static final byte HARMOTAB_ICON_32 = 10;
    public static final byte HARMOTAB_ICON_48 = 11;
    public static final byte HARMOTAB_ICON_64 = 12;
    public static final byte HARMOTAB_ICON_96 = 13;
    public static final byte HARMOTAB_ICON_128 = 14;
    public static final byte HARMOTAB_ICON_192 = 15;
    public static final byte NUMBER_OF_ICONS = 16;
    private static Vector<ImageIcon> m_icons;
    
    static {
        (GuiIcon.m_icons = new Vector<ImageIcon>()).setSize(16);
        addIcon((byte)0, "/res/gui/harmo-start.png");
        addIcon((byte)1, "/res/gui/harmo-end-diato.png");
        addIcon((byte)2, "/res/gui/harmo-end-chroma-natural.png");
        addIcon((byte)3, "/res/gui/harmo-end-chroma-pushed.png");
        addIcon((byte)4, "/res/gui/harmo-hole.png");
        addIcon((byte)5, "/res/controllers/natural.png");
        addIcon((byte)6, "/res/controllers/sharp.png");
        addIcon((byte)7, "/res/controllers/flat.png");
        addIcon((byte)8, "/res/icons/list-add.png");
        addIcon((byte)9, "/res/icons/soft/icone2_16.png");
        addIcon((byte)10, "/res/icons/soft/icone_32.png");
        addIcon((byte)11, "/res/icons/soft/icone_48.png");
        addIcon((byte)12, "/res/icons/soft/icone_64.png");
        addIcon((byte)13, "/res/icons/soft/icone_96.png");
        addIcon((byte)14, "/res/icons/soft/icone_128.png");
        addIcon((byte)15, "/res/icons/soft/icone_192.png");
    }
    
    private static void addIcon(final byte id, final String path) {
        GuiIcon.m_icons.setElementAt(new ImageIcon(ResourceLoader.getInstance().loadImage(path)), id);
    }
    
    public static ImageIcon getIcon(final byte icon) {
        return GuiIcon.m_icons.elementAt(icon);
    }
}
