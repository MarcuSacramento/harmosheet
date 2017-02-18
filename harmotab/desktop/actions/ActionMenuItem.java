// 
// Decompiled by Procyon v0.5.30
// 

package harmotab.desktop.actions;

import java.awt.event.ActionListener;
import javax.swing.Icon;
import javax.swing.JMenuItem;

public class ActionMenuItem extends JMenuItem
{
    private static final long serialVersionUID = 1L;
    
    public ActionMenuItem(final UserAction action) {
        this.setText(action.getLabel());
        this.setIcon(action.getLittleIcon());
        this.addActionListener(action);
    }
}
