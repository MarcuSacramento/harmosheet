// 
// Decompiled by Procyon v0.5.30
// 

package harmotab.desktop.actions;

import javax.swing.SwingUtilities;
import java.awt.event.ActionEvent;
import javax.swing.ImageIcon;
import javax.swing.AbstractAction;

public abstract class UserAction extends AbstractAction implements Runnable
{
    private static final long serialVersionUID = 1L;
    protected String m_actionLabel;
    protected String m_actionDescription;
    protected ImageIcon m_actionIcon;
    protected ImageIcon m_actionLittleIcon;
    
    public UserAction(final String label, final String description, final ImageIcon icon) {
        this.m_actionLabel = null;
        this.m_actionDescription = null;
        this.m_actionIcon = null;
        this.m_actionLittleIcon = null;
        this.setLabel(label);
        this.setDescription(description);
        this.setIcon(icon);
        this.setLittleIcon(icon);
    }
    
    public UserAction(final String label, final ImageIcon icon) {
        this(label, label, icon);
    }
    
    protected void setLabel(final String label) {
        this.m_actionLabel = label;
    }
    
    public String getLabel() {
        return this.m_actionLabel;
    }
    
    protected void setDescription(final String description) {
        this.m_actionDescription = description;
    }
    
    public String getDescription() {
        return this.m_actionDescription;
    }
    
    protected void setIcon(final ImageIcon icon) {
        this.m_actionIcon = icon;
    }
    
    public ImageIcon getIcon() {
        return this.m_actionIcon;
    }
    
    public void setLittleIcon(final ImageIcon icon) {
        this.m_actionLittleIcon = icon;
    }
    
    public ImageIcon getLittleIcon() {
        return this.m_actionLittleIcon;
    }
    
    @Override
    public void actionPerformed(final ActionEvent event) {
        SwingUtilities.invokeLater(this);
    }
}
