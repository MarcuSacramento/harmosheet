// 
// Decompiled by Procyon v0.5.30
// 

package harmotab.element;

import harmotab.core.undo.RestoreCommand;

class TabRestoreCommand extends Tab implements RestoreCommand
{
    private Tab m_saved;
    
    public TabRestoreCommand(final Tab saved) {
        this.m_saved = saved;
        this.m_hole = this.m_saved.m_hole;
        this.m_direction = this.m_saved.m_direction;
        this.m_bend = this.m_saved.m_bend;
        this.m_pushed = this.m_saved.m_pushed;
        this.m_effect = this.m_saved.m_effect;
    }
    
    @Override
    public void execute() {
        if (this.m_hole != this.m_saved.m_hole) {
            this.m_saved.setHole(this.m_hole);
        }
        if (this.m_direction != this.m_saved.m_direction) {
            this.m_saved.setDirection(this.m_direction);
        }
        if (this.m_bend != this.m_saved.m_bend) {
            this.m_saved.setBend(this.m_bend);
        }
        if (this.m_pushed != this.m_saved.m_pushed) {
            this.m_saved.setPushed(this.m_pushed);
        }
        if (this.m_effect != this.m_saved.m_effect) {
            this.m_saved.setEffect(this.m_effect);
        }
    }
    
    @Override
    public RestoreCommand getInvertCommand() {
        return new TabRestoreCommand(this.m_saved);
    }
}
