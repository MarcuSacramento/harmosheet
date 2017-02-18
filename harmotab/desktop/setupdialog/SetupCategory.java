// 
// Decompiled by Procyon v0.5.30
// 

package harmotab.desktop.setupdialog;

import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;
import java.awt.LayoutManager;
import rvt.util.gui.VerticalLayout;
import javax.swing.JPanel;

public class SetupCategory
{
    private String m_title;
    private JPanel m_pane;
    
    public SetupCategory(final String title) {
        this.m_title = null;
        this.m_pane = null;
        this.m_title = title;
        (this.m_pane = new JPanel(new VerticalLayout(10, 3))).setBorder(new EmptyBorder(10, 10, 10, 10));
        this.m_pane.setOpaque(false);
    }
    
    public String getTitle() {
        return this.m_title;
    }
    
    public JPanel getPanel() {
        return this.m_pane;
    }
    
    public void setPanel(final JPanel panel) {
        this.m_pane = panel;
    }
}
