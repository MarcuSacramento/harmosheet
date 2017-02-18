// 
// Decompiled by Procyon v0.5.30
// 

package harmotab.core;

import harmotab.core.undo.RestoreCommand;

class RepeatAttributeRestoreCommand extends RepeatAttribute implements RestoreCommand
{
    private RepeatAttribute m_saved;
    
    public RepeatAttributeRestoreCommand(final RepeatAttribute saved) {
        this.m_saved = saved;
        this.m_alternateEnding = this.m_saved.m_alternateEnding;
        this.m_isBeginning = this.m_saved.m_isBeginning;
        this.m_isEnd = this.m_saved.m_isEnd;
        this.m_repeatTimes = this.m_saved.m_repeatTimes;
    }
    
    @Override
    public void execute() {
        if (this.m_saved.m_alternateEnding != this.m_alternateEnding) {
            this.m_saved.setAlternateEnding(this.m_alternateEnding);
        }
        if (this.m_saved.m_isBeginning != this.m_isBeginning) {
            this.m_saved.setBeginning(this.m_isBeginning);
        }
        if (this.m_saved.m_isEnd != this.m_isEnd) {
            this.m_saved.setEnd(this.m_isEnd);
        }
        if (this.m_saved.m_repeatTimes != this.m_repeatTimes) {
            this.m_saved.setRepeatTimes(this.m_repeatTimes);
        }
    }
    
    @Override
    public RestoreCommand getInvertCommand() {
        return new RepeatAttributeRestoreCommand(this.m_saved);
    }
}
