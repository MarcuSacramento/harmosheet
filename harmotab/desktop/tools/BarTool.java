// 
// Decompiled by Procyon v0.5.30
// 

package harmotab.desktop.tools;

import harmotab.element.Element;
import harmotab.core.RepeatAttribute;
import javax.swing.event.ChangeEvent;
import harmotab.element.TimeSignature;
import harmotab.core.undo.UndoManager;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import javax.swing.JLabel;
import java.awt.Component;
import javax.swing.SpinnerModel;
import javax.swing.SpinnerNumberModel;
import harmotab.core.Localizer;
import harmotab.renderer.LocationItem;
import harmotab.core.Score;
import java.awt.Container;
import javax.swing.JSpinner;
import harmotab.element.Bar;
import javax.swing.event.ChangeListener;
import java.awt.event.ActionListener;

public class BarTool extends Tool implements ActionListener, ChangeListener
{
    private static final long serialVersionUID = 1L;
    private Bar m_bar;
    private ToolButton m_addTimeSignatureButton;
    private ToolToggleButton m_beginningButton;
    private ToolToggleButton m_endingButton;
    private JSpinner m_repeatsSpinner;
    
    public BarTool(final Container container, final Score score, final LocationItem item) {
        super(container, score, item);
        this.m_bar = null;
        this.m_addTimeSignatureButton = null;
        this.m_beginningButton = null;
        this.m_endingButton = null;
        this.m_repeatsSpinner = null;
        this.m_bar = (Bar)item.getElement();
        this.m_addTimeSignatureButton = new ToolButton(Localizer.get("TT_ADD_TIME_SIGNATURE"), (byte)25);
        this.m_beginningButton = new ToolToggleButton(Localizer.get("TT_BEGINNING_OF_PHRASE"), (byte)21);
        this.m_endingButton = new ToolToggleButton(Localizer.get("TT_END_OF_PHRASE"), (byte)22);
        this.m_repeatsSpinner = new JSpinner(new SpinnerNumberModel(1, 1, 32, 1));
        this.m_addTimeSignatureButton.setEnabled(this.m_bar.getTimeSignature() == null);
        this.m_beginningButton.setSelected(this.m_bar.getRepeatAttribute().isBeginning());
        this.m_endingButton.setSelected(this.m_bar.getRepeatAttribute().isEnd());
        this.m_repeatsSpinner.setValue(this.m_bar.getRepeatAttribute().getRepeatTimes());
        this.m_repeatsSpinner.setEnabled(this.m_bar.getRepeatAttribute().isEnd());
        this.add(this.m_addTimeSignatureButton);
        this.addSeparator();
        this.add(this.m_beginningButton);
        this.add(this.m_endingButton);
        this.addSeparator();
        this.add(this.m_repeatsSpinner);
        this.add(new JLabel(" times "));
        this.m_addTimeSignatureButton.addActionListener(this);
        this.m_beginningButton.addActionListener(this);
        this.m_endingButton.addActionListener(this);
        this.m_repeatsSpinner.addChangeListener(this);
    }
    
    @Override
    public void keyTyped(final KeyEvent event) {
    }
    
    @Override
    public void actionPerformed(final ActionEvent event) {
        if (event.getSource() == this.m_addTimeSignatureButton) {
            UndoManager.getInstance().addUndoCommand(this.m_bar.createRestoreCommand(), Localizer.get("TT_ADD_TIME_SIGNATURE"));
            this.m_bar.setTimeSignature(new TimeSignature());
        }
        else if (event.getSource() == this.m_beginningButton) {
            UndoManager.getInstance().addUndoCommand(this.m_bar.createRestoreCommand(), Localizer.get("TT_BEGINNING_OF_PHRASE"));
            this.m_bar.getRepeatAttribute().setBeginning(this.m_beginningButton.isSelected());
        }
        else if (event.getSource() == this.m_endingButton) {
            UndoManager.getInstance().addUndoCommand(this.m_bar.createRestoreCommand(), Localizer.get("TT_END_OF_PHRASE"));
            this.m_bar.getRepeatAttribute().setEnd(this.m_endingButton.isSelected());
        }
        this.validateModifications();
    }
    
    @Override
    public void stateChanged(final ChangeEvent event) {
        if (event.getSource() == this.m_repeatsSpinner) {
            final RepeatAttribute repeat = this.m_bar.getRepeatAttribute();
            UndoManager.getInstance().addUndoCommand(repeat.createRestoreCommand(), Localizer.get("N_REPEAT_TIMES"));
            repeat.setRepeatTimes((int)this.m_repeatsSpinner.getValue());
        }
        this.validateModifications();
    }
    
    private void validateModifications() {
        if (this.m_locationItem.getFlag(4)) {
            UndoManager.getInstance().addUndoCommand(this.getTrack().createRestoreCommand(), Localizer.get("N_BAR"));
            this.getTrack().add(this.m_locationItem.m_elementIndex, this.m_bar);
            this.m_locationItem.setFlag(4, false);
        }
    }
}
