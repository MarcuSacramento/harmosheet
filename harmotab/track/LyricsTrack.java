// 
// Decompiled by Procyon v0.5.30
// 

package harmotab.track;

import harmotab.io.SerializedObject;
import harmotab.io.ObjectSerializer;
import harmotab.element.Silence;
import harmotab.element.Lyrics;
import java.util.ArrayList;
import harmotab.element.TrackElement;
import java.util.Collection;
import harmotab.core.undo.RestoreCommand;
import harmotab.core.Localizer;
import harmotab.track.soundlayout.SoundLayout;
import harmotab.track.soundlayout.LyricsTrackSoundLayout;
import harmotab.track.layout.TrackLayout;
import harmotab.track.layout.LyricsTrackLayout;
import harmotab.core.Score;

public class LyricsTrack extends Track
{
    public static final String LYRICS_TRACK_TYPESTR = "lyricsTrack";
    
    protected LyricsTrack() {
    }
    
    public LyricsTrack(final Score score, final Track linkedTrack) {
        super(score);
        this.setLinkedTrack(linkedTrack);
        this.setTrackLayout(new LyricsTrackLayout(this));
        this.setSoundLayout(new LyricsTrackSoundLayout(this));
        this.setName(Localizer.get("N_LYRICS_TRACK"));
    }
    
    @Override
    public RestoreCommand createRestoreCommand() {
        return new LyricsTrackRestoreCommand(this);
    }
    
    @Override
    public Collection<TrackElement> getAddableElements() {
        final ArrayList<TrackElement> list = new ArrayList<TrackElement>();
        list.add(new Lyrics());
        list.add(new Silence());
        return list;
    }
    
    @Override
    public SerializedObject serialize(final ObjectSerializer serializer) {
        final SerializedObject object = super.serialize(serializer);
        object.setAttribute("type", "lyricsTrack");
        return object;
    }
}
