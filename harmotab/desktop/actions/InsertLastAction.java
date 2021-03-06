// 
// Decompiled by Procyon v0.5.30
// 

package harmotab.desktop.actions;

import javax.swing.JPopupMenu;
import harmotab.track.Track;
import harmotab.core.ScoreViewSelection;
import harmotab.desktop.AddElementMenu;
import harmotab.desktop.DesktopController;
import javax.swing.JComponent;
import java.awt.event.ActionEvent;
import harmotab.desktop.ActionIcon;
import harmotab.core.Localizer;
import java.awt.Component;

public class InsertLastAction extends UserAction
{
    private static final long serialVersionUID = 1L;
    private int m_displayX;
    private int m_displayY;
    private Component m_source;
    
    public InsertLastAction() {
        super(Localizer.get("MENU_INSERT_LAST"), ActionIcon.getIcon((byte)16));
        this.m_displayX = 0;
        this.m_displayY = 0;
        this.m_source = null;
        this.setLittleIcon(ActionIcon.getIcon((byte)17));
    }
    
    @Override
    public void actionPerformed(final ActionEvent e) {
        this.m_displayX = 0;
        this.m_displayY = ((JComponent)e.getSource()).getHeight();
        this.m_source = (Component)e.getSource();
        super.actionPerformed(e);
    }
    
    @Override
    public void run() {
        final ScoreViewSelection selection = DesktopController.getInstance().getCurrentSelection();
        if (selection != null) {
            final Track track = selection.getTrack();
            final AddElementMenu menu = AddElementMenu.createInsertLast(track);
            menu.populate();
            final JPopupMenu popup = menu.getPopupMenu();
            popup.show(this.m_source, this.m_displayX, this.m_displayY);
        }
    }
}
