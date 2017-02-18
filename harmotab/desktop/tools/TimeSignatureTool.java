// 
// Decompiled by Procyon v0.5.30
// 

package harmotab.desktop.tools;

import harmotab.core.Localizer;
import harmotab.core.undo.UndoManager;
import java.awt.event.KeyEvent;
import java.awt.Component;
import harmotab.renderer.LocationItem;
import harmotab.core.Score;
import java.awt.Container;
import harmotab.element.TimeSignature;
import harmotab.desktop.components.TimeSignatureChooser;

public class TimeSignatureTool extends Tool implements TimeSignatureChooser.TimeSignatureListener
{
    private static final long serialVersionUID = 1L;
    private TimeSignature m_timeSignature;
    private TimeSignatureChooser m_timeSignatureChooser;
    
    public TimeSignatureTool(final Container container, final Score score, final LocationItem item) {
        super(container, score, item);
        this.m_timeSignature = null;
        this.m_timeSignatureChooser = null;
        this.m_timeSignature = (TimeSignature)this.m_locationItem.getElement();
        (this.m_timeSignatureChooser = new TimeSignatureChooser(this.m_timeSignature)).addTimeSignatureListener(this);
        this.add(this.m_timeSignatureChooser);
    }
    
    @Override
    public void keyTyped(final KeyEvent event) {
    }
    
    @Override
    public void onNumberChanged(final byte number) {
        UndoManager.getInstance().addUndoCommand(this.m_timeSignature.createRestoreCommand(), Localizer.get("N_TIME_SIGNATURE"));
        this.m_timeSignature.setNumber(number);
    }
    
    @Override
    public void onReferenceChanged(final byte reference) {
        UndoManager.getInstance().addUndoCommand(this.m_timeSignature.createRestoreCommand(), Localizer.get("N_TIME_SIGNATURE"));
        this.m_timeSignature.setReference(reference);
    }
}
