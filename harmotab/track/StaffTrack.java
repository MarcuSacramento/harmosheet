// 
// Decompiled by Procyon v0.5.30
// 

package harmotab.track;

import harmotab.io.SerializedObject;
import harmotab.io.ObjectSerializer;
import harmotab.element.Bar;
import harmotab.element.Note;
import java.util.ArrayList;
import harmotab.element.TrackElement;
import java.util.Collection;
import harmotab.core.undo.RestoreCommand;
import harmotab.core.Localizer;
import harmotab.track.soundlayout.SoundLayout;
import harmotab.track.soundlayout.StaffTrackSoundLayout;
import harmotab.track.layout.TrackLayout;
import harmotab.track.layout.StaffTrackLayout;
import harmotab.core.Score;

public class StaffTrack extends Track
{
    public static String STAFF_TRACK_TYPESTR;
    
    static {
        StaffTrack.STAFF_TRACK_TYPESTR = "staffTrack";
    }
    
    protected StaffTrack() {
    }
    
    public StaffTrack(final Score score) {
        super(score);
        this.setTrackLayout(new StaffTrackLayout(this));
        this.setSoundLayout(new StaffTrackSoundLayout(this));
        this.setName(Localizer.get("N_STAFF_TRACK"));
    }
    
    @Override
    public RestoreCommand createRestoreCommand() {
        return new StaffTrackRestoreCommand(this);
    }
    
    @Override
    public Collection<TrackElement> getAddableElements() {
        final ArrayList<TrackElement> list = new ArrayList<TrackElement>();
        list.add(new Note());
        list.add(new Bar());
        return list;
    }
    
    @Override
    public SerializedObject serialize(final ObjectSerializer serializer) {
        final SerializedObject object = super.serialize(serializer);
        object.setAttribute("type", StaffTrack.STAFF_TRACK_TYPESTR);
        return object;
    }
}
