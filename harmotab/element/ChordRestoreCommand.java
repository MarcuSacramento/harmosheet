// 
// Decompiled by Procyon v0.5.30
// 

package harmotab.element;

import harmotab.core.Height;
import java.util.LinkedHashSet;
import harmotab.core.undo.RestoreCommand;

class ChordRestoreCommand extends Chord implements RestoreCommand
{
    private Chord m_saved;
    
    public ChordRestoreCommand(final Chord saved) {
        this.m_saved = saved;
        this.m_chordName = this.m_saved.m_chordName;
        this.m_figure = this.m_saved.m_figure;
        this.m_heights = (LinkedHashSet<Height>)this.m_saved.m_heights.clone();
    }
    
    @Override
    public void execute() {
        if (this.m_saved.m_chordName != this.m_chordName) {
            this.m_saved.setName(this.m_chordName);
        }
        if (this.m_saved.m_figure != this.m_figure) {
            this.m_saved.setFigure(this.m_figure);
        }
        this.m_saved.m_heights = this.m_heights;
    }
    
    @Override
    public RestoreCommand getInvertCommand() {
        return new ChordRestoreCommand(this.m_saved);
    }
}
