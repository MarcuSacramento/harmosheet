// 
// Decompiled by Procyon v0.5.30
// 

package harmotab.harmonica;

import harmotab.core.undo.RestoreCommand;

class HarmonicaRestoreCommand extends Harmonica implements RestoreCommand
{
    private Harmonica m_saved;
    
    public HarmonicaRestoreCommand(final Harmonica saved) {
        this.m_saved = saved;
        this.m_name = this.m_saved.m_name;
        this.m_model = this.m_saved.m_model;
        this.m_tunning = this.m_saved.m_tunning;
        this.m_tunningOffset = this.m_saved.m_tunningOffset;
    }
    
    @Override
    public void execute() {
        if (this.m_saved.m_name != this.m_name) {
            this.m_saved.setName(this.m_name);
        }
        if (this.m_saved.m_model != this.m_model) {
            this.m_saved.setModel(this.m_model);
        }
        if (this.m_saved.m_tunning != this.m_tunning) {
            this.m_saved.setTunning(this.m_tunning);
        }
        if (this.m_saved.m_tunningOffset != this.m_tunningOffset) {
            this.m_saved.m_tunningOffset = this.m_tunningOffset;
        }
    }
    
    @Override
    public RestoreCommand getInvertCommand() {
        return new HarmonicaRestoreCommand(this.m_saved);
    }
}
