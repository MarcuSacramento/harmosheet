// 
// Decompiled by Procyon v0.5.30
// 

package harmotab.core;

import harmotab.track.Track;
import java.util.Iterator;
import java.util.ArrayList;
import java.util.LinkedList;
import harmotab.core.undo.RestoreCommand;

class ScoreRestoreCommand extends Score implements RestoreCommand
{
    private Score m_saved;
    private LinkedList<RestoreCommand> m_attributesRestoreCommands;
    
    public ScoreRestoreCommand(final Score saved) {
        this.m_attributesRestoreCommands = null;
        this.m_saved = saved;
        this.m_tracks = (ArrayList<Track>)this.m_saved.m_tracks.clone();
        this.m_title = this.m_saved.m_title;
        this.m_songwriter = this.m_saved.m_songwriter;
        this.m_tempo = this.m_saved.m_tempo;
        this.m_comment = this.m_saved.m_comment;
        this.m_description = this.m_saved.m_description;
        (this.m_attributesRestoreCommands = new LinkedList<RestoreCommand>()).add(this.m_title.createRestoreCommand());
        this.m_attributesRestoreCommands.add(this.m_songwriter.createRestoreCommand());
        this.m_attributesRestoreCommands.add(this.m_tempo.createRestoreCommand());
        this.m_attributesRestoreCommands.add(this.m_comment.createRestoreCommand());
    }
    
    @Override
    public void execute() {
        for (final RestoreCommand command : this.m_attributesRestoreCommands) {
            command.execute();
        }
        if (this.m_saved.m_tracks != this.m_tracks) {
            this.m_saved.m_tracks = this.m_tracks;
        }
        if (this.m_saved.m_title != this.m_title) {
            this.m_saved.setTitle(this.m_title);
        }
        if (this.m_saved.m_songwriter != this.m_songwriter) {
            this.m_saved.setSongwriter(this.m_songwriter);
        }
        if (this.m_saved.m_tempo != this.m_tempo) {
            this.m_saved.setTempo(this.m_tempo);
        }
        if (this.m_saved.m_comment != this.m_comment) {
            this.m_saved.setComment(this.m_comment);
        }
        if (this.m_saved.m_description != this.m_description) {
            this.m_saved.setDescription(this.m_description);
        }
    }
    
    @Override
    public RestoreCommand getInvertCommand() {
        return new ScoreRestoreCommand(this.m_saved);
    }
}
