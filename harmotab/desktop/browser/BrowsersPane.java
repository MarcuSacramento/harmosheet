// 
// Decompiled by Procyon v0.5.30
// 

package harmotab.desktop.browser;

import java.awt.Component;
import harmotab.core.Localizer;
import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;
import harmotab.core.ScoreController;
import javax.swing.JTabbedPane;

public class BrowsersPane extends JTabbedPane
{
    private static final long serialVersionUID = 1L;
    private HomeBrowser m_homeBrowser;
    private LocalBrowser m_localBrowser;
    
    public BrowsersPane(final ScoreController controller) {
        this.m_homeBrowser = null;
        this.m_localBrowser = null;
        this.setBorder(new EmptyBorder(5, 5, 5, 5));
        this.m_homeBrowser = new HomeBrowser(controller);
        this.m_localBrowser = new LocalBrowser(controller);
        this.addTab(Localizer.get("ET_HOME_BROWSER"), this.m_homeBrowser);
        this.addTab(Localizer.get("ET_LOCAL_CONTENT"), this.m_localBrowser);
    }
    
    public void setSelectedTab(final Browser browser) {
        this.setSelectedComponent(browser);
    }
    
    public Browser getHomeBrowser() {
        return this.m_homeBrowser;
    }
    
    public Browser getLocalBrowser() {
        return this.m_localBrowser;
    }
}
