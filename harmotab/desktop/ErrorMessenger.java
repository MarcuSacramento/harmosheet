// 
// Decompiled by Procyon v0.5.30
// 

package harmotab.desktop;

import javax.swing.JOptionPane;
import java.awt.Component;

public class ErrorMessenger
{
    private static boolean m_guiMode;
    
    static {
        ErrorMessenger.m_guiMode = true;
    }
    
    public static void setGuiMode() {
        ErrorMessenger.m_guiMode = true;
    }
    
    public static void setConsoleMode() {
        ErrorMessenger.m_guiMode = false;
    }
    
    public static final void showErrorMessage(final String msg) {
        if (ErrorMessenger.m_guiMode) {
            showErrorMessageDialog(DesktopController.getInstance().getGuiWindow(), msg);
        }
        else {
            showConsoleErrorMessage(msg);
        }
    }
    
    public static final void showErrorMessage(final Component parent, final String msg) {
        showErrorMessageDialog(DesktopController.getInstance().getGuiWindow(), msg);
    }
    
    private static final void showErrorMessageDialog(final Component parent, final String msg) {
        JOptionPane.showMessageDialog(parent, msg, "HarmoTab", 0);
    }
    
    private static final void showConsoleErrorMessage(final String msg) {
        System.err.println(msg);
    }
}
