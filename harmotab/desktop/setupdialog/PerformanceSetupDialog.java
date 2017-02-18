// 
// Decompiled by Procyon v0.5.30
// 

package harmotab.desktop.setupdialog;

import java.awt.Component;
import harmotab.desktop.ErrorMessenger;
import javax.swing.JPanel;
import javax.swing.JComponent;
import harmotab.core.Localizer;
import java.awt.Window;
import harmotab.desktop.components.HarmonicaTunningChooser;
import javax.swing.JTextField;
import harmotab.performance.Performance;

public class PerformanceSetupDialog extends SetupDialog
{
    private static final long serialVersionUID = 1L;
    protected Performance m_performance;
    protected JTextField m_recordNameField;
    protected JTextField m_harmonicaName;
    protected HarmonicaTunningChooser m_harmonicaTunningCombo;
    
    public PerformanceSetupDialog(final Window parent, final Performance performance) {
        super(parent, Localizer.get("N_RECORDING"));
        this.m_performance = null;
        this.m_recordNameField = null;
        this.m_harmonicaName = null;
        this.m_harmonicaTunningCombo = null;
        this.m_performance = performance;
        this.m_recordNameField = new JTextField(this.m_performance.getName());
        this.m_harmonicaName = new JTextField(this.m_performance.getHarmonica().getName());
        this.m_harmonicaTunningCombo = new HarmonicaTunningChooser(this.m_performance.getHarmonica().getTunning());
        final SetupCategory performanceSetupCategory = new SetupCategory(Localizer.get("N_RECORDING"));
        final JPanel performanceSetupPane = performanceSetupCategory.getPanel();
        performanceSetupPane.add(this.createSetupSeparator(Localizer.get("N_RECORDING")));
        performanceSetupPane.add(this.createSetupField(Localizer.get("N_NAME"), this.m_recordNameField));
        performanceSetupPane.add(this.createSetupSeparator(Localizer.get("N_HARMONICA")));
        performanceSetupPane.add(this.createSetupField(Localizer.get("N_NAME"), this.m_harmonicaName));
        performanceSetupPane.add(this.createSetupField(Localizer.get("N_TONALITY"), this.m_harmonicaTunningCombo));
        this.addSetupCategory(performanceSetupCategory);
    }
    
    public Performance getPerformance() {
        return this.m_performance;
    }
    
    @Override
    protected boolean save() {
        final String name = this.m_recordNameField.getText().trim();
        if (name.equals("")) {
            ErrorMessenger.showErrorMessage(this, Localizer.get("M_NO_NAME_ERROR"));
            this.m_recordNameField.requestFocus();
            return false;
        }
        this.m_performance.setName(name);
        this.m_performance.getHarmonica().setName(this.m_harmonicaName.getText());
        this.m_performance.getHarmonica().setTunning(this.m_harmonicaTunningCombo.getSelectedTunning());
        return true;
    }
    
    @Override
    protected void discard() {
    }
}
