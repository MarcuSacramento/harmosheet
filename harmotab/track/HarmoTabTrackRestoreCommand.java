// 
// Decompiled by Procyon v0.5.30
// 

package harmotab.track;

import harmotab.element.Element;
import java.util.LinkedList;
import harmotab.core.undo.RestoreCommand;

class HarmoTabTrackRestoreCommand extends HarmoTabTrack implements RestoreCommand
{
    private HarmoTabTrack m_saved;
    
    public HarmoTabTrackRestoreCommand(final HarmoTabTrack saved) {
        this.m_saved = saved;
        this.m_name = this.m_saved.m_name;
        this.m_instrument = this.m_saved.m_instrument;
        this.m_comment = this.m_saved.m_comment;
        this.m_volumePercentage = this.m_saved.m_volumePercentage;
        this.m_elements = (LinkedList<Element>)this.m_saved.m_elements.clone();
        this.m_tabModel = this.m_saved.m_tabModel;
        this.m_harmonica = this.m_saved.m_harmonica;
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
        if (this.m_saved.m_tabModel != this.m_tabModel) {
            this.m_saved.setTabModel(this.m_tabModel);
        }
        if (this.m_saved.m_harmonica != this.m_harmonica) {
            this.m_saved.setHarmonica(this.m_harmonica);
        }
    }
    
    @Override
    public RestoreCommand getInvertCommand() {
        return new HarmoTabTrackRestoreCommand(this.m_saved);
    }
}
