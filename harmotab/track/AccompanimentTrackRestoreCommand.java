// 
// Decompiled by Procyon v0.5.30
// 

package harmotab.track;

import harmotab.element.Element;
import java.util.LinkedList;
import harmotab.core.undo.RestoreCommand;

class AccompanimentTrackRestoreCommand extends AccompanimentTrack implements RestoreCommand
{
    private AccompanimentTrack m_saved;
    
    public AccompanimentTrackRestoreCommand(final AccompanimentTrack saved) {
        this.m_saved = saved;
        this.m_name = this.m_saved.m_name;
        this.m_instrument = this.m_saved.m_instrument;
        this.m_comment = this.m_saved.m_comment;
        this.m_volumePercentage = this.m_saved.m_volumePercentage;
        this.m_elements = (LinkedList<Element>)this.m_saved.m_elements.clone();
    }
    
    @Override
    public void execute() {
        if (this.m_saved.m_name != this.m_name) {
            this.m_saved.setName(this.m_name);
        }
        if (this.m_saved.m_instrument != this.m_instrument) {
            this.m_saved.setInstrument(this.m_instrument);
        }
        if (this.m_saved.m_comment != this.m_comment) {
            this.m_saved.setComment(this.m_comment);
        }
        if (this.m_saved.m_volumePercentage != this.m_volumePercentage) {
            this.m_saved.setVolume(this.m_volumePercentage);
        }
        if (this.m_saved.m_elements != this.m_elements) {
            this.m_saved.m_elements = this.m_elements;
        }
    }
    
    @Override
    public RestoreCommand getInvertCommand() {
        return new AccompanimentTrackRestoreCommand(this.m_saved);
    }
}
