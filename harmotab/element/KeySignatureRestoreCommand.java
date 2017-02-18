// 
// Decompiled by Procyon v0.5.30
// 

package harmotab.element;

import harmotab.core.undo.RestoreCommand;

class KeySignatureRestoreCommand extends KeySignature implements RestoreCommand
{
    private KeySignature m_saved;
    
    public KeySignatureRestoreCommand(final KeySignature saved) {
        this.m_saved = saved;
        this.m_keySignature = this.m_saved.m_keySignature;
    }
    
    @Override
    public void execute() {
        if (this.m_saved.m_keySignature != this.m_keySignature) {
            this.m_saved.setIndex(this.m_keySignature);
        }
    }
    
    @Override
    public RestoreCommand getInvertCommand() {
        return new KeySignatureRestoreCommand(this.m_saved);
    }
}
