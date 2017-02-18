// 
// Decompiled by Procyon v0.5.30
// 

package harmotab.desktop;

import res.ResourceLoader;
import javax.swing.ImageIcon;
import java.util.Vector;

public class ActionIcon
{
    public static final byte OPEN = 0;
    public static final byte CLOSE = 1;
    public static final byte SAVE = 2;
    public static final byte SAVE_AS = 3;
    public static final byte NEW = 4;
    public static final byte WIZARD = 5;
    public static final byte PLAY_CHORD = 6;
    public static final byte CONFIGURE = 7;
    public static final byte CONFIGURE_LITTLE = 8;
    public static final byte PRINT = 9;
    public static final byte HELP = 10;
    public static final byte ABOUT = 11;
    public static final byte ADD_BEFORE = 12;
    public static final byte ADD_BEFORE_LITTLE = 13;
    public static final byte ADD_AFTER = 14;
    public static final byte ADD_AFTER_LITTLE = 15;
    public static final byte ADD_LAST = 16;
    public static final byte ADD_LAST_LITTLE = 17;
    public static final byte DELETE = 18;
    public static final byte DELETE_LITTLE = 19;
    public static final byte INSERT = 20;
    public static final byte INSERT_LITTLE = 21;
    public static final byte SCORE_PROPERTIES = 22;
    public static final byte SCORE_PROPERTIES_LITTLE = 23;
    public static final byte EXPORT = 24;
    public static final byte EXPORT_SOUND = 25;
    public static final byte EXPORT_IMAGE = 26;
    public static final byte DROP_DOWN = 27;
    public static final byte RECORD = 28;
    public static final byte EDIT = 29;
    public static final byte UNDO = 30;
    public static final byte UNDO_LITTLE = 31;
    public static final byte REDO = 32;
    public static final byte REDO_LITTLE = 33;
    public static final byte MODEL = 34;
    public static final byte MODEL_LITTLE = 35;
    public static final byte OK = 36;
    public static final byte CANCEL = 37;
    public static final byte NUMBER_OF_ICONS = 38;
    private static Vector<ImageIcon> m_icons;
    
    static {
        (ActionIcon.m_icons = new Vector<ImageIcon>()).setSize(38);
        addIcon((byte)0, "/res/toolbar/document-open.png");
        addIcon((byte)1, "/res/toolbar/dialog-close.png");
        addIcon((byte)2, "/res/toolbar/document-save.png");
        addIcon((byte)3, "/res/toolbar/document-save-as.png");
        addIcon((byte)4, "/res/toolbar/document-new.png");
        addIcon((byte)5, "/res/toolbar/tools-wizard.png");
        addIcon((byte)6, "/res/toolbar/play-chord.png");
        addIcon((byte)7, "/res/toolbar/configure.png");
        addIcon((byte)8, "/res/toolbar/configure-16.png");
        addIcon((byte)9, "/res/toolbar/document-print.png");
        addIcon((byte)10, "/res/toolbar/help-contents.png");
        addIcon((byte)11, "/res/toolbar/help-about.png");
        addIcon((byte)12, "/res/toolbar/edit-table-insert-column-left.png");
        addIcon((byte)14, "/res/toolbar/edit-table-insert-column-right.png");
        addIcon((byte)16, "/res/toolbar/edit-table-insert-row-under.png");
        addIcon((byte)13, "/res/toolbar/edit-table-insert-column-left-16.png");
        addIcon((byte)15, "/res/toolbar/edit-table-insert-column-right-16.png");
        addIcon((byte)17, "/res/toolbar/edit-table-insert-row-under-16.png");
        addIcon((byte)18, "/res/toolbar/edit-delete.png");
        addIcon((byte)19, "/res/toolbar/edit-delete-16.png");
        addIcon((byte)20, "/res/toolbar/insert.png");
        addIcon((byte)22, "/res/toolbar/view-table-of-contents-ltr.png");
        addIcon((byte)23, "/res/toolbar/view-table-of-contents-ltr-16.png");
        addIcon((byte)24, "/res/toolbar/export.png");
        addIcon((byte)25, "/res/toolbar/export-sound.png");
        addIcon((byte)26, "/res/toolbar/export-image.png");
        addIcon((byte)27, "/res/toolbar/arrow-down.png");
        addIcon((byte)28, "/res/toolbar/media-record.png");
        addIcon((byte)29, "/res/toolbar/document-edit.png");
        addIcon((byte)30, "/res/toolbar/edit-undo.png");
        addIcon((byte)31, "/res/toolbar/edit-undo-16.png");
        addIcon((byte)32, "/res/toolbar/edit-redo.png");
        addIcon((byte)33, "/res/toolbar/edit-redo-16.png");
        addIcon((byte)34, "/res/toolbar/tools-wizard.png");
        addIcon((byte)35, "/res/toolbar/tools-wizard-16.png");
        addIcon((byte)36, "/res/toolbar/dialog-ok-apply.png");
        addIcon((byte)37, "/res/toolbar/dialog-cancel.png");
    }
    
    private static void addIcon(final byte id, final String path) {
        final ResourceLoader loader = ResourceLoader.getInstance();
        ActionIcon.m_icons.setElementAt(new ImageIcon(loader.loadImage(path)), id);
    }
    
    public static ImageIcon getIcon(final byte icon) {
        return ActionIcon.m_icons.elementAt(icon);
    }
}
