// 
// Decompiled by Procyon v0.5.30
// 

package harmotab.desktop;

import harmotab.throwables.NotImplementedError;
import harmotab.core.undo.UndoManager;
import java.awt.event.ActionEvent;
import java.util.Iterator;
import javax.swing.ImageIcon;
import java.awt.event.ActionListener;
import javax.swing.JMenuItem;
import harmotab.element.TrackElement;
import javax.swing.Icon;
import harmotab.core.Localizer;
import harmotab.track.Track;
import harmotab.element.Element;
import javax.swing.JMenu;

public class AddElementMenu extends JMenu
{
    private static final long serialVersionUID = 1L;
    private Element m_referenceElement;
    private boolean m_insertAfter;
    private Float m_time;
    private Track m_track;
    
    private AddElementMenu(final String label, final Track track) {
        super(label);
        this.m_referenceElement = null;
        this.m_insertAfter = false;
        this.m_time = null;
        this.m_referenceElement = null;
        this.m_insertAfter = false;
        this.m_time = null;
        this.m_track = track;
    }
    
    public static AddElementMenu createInsertBefore(final Track track, final Element beforeElement) {
        final AddElementMenu menu = new AddElementMenu(Localizer.get("MENU_INSERT_BEFORE"), track);
        menu.setIcon(ActionIcon.getIcon((byte)13));
        menu.setInsertBerfore(beforeElement);
        return menu;
    }
    
    public static AddElementMenu createInsertAfter(final Track track, final Element afterElement) {
        final AddElementMenu menu = new AddElementMenu(Localizer.get("MENU_INSERT_AFTER"), track);
        menu.setIcon(ActionIcon.getIcon((byte)15));
        menu.setInsertAfter(afterElement);
        return menu;
    }
    
    public static AddElementMenu createInsertLast(final Track track) {
        final AddElementMenu menu = new AddElementMenu(Localizer.get("MENU_INSERT_LAST"), track);
        menu.setIcon(ActionIcon.getIcon((byte)17));
        menu.setInsertLast();
        return menu;
    }
    
    public void setReference(final Track track, final Element element) {
        this.m_track = track;
        this.m_referenceElement = element;
    }
    
    private void insertAddMenuItems(final TrackElement element) {
        final JMenuItem menuItem = new JMenuItem(element.getTrackElementLocalizedName());
        menuItem.addActionListener(new AddElementAction(element));
        final ImageIcon icon = ElementIcon.getIcon(element);
        if (icon != null) {
            menuItem.setIcon(icon);
        }
        this.add(menuItem);
    }
    
    protected void setInsertAfter(final Element element) {
        this.m_insertAfter = true;
        this.m_referenceElement = element;
        this.m_time = null;
    }
    
    protected void setInsertBerfore(final Element element) {
        this.m_insertAfter = false;
        this.m_referenceElement = element;
        this.m_time = null;
    }
    
    protected void setInsertAt(final Element element, final float time) {
        this.m_insertAfter = false;
        this.m_referenceElement = element;
        this.m_time = time;
    }
    
    protected void setInsertLast() {
        this.m_referenceElement = null;
        this.m_time = null;
    }
    
    public void populate() {
        this.removeAll();
        if (this.m_track != null) {
            for (final TrackElement e : this.m_track.getAddableElements()) {
                this.insertAddMenuItems(e);
            }
        }
        this.validate();
    }
    
    @Override
    protected void fireMenuSelected() {
        this.populate();
        super.fireMenuSelected();
    }
    
    private class AddElementAction implements ActionListener
    {
        private TrackElement m_trackElement;
        
        public AddElementAction(final TrackElement element) {
            this.m_trackElement = null;
            this.m_trackElement = element;
        }
        
        @Override
        public void actionPerformed(final ActionEvent event) {
            final TrackElement newElement = (TrackElement)this.m_trackElement.clone();
            if (AddElementMenu.this.m_referenceElement == null) {
                UndoManager.getInstance().addUndoCommand(AddElementMenu.this.m_track.createRestoreCommand(), Localizer.get("ACT_ADD_ELEMENT"));
                AddElementMenu.this.m_track.add(newElement);
            }
            else {
                if (AddElementMenu.this.m_time != null) {
                    throw new NotImplementedError("Add to a specific time not implemented");
                }
                int index = AddElementMenu.this.m_track.indexOf(AddElementMenu.this.m_referenceElement);
                if (AddElementMenu.this.m_insertAfter) {
                    ++index;
                }
                UndoManager.getInstance().addUndoCommand(AddElementMenu.this.m_track.createRestoreCommand(), Localizer.get("ACT_INSERT_ELEMENT"));
                AddElementMenu.this.m_track.add(index, newElement);
            }
        }
    }
}
