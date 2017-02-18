// 
// Decompiled by Procyon v0.5.30
// 

package harmotab.element;

import harmotab.core.undo.RestoreCommand;

class BarRestoreCommand extends Bar implements RestoreCommand
{
    private Bar m_saved;
    
    public BarRestoreCommand(final Bar saved) {
        this.m_saved = saved;
        this.m_key = this.m_saved.m_key;
        this.m_keySignature = this.m_saved.m_keySignature;
        this.m_timeSignature = this.m_saved.m_timeSignature;
        this.m_repeatAttribute = this.m_saved.m_repeatAttribute;
    }
    
    @Override
    public void execute() {
        if (this.m_saved.m_key != this.m_key) {
            this.m_saved.setKey(this.m_key);
        }
        if (this.m_saved.m_keySignature != this.m_keySignature) {
            this.m_saved.setKeySignature(this.m_keySignature);
        }
        if (this.m_saved.m_timeSignature != this.m_timeSignature) {
            this.m_saved.setTimeSignature(this.m_timeSignature);
        }
        if (this.m_saved.m_repeatAttribute != this.m_repeatAttribute) {
            this.m_saved.setRepeatAttribute(this.m_repeatAttribute);
        }
    }
    
    @Override
    public RestoreCommand getInvertCommand() {
        return new BarRestoreCommand(this.m_saved);
    }
}
