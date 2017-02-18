// 
// Decompiled by Procyon v0.5.30
// 

package harmotab.element;

import harmotab.core.undo.RestoreCommand;

class LyricsRestoreCommand extends Lyrics implements RestoreCommand
{
    private Lyrics m_saved;
    
    public LyricsRestoreCommand(final Lyrics saved) {
        this.m_saved = saved;
        this.m_text = this.m_saved.m_text;
        this.m_duration = this.m_saved.m_duration;
    }
    
    @Override
    public void execute() {
        if (this.m_saved.m_text != this.m_text) {
            this.m_saved.setText(this.m_text);
        }
        if (this.m_saved.m_duration != this.m_duration) {
            this.m_saved.setDurationObject(this.m_duration);
        }
    }
    
    @Override
    public RestoreCommand getInvertCommand() {
        return new LyricsRestoreCommand(this.m_saved);
    }
}
