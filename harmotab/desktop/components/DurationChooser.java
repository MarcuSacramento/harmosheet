// 
// Decompiled by Procyon v0.5.30
// 

package harmotab.desktop.components;

import harmotab.core.Duration;
import javax.swing.SpinnerModel;
import javax.swing.SpinnerNumberModel;
import harmotab.core.Localizer;
import rvt.util.gui.LabelledSpinner;

public class DurationChooser extends LabelledSpinner
{
    private static final long serialVersionUID = 1L;
    
    public DurationChooser(final float duration) {
        super(Localizer.get("W_BEATS"), new SpinnerNumberModel(duration, 0.0, 100.0, 0.25));
        this.setOpaque(false);
    }
    
    public DurationChooser(final Duration duration) {
        this(duration.getDuration());
    }
    
    public float getDurationValue() {
        return ((SpinnerNumberModel)this.getModel()).getNumber().floatValue();
    }
}
