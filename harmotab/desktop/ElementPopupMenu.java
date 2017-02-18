// 
// Decompiled by Procyon v0.5.30
// 

package harmotab.desktop;

import harmotab.track.Track;
import harmotab.desktop.actions.UserAction;
import harmotab.desktop.actions.ActionMenuItem;
import harmotab.desktop.actions.DeleteAction;
import java.awt.Point;
import java.awt.Component;
import javax.swing.JMenuItem;
import javax.swing.JMenu;
import harmotab.renderer.LocationItem;
import harmotab.core.Score;
import javax.swing.JPopupMenu;

public class ElementPopupMenu extends JPopupMenu
{
    private static final long serialVersionUID = 1L;
    private Score m_score;
    private LocationItem m_item;
    private JMenu m_insertBeforeMenu;
    private JMenu m_insertAfterMenu;
    private JMenuItem m_deleteMenu;
    
    public ElementPopupMenu(final Component parent, final Point mouseLocation, final Score score, final LocationItem item) {
        this.m_score = null;
        this.m_item = null;
        this.m_insertBeforeMenu = null;
        this.m_insertAfterMenu = null;
        this.m_deleteMenu = null;
        this.m_score = score;
        this.m_item = item;
        final Track track = this.m_score.getTrack(this.m_item.getTrackId());
        this.m_insertBeforeMenu = AddElementMenu.createInsertBefore(track, item.getRootElement());
        this.m_insertAfterMenu = AddElementMenu.createInsertAfter(track, item.getRootElement());
        this.m_deleteMenu = new ActionMenuItem(new DeleteAction());
        this.add(this.m_insertBeforeMenu);
        this.add(this.m_insertAfterMenu);
        this.addSeparator();
        this.add(this.m_deleteMenu);
        this.show(parent, mouseLocation.x, mouseLocation.y);
    }
}
