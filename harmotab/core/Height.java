// 
// Decompiled by Procyon v0.5.30
// 

package harmotab.core;

import harmotab.io.SerializedObject;
import harmotab.io.ObjectSerializer;
import harmotab.throwables.UnhandledCaseError;
import harmotab.throwables.OutOfSpecificationError;
import harmotab.core.undo.RestoreCommand;
import harmotab.throwables.OutOfBoundsError;

public class Height extends HarmoTabObject implements Cloneable
{
    public static final String HEIGHT_TYPESTR = "height";
    public static final String VALUE_ATTR = "value";
    public static final String NOTE_ATTR = "note";
    public static final String OCTAVE_ATTR = "octave";
    public static final String ALTERATION_ATTR = "alteration";
    public static final int MIN_VALUE = 36;
    public static final int MAX_VALUE = 84;
    public static final byte NATURAL = 0;
    public static final byte SHARP = 1;
    public static final byte FLAT = 2;
    public static final int ALTERATIONS_NUMBER = 3;
    public static final byte DEFAULT_ALTERATION = 0;
    public static final int MIN_OCTAVE = 3;
    public static final int MAX_OCTAVE = 6;
    public static final int OCTAVE_EXTREMUM = 10;
    public static final int DEFAULT_OCTAVE = 5;
    public static final byte C = 0;
    public static final byte D = 1;
    public static final byte E = 2;
    public static final byte F = 3;
    public static final byte G = 4;
    public static final byte A = 5;
    public static final byte B = 6;
    public static final byte NUMBER_OF_NOTES_PER_OCTAVE = 7;
    public static final byte NUMBER_OF_ALTERED_NOTES_PER_OCTAVE = 12;
    public static final byte DEFAULT_NOTE = 0;
    protected byte m_note;
    protected int m_octave;
    protected byte m_alteration;
    
    public Height() {
        this.setNote((byte)0);
        this.setOctave(5);
        this.setAlteration((byte)0);
    }
    
    public Height(final byte note) {
        this.setNote(note);
        this.setOctave(5);
        this.setAlteration((byte)0);
    }
    
    public Height(final byte note, final int octave) {
        this.setNote(note);
        this.setOctave(octave);
        this.setAlteration((byte)0);
    }
    
    public Height(final byte note, final int octave, final byte alteration) {
        this.setNote(note);
        this.setOctave(octave);
        this.setAlteration(alteration);
    }
    
    public Height(final int soundId) {
        if (soundId < 36 || soundId > 84) {
            throw new OutOfBoundsError("Invalid sound identifier (" + soundId + ") !");
        }
        this.setOctave(soundId / 12);
        switch (soundId % 12) {
            case 0: {
                this.setNote((byte)0);
                this.setAlteration((byte)0);
                break;
            }
            case 1: {
                this.setNote((byte)0);
                this.setAlteration((byte)1);
                break;
            }
            case 2: {
                this.setNote((byte)1);
                this.setAlteration((byte)0);
                break;
            }
            case 3: {
                this.setNote((byte)2);
                this.setAlteration((byte)2);
                break;
            }
            case 4: {
                this.setNote((byte)2);
                this.setAlteration((byte)0);
                break;
            }
            case 5: {
                this.setNote((byte)3);
                this.setAlteration((byte)0);
                break;
            }
            case 6: {
                this.setNote((byte)3);
                this.setAlteration((byte)1);
                break;
            }
            case 7: {
                this.setNote((byte)4);
                this.setAlteration((byte)0);
                break;
            }
            case 8: {
                this.setNote((byte)4);
                this.setAlteration((byte)1);
                break;
            }
            case 9: {
                this.setNote((byte)5);
                this.setAlteration((byte)0);
                break;
            }
            case 10: {
                this.setNote((byte)6);
                this.setAlteration((byte)2);
                break;
            }
            case 11: {
                this.setNote((byte)6);
                this.setAlteration((byte)0);
                break;
            }
        }
    }
    
    public Height(final String height) {
        this.setHeight(height);
    }
    
    public Height(final Height height) {
        this.setNote(height.getNote());
        this.setOctave(height.getOctave());
        this.setAlteration(height.getAlteration());
    }
    
    public Object clone() {
        return super.clone();
    }
    
    @Override
    public RestoreCommand createRestoreCommand() {
        return new HeightRestoreCommand(this);
    }
    
    @Override
    public boolean equals(final Object object) {
        if (object == null) {
            return false;
        }
        final Height height = (Height)object;
        return this.m_note == height.m_note && this.m_octave == height.m_octave && this.m_alteration == height.m_alteration;
    }
    
    public void setNote(final byte note) {
        switch (note) {
            case 0:
            case 1:
            case 2:
            case 3:
            case 4:
            case 5:
            case 6: {
                this.m_note = note;
                this.fireObjectChanged("note");
            }
            default: {
                throw new OutOfSpecificationError("Invalid note identifier '#" + note + "'");
            }
        }
    }
    
    public void setNote(final String name) {
        if (name.length() < 1 || name.length() > 2) {
            throw new OutOfSpecificationError("Invalid note name.");
        }
        switch (name.charAt(0)) {
            case 'C': {
                this.setNote((byte)0);
                break;
            }
            case 'D': {
                this.setNote((byte)1);
                break;
            }
            case 'E': {
                this.setNote((byte)2);
                break;
            }
            case 'F': {
                this.setNote((byte)3);
                break;
            }
            case 'G': {
                this.setNote((byte)4);
                break;
            }
            case 'A': {
                this.setNote((byte)5);
                break;
            }
            case 'B': {
                this.setNote((byte)6);
                break;
            }
            default: {
                throw new OutOfSpecificationError("Unknown note name.");
            }
        }
    }
    
    public byte getNote() {
        return this.m_note;
    }
    
    public String getNoteChar() {
        switch (this.m_note) {
            case 0: {
                return "C";
            }
            case 1: {
                return "D";
            }
            case 2: {
                return "E";
            }
            case 3: {
                return "F";
            }
            case 4: {
                return "G";
            }
            case 5: {
                return "A";
            }
            case 6: {
                return "B";
            }
            default: {
                throw new UnhandledCaseError("Note height has no name !");
            }
        }
    }
    
    public String getNoteName() {
        return String.valueOf(this.getNoteChar()) + this.getAlterationChar();
    }
    
    public static String[] getNotesName() {
        final String[] notes = { new String("C"), new String("C#"), new String("D"), new String("D#"), new String("E"), new String("F"), new String("F#"), new String("G"), new String("G#"), new String("A"), new String("Bb"), new String("B") };
        return notes;
    }
    
    public int getOctave() {
        return this.m_octave;
    }
    
    public void setOctave(final int octave) {
        if (octave < 3 || octave > 6) {
            throw new OutOfBoundsError("Invalid octave (" + octave + ").");
        }
        this.m_octave = octave;
        this.fireObjectChanged("octave");
    }
    
    public byte getAlteration() {
        return this.m_alteration;
    }
    
    public String getAlterationChar() {
        switch (this.m_alteration) {
            case 2: {
                return "b";
            }
            case 1: {
                return "#";
            }
            default: {
                return "";
            }
        }
    }
    
    public void setAlteration(final byte alteration) {
        switch (alteration) {
            case 0:
            case 1:
            case 2: {
                this.m_alteration = alteration;
                this.fireObjectChanged("alteration");
            }
            default: {
                throw new OutOfSpecificationError("Invalid alteration identifier !");
            }
        }
    }
    
    public void setAlteration(final String alteration) {
        if (alteration == null) {
            throw new NullPointerException();
        }
        if (alteration.equals("")) {
            this.m_alteration = 0;
        }
        else if (alteration.equals("#")) {
            this.m_alteration = 1;
        }
        else {
            if (!alteration.equals("b")) {
                throw new IllegalArgumentException("Unhandled alteration '" + alteration + "'");
            }
            this.m_alteration = 2;
        }
    }
    
    public void print() {
        System.out.println(String.valueOf(this.getNoteName()) + this.getOctave());
    }
    
    public void setHeight(String name) {
        if (name.length() < 1 || name.length() > 4) {
            throw new OutOfSpecificationError("Empty note name.");
        }
        switch (name.charAt(0)) {
            case 'C': {
                this.setNote((byte)0);
                break;
            }
            case 'D': {
                this.setNote((byte)1);
                break;
            }
            case 'E': {
                this.setNote((byte)2);
                break;
            }
            case 'F': {
                this.setNote((byte)3);
                break;
            }
            case 'G': {
                this.setNote((byte)4);
                break;
            }
            case 'A': {
                this.setNote((byte)5);
                break;
            }
            case 'B': {
                this.setNote((byte)6);
                break;
            }
            default: {
                throw new OutOfSpecificationError("Unknown note name '" + name.charAt(0) + "'");
            }
        }
        name = name.substring(1);
        this.setAlteration((byte)0);
        if (name.length() >= 1) {
            switch (name.charAt(0)) {
                case 'b': {
                    this.setAlteration((byte)2);
                    name = name.substring(1);
                    break;
                }
                case '#': {
                    this.setAlteration((byte)1);
                    name = name.substring(1);
                    break;
                }
            }
        }
        this.setOctave(5);
        if (name.length() >= 1) {
            String octave = "";
            for (int i = 0; i < name.length(); ++i) {
                if (name.charAt(0) >= '0' && name.charAt(0) <= '9') {
                    octave = String.valueOf(octave) + name.charAt(0);
                    name = name.substring(1);
                }
            }
            if (octave.length() > 0) {
                this.setOctave(Integer.decode(octave));
            }
        }
    }
    
    public int getOrdinate() {
        return 120 - (this.m_note + 7 * this.m_octave);
    }
    
    public int getAlteredNoteId() {
        int value = 0;
        switch (this.m_note) {
            case 0: {
                value += 0;
                break;
            }
            case 1: {
                value += 2;
                break;
            }
            case 2: {
                value += 4;
                break;
            }
            case 3: {
                value += 5;
                break;
            }
            case 4: {
                value += 7;
                break;
            }
            case 5: {
                value += 9;
                break;
            }
            case 6: {
                value += 11;
                break;
            }
        }
        switch (this.m_alteration) {
            case 1: {
                ++value;
                break;
            }
            case 2: {
                --value;
                break;
            }
        }
        if (value == -1) {
            value = 11;
        }
        if (value == 12) {
            value = 0;
        }
        return value;
    }
    
    public int getSoundId() {
        int value = this.getUnalteredSoundId();
        if (this.m_alteration == 1) {
            ++value;
        }
        if (this.m_alteration == 2) {
            --value;
        }
        return value;
    }
    
    public int getUnalteredSoundId() {
        int value = this.m_octave * 12;
        switch (this.m_note) {
            case 0: {
                value += 0;
                break;
            }
            case 1: {
                value += 2;
                break;
            }
            case 2: {
                value += 4;
                break;
            }
            case 3: {
                value += 5;
                break;
            }
            case 4: {
                value += 7;
                break;
            }
            case 5: {
                value += 9;
                break;
            }
            case 6: {
                value += 11;
                break;
            }
        }
        return value;
    }
    
    public void moveUp() {
        if (this.getSoundId() >= 83) {
            return;
        }
        ++this.m_note;
        if (this.m_note >= 7) {
            this.m_note = 0;
            ++this.m_octave;
        }
        if (this.m_octave > 6) {
            --this.m_octave;
            this.m_note = 6;
            this.fireObjectChanged("octave");
        }
        this.fireObjectChanged("note");
    }
    
    public void moveDown() {
        if (this.getSoundId() <= 36) {
            return;
        }
        --this.m_note;
        if (this.m_note < 0) {
            --this.m_octave;
            this.m_note = 6;
        }
        if (this.m_octave < 0) {
            this.m_note = 0;
            ++this.m_octave;
            this.fireObjectChanged("octave");
        }
        this.fireObjectChanged("note");
    }
    
    @Override
    public SerializedObject serialize(final ObjectSerializer serializer) {
        final SerializedObject object = serializer.createSerializedObject("height", this.hashCode());
        object.setAttribute("note", this.getNoteChar());
        object.setAttribute("octave", new StringBuilder(String.valueOf(this.getOctave())).toString());
        if (this.getAlteration() != 0) {
            object.setAttribute("alteration", this.getAlterationChar());
        }
        return object;
    }
    
    @Override
    public void deserialize(final ObjectSerializer serializer, final SerializedObject object) {
        String height = object.getAttribute("note");
        if (object.hasAttribute("alteration")) {
            height = String.valueOf(height) + object.getAttribute("alteration");
        }
        if (object.hasAttribute("octave")) {
            height = String.valueOf(height) + object.getAttribute("octave");
        }
        this.setHeight(height);
    }
}
