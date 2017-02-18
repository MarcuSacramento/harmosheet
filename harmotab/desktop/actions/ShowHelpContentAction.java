// 
// Decompiled by Procyon v0.5.30
// 

package harmotab.desktop.actions;

import java.net.URISyntaxException;
import java.io.IOException;
import java.awt.Component;
import harmotab.desktop.ErrorMessenger;
import harmotab.desktop.DesktopController;
import java.net.URI;
import java.awt.Desktop;
import harmotab.desktop.ActionIcon;
import harmotab.core.Localizer;

public class ShowHelpContentAction extends UserAction
{
    private static final long serialVersionUID = 1L;
    
    public ShowHelpContentAction() {
        super(Localizer.get("MENU_HELP_CONTENT"), ActionIcon.getIcon((byte)10));
    }
    
    @Override
    public void run() {
        try {
            Desktop.getDesktop().browse(new URI(Localizer.get("URL_WEBSITE")));
        }
        catch (IOException e1) {
            e1.printStackTrace();
            ErrorMessenger.showErrorMessage(DesktopController.getInstance().getGuiWindow(), Localizer.get("M_NO_DEFAULT_WEB_BROWSER"));
        }
        catch (URISyntaxException ex) {}
    }
}
