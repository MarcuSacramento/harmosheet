// 
// Decompiled by Procyon v0.5.30
// 

package harmotab.desktop.browser;

import java.net.URISyntaxException;
import harmotab.desktop.ErrorMessenger;
import java.net.URI;
import java.awt.Desktop;
import javax.swing.event.HyperlinkEvent;
import java.io.IOException;
import harmotab.HarmoTabConstants;
import java.awt.Dimension;
import harmotab.core.Localizer;
import javax.swing.event.HyperlinkListener;
import java.awt.Component;
import java.awt.LayoutManager;
import java.awt.BorderLayout;
import javax.swing.JEditorPane;

public class SoftwarePane extends Browser
{
    private static final long serialVersionUID = 1L;
    private JEditorPane m_htmlContent;
    
    public SoftwarePane() {
        this.m_htmlContent = null;
        (this.m_htmlContent = new JEditorPane()).setOpaque(false);
        this.m_htmlContent.setEditable(false);
        this.setLayout(new BorderLayout());
        this.add(this.m_htmlContent, "Center");
        this.m_htmlContent.addHyperlinkListener(new LinkActionObserver());
        this.setOpaque(false);
        this.m_htmlContent.setText(Localizer.get("ET_LOADING"));
        this.m_htmlContent.setPreferredSize(new Dimension(150, 300));
        new GetHtmlContentAction().start();
    }
    
    private class GetHtmlContentAction extends Thread
    {
        @Override
        public void run() {
            try {
                SoftwarePane.this.m_htmlContent.setPage(HarmoTabConstants.HT_WELCOME_PAGE);
            }
            catch (IOException e) {
                SoftwarePane.this.remove(SoftwarePane.this.m_htmlContent);
                SoftwarePane.this.repaint();
            }
        }
    }
    
    private class LinkActionObserver implements HyperlinkListener
    {
        @Override
        public void hyperlinkUpdate(final HyperlinkEvent event) {
            final HyperlinkEvent.EventType eventType = event.getEventType();
            if (eventType == HyperlinkEvent.EventType.ACTIVATED) {
                try {
                    Desktop.getDesktop().browse(new URI(event.getURL().toString()));
                }
                catch (IOException e) {
                    e.printStackTrace();
                    ErrorMessenger.showErrorMessage(Localizer.get("M_NO_DEFAULT_WEB_BROWSER"));
                }
                catch (URISyntaxException e2) {
                    e2.printStackTrace();
                }
            }
        }
    }
}
