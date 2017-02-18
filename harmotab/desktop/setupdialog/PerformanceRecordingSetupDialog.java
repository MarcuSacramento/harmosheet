// 
// Decompiled by Procyon v0.5.30
// 

package harmotab.desktop.setupdialog;

import javax.swing.JPanel;
import javax.swing.JComponent;
import harmotab.core.Localizer;
import harmotab.sound.PcmRecorder;
import harmotab.performance.Performance;
import java.awt.Window;
import harmotab.sound.Recorder;
import harmotab.desktop.components.InputLevelViewer;

public class PerformanceRecordingSetupDialog extends PerformanceSetupDialog
{
    private static final long serialVersionUID = 1L;
    protected InputLevelViewer m_levelViewer;
    protected Recorder m_recorder;
    
    public PerformanceRecordingSetupDialog(final Window parent) {
        super(parent, new Performance());
        this.m_levelViewer = null;
        this.m_recorder = null;
        this.m_recorder = new PcmRecorder();
        this.m_levelViewer = new InputLevelViewer(this.m_recorder);
        final SetupCategory recordSetupCategory = new SetupCategory(Localizer.get("N_RECORDING"));
        final JPanel recordSetupPane = recordSetupCategory.getPanel();
        recordSetupPane.add(this.createSetupSeparator(Localizer.get("N_RECORDING_PARAMETERS")));
        recordSetupPane.add(this.createSetupField(Localizer.get("N_RECORDING_INPUT"), this.m_levelViewer));
        this.addSetupCategory(recordSetupCategory);
        this.m_levelViewer.start();
    }
    
    public Recorder getRecorder() {
        return this.m_recorder;
    }
    
    @Override
    public Performance getPerformance() {
        return this.m_performance;
    }
    
    @Override
    protected boolean save() {
        if (super.save()) {
            this.m_levelViewer.stop();
            return true;
        }
        return false;
    }
    
    @Override
    protected void discard() {
        this.m_levelViewer.stop();
        this.m_recorder = null;
        this.m_performance = null;
        super.discard();
    }
}
