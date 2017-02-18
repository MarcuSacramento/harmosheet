// 
// Decompiled by Procyon v0.5.30
// 

package harmotab.element;

import harmotab.core.undo.RestoreCommand;

class TempoRestoreCommand extends Tempo implements RestoreCommand
{
    private Tempo m_saved;
    
    public TempoRestoreCommand(final Tempo saved) {
        this.m_saved = saved;
        this.m_tempo = this.m_saved.m_tempo;
    }
    
    @Override
    public void execute() {
        if (this.m_tempo != this.m_saved.m_tempo) {
            this.m_saved.setValue(this.m_tempo);
        }
    }
    
    @Override
    public RestoreCommand getInvertCommand() {
        return new TempoRestoreCommand(this.m_saved);
    }
}
