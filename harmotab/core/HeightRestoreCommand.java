// 
// Decompiled by Procyon v0.5.30
// 

package harmotab.core;

import harmotab.core.undo.RestoreCommand;

class HeightRestoreCommand extends Height implements RestoreCommand
{
    private Height m_saved;
    
    public HeightRestoreCommand(final Height saved) {
        this.m_saved = saved;
        this.m_note = saved.m_note;
        this.m_octave = saved.m_octave;
        this.m_alteration = saved.m_alteration;
    }
    
    @Override
    public void execute() {
        if (this.m_saved.m_note != this.m_note) {
            this.m_saved.setNote(this.m_note);
        }
        if (this.m_saved.m_octave != this.m_octave) {
            this.m_saved.setOctave(this.m_octave);
        }
        if (this.m_saved.m_alteration != this.m_alteration) {
            this.m_saved.setAlteration(this.m_alteration);
        }
    }
    
    @Override
    public RestoreCommand getInvertCommand() {
        return new HeightRestoreCommand(this.m_saved);
    }
}
