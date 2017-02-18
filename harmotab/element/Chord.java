// 
// Decompiled by Procyon v0.5.30
// 

package harmotab.element;

import harmotab.core.HarmoTabObjectFactory;
import harmotab.io.SerializedObject;
import harmotab.io.ObjectSerializer;
import java.util.Collection;
import harmotab.throwables.OutOfSpecificationError;
import harmotab.core.Localizer;
import harmotab.core.HarmoTabObject;
import harmotab.core.undo.RestoreCommand;
import java.util.Iterator;
import harmotab.core.Height;
import java.util.LinkedHashSet;
import harmotab.core.Figure;

public class Chord extends TrackElement implements Comparable<Chord>
{
    public static final String NAME_ATTR = "name";
    public static final String FIGURE_ATTR = "figure";
    public static final String HEIGHTS_ATTR = "heights";
    public static final String UNDEFINED = "?";
    public static final String[] m_heightNames;
    public static final String[] m_alterationsNames;
    public static final String[] m_typesNames;
    public static final int DEFAULT_CHORD_OCTAVE = 4;
    public static final int DEFAULT_CHORD_BASS_OCTAVE = 3;
    protected String m_chordName;
    protected Figure m_figure;
    protected LinkedHashSet<Height> m_heights;
    
    static {
        m_heightNames = new String[] { new String("C"), new String("D"), new String("E"), new String("F"), new String("G"), new String("A"), new String("B") };
        m_alterationsNames = new String[] { new String(""), new String("#"), new String("b") };
        m_typesNames = new String[] { new String("7"), new String("7M"), new String("6"), new String("m"), new String("m7"), new String("m6"), new String("sus4"), new String("dim"), new String("aug"), new String("9"), new String("M9"), new String("m9"), new String("13") };
    }
    
    public Chord() {
        super((byte)12);
        this.m_chordName = null;
        this.m_figure = null;
        this.m_heights = null;
        this.instanciateEmptyHeights();
        this.setChord(new Height(), null, null);
        this.setFigure(null);
    }
    
    public Chord(final String name) {
        super((byte)12);
        this.m_chordName = null;
        this.m_figure = null;
        this.m_heights = null;
        this.setName(name);
        this.setFigure(new Figure());
        this.instanciateEmptyHeights();
    }
    
    public Chord(final String name, final Figure figure) {
        super((byte)12);
        this.m_chordName = null;
        this.m_figure = null;
        this.m_heights = null;
        this.setName(name);
        this.setFigure(figure);
        this.instanciateEmptyHeights();
    }
    
    public Chord(final Height main, final String mode, final Height bass) {
        super((byte)12);
        this.m_chordName = null;
        this.m_figure = null;
        this.m_heights = null;
        this.instanciateEmptyHeights();
        this.setChord(main, mode, bass);
    }
    
    private void instanciateEmptyHeights() {
        this.m_heights = new LinkedHashSet<Height>();
    }
    
    @Override
    public Object clone() {
        final Chord chord = (Chord)super.clone();
        chord.setName(this.m_chordName);
        final Figure f = (this.m_figure != null) ? ((Figure)this.m_figure.clone()) : null;
        chord.m_figure = null;
        chord.setFigure(f);
        this.instanciateEmptyHeights();
        for (final Height height : this.m_heights) {
            chord.addHeight((Height)height.clone());
        }
        return chord;
    }
    
    public void set(final Chord chord) {
        this.setName(chord.getName());
        this.setFigure((Figure)chord.getFigure().clone());
        for (final Height height : chord.getHeights()) {
            this.addHeight((Height)height.clone());
        }
        this.fireObjectChanged("name");
    }
    
    @Override
    public int compareTo(final Chord chord) {
        if (chord == null) {
            return 1;
        }
        return chord.getName().compareTo(this.getName());
    }
    
    @Override
    public RestoreCommand createRestoreCommand() {
        return new ChordRestoreCommand(this);
    }
    
    public void setName(final String name) {
        this.m_chordName = name;
        this.fireObjectChanged("name");
    }
    
    public String getName() {
        return this.m_chordName;
    }
    
    public void setFigure(final Figure figure) {
        this.removeAttributeChangesObserver(this.m_figure, "figure");
        this.addAttributeChangesObserver(this.m_figure = figure, "figure");
        this.fireObjectChanged("figure");
    }
    
    public Figure getFigure() {
        return this.m_figure;
    }
    
    @Override
    public String getTrackElementLocalizedName() {
        return Localizer.get("N_CHORD");
    }
    
    public void setChord(final Height main, String mode, final Height bass) {
        String chordName = main.getNoteName();
        if (mode != null && mode.equals("")) {
            mode = null;
        }
        if (mode != null) {
            chordName = String.valueOf(chordName) + mode;
        }
        else {
            mode = "M";
        }
        if (bass != null) {
            chordName = String.valueOf(chordName) + "/" + bass.getNoteName();
        }
        this.setName(chordName);
        this.setFigure(new Figure());
        if (bass != null) {
            final Height bassHeight = new Height(bass.getSoundId());
            this.m_heights.add(bassHeight);
        }
        final int mainSId = main.getSoundId();
        if (mode.equals("M")) {
            this.m_heights.add(new Height(mainSId + 0));
            this.m_heights.add(new Height(mainSId + 4));
            this.m_heights.add(new Height(mainSId + 7));
            this.m_heights.add(new Height(mainSId + 12));
        }
        else if (mode.equals("7")) {
            this.m_heights.add(new Height(mainSId + 0));
            this.m_heights.add(new Height(mainSId + 4));
            this.m_heights.add(new Height(mainSId + 7));
            this.m_heights.add(new Height(mainSId + 10));
            this.m_heights.add(new Height(mainSId + 12));
        }
        else if (mode.equals("7M")) {
            this.m_heights.add(new Height(mainSId + 0));
            this.m_heights.add(new Height(mainSId + 4));
            this.m_heights.add(new Height(mainSId + 7));
            this.m_heights.add(new Height(mainSId + 11));
            this.m_heights.add(new Height(mainSId + 12));
        }
        else if (mode.equals("6")) {
            this.m_heights.add(new Height(mainSId + 0));
            this.m_heights.add(new Height(mainSId + 4));
            this.m_heights.add(new Height(mainSId + 7));
            this.m_heights.add(new Height(mainSId + 9));
            this.m_heights.add(new Height(mainSId + 12));
        }
        else if (mode.equals("m")) {
            this.m_heights.add(new Height(mainSId + 0));
            this.m_heights.add(new Height(mainSId + 3));
            this.m_heights.add(new Height(mainSId + 7));
            this.m_heights.add(new Height(mainSId + 12));
        }
        else if (mode.equals("m7")) {
            this.m_heights.add(new Height(mainSId + 0));
            this.m_heights.add(new Height(mainSId + 3));
            this.m_heights.add(new Height(mainSId + 7));
            this.m_heights.add(new Height(mainSId + 10));
            this.m_heights.add(new Height(mainSId + 12));
        }
        else if (mode.equals("m6")) {
            this.m_heights.add(new Height(mainSId + 0));
            this.m_heights.add(new Height(mainSId + 3));
            this.m_heights.add(new Height(mainSId + 7));
            this.m_heights.add(new Height(mainSId + 9));
            this.m_heights.add(new Height(mainSId + 12));
        }
        else if (mode.equals("sus4")) {
            this.m_heights.add(new Height(mainSId + 0));
            this.m_heights.add(new Height(mainSId + 5));
            this.m_heights.add(new Height(mainSId + 7));
            this.m_heights.add(new Height(mainSId + 12));
        }
        else if (mode.equals("dim")) {
            this.m_heights.add(new Height(mainSId + 0));
            this.m_heights.add(new Height(mainSId + 2));
            this.m_heights.add(new Height(mainSId + 3));
            this.m_heights.add(new Height(mainSId + 7));
            this.m_heights.add(new Height(mainSId + 10));
            this.m_heights.add(new Height(mainSId + 12));
        }
        else if (mode.equals("aug")) {
            this.m_heights.add(new Height(mainSId + 0));
            this.m_heights.add(new Height(mainSId + 4));
            this.m_heights.add(new Height(mainSId + 8));
            this.m_heights.add(new Height(mainSId + 12));
        }
        else if (mode.equals("M9")) {
            this.m_heights.add(new Height(mainSId + 0));
            this.m_heights.add(new Height(mainSId + 2));
            this.m_heights.add(new Height(mainSId + 4));
            this.m_heights.add(new Height(mainSId + 7));
            this.m_heights.add(new Height(mainSId + 11));
            this.m_heights.add(new Height(mainSId + 12));
        }
        else if (mode.equals("9")) {
            this.m_heights.add(new Height(mainSId + 0));
            this.m_heights.add(new Height(mainSId + 2));
            this.m_heights.add(new Height(mainSId + 4));
            this.m_heights.add(new Height(mainSId + 7));
            this.m_heights.add(new Height(mainSId + 10));
            this.m_heights.add(new Height(mainSId + 12));
        }
        else if (mode.equals("m9")) {
            this.m_heights.add(new Height(mainSId + 0));
            this.m_heights.add(new Height(mainSId + 2));
            this.m_heights.add(new Height(mainSId + 3));
            this.m_heights.add(new Height(mainSId + 7));
            this.m_heights.add(new Height(mainSId + 10));
            this.m_heights.add(new Height(mainSId + 12));
        }
        else {
            if (!mode.equals("13")) {
                throw new OutOfSpecificationError("Unknown mode type.");
            }
            this.m_heights.add(new Height(mainSId + 0));
            this.m_heights.add(new Height(mainSId + 2));
            this.m_heights.add(new Height(mainSId + 4));
            this.m_heights.add(new Height(mainSId + 7));
            this.m_heights.add(new Height(mainSId + 9));
            this.m_heights.add(new Height(mainSId + 10));
            this.m_heights.add(new Height(mainSId + 12));
        }
        this.fireObjectChanged("name");
    }
    
    public Height extractNoteHeight() {
        String note = "";
        String alt = "";
        if (this.m_chordName.length() > 0) {
            note = this.m_chordName.substring(0, 1);
        }
        if (this.m_chordName.length() > 1) {
            alt = this.m_chordName.substring(1, 2);
        }
        if (!alt.equals("#") && !alt.equals("b")) {
            alt = "";
        }
        Height height = null;
        try {
            height = new Height(String.valueOf(note) + alt);
        }
        catch (Exception exception) {
            exception.printStackTrace();
        }
        return height;
    }
    
    public Height extractBassHeight() {
        Height height = null;
        final int slashPosition = this.m_chordName.indexOf("/");
        if (slashPosition != -1) {
            final String note = this.m_chordName.substring(slashPosition + 1);
            try {
                height = new Height(note);
            }
            catch (Exception exception) {
                exception.printStackTrace();
            }
        }
        return height;
    }
    
    public String extractType() {
        int typeStarts = 1;
        if (this.m_chordName.length() > 1 && (this.m_chordName.charAt(1) == 'b' || this.m_chordName.charAt(1) == '#')) {
            typeStarts = 2;
        }
        int typeEnds = this.m_chordName.indexOf("/");
        if (typeEnds < 0) {
            typeEnds = this.m_chordName.length();
        }
        if (typeStarts < typeEnds) {
            return this.m_chordName.substring(typeStarts, typeEnds);
        }
        return null;
    }
    
    public boolean isDefined() {
        return !this.getName().equals("?");
    }
    
    @Override
    public float getDuration() {
        return this.m_figure.getDuration();
    }
    
    public Iterator<Height> getHeightsIterator() {
        return this.m_heights.iterator();
    }
    
    public Collection<Height> getHeights() {
        return this.m_heights;
    }
    
    public void addHeight(final Height height) {
        this.m_heights.add(height);
        this.addAttributeChangesObserver(height, "heights");
        this.fireObjectChanged("heights");
    }
    
    public void removeHeight(final Height height) {
        this.m_heights.remove(height);
        this.removeAttributeChangesObserver(height, "heights");
        this.fireObjectChanged("heights");
    }
    
    public void clearHeights() {
        for (final Height height : this.m_heights) {
            this.removeAttributeChangesObserver(height, "heights");
        }
        this.m_heights.clear();
        this.fireObjectChanged("heights");
    }
    
    @Override
    public SerializedObject serialize(final ObjectSerializer serializer) {
        final SerializedObject object = super.serialize(serializer);
        object.setAttribute("name", this.getName());
        if (this.getFigure() != null) {
            object.setElementAttribute("figure", this.getFigure());
        }
        for (final Height height : this.m_heights) {
            object.addChild(height.serialize(serializer));
        }
        return object;
    }
    
    @Override
    public void deserialize(final ObjectSerializer serializer, final SerializedObject object) {
        this.setName(object.getAttribute("name"));
        this.setFigure(object.hasAttribute("figure") ? ((Figure)object.getElementAttribute("figure")) : null);
        this.clearHeights();
        for (int i = 0; i < object.getChildsNumber(); ++i) {
            final SerializedObject child = object.getChild(i);
            if (child != null) {
                final HarmoTabObject htObj = HarmoTabObjectFactory.create(serializer, child);
                if (htObj instanceof Height) {
                    this.addHeight((Height)htObj);
                }
            }
        }
    }
}
