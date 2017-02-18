// 
// Decompiled by Procyon v0.5.30
// 

package harmotab.core;

import harmotab.track.TrackFactory;
import java.util.HashMap;
import harmotab.io.SerializedObject;
import harmotab.io.ObjectSerializer;
import harmotab.element.Bar;
import harmotab.element.TimeSignature;
import harmotab.track.HarmoTabTrack;
import java.util.Comparator;
import java.util.List;
import java.util.Collections;
import java.util.Iterator;
import harmotab.throwables.OutOfBoundsError;
import harmotab.core.undo.RestoreCommand;
import java.awt.Font;
import harmotab.element.Tempo;
import harmotab.element.TextElement;
import java.util.ArrayList;
import harmotab.track.Track;

public class Score extends HarmoTabObject implements Iterable<Track>
{
    public static final String SCORE_TYPESTR = "score";
    public static final String TITLE_ATTR = "title";
    public static final String SONGWRITER_ATTR = "songwriter";
    public static final String COMMENT_ATTR = "comment";
    public static final String TEMPO_ATTR = "tempo";
    public static final String DESCRIPTION_ATTR = "description";
    public static final String PROPERTIES_CHANGED_EVENT = "propertiesChanged";
    public static final String TRACK_CHANGED_EVENT = "trackChanged";
    public static final String TRACK_LIST_CHANGED_EVENT = "trackListChanged";
    protected ArrayList<Track> m_tracks;
    protected TextElement m_title;
    protected TextElement m_songwriter;
    protected Tempo m_tempo;
    protected TextElement m_comment;
    protected String m_description;
    
    public Score() {
        this.m_tracks = new ArrayList<Track>(0);
        this.reset();
    }
    
    public void reset() {
        this.setTitle(new TextElement("", new Font("DejaVu Sans", 1, 26), "center"));
        this.setSongwriter(new TextElement("", new Font("DejaVu Sans", 0, 20), "center"));
        this.setComment(new TextElement("", new Font("DejaVu Sans", 0, 14), "right"));
        this.setTempo(new Tempo());
        this.setDescription("");
        this.m_tracks.clear();
    }
    
    @Override
    public RestoreCommand createRestoreCommand() {
        return new ScoreRestoreCommand(this);
    }
    
    public String getTitleString() {
        return this.m_title.getText();
    }
    
    public TextElement getTitle() {
        return this.m_title;
    }
    
    public void setTitle(final String title) {
        this.m_title.setText(title);
    }
    
    public void setTitle(final TextElement title) {
        if (title == null) {
            throw new NullPointerException();
        }
        this.removeAttributeChangesObserver(this.m_title, "title");
        this.addAttributeChangesObserver(this.m_title = title, "title");
        this.fireObjectChanged("title");
    }
    
    public String getSongwriterString() {
        return this.m_songwriter.getText();
    }
    
    public TextElement getSongwriter() {
        return this.m_songwriter;
    }
    
    public void setSongwriter(final String songwriter) {
        this.m_songwriter.setText(songwriter);
    }
    
    public void setSongwriter(final TextElement songwriter) {
        if (songwriter == null) {
            throw new NullPointerException();
        }
        this.removeAttributeChangesObserver(this.m_songwriter, "songwriter");
        this.addAttributeChangesObserver(this.m_songwriter = songwriter, "songwriter");
        this.fireObjectChanged("songwriter");
    }
    
    public int getTempoValue() {
        return this.m_tempo.getValue();
    }
    
    public Tempo getTempo() {
        return this.m_tempo;
    }
    
    public String getTempoString() {
        return String.valueOf(Localizer.get("ET_TEMPO_EQUALS")) + this.m_tempo;
    }
    
    public void setTempo(final int tempo) {
        this.setTempo(new Tempo(tempo));
    }
    
    public void setTempo(final Tempo tempo) {
        if (tempo == null) {
            throw new NullPointerException();
        }
        this.removeAttributeChangesObserver(this.m_tempo, "tempo");
        this.addAttributeChangesObserver(this.m_tempo = tempo, "tempo");
        this.fireObjectChanged("tempo");
    }
    
    public String getCommentString() {
        return this.m_comment.getText();
    }
    
    public TextElement getComment() {
        return this.m_comment;
    }
    
    public void setComment(final String comment) {
        this.m_comment.setText(comment);
    }
    
    public void setComment(final TextElement comment) {
        if (comment == null) {
            throw new NullPointerException();
        }
        this.removeAttributeChangesObserver(this.m_comment, "comment");
        this.addAttributeChangesObserver(this.m_comment = comment, "comment");
        this.fireObjectChanged("comment");
    }
    
    public void setDescription(final String description) {
        if (description == null) {
            throw new NullPointerException();
        }
        this.m_description = description;
        this.fireObjectChanged("description");
    }
    
    public String getDescription() {
        return this.m_description;
    }
    
    public Track getTrack(final int index) {
        if (index < 0 || index >= this.m_tracks.size()) {
            throw new OutOfBoundsError("Out of bound track index " + index);
        }
        return this.m_tracks.get(index);
    }
    
    public Track getTrack(final Class<?> trackClass, int index) {
        for (final Track track : this.m_tracks) {
            if (track.getClass().equals(trackClass)) {
                if (index == 0) {
                    return track;
                }
                --index;
            }
        }
        return null;
    }
    
    public void addTrack(final Track track) {
        this.m_tracks.add(track);
        this.addAttributeChangesObserver(track, "trackChanged");
        this.dispatchEvent(new HarmoTabObjectEvent(this, "trackListChanged", new HarmoTabObjectEvent(track)));
    }
    
    public void removeTrack(final Track track) {
        this.m_tracks.remove(track);
        this.removeAttributeChangesObserver(track, "trackChanged");
        this.dispatchEvent(new HarmoTabObjectEvent(this, "trackListChanged", new HarmoTabObjectEvent(track)));
    }
    
    public int getTracksCount() {
        return this.m_tracks.size();
    }
    
    public int getTrackId(final Track track) {
        return this.m_tracks.indexOf(track);
    }
    
    @Override
    public Iterator<Track> iterator() {
        return this.m_tracks.iterator();
    }
    
    public float getDuration() {
        float scoreDuration = 0.0f;
        for (final Track track : this.m_tracks) {
            final float trackDuration = track.getDuration();
            if (trackDuration > scoreDuration) {
                scoreDuration = trackDuration;
            }
        }
        return scoreDuration;
    }
    
    public void updateTracksIndex() {
        int index = 0;
        for (final Track track : this.m_tracks) {
            track.setTrackIndex(index);
            ++index;
        }
    }
    
    public void sortTracksUsingIndex() {
        Collections.sort(this.m_tracks, new TrackIndexComparator());
    }
    
    public String getScoreName() {
        final String sw = this.getSongwriterString();
        final String ttl = this.getTitleString();
        if (!sw.isEmpty() && !ttl.isEmpty()) {
            return String.valueOf(sw) + " - " + ttl;
        }
        if (!sw.isEmpty()) {
            return sw;
        }
        if (!ttl.isEmpty()) {
            return ttl;
        }
        return "HarmoTab score";
    }
    
    public Track getMainTrack() {
        return this.getTrack(HarmoTabTrack.class, 0);
    }
    
    public TimeSignature getFirstTimeSignature() {
        final Track mainTrack = this.getMainTrack();
        final Bar firstBar = (Bar)mainTrack.get(Bar.class, 0);
        return firstBar.getTimeSignature();
    }
    
    @Override
    public SerializedObject serialize(final ObjectSerializer serializer) {
        final SerializedObject object = serializer.createSerializedObject("score", this.hashCode());
        this.updateTracksIndex();
        object.setElementAttribute("title", this.getTitle());
        object.setElementAttribute("songwriter", this.getSongwriter());
        object.setElementAttribute("tempo", this.getTempo());
        object.setElementAttribute("comment", this.getComment());
        object.setAttribute("description", this.getDescription());
        int round = 0;
        int remainingTracks = this.m_tracks.size();
        final HashMap<Track, SerializedObject> trackObjectsMap = new HashMap<Track, SerializedObject>(this.m_tracks.size());
        while (remainingTracks > 0) {
            for (final Track track : this.m_tracks) {
                if (track.getTrackLayout().getLayoutRound() == round) {
                    final SerializedObject trackSerialized = track.serialize(serializer);
                    trackObjectsMap.put(track, trackSerialized);
                    if (track.getLinkedTrack() == null) {
                        object.addChild(trackSerialized);
                    }
                    else {
                        final SerializedObject linkedObject = trackObjectsMap.get(track.getLinkedTrack());
                        if (linkedObject == null) {
                            throw new NullPointerException("Linked track not yet serialized.");
                        }
                        linkedObject.addChild(trackSerialized);
                    }
                    --remainingTracks;
                }
            }
            ++round;
        }
        return object;
    }
    
    @Override
    public void deserialize(final ObjectSerializer serializer, final SerializedObject object) {
        this.reset();
        if (object.hasAttribute("title")) {
            this.setTitle((TextElement)object.getElementAttribute("title"));
        }
        if (object.hasAttribute("songwriter")) {
            this.setSongwriter((TextElement)object.getElementAttribute("songwriter"));
        }
        if (object.hasAttribute("tempo")) {
            this.setTempo((Tempo)object.getElementAttribute("tempo"));
        }
        if (object.hasAttribute("comment")) {
            this.setComment((TextElement)object.getElementAttribute("comment"));
        }
        if (object.hasAttribute("description")) {
            this.setDescription(object.getAttribute("description"));
        }
        for (int i = 0; i < object.getChildsNumber(); ++i) {
            final SerializedObject serialized = object.getChild(i);
            if (serialized != null && serialized.getObjectType().equals("track")) {
                final Track track = TrackFactory.create(this, serializer, serialized);
                this.addTrack(track);
            }
        }
        this.sortTracksUsingIndex();
    }
    
    public class TrackIndexComparator implements Comparator<Track>
    {
        @Override
        public int compare(final Track track0, final Track track1) {
            return track0.getTrackIndex() - track1.getTrackIndex();
        }
    }
}
