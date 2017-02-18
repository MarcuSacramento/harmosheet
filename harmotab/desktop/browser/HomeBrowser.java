// 
// Decompiled by Procyon v0.5.30
// 

package harmotab.desktop.browser;

import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;
import harmotab.core.GlobalPreferences;
import java.awt.Component;
import java.awt.LayoutManager;
import java.awt.Container;
import javax.swing.BoxLayout;
import harmotab.core.ScoreController;

public class HomeBrowser extends Browser
{
    private static final long serialVersionUID = 1L;
    private SoftwarePane m_softwarePane;
    private ActionPane m_actionPane;
    
    public HomeBrowser(final ScoreController controller) {
        this.m_softwarePane = null;
        this.m_actionPane = null;
        this.m_actionPane = new ActionPane(controller);
        this.setLayout(new BoxLayout(this, 3));
        this.add(this.m_actionPane);
        if (GlobalPreferences.isNetworkEnabled()) {
            this.add(this.m_softwarePane = new SoftwarePane());
            this.m_softwarePane.setAlignmentX(0.0f);
        }
        this.m_actionPane.setAlignmentX(0.0f);
        this.setBorder(new EmptyBorder(10, 10, 10, 10));
    }
}
