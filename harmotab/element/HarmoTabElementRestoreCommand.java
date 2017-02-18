// 
// Decompiled by Procyon v0.5.30
// 

package harmotab.element;

import harmotab.core.undo.RestoreCommand;

class HarmoTabElementRestoreCommand extends HarmoTabElement implements RestoreCommand
{
    private HarmoTabElement m_saved;
    private RestoreCommand m_superRestoreCommand;
    
    public HarmoTabElementRestoreCommand(final HarmoTabElement saved, final RestoreCommand superRestoreCommand) {
        this.m_superRestoreCommand = null;
        this.m_saved = saved;
        this.m_superRestoreCommand = superRestoreCommand;
        this.m_tab = this.m_saved.m_tab;
    }
    
    @Override
    public void execute() {
        if (this.m_saved.m_tab != this.m_tab) {
            this.m_saved.setTab(this.m_tab);
        }
        this.m_superRestoreCommand.execute();
    }
    
    @Override
    public RestoreCommand getInvertCommand() {
        return new HarmoTabElementRestoreCommand(this.m_saved, this.m_superRestoreCommand.getInvertCommand());
    }
}
