// 
// Decompiled by Procyon v0.5.30
// 

package harmotab.element;

import harmotab.core.Duration;
import java.util.Vector;
import harmotab.core.undo.RestoreCommand;

class AccompanimentRestoreCommand extends Accompaniment implements RestoreCommand
{
    private Accompaniment m_saved;
    
    public AccompanimentRestoreCommand(final Accompaniment saved) {
        this.m_saved = saved;
        this.m_chord = this.m_saved.m_chord;
        this.m_rhythmic = (Vector<Duration>)this.m_saved.m_rhythmic.clone();
        this.m_hasCustomDuration = this.m_saved.m_hasCustomDuration;
    }
    
    @Override
    public void execute() {
        if (this.m_saved.m_chord != this.m_chord) {
            this.m_saved.setChord(this.m_chord);
        }
        this.m_saved.m_rhythmic = this.m_rhythmic;
        this.m_saved.m_hasCustomDuration = this.m_hasCustomDuration;
    }
    
    @Override
    public RestoreCommand getInvertCommand() {
        return new AccompanimentRestoreCommand(this.m_saved);
    }
}
