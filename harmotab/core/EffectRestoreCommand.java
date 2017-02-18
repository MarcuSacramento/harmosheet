// 
// Decompiled by Procyon v0.5.30
// 

package harmotab.core;

import harmotab.core.undo.RestoreCommand;

class EffectRestoreCommand extends Effect implements RestoreCommand
{
    private Effect m_saved;
    
    public EffectRestoreCommand(final Effect saved) {
        this.m_saved = saved;
        this.m_effect = this.m_saved.m_effect;
    }
    
    @Override
    public void execute() {
        if (this.m_saved.m_effect != this.m_effect) {
            this.m_saved.setType(this.m_effect);
        }
    }
    
    @Override
    public RestoreCommand getInvertCommand() {
        return new EffectRestoreCommand(this.m_saved);
    }
}
