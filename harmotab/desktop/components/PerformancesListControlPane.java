// 
// Decompiled by Procyon v0.5.30
// 

package harmotab.desktop.components;

import harmotab.desktop.setupdialog.SetupDialog;
import harmotab.performance.Performance;
import harmotab.sound.Recorder;
import javax.swing.JOptionPane;
import harmotab.sound.PerformanceScorePlayer;
import harmotab.desktop.DesktopController;
import harmotab.desktop.setupdialog.PerformanceSetupDialog;
import harmotab.desktop.ErrorMessenger;
import java.awt.Window;
import harmotab.desktop.setupdialog.PerformanceRecordingSetupDialog;
import java.awt.event.ActionEvent;
import harmotab.performance.RecordingWorker;
import harmotab.sound.ScorePlayer;
import harmotab.core.Score;
import java.awt.Component;
import harmotab.core.Localizer;
import harmotab.desktop.tools.ToolButton;
import harmotab.performance.PerformancesList;
import harmotab.core.ScoreController;
import harmotab.performance.RecordingListener;
import harmotab.core.ScoreControllerListener;
import java.awt.event.ActionListener;
import javax.swing.JToolBar;

public class PerformancesListControlPane extends JToolBar implements ActionListener, ScoreControllerListener, RecordingListener
{
    private static final long serialVersionUID = 1L;
    protected ScoreController m_scoreController;
    protected PerformancesList m_perfs;
    protected ToolButton m_recordingButton;
    protected PerformancesChooser m_performancesChooser;
    protected ToolButton m_editButton;
    protected ToolButton m_deleteButton;
    
    public PerformancesListControlPane(final ScoreController controller) {
        this.m_perfs = null;
        this.m_recordingButton = null;
        this.m_performancesChooser = null;
        this.m_editButton = null;
        this.m_deleteButton = null;
        this.m_scoreController = controller;
        this.setFloatable(false);
        this.setOpaque(false);
        this.setPerformancesList(controller.getPerformancesList());
        controller.addScoreControllerListener(this);
    }
    
    private void constructGui() {
        this.removeAll();
        if (this.m_perfs != null) {
            (this.m_editButton = new ToolButton(Localizer.get("ET_EDIT_PERFORMANCE"), (byte)29)).setWide(true);
            this.m_editButton.setEnabled(false);
            (this.m_deleteButton = new ToolButton(Localizer.get("ET_DELETE_PERFORMANCE"), (byte)15)).setWide(true);
            this.m_deleteButton.setEnabled(false);
            this.m_performancesChooser = new PerformancesChooser(this.m_perfs, this.m_perfs.getDefaultPerformance());
            (this.m_recordingButton = new ToolButton(Localizer.get("ET_START_RECORD"), (byte)26)).setWide(true);
            this.add(this.m_recordingButton);
            this.addSeparator();
            this.add(this.m_performancesChooser);
            this.add(this.m_editButton);
            this.add(this.m_deleteButton);
            this.addSeparator();
            this.m_editButton.addActionListener(this);
            this.m_deleteButton.addActionListener(this);
            this.m_performancesChooser.addActionListener(this);
            this.m_recordingButton.addActionListener(this);
            this.updateUI();
        }
        else {
            this.m_editButton = null;
            this.m_deleteButton = null;
            this.m_performancesChooser = null;
        }
    }
    
    protected void setPerformancesList(final PerformancesList perfs) {
        this.m_perfs = perfs;
        this.constructGui();
    }
    
    @Override
    public void onControlledScoreChanged(final ScoreController controller, final Score scoreControlled) {
        this.setPerformancesList(controller.getPerformancesList());
    }
    
    @Override
    public void onScorePlayerChanged(final ScoreController controller, final ScorePlayer soundPlayer) {
    }
    
    @Override
    public void onRecordingStarted(final RecordingWorker worker) {
        this.m_recordingButton.setEnabled(false);
        this.m_performancesChooser.setEnabled(false);
        this.m_editButton.setEnabled(false);
    }
    
    @Override
    public void onRecordingStopped(final RecordingWorker worker) {
        this.m_recordingButton.setEnabled(true);
        this.m_performancesChooser.setEnabled(true);
        this.m_editButton.setEnabled(true);
    }
    
    @Override
    public void actionPerformed(final ActionEvent event) {
        final Object source = event.getSource();
        if (source == this.m_recordingButton) {
            final PerformanceRecordingSetupDialog dlg = new PerformanceRecordingSetupDialog((Window)null);
            dlg.setVisible(true);
            final Recorder recorder = dlg.getRecorder();
            final Performance performance = dlg.getPerformance();
            if (recorder != null && performance != null) {
                try {
                    final RecordingWorker worker = new RecordingWorker(this.m_scoreController, recorder, performance);
                    worker.addRecordingListener(this);
                    worker.start();
                }
                catch (Exception e) {
                    ErrorMessenger.showErrorMessage("Recording error !");
                }
            }
        }
        else if (source == this.m_editButton) {
            final Performance selectedPerformance = this.m_performancesChooser.getSelectedPerformance();
            if (selectedPerformance != null) {
                final int selectedIndex = this.m_performancesChooser.getSelectedIndex();
                final SetupDialog dlg2 = new PerformanceSetupDialog(null, selectedPerformance);
                dlg2.setVisible(true);
                this.m_performancesChooser.setSelectedIndex(selectedIndex);
            }
        }
        else if (source == this.m_performancesChooser) {
            final Performance selectedPerformance = this.m_performancesChooser.getSelectedPerformance();
            ScorePlayer player = null;
            if (selectedPerformance != null) {
                final Score score = DesktopController.getInstance().getScoreController().getScore();
                player = new PerformanceScorePlayer(selectedPerformance, score);
            }
            this.m_scoreController.setScorePlayer(player);
            final boolean selectionIsAUserPerformance = selectedPerformance != null;
            this.m_editButton.setEnabled(selectionIsAUserPerformance);
            this.m_deleteButton.setEnabled(selectionIsAUserPerformance);
        }
        else if (source == this.m_deleteButton) {
            final Performance selectedPerformance = this.m_performancesChooser.getSelectedPerformance();
            if (selectedPerformance != null) {
                final int res = JOptionPane.showConfirmDialog(null, Localizer.get("M_DELETE_PERFORMANCE_CONFIRMATION").replace("%PERF%", selectedPerformance.getName()), Localizer.get("ET_DELETE_PERFORMANCE"), 0);
                if (res == 0) {
                    this.m_perfs.remove(selectedPerformance);
                }
            }
        }
    }
}
