// 
// Decompiled by Procyon v0.5.30
// 

package harmotab.element;

import harmotab.core.undo.RestoreCommand;

class SilenceRestoreCommand extends Silence implements RestoreCommand
{
    private float savedDuration;
    private Silence m_saved;
    
    public SilenceRestoreCommand(final Silence saved) {
        this.m_saved = saved;
        this.savedDuration = this.m_saved.m_duration.getDuration();
    }
    
    @Override
    public void execute() {
        this.m_saved.setDuration(this.savedDuration);
    }
    
    @Override
    public RestoreCommand getInvertCommand() {
        return new SilenceRestoreCommand(this.m_saved);
    }
}
