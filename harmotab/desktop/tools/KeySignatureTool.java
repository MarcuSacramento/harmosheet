// 
// Decompiled by Procyon v0.5.30
// 

package harmotab.desktop.tools;

import harmotab.core.Localizer;
import harmotab.core.undo.UndoManager;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.awt.event.ActionListener;
import java.awt.Component;
import harmotab.renderer.LocationItem;
import harmotab.core.Score;
import java.awt.Container;
import harmotab.desktop.components.TonalityChooser;
import harmotab.element.KeySignature;

public class KeySignatureTool extends Tool
{
    private static final long serialVersionUID = 1L;
    private KeySignature m_keySignature;
    private TonalityChooser m_tonalityChooser;
    
    public KeySignatureTool(final Container container, final Score score, final LocationItem item) {
        super(container, score, item);
        this.m_keySignature = null;
        this.m_tonalityChooser = null;
        this.m_keySignature = (KeySignature)item.getElement();
        this.add(this.m_tonalityChooser = new TonalityChooser(this.m_keySignature.getValue()));
        this.m_tonalityChooser.addActionListener(new TonalityChangesObserver((TonalityChangesObserver)null));
    }
    
    @Override
    public void keyTyped(final KeyEvent event) {
    }
    
    private class TonalityChangesObserver implements ActionListener
    {
        @Override
        public void actionPerformed(final ActionEvent event) {
            UndoManager.getInstance().addUndoCommand(KeySignatureTool.this.m_keySignature.createRestoreCommand(), Localizer.get("N_KEY_SIGNATURE"));
            KeySignatureTool.this.m_keySignature.setIndex(KeySignatureTool.this.m_tonalityChooser.getTonality());
        }
    }
}
