// 
// Decompiled by Procyon v0.5.30
// 

package harmotab.desktop.setupdialog;

import java.awt.event.ActionEvent;
import javax.swing.AbstractAction;
import java.awt.event.ActionListener;
import javax.swing.JLabel;
import java.awt.Component;
import java.awt.LayoutManager;
import javax.swing.JPanel;
import java.awt.FlowLayout;
import javax.swing.JComponent;
import javax.swing.AbstractButton;
import javax.swing.ButtonGroup;
import javax.swing.SpinnerModel;
import javax.swing.SpinnerNumberModel;
import harmotab.core.Figure;
import harmotab.core.Localizer;
import java.awt.Window;
import harmotab.desktop.components.DurationChooser;
import javax.swing.JSpinner;
import harmotab.desktop.components.FigureChooser;
import javax.swing.JRadioButton;
import harmotab.element.Accompaniment;

public class AccompanimentSetupDialog extends ChordSetupDialog
{
    private static final long serialVersionUID = 1L;
    private Accompaniment m_accompaniment;
    private JRadioButton m_figureRhythmicRadio;
    private JRadioButton m_fixedDurationRhytmicRadio;
    private FigureChooser m_figureChooser;
    private JSpinner m_repeatSpinner;
    private DurationChooser m_customDurationChooser;
    
    public AccompanimentSetupDialog(final Window parent, final Accompaniment accompaniment) {
        super(parent, accompaniment.getChord());
        this.m_accompaniment = null;
        this.m_figureRhythmicRadio = null;
        this.m_fixedDurationRhytmicRadio = null;
        this.m_figureChooser = null;
        this.m_repeatSpinner = null;
        this.m_customDurationChooser = null;
        this.m_accompaniment = accompaniment;
        this.create(false);
    }
    
    public AccompanimentSetupDialog(final Window parent, final Accompaniment accompaniment, final boolean showAccompanimentTab) {
        super(parent, accompaniment.getChord());
        this.m_accompaniment = null;
        this.m_figureRhythmicRadio = null;
        this.m_fixedDurationRhytmicRadio = null;
        this.m_figureChooser = null;
        this.m_repeatSpinner = null;
        this.m_customDurationChooser = null;
        this.m_accompaniment = accompaniment;
        this.create(showAccompanimentTab);
    }
    
    private void create(final boolean showAccompanimentTab) {
        this.setTitle(Localizer.get("ET_ACCOMPANIMENT_CHORD_SETUP"));
        final boolean customDuration = this.m_accompaniment.hasCustomDuration();
        this.m_figureChooser = new FigureChooser(customDuration ? new Figure() : this.m_accompaniment.getRepeatedFigure());
        this.m_repeatSpinner = new JSpinner(new SpinnerNumberModel(1, 1, 100, 1));
        if (!customDuration) {
            this.m_repeatSpinner.setValue(this.m_accompaniment.getRepeatTime());
        }
        final ButtonGroup group = new ButtonGroup();
        (this.m_figureRhythmicRadio = new JRadioButton(Localizer.get("ET_FIGURE_BASED_RHYTHMIC"))).setOpaque(false);
        group.add(this.m_figureRhythmicRadio);
        (this.m_fixedDurationRhytmicRadio = new JRadioButton(Localizer.get("ET_FIXED_DURATION"))).setOpaque(false);
        group.add(this.m_fixedDurationRhytmicRadio);
        this.m_fixedDurationRhytmicRadio.setSelected(customDuration);
        this.m_figureRhythmicRadio.setSelected(!customDuration);
        this.m_customDurationChooser = new DurationChooser(this.m_accompaniment.getDuration());
        final SetupCategory rhythmicSetupCategory = new SetupCategory(Localizer.get("ET_RHYTHMIC_SETUP_CATEGORY"));
        final JPanel rhythmicSetupPane = rhythmicSetupCategory.getPanel();
        rhythmicSetupPane.add(this.createSetupSeparator(Localizer.get("ET_FIGURE_BASED_RHYTHMIC_SETUP")));
        rhythmicSetupPane.add(this.createSetupField("", this.m_figureRhythmicRadio));
        final JPanel rhythmicPane = new JPanel(new FlowLayout());
        rhythmicPane.setOpaque(false);
        rhythmicPane.add(this.m_repeatSpinner);
        rhythmicPane.add(new JLabel(" x "));
        rhythmicPane.add(this.m_figureChooser);
        rhythmicSetupPane.add(this.createSetupField(Localizer.get("N_RHYTHMIC"), rhythmicPane));
        rhythmicSetupPane.add(this.createSetupSeparator(Localizer.get("ET_DURATION_BASED_RHYTHMIC_SETUP")));
        rhythmicSetupPane.add(this.createSetupField("", this.m_fixedDurationRhytmicRadio));
        rhythmicSetupPane.add(this.createSetupField(Localizer.get("N_DURATION"), this.m_customDurationChooser));
        this.addSetupCategory(rhythmicSetupCategory);
        final UserActionListener listener = new UserActionListener();
        this.m_fixedDurationRhytmicRadio.addActionListener(listener);
        this.m_figureRhythmicRadio.addActionListener(listener);
        this.update();
        if (showAccompanimentTab) {
            this.displayCategory(rhythmicSetupCategory);
        }
    }
    
    private void update() {
        final boolean fixedDurationMode = this.m_fixedDurationRhytmicRadio.isSelected();
        this.m_figureChooser.setEnabled(!fixedDurationMode);
        this.m_repeatSpinner.setEnabled(!fixedDurationMode);
        this.m_customDurationChooser.setEnabled(fixedDurationMode);
    }
    
    @Override
    protected void discard() {
        super.discard();
    }
    
    @Override
    protected boolean save() {
        if (!super.save()) {
            return false;
        }
        if (this.m_figureRhythmicRadio.isSelected()) {
            this.m_accompaniment.setRhythmic(this.m_figureChooser.getSelectedFigure(), (int)this.m_repeatSpinner.getValue());
        }
        else if (this.m_fixedDurationRhytmicRadio.isSelected()) {
            this.m_accompaniment.setCustomDuration(this.m_customDurationChooser.getDurationValue());
        }
        return true;
    }
    
    private class UserActionListener extends AbstractAction
    {
        private static final long serialVersionUID = 1L;
        
        @Override
        public void actionPerformed(final ActionEvent event) {
            if (event.getSource() == AccompanimentSetupDialog.this.m_fixedDurationRhytmicRadio || event.getSource() == AccompanimentSetupDialog.this.m_figureRhythmicRadio) {
                AccompanimentSetupDialog.this.update();
            }
        }
    }
}
