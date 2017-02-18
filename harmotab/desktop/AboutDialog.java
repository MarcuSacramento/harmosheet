// 
// Decompiled by Procyon v0.5.30
// 

package harmotab.desktop;

import java.awt.Cursor;
import java.net.URISyntaxException;
import java.io.IOException;
import java.net.URI;
import java.awt.Desktop;
import java.awt.event.MouseEvent;
import java.awt.event.ActionEvent;
import javax.swing.AbstractAction;
import java.awt.event.MouseListener;
import java.awt.event.ActionListener;
import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;
import java.awt.BorderLayout;
import javax.swing.JButton;
import java.awt.FlowLayout;
import javax.swing.Icon;
import java.awt.Component;
import javax.swing.JLabel;
import harmotab.HarmoTabConstants;
import java.awt.LayoutManager;
import java.awt.Container;
import javax.swing.BoxLayout;
import javax.swing.JPanel;
import java.awt.Dialog;
import harmotab.core.Localizer;
import java.awt.Window;
import javax.swing.JDialog;

public class AboutDialog extends JDialog
{
    private static final long serialVersionUID = 1L;
    private Window m_window;
    
    public AboutDialog() {
        super((Window)null, Localizer.get("ET_ABOUT"));
        this.m_window = null;
        this.setDefaultCloseOperation(2);
        this.setModalityType(ModalityType.APPLICATION_MODAL);
        this.m_window = this;
        final JPanel descriptionPane = new JPanel();
        descriptionPane.setLayout(new BoxLayout(descriptionPane, 3));
        final JLabel descriptionLabel = new JLabel("<html>\t<h2>" + Localizer.get("M_ABOUT_SOFTWARE_TITLE").replace("%VERSION%", HarmoTabConstants.getVersionString()) + "</h2>" + "\t<p>" + Localizer.get("M_ABOUT_SOFTWARE_DESC") + "</p>" + "\t<p>&nbsp;</p>" + "<html>");
        final JLabel linkLabel = new JLabel("<html>\t<p>" + Localizer.get("M_ABOUT_WEBSITE_DESC") + "<br>" + "\t<a href=\"" + Localizer.get("URL_WEBSITE") + "\">" + Localizer.get("URL_WEBSITE") + "</a></p>" + "<html>");
        descriptionPane.add(descriptionLabel);
        descriptionPane.add(linkLabel);
        final JLabel iconLabel = new JLabel(GuiIcon.getIcon((byte)12));
        final JPanel iconPane = new JPanel(new FlowLayout(3));
        iconPane.add(iconLabel);
        final JButton closeButton = new JButton(Localizer.get("ET_CLOSE_DIALOG"));
        final JPanel buttonPane = new JPanel(new FlowLayout(2, 0, 0));
        buttonPane.add(closeButton);
        final JPanel contentPane = (JPanel)this.getContentPane();
        contentPane.setLayout(new BorderLayout(20, 20));
        contentPane.setBorder(new EmptyBorder(20, 20, 20, 20));
        contentPane.add(iconPane, "West");
        contentPane.add(descriptionPane, "Center");
        contentPane.add(buttonPane, "South");
        closeButton.addActionListener(new CloseAction((CloseAction)null));
        linkLabel.addMouseListener(new WebSiteAction((WebSiteAction)null));
        this.pack();
        this.setLocationRelativeTo(this.getParent());
        this.setVisible(true);
    }
    
    private class CloseAction extends AbstractAction
    {
        private static final long serialVersionUID = 1L;
        
        @Override
        public void actionPerformed(final ActionEvent event) {
            AboutDialog.this.dispose();
        }
    }
    
    private class WebSiteAction implements MouseListener
    {
        @Override
        public void mouseClicked(final MouseEvent event) {
            try {
                Desktop.getDesktop().browse(new URI(Localizer.get("URL_WEBSITE")));
            }
            catch (IOException e) {
                e.printStackTrace();
                ErrorMessenger.showErrorMessage(AboutDialog.this.m_window, Localizer.get("M_NO_DEFAULT_WEB_BROWSER"));
            }
            catch (URISyntaxException e2) {
                e2.printStackTrace();
            }
        }
        
        @Override
        public void mouseEntered(final MouseEvent event) {
            AboutDialog.this.setCursor(new Cursor(12));
        }
        
        @Override
        public void mouseExited(final MouseEvent event) {
            AboutDialog.this.setCursor(new Cursor(0));
        }
        
        @Override
        public void mousePressed(final MouseEvent event) {
        }
        
        @Override
        public void mouseReleased(final MouseEvent event) {
        }
    }
}
