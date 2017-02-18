// 
// Decompiled by Procyon v0.5.30
// 

package harmotab.desktop.components;

import harmotab.core.Height;
import javax.swing.JComboBox;

public class HarmonicaTunningChooser extends JComboBox
{
    private static final long serialVersionUID = 1L;
    
    public HarmonicaTunningChooser() {
        super(Height.getNotesName());
    }
    
    public HarmonicaTunningChooser(final Height tunning) {
        this();
        this.setSelectedTunning(tunning);
    }
    
    public void setSelectedTunning(final Height tunning) {
        this.setSelectedItem(tunning.getNoteName());
    }
    
    public Height getSelectedTunning() {
        return new Height((String)this.getSelectedItem());
    }
}
