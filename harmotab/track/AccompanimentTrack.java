// 
// Decompiled by Procyon v0.5.30
// 

package harmotab.track;

import harmotab.io.SerializedObject;
import harmotab.io.ObjectSerializer;
import harmotab.element.Silence;
import harmotab.element.Accompaniment;
import harmotab.element.Chord;
import java.util.ArrayList;
import harmotab.element.TrackElement;
import java.util.Collection;
import harmotab.core.undo.RestoreCommand;
import harmotab.core.Localizer;
import harmotab.track.soundlayout.SoundLayout;
import harmotab.track.soundlayout.AccompanimentTrackSoundLayout;
import harmotab.track.layout.TrackLayout;
import harmotab.track.layout.AccompanimentTrackLayout;
import harmotab.core.Score;

public class AccompanimentTrack extends Track
{
    private static final int ACCOMPANIMENT_DEFAULT_VOLUME = 90;
    public static String ACCOMPANIMENT_TRACK_TYPESTR;
    
    static {
        AccompanimentTrack.ACCOMPANIMENT_TRACK_TYPESTR = "accompanimentTrack";
    }
    
    protected AccompanimentTrack() {
    }
    
    public AccompanimentTrack(final Score score, final Track linkedTrack) {
        super(score);
        this.setLinkedTrack(linkedTrack);
        this.setTrackLayout(new AccompanimentTrackLayout(this));
        this.setSoundLayout(new AccompanimentTrackSoundLayout(this));
        this.setVolume(90);
        this.setName(Localizer.get("N_ACCOMPANIMENT_TRACK"));
    }
    
    @Override
    public RestoreCommand createRestoreCommand() {
        return new AccompanimentTrackRestoreCommand(this);
    }
    
    @Override
    public Collection<TrackElement> getAddableElements() {
        final ArrayList<TrackElement> list = new ArrayList<TrackElement>();
        list.add(new Accompaniment(new Chord()));
        list.add(new Silence());
        return list;
    }
    
    @Override
    public SerializedObject serialize(final ObjectSerializer serializer) {
        final SerializedObject object = super.serialize(serializer);
        object.setAttribute("type", AccompanimentTrack.ACCOMPANIMENT_TRACK_TYPESTR);
        return object;
    }
}
