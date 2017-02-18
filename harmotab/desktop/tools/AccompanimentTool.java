// 
// Decompiled by Procyon v0.5.30
// 

package harmotab.desktop.tools;

import javax.swing.event.ChangeEvent;
import harmotab.core.undo.UndoManager;
import java.awt.event.ActionEvent;
import javax.swing.AbstractAction;
import java.awt.event.KeyEvent;
import java.awt.Window;
import harmotab.desktop.setupdialog.AccompanimentSetupDialog;
import javax.swing.event.ChangeListener;
import java.awt.event.ActionListener;
import java.awt.Component;
import javax.swing.SpinnerModel;
import rvt.util.gui.LabelledSpinner;
import harmotab.core.Localizer;
import harmotab.renderer.LocationItem;
import harmotab.core.Score;
import java.awt.Container;
import javax.swing.SpinnerNumberModel;
import javax.swing.JSpinner;
import harmotab.element.Accompaniment;

public class AccompanimentTool extends Tool
{
    private static final long serialVersionUID = 1L;
    private Accompaniment m_accompaniment;
    private ToolButton m_chordEditorButton;
    private JSpinner m_durationSpinner;
    private SpinnerNumberModel m_durationSpinnerModel;
    
    public AccompanimentTool(final Container container, final Score score, final LocationItem item) {
        super(container, score, item);
        this.m_accompaniment = (Accompaniment)item.getElement();
        this.m_chordEditorButton = new ToolButton(Localizer.get("TT_CHORD_SELECTION"), (byte)16, "");
        if (this.m_accompaniment.isOneFigureRepeated()) {
            this.m_durationSpinnerModel = new SpinnerNumberModel(this.m_accompaniment.getRepeatTime(), 1, 100, 1);
            this.m_durationSpinner = new LabelledSpinner("x " + this.m_accompaniment.getRepeatedFigure().getLocalizedName(), this.m_durationSpinnerModel);
        }
        else {
            final float value = this.m_accompaniment.getDuration();
            this.m_durationSpinnerModel = new SpinnerNumberModel(value, 0.0, 100.0, 0.25);
            this.m_durationSpinner = new LabelledSpinner(Localizer.get((value > 1.0f) ? "W_BEATS" : "W_BEAT"), this.m_durationSpinnerModel);
        }
        this.m_chordEditorButton.setText(" " + this.m_accompaniment.getChord().getName() + " ");
        this.add(this.m_chordEditorButton);
        this.addSeparator();
        this.add(this.m_durationSpinner);
        this.m_chordEditorButton.addActionListener(new DisplaySetupAction());
        this.m_durationSpinnerModel.addChangeListener(new DurationChangeAction());
        if (!this.m_accompaniment.getChord().isDefined()) {
            new AccompanimentSetupDialog(null, this.m_accompaniment).setVisible(true);
        }
    }
    
    @Override
    public void keyTyped(final KeyEvent event) {
    }
    
    private class DisplaySetupAction extends AbstractAction
    {
        private static final long serialVersionUID = 1L;
        
        @Override
        public void actionPerformed(final ActionEvent event) {
            if (event.getSource() == AccompanimentTool.this.m_chordEditorButton) {
                UndoManager.getInstance().addUndoCommand(AccompanimentTool.this.m_accompaniment.createRestoreCommand(), Localizer.get("N_ACCOMPANIMENT"));
                new AccompanimentSetupDialog(null, AccompanimentTool.this.m_accompaniment).setVisible(true);
            }
        }
    }
    
    private class DurationChangeAction implements ChangeListener
    {
        @Override
        public void stateChanged(final ChangeEvent event) {
            if (AccompanimentTool.this.m_accompaniment.isOneFigureRepeated()) {
                try {
                    final int duration = AccompanimentTool.this.m_durationSpinnerModel.getNumber().intValue();
                    UndoManager.getInstance().addUndoCommand(AccompanimentTool.this.m_accompaniment.createRestoreCommand(), Localizer.get("N_DURATION"));
                    AccompanimentTool.this.m_accompaniment.setRhythmic(AccompanimentTool.this.m_accompaniment.getRepeatedFigure(), duration);
                }
                catch (IllegalArgumentException e) {
                    AccompanimentTool.this.m_durationSpinnerModel.setValue(AccompanimentTool.this.m_accompaniment.getRepeatTime());
                }
            }
            else {
                try {
                    final float duration2 = AccompanimentTool.this.m_durationSpinnerModel.getNumber().floatValue();
                    UndoManager.getInstance().addUndoCommand(AccompanimentTool.this.m_accompaniment.createRestoreCommand(), Localizer.get("N_DURATION"));
                    AccompanimentTool.this.m_accompaniment.setCustomDuration(duration2);
                }
                catch (IllegalArgumentException e) {
                    AccompanimentTool.this.m_durationSpinnerModel.setValue(AccompanimentTool.this.m_accompaniment.getDuration());
                }
            }
        }
    }
}
