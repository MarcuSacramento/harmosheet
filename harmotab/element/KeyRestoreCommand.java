// 
// Decompiled by Procyon v0.5.30
// 

package harmotab.element;

import harmotab.core.undo.RestoreCommand;

class KeyRestoreCommand extends Key implements RestoreCommand
{
    private Key m_saved;
    
    public KeyRestoreCommand(final Key saved) {
        this.m_saved = saved;
        this.m_key = this.m_saved.m_key;
    }
    
    @Override
    public void execute() {
        if (this.m_saved.m_key != this.m_key) {
            this.m_saved.setValue(this.m_key);
        }
    }
    
    @Override
    public RestoreCommand getInvertCommand() {
        return new KeyRestoreCommand(this.m_saved);
    }
}
