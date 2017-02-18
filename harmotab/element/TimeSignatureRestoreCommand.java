// 
// Decompiled by Procyon v0.5.30
// 

package harmotab.element;

import harmotab.core.undo.RestoreCommand;

class TimeSignatureRestoreCommand extends TimeSignature implements RestoreCommand
{
    private TimeSignature m_saved;
    
    public TimeSignatureRestoreCommand(final TimeSignature saved) {
        this.m_saved = saved;
        this.m_number = this.m_saved.m_number;
        this.m_reference = this.m_saved.m_reference;
    }
    
    @Override
    public void execute() {
        if (this.m_number != this.m_saved.m_number) {
            this.m_saved.setNumber(this.m_number);
        }
        if (this.m_reference != this.m_saved.m_reference) {
            this.m_saved.setReference(this.m_reference);
        }
    }
    
    @Override
    public RestoreCommand getInvertCommand() {
        return new TimeSignatureRestoreCommand(this.m_saved);
    }
}
