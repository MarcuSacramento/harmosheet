// 
// Decompiled by Procyon v0.5.30
// 

package harmotab.core;

import harmotab.core.undo.RestoreCommand;

class DurationRestoreCommand extends Duration implements RestoreCommand
{
    private Duration m_saved;
    
    public DurationRestoreCommand(final Duration saved) {
        this.m_saved = saved;
        this.m_duration = this.m_saved.m_duration;
    }
    
    @Override
    public void execute() {
        if (this.m_saved.m_duration != this.m_duration) {
            this.m_saved.setDuration(this.m_duration);
        }
    }
    
    @Override
    public RestoreCommand getInvertCommand() {
        return new DurationRestoreCommand(this.m_saved);
    }
}
