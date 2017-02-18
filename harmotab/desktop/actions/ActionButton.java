// 
// Decompiled by Procyon v0.5.30
// 

package harmotab.desktop.actions;

import java.awt.event.ActionListener;
import javax.swing.Icon;
import javax.swing.JButton;

public class ActionButton extends JButton
{
    private static final long serialVersionUID = 1L;
    
    public ActionButton(final UserAction action) {
        this.setIcon(action.getIcon());
        this.setText(action.getLabel());
        this.setToolTipText(action.getDescription());
        this.addActionListener(action);
    }
}
