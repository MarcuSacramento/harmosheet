// 
// Decompiled by Procyon v0.5.30
// 

package harmotab.desktop.components;

import javax.swing.SpinnerModel;
import javax.swing.SpinnerNumberModel;
import javax.swing.JSpinner;

public class NumberOfHolesChooser extends JSpinner
{
    private static final long serialVersionUID = 1L;
    
    public NumberOfHolesChooser(final int value) {
        super(new SpinnerNumberModel(value, 4, 50, 1));
        this.setOpaque(false);
    }
    
    public int getNumberOfHoles() {
        return (int)this.getValue();
    }
}
