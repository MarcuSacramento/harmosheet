// 
// Decompiled by Procyon v0.5.30
// 

package harmotab.performance;

import harmotab.core.undo.RestoreCommand;

class PerformanceRestoreCommand extends Performance implements RestoreCommand
{
    private Performance m_saved;
    
    public PerformanceRestoreCommand(final Performance saved) {
        this.m_saved = saved;
        this.m_file = this.m_saved.m_file;
        this.m_name = this.m_saved.m_name;
        this.m_harmonica = this.m_saved.m_harmonica;
    }
    
    @Override
    public void execute() {
        if (this.m_saved.m_file != this.m_file) {
            this.m_saved.setFile(this.m_file);
        }
        if (this.m_saved.m_name != this.m_name) {
            this.m_saved.setName(this.m_name);
        }
        if (this.m_saved.m_harmonica != this.m_harmonica) {
            this.m_saved.setHarmonica(this.m_harmonica);
        }
    }
    
    @Override
    public RestoreCommand getInvertCommand() {
        return new PerformanceRestoreCommand(this.m_saved);
    }
}
