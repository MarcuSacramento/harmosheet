// 
// Decompiled by Procyon v0.5.30
// 

package harmotab.track;

import harmotab.io.SerializedObject;
import harmotab.io.ObjectSerializer;
import harmotab.core.Score;

public class TrackFactory
{
    public static Track create(final Score score, final ObjectSerializer serializer, final SerializedObject object) {
        Track track = null;
        if (!object.getObjectType().equals("track")) {
            throw new IllegalArgumentException("Object type '" + object.getObjectType() + "' is not a track.");
        }
        final String type = object.getAttribute("type");
        if (type == null || type.equals("")) {
            throw new IllegalArgumentException();
        }
        if (type.equals(StaffTrack.STAFF_TRACK_TYPESTR)) {
            track = new StaffTrack(score);
        }
        else if (type.equals(HarmoTabTrack.HARMOTAB_TRACK_TYPESTR)) {
            track = new HarmoTabTrack(score);
        }
        else if (type.equals(AccompanimentTrack.ACCOMPANIMENT_TRACK_TYPESTR)) {
            track = new AccompanimentTrack(score, null);
        }
        else if (type.equals("lyricsTrack")) {
            track = new LyricsTrack(score, null);
        }
        else {
            System.err.println("TrackFactory::create: Unhandled track type (" + type + ")");
        }
        if (track != null) {
            track.deserialize(serializer, object);
        }
        return track;
    }
}
