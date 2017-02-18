// 
// Decompiled by Procyon v0.5.30
// 

package harmotab.desktop;

import harmotab.element.Lyrics;
import harmotab.element.Silence;
import harmotab.element.Accompaniment;
import harmotab.element.Bar;
import harmotab.element.Note;
import harmotab.element.Element;
import res.ResourceLoader;
import javax.swing.ImageIcon;
import java.util.Vector;

public class ElementIcon
{
    public static final byte NOTE = 0;
    public static final byte BAR = 1;
    public static final byte ACCOMPANIMENT = 2;
    public static final byte SILENCE = 3;
    public static final byte LYRICS = 4;
    public static final byte NUMBER_OF_ICONS = 5;
    private static Vector<ImageIcon> m_icons;
    
    static {
        (ElementIcon.m_icons = new Vector<ImageIcon>()).setSize(5);
        addIcon((byte)0, "/res/elements/note-icon.png");
        addIcon((byte)1, "/res/elements/bar-icon.png");
        addIcon((byte)2, "/res/elements/accompaniment-icon.png");
        addIcon((byte)3, "/res/elements/silence-icon.png");
        addIcon((byte)4, "/res/elements/lyrics-icon.png");
    }
    
    private static void addIcon(final byte id, final String path) {
        final ResourceLoader loader = ResourceLoader.getInstance();
        ElementIcon.m_icons.setElementAt(new ImageIcon(loader.loadImage(path)), id);
    }
    
    public static ImageIcon getIcon(final Element element) {
        if (element instanceof Note) {
            return ElementIcon.m_icons.elementAt(0);
        }
        if (element instanceof Bar) {
            return ElementIcon.m_icons.elementAt(1);
        }
        if (element instanceof Accompaniment) {
            return ElementIcon.m_icons.elementAt(2);
        }
        if (element instanceof Silence) {
            return ElementIcon.m_icons.elementAt(3);
        }
        if (element instanceof Lyrics) {
            return ElementIcon.m_icons.elementAt(4);
        }
        return null;
    }
}
