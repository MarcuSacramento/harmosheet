// 
// Decompiled by Procyon v0.5.30
// 

package harmotab.harmonica;

import harmotab.element.Tab;
import java.util.Vector;
import harmotab.core.undo.RestoreCommand;

class TabModelRestoreCommand extends TabModel implements RestoreCommand
{
    private TabModel m_saved;
    
    public TabModelRestoreCommand(final TabModel saved) {
        this.m_saved = saved;
        this.m_model = (Vector<Tab>)this.m_saved.m_model.clone();
    }
    
    @Override
    public void execute() {
        if (this.m_saved.m_model != this.m_model) {
            this.m_saved.m_model = this.m_model;
        }
    }
    
    @Override
    public RestoreCommand getInvertCommand() {
        return new TabModelRestoreCommand(this.m_saved);
    }
}
