// 
// Decompiled by Procyon v0.5.30
// 

package harmotab.desktop.components;

import harmotab.harmonica.HarmonicaType;
import javax.swing.JComboBox;

public class HarmonicaTypeChooser extends JComboBox
{
    private static final long serialVersionUID = 1L;
    
    public HarmonicaTypeChooser(final HarmonicaType selected) {
        super(HarmonicaType.getLocalizedNamesList());
        this.setSelectedHarmonicaType(selected);
        this.setOpaque(false);
    }
    
    public HarmonicaType getSelectedHarmonicaType() {
        final String selected = (String)this.getSelectedItem();
        if (selected == null) {
            return null;
        }
        return HarmonicaType.parseLocalizedName(selected);
    }
    
    public void setSelectedHarmonicaType(final HarmonicaType selected) {
        this.setSelectedItem(selected.getLocalizedName());
    }
}
