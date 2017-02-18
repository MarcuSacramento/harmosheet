// 
// Decompiled by Procyon v0.5.30
// 

package harmotab.desktop.components;

import harmotab.core.Localizer;
import javax.swing.JCheckBox;

public class PushedChooser extends JCheckBox
{
    private static final long serialVersionUID = 1L;
    
    public PushedChooser(final boolean pushed) {
        super(Localizer.get("N_PUSHED"));
        this.setSelected(pushed);
        this.setOpaque(false);
    }
}
