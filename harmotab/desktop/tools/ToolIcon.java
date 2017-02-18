// 
// Decompiled by Procyon v0.5.30
// 

package harmotab.desktop.tools;

import res.ResourceLoader;
import javax.swing.ImageIcon;
import java.util.Vector;

public class ToolIcon
{
    public static final byte NO_ICON = 0;
    public static final byte ALIGN_LEFT = 1;
    public static final byte ALIGN_CENTER = 2;
    public static final byte ALIGN_RIGHT = 3;
    public static final byte ADD = 4;
    public static final byte UP = 5;
    public static final byte DOWN = 6;
    public static final byte REST = 7;
    public static final byte DOT = 8;
    public static final byte PLAY = 9;
    public static final byte PLAY_LITTLE = 10;
    public static final byte PAUSE = 11;
    public static final byte PAUSE_LITTLE = 12;
    public static final byte STOP = 13;
    public static final byte STOP_LITTLE = 14;
    public static final byte DELETE = 15;
    public static final byte TUNE = 16;
    public static final byte VALID = 17;
    public static final byte CANCEL = 18;
    public static final byte LINK = 19;
    public static final byte TRIPLET = 20;
    public static final byte PHRASE_START = 21;
    public static final byte PHRASE_END = 22;
    public static final byte PLAY_FROM = 23;
    public static final byte PLAY_FROM_LITTLE = 24;
    public static final byte ADD_BAR = 25;
    public static final byte START_RECORD = 26;
    public static final byte STOP_RECORD = 27;
    public static final byte EDITABLE = 28;
    public static final byte EDIT = 29;
    public static final byte NUMBER_OF_ICONS = 30;
    private static Vector<ImageIcon> m_icons;
    
    static {
        (ToolIcon.m_icons = new Vector<ImageIcon>()).setSize(30);
        addIcon((byte)0, "/res/icons/unknown.png");
        addIcon((byte)1, "/res/icons/align-horizontal-left.png");
        addIcon((byte)2, "/res/icons/align-horizontal-center.png");
        addIcon((byte)3, "/res/icons/align-horizontal-right.png");
        addIcon((byte)4, "/res/icons/list-add.png");
        addIcon((byte)5, "/res/icons/arrow-up.png");
        addIcon((byte)6, "/res/icons/arrow-down.png");
        addIcon((byte)7, "/res/icons/ht-rest.png");
        addIcon((byte)8, "/res/icons/ht-dot.png");
        addIcon((byte)9, "/res/icons/media-playback-start.png");
        addIcon((byte)10, "/res/icons/media-playback-start-16.png");
        addIcon((byte)11, "/res/icons/media-playback-pause.png");
        addIcon((byte)12, "/res/icons/media-playback-pause-16.png");
        addIcon((byte)13, "/res/icons/media-playback-stop.png");
        addIcon((byte)14, "/res/icons/media-playback-stop-16.png");
        addIcon((byte)15, "/res/icons/dialog-close.png");
        addIcon((byte)16, "/res/icons/kmix-master.png");
        addIcon((byte)17, "/res/icons/dialog-ok-apply.png");
        addIcon((byte)18, "/res/icons/edit-delete.png");
        addIcon((byte)19, "/res/icons/ht-link.png");
        addIcon((byte)20, "/res/icons/ht-triplet.png");
        addIcon((byte)21, "/res/icons/ht-phrase-start.png");
        addIcon((byte)22, "/res/icons/ht-phrase-end.png");
        addIcon((byte)23, "/res/icons/media-playback-start.png");
        addIcon((byte)24, "/res/icons/media-playback-start-16.png");
        addIcon((byte)25, "/res/icons/add-bar.png");
        addIcon((byte)26, "/res/icons/media-record.png");
        addIcon((byte)27, "/res/icons/media-record-stop.png");
        addIcon((byte)28, "/res/icons/draw-path.png");
        addIcon((byte)29, "/res/icons/document-edit.png");
    }
    
    private static void addIcon(final byte id, final String path) {
        ToolIcon.m_icons.setElementAt(new ImageIcon(ResourceLoader.getInstance().loadImage(path)), id);
    }
    
    public static ImageIcon getIcon(final byte icon) {
        return ToolIcon.m_icons.elementAt(icon);
    }
}
