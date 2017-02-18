// 
// Decompiled by Procyon v0.5.30
// 

package harmotab.desktop.browser;

import java.awt.Component;
import javax.swing.JLabel;
import java.awt.LayoutManager;
import java.awt.BorderLayout;
import harmotab.core.ScoreController;

public class OnlineBrowser extends Browser
{
    private static final long serialVersionUID = 1L;
    
    public OnlineBrowser(final ScoreController controller) {
        this.setLayout(new BorderLayout());
        this.add(new JLabel("<html><i>Incoming feature</i></html>"), "Center");
    }
}
