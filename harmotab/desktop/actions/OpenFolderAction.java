// 
// Decompiled by Procyon v0.5.30
// 

package harmotab.desktop.actions;

import harmotab.desktop.browser.BrowsersPane;
import harmotab.desktop.browser.LocalBrowser;
import harmotab.desktop.DesktopController;
import java.awt.Component;
import javax.swing.JFileChooser;
import harmotab.core.GlobalPreferences;
import harmotab.desktop.ActionIcon;
import harmotab.core.Localizer;

public class OpenFolderAction extends UserAction
{
    private static final long serialVersionUID = 1L;
    
    public OpenFolderAction() {
        super(Localizer.get("MENU_OPEN_FOLDER"), ActionIcon.getIcon((byte)0));
    }
    
    @Override
    public void run() {
        final JFileChooser chooser = new JFileChooser(GlobalPreferences.getScoresBrowsingFolder());
        chooser.setFileSelectionMode(1);
        if (chooser.showOpenDialog(null) == 0) {
            final String folder = chooser.getSelectedFile().getAbsolutePath();
            final BrowsersPane bp = DesktopController.getInstance().getBrowsersPane();
            ((LocalBrowser)bp.getLocalBrowser()).setFolder(folder);
            bp.setSelectedComponent(bp.getLocalBrowser());
        }
    }
}
