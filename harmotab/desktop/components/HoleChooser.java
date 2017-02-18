// 
// Decompiled by Procyon v0.5.30
// 

package harmotab.desktop.components;

import java.awt.Dimension;
import javax.swing.JComponent;
import javax.swing.SpinnerModel;
import javax.swing.SpinnerNumberModel;
import rvt.util.gui.LabelledSpinner;

public class HoleChooser extends LabelledSpinner
{
    private static final long serialVersionUID = 1L;
    private static final int NUMBER_OF_COLUMNS = 2;
    private HoleChooserEditor m_editor;
    
    public HoleChooser(final byte hole) {
        super("", new SpinnerNumberModel(hole, 0, 50, 1), 2);
        this.m_editor = null;
        this.setEditor(this.m_editor = new HoleChooserEditor(""));
    }
    
    class HoleChooserEditor extends LabelledSpinnerEditor
    {
        private static final long serialVersionUID = 1L;
        
        HoleChooserEditor(final String label) {
            super(label);
            this.setPreferredSize(new Dimension(40, 25));
        }
        
        @Override
        public void updateView() {
            super.updateView();
            final Integer value = (Integer)HoleChooser.this.m_labelledSpinnerInstance.getValue();
            if (value == 0) {
                this.m_textField.setText("");
            }
        }
    }
}
