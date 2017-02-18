// 
// Decompiled by Procyon v0.5.30
// 

package harmotab.desktop.tools;

import harmotab.core.Localizer;
import harmotab.core.undo.UndoManager;
import javax.swing.event.ChangeEvent;
import java.awt.event.KeyEvent;
import java.awt.Component;
import javax.swing.SpinnerModel;
import javax.swing.SpinnerNumberModel;
import harmotab.renderer.LocationItem;
import harmotab.core.Score;
import java.awt.Container;
import javax.swing.JSpinner;
import harmotab.element.Tempo;
import javax.swing.event.ChangeListener;

public class TempoTool extends Tool implements ChangeListener
{
    private static final long serialVersionUID = 1L;
    private Tempo m_tempo;
    private JSpinner m_tempoSpinner;
    
    public TempoTool(final Container container, final Score score, final LocationItem item) {
        super(container, score, item);
        this.m_tempo = (Tempo)item.getElement();
        this.add(this.m_tempoSpinner = new JSpinner(new SpinnerNumberModel(this.m_tempo.getValue(), 10, 500, 1)));
        this.m_tempoSpinner.addChangeListener(this);
    }
    
    @Override
    public void keyTyped(final KeyEvent event) {
    }
    
    @Override
    public void stateChanged(final ChangeEvent event) {
        if (event.getSource() == this.m_tempoSpinner) {
            UndoManager.getInstance().addUndoCommand(this.m_tempo.createRestoreCommand(), Localizer.get("N_TEMPO"));
            this.m_tempo.setValue((int)this.m_tempoSpinner.getValue());
        }
    }
}
