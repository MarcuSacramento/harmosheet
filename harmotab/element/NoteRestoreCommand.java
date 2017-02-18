// 
// Decompiled by Procyon v0.5.30
// 

package harmotab.element;

import harmotab.core.undo.RestoreCommand;

class NoteRestoreCommand extends Note implements RestoreCommand
{
    private Note m_saved;
    
    public NoteRestoreCommand(final Note saved) {
        this.m_saved = saved;
        this.m_figure = saved.m_figure;
        this.m_height = saved.m_height;
        this.m_isRest = saved.m_isRest;
        this.m_isTied = saved.m_isTied;
    }
    
    @Override
    public void execute() {
        if (this.m_saved.m_figure != this.m_figure) {
            this.m_saved.setFigure(this.m_figure);
        }
        if (this.m_saved.m_height != this.m_height) {
            this.m_saved.setHeight(this.m_height);
        }
        if (this.m_saved.m_isRest != this.m_isRest) {
            this.m_saved.setRest(this.m_isRest);
        }
        if (this.m_saved.m_isTied != this.m_isTied) {
            this.m_saved.setTied(this.m_isTied);
        }
    }
    
    @Override
    public RestoreCommand getInvertCommand() {
        return new NoteRestoreCommand(this.m_saved);
    }
}
