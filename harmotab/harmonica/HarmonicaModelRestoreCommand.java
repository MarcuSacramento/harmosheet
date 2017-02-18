// 
// Decompiled by Procyon v0.5.30
// 

package harmotab.harmonica;

import harmotab.core.Height;
import java.util.Vector;
import harmotab.core.undo.RestoreCommand;

class HarmonicaModelRestoreCommand extends HarmonicaModel implements RestoreCommand
{
    private HarmonicaModel m_saved;
    
    public HarmonicaModelRestoreCommand(final HarmonicaModel saved) {
        this.m_saved = saved;
        this.m_name = this.m_saved.m_name;
        this.m_numberOfHoles = this.m_saved.m_numberOfHoles;
        this.m_harmonicaType = this.m_saved.m_harmonicaType;
        this.m_naturalModel = (Vector<Vector<Height>>)this.m_saved.m_naturalModel.clone();
        this.m_pushedModel = (Vector<Vector<Height>>)this.m_saved.m_pushedModel.clone();
    }
    
    @Override
    public void execute() {
        if (this.m_saved.m_name != this.m_name) {
            this.m_saved.setName(this.m_name);
        }
        if (this.m_saved.m_numberOfHoles != this.m_numberOfHoles) {
            this.m_saved.setNumberOfHoles(this.m_numberOfHoles);
        }
        if (this.m_saved.m_harmonicaType != this.m_harmonicaType) {
            this.m_saved.setHarmonicaType(this.m_harmonicaType);
        }
        if (this.m_saved.m_naturalModel != this.m_naturalModel) {
            this.m_saved.m_naturalModel = this.m_naturalModel;
        }
        if (this.m_saved.m_pushedModel != this.m_pushedModel) {
            this.m_saved.m_pushedModel = this.m_pushedModel;
        }
    }
    
    @Override
    public RestoreCommand getInvertCommand() {
        return new HarmonicaModelRestoreCommand(this.m_saved);
    }
}
