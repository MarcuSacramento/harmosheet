// 
// Decompiled by Procyon v0.5.30
// 

package harmotab.track;

import harmotab.core.HarmoTabObjectFactory;
import harmotab.io.SerializedObject;
import harmotab.io.ObjectSerializer;
import harmotab.element.TrackElement;
import java.util.Collection;
import harmotab.renderer.LocationItem;
import harmotab.throwables.ObjectNotFoundError;
import java.util.ListIterator;
import java.util.Iterator;
import harmotab.core.HarmoTabObjectEvent;
import harmotab.track.soundlayout.SoundLayout;
import harmotab.track.layout.TrackLayout;
import harmotab.core.Score;
import java.util.LinkedList;
import harmotab.element.Element;
import harmotab.io.SerializableObject;
import harmotab.core.HarmoTabObject;

public abstract class Track extends HarmoTabObject implements SerializableObject, Iterable<Element>
{
    public static final String TRACK_TYPESTR = "track";
    public static final String NAME_ATTR = "name";
    public static final String INSTRUMENT_ATTR = "instrument";
    public static final String COMMENT_ATTR = "comment";
    public static final String VOLUME_ATTR = "volume";
    public static final String TRACK_INDEX_ATTR = "index";
    public static final String ELEMENT_CHANGED_EVENT = "elementChanged";
    public static final String ELEMENT_LIST_CHANGED_EVENT = "elementListChanged";
    public static final String DEFAULT_NAME = "";
    public static final int DEFAULT_INSTRUMENT = 0;
    public static final String DEFAULT_COMMENT = "";
    public static final int DEFAULT_VOLUME = 100;
    public static final int DEFAULT_TRACK_INDEX = 0;
    protected String m_name;
    protected int m_instrument;
    protected String m_comment;
    protected int m_volumePercentage;
    protected LinkedList<Element> m_elements;
    private Score m_score;
    private TrackLayout m_trackLayout;
    private SoundLayout m_soundLayout;
    private Track m_linkedTrack;
    private int m_trackIndex;
    
    protected Track() {
        this.m_elements = new LinkedList<Element>();
        this.m_score = null;
        this.m_trackLayout = null;
        this.m_soundLayout = null;
        this.m_linkedTrack = null;
        this.m_trackIndex = 0;
    }
    
    public Track(final Score score) {
        this.m_elements = new LinkedList<Element>();
        this.m_score = null;
        this.m_trackLayout = null;
        this.m_soundLayout = null;
        this.m_linkedTrack = null;
        this.m_trackIndex = 0;
        this.setScore(score);
        this.m_trackLayout = null;
        this.setLinkedTrack(null);
        this.setName("");
        this.setInstrument(0);
        this.setComment("");
        this.setVolume(100);
        this.setTrackIndex(0);
    }
    
    public String getName() {
        return this.m_name;
    }
    
    public void setName(final String name) {
        this.m_name = name;
        this.fireObjectChanged("name");
    }
    
    public int getInstrument() {
        return this.m_instrument;
    }
    
    public void setInstrument(final int instrument) {
        this.m_instrument = instrument;
        this.fireObjectChanged("instrument");
    }
    
    public String getComment() {
        return this.m_comment;
    }
    
    public void setComment(final String comment) {
        this.m_comment = comment;
        this.fireObjectChanged("comment");
    }
    
    public int getVolume() {
        return this.m_volumePercentage;
    }
    
    public void setVolume(final int volumePercentage) {
        if (volumePercentage < 0 || volumePercentage > 100) {
            throw new IllegalArgumentException("Invalid volume percentage (" + volumePercentage + ").");
        }
        this.m_volumePercentage = volumePercentage;
        this.fireObjectChanged("volume");
    }
    
    public Score getScore() {
        return this.m_score;
    }
    
    public void setScore(final Score score) {
        this.m_score = score;
    }
    
    public void setTrackLayout(final TrackLayout trackLayout) {
        if (trackLayout == null) {
            throw new NullPointerException();
        }
        this.m_trackLayout = trackLayout;
    }
    
    public TrackLayout getTrackLayout() {
        return this.m_trackLayout;
    }
    
    public void setSoundLayout(final SoundLayout layout) {
        if (layout == null) {
            throw new NullPointerException();
        }
        this.m_soundLayout = layout;
    }
    
    public SoundLayout getSoundLayout() {
        return this.m_soundLayout;
    }
    
    public Track getLinkedTrack() {
        return this.m_linkedTrack;
    }
    
    public void setLinkedTrack(final Track track) {
        this.m_linkedTrack = track;
    }
    
    public int getTrackIndex() {
        return this.m_trackIndex;
    }
    
    public void setTrackIndex(final int index) {
        this.m_trackIndex = index;
    }
    
    public void add(final Element element) {
        this.m_elements.add(element);
        this.addAttributeChangesObserver(element, "elementChanged");
        this.dispatchEvent(new HarmoTabObjectEvent(this, "elementListChanged", new HarmoTabObjectEvent(element)));
    }
    
    public void add(final int index, final Element element) {
        this.m_elements.add(index, element);
        this.addAttributeChangesObserver(element, "elementChanged");
        this.dispatchEvent(new HarmoTabObjectEvent(this, "elementListChanged", new HarmoTabObjectEvent(element)));
    }
    
    public void remove(final Element element) {
        if (!this.m_elements.remove(element)) {
            boolean deleted = false;
            for (final Element cur : this.m_elements) {
                if (cur.delete(element)) {
                    deleted = true;
                }
            }
            if (!deleted) {
                System.err.println("Track.remove: Element " + element + " not found.");
            }
        }
        this.removeAttributeChangesObserver(element, "elementChanged");
        this.dispatchEvent(new HarmoTabObjectEvent(this, "elementListChanged", new HarmoTabObjectEvent(element)));
    }
    
    public Element get(final int index) {
        return this.m_elements.get(index);
    }
    
    public Element get(final Class<?> elementClass, int index) {
        for (final Element element : this) {
            if (element.getClass().equals(elementClass)) {
                if (index == 0) {
                    return element;
                }
                --index;
            }
        }
        return null;
    }
    
    public int size() {
        return this.m_elements.size();
    }
    
    public int indexOf(final Element element) {
        return this.m_elements.indexOf(element);
    }
    
    @Override
    public Iterator<Element> iterator() {
        return this.m_elements.iterator();
    }
    
    public ListIterator<Element> listIterator() {
        return this.m_elements.listIterator();
    }
    
    public ListIterator<Element> listIterator(final Element element) {
        final int index = this.m_elements.indexOf(element);
        if (index == -1) {
            throw new ObjectNotFoundError("Cannot create iterator on element #" + element + " !");
        }
        return this.m_elements.listIterator(index);
    }
    
    public ListIterator<Element> listIterator(final LocationItem item) {
        int index = this.m_elements.indexOf(item.getElement());
        if (index == -1) {
            index = this.m_elements.indexOf(item.getParent());
            if (index == -1) {
                throw new ObjectNotFoundError("Cannot create iterator on item #" + item + " !");
            }
        }
        return this.m_elements.listIterator(index);
    }
    
    public void clear() {
        for (final Element element : this.m_elements) {
            this.removeAttributeChangesObserver(element, "elementChanged");
        }
        this.m_elements.clear();
        this.dispatchEvent(new HarmoTabObjectEvent(this, "elementListChanged"));
    }
    
    public float getDuration() {
        float duration = 0.0f;
        for (final Element element : this.m_elements) {
            duration += element.getDuration();
        }
        return duration;
    }
    
    public abstract Collection<TrackElement> getAddableElements();
    
    @Override
    public SerializedObject serialize(final ObjectSerializer serializer) {
        final SerializedObject object = serializer.createSerializedObject("track", this.hashCode());
        object.setAttribute("index", new StringBuilder(String.valueOf(this.getTrackIndex())).toString());
        object.setAttribute("name", this.getName());
        object.setAttribute("instrument", new StringBuilder(String.valueOf(this.getInstrument())).toString());
        object.setAttribute("volume", new StringBuilder(String.valueOf(this.getVolume())).toString());
        object.setAttribute("comment", this.getComment());
        for (final Element element : this.m_elements) {
            object.addChild(element.serialize(serializer));
        }
        return object;
    }
    
    @Override
    public void deserialize(final ObjectSerializer serializer, final SerializedObject object) {
        this.setTrackIndex(object.hasAttribute("index") ? Integer.parseInt(object.getAttribute("index")) : 0);
        this.setName(object.hasAttribute("name") ? object.getAttribute("name") : "");
        this.setInstrument(object.hasAttribute("instrument") ? Integer.parseInt(object.getAttribute("instrument")) : 0);
        this.setVolume(object.hasAttribute("volume") ? Integer.parseInt(object.getAttribute("volume")) : 100);
        this.setComment(object.hasAttribute("comment") ? object.getAttribute("comment") : "");
        for (int i = 0; i < object.getChildsNumber(); ++i) {
            final SerializedObject child = object.getChild(i);
            if (child != null) {
                if (child.getObjectType().equals("track")) {
                    final Track track = TrackFactory.create(this.m_score, serializer, object.getChild(i));
                    track.setLinkedTrack(this);
                    this.m_score.addTrack(track);
                }
                else {
                    final HarmoTabObject htObject = HarmoTabObjectFactory.create(serializer, child);
                    if (htObject instanceof Element) {
                        this.add((Element)htObject);
                    }
                }
            }
        }
    }
    
    public void printTrace() {
        System.out.println("Track " + this.m_name + ", " + this.m_elements.size() + " elements :");
        int index = 0;
        for (final Element e : this.m_elements) {
            System.out.println(String.valueOf(index) + ". " + e);
            ++index;
        }
    }
}
