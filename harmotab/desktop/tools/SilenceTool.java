// 
// Decompiled by Procyon v0.5.30
// 

package harmotab.desktop.tools;

import harmotab.core.Localizer;
import harmotab.core.undo.UndoManager;
import javax.swing.event.ChangeEvent;
import java.awt.event.KeyEvent;
import javax.swing.event.ChangeListener;
import java.awt.Component;
import harmotab.renderer.LocationItem;
import harmotab.core.Score;
import java.awt.Container;
import harmotab.desktop.components.DurationChooser;
import harmotab.element.Silence;

public class SilenceTool extends Tool
{
    private static final long serialVersionUID = 1L;
    private Silence m_silence;
    private DurationChooser m_durationChooser;
    
    public SilenceTool(final Container container, final Score score, final LocationItem item) {
        super(container, score, item);
        this.m_silence = null;
        this.m_durationChooser = null;
        this.m_silence = (Silence)item.getElement();
        this.add(this.m_durationChooser = new DurationChooser(this.m_silence.getDuration()));
        this.m_durationChooser.addChangeListener(new DurationChangeAction((DurationChangeAction)null));
    }
    
    @Override
    public void keyTyped(final KeyEvent event) {
    }
    
    private class DurationChangeAction implements ChangeListener
    {
        @Override
        public void stateChanged(final ChangeEvent event) {
            UndoManager.getInstance().addUndoCommand(SilenceTool.this.m_silence.createRestoreCommand(), Localizer.get("N_DURATION"));
            SilenceTool.this.m_silence.setDuration(SilenceTool.this.m_durationChooser.getDurationValue());
        }
    }
}
