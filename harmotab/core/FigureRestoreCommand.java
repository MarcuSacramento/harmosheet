// 
// Decompiled by Procyon v0.5.30
// 

package harmotab.core;

import harmotab.core.undo.RestoreCommand;

class FigureRestoreCommand extends Figure implements RestoreCommand
{
    private Figure m_saved;
    
    public FigureRestoreCommand(final Figure saved) {
        this.m_saved = saved;
        this.m_type = saved.m_type;
        this.m_isDotted = saved.m_isDotted;
        this.m_isTriplet = saved.m_isTriplet;
    }
    
    @Override
    public void execute() {
        if (this.m_saved.m_type != this.m_type) {
            this.m_saved.setType(this.m_type);
        }
        if (this.m_saved.m_isDotted != this.m_isDotted) {
            this.m_saved.setDotted(this.m_isDotted);
        }
        if (this.m_saved.m_isTriplet != this.m_isTriplet) {
            this.m_saved.setTriplet(this.m_isTriplet);
        }
    }
    
    @Override
    public RestoreCommand getInvertCommand() {
        return new FigureRestoreCommand(this.m_saved);
    }
}
