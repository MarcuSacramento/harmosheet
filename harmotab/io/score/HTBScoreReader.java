// 
// Decompiled by Procyon v0.5.30
// 

package harmotab.io.score;

import harmotab.element.Key;
import harmotab.element.Chord;
import harmotab.core.RepeatAttribute;
import harmotab.core.Effect;
import harmotab.element.Element;
import harmotab.element.Silence;
import harmotab.element.Tab;
import harmotab.core.Height;
import harmotab.throwables.UnhandledCaseError;
import harmotab.core.Figure;
import harmotab.element.HarmoTabElement;
import harmotab.harmonica.Harmonica;
import java.io.Reader;
import java.io.InputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import harmotab.harmonica.TabModelController;
import harmotab.core.Duration;
import java.util.Scanner;
import java.io.InputStreamReader;
import java.io.FileInputStream;
import harmotab.track.Track;
import java.io.File;
import harmotab.core.Score;
import harmotab.element.Bar;
import harmotab.element.TimeSignature;
import harmotab.element.KeySignature;
import harmotab.element.Lyrics;
import harmotab.element.Accompaniment;
import harmotab.track.LyricsTrack;
import harmotab.track.AccompanimentTrack;
import harmotab.track.HarmoTabTrack;

public class HTBScoreReader extends ScoreReader
{
    private static final int SCORE_TITLE = 0;
    private static final int SCORE_AUTHOR = 1;
    private static final int SCORE_COMMENT = 2;
    private static final int SCORE_HARMO = 3;
    private static final int SCORE_INFO_SUP = 4;
    private static final int SCORE_TEMPO = 5;
    private static final int SCORE_X_WIDTH = 6;
    private static final int NOTE_TYPE = 0;
    private static final int NOTE_HEIGHT = 1;
    private static final int NOTE_HOLE = 2;
    private static final int NOTE_DIRECTION = 3;
    private static final int NOTE_ARM = 4;
    private static final int NOTE_DOTED = 5;
    private static final int NOTE_REST = 6;
    private static final int NOTE_TRIPLET = 7;
    private static final int NOTE_TXT = 8;
    private static final int NOTE_EFFECT = 9;
    private static final int NOTE_BAR = 10;
    private static final int NOTE_CHORD = 11;
    private static final int NOTE_LINKED = 12;
    private static final int KEY_TYPE = 0;
    private static final int KEY_NUMBER = 1;
    private static final int KEY_REFERENCE = 2;
    private static final int KEY_ARM = 3;
    private static final int FIGURE_WHOLE = 0;
    private static final int FIGURE_HALF = 1;
    private static final int FIGURE_QUARTER = 2;
    private static final int FIGURE_EIGHTH = 3;
    private static final int FIGURE_SIXTEENTH = 4;
    private static final int FIGURE_APOGIATURE = 5;
    private static final int FIGURE_SIGN = 10;
    private static final int DIRECTION_UNDEFINED = 0;
    private static final int DIRECTION_BLOW = 1;
    private static final int DIRECTION_DRAW = 2;
    private static final int ARM_NONE = 0;
    private static final int ARM_SHARP = 1;
    private static final int ARM_FLAT = 2;
    private static final int ARM_NAT = 3;
    private static final int EFFECT_NONE = 0;
    private static final int EFFECT_BEND = 1;
    private static final int EFFECT_WAHWAH = 2;
    private static final int EFFECT_SLIDE = 3;
    private static final int BAR_NONE = 0;
    private static final int BAR_DEFAULT = 1;
    private static final int BAR_START = 2;
    private static final int BAR_END = 3;
    private static final int BAR_START_WITH_REP = 4;
    private static final int BAR_START_END = 5;
    private HarmoTabTrack m_htTrack;
    private AccompanimentTrack m_accTrack;
    private LyricsTrack m_lyricsTrack;
    private float m_currentTime;
    private float m_prevChordStartTime;
    private Accompaniment m_prevChord;
    private float m_prevLyricsStartTime;
    private Lyrics m_prevLyrics;
    private byte[] m_implicitAlteration;
    private KeySignature m_currentKeySignature;
    private TimeSignature m_currentTimeSignature;
    private float m_barCurrentTime;
    private Bar m_barToAdd;
    
    public HTBScoreReader(final Score score, final String path) {
        super(score, path);
        this.m_htTrack = null;
        this.m_accTrack = null;
        this.m_lyricsTrack = null;
        this.m_currentTime = 0.0f;
        this.m_prevChordStartTime = 0.0f;
        this.m_prevChord = null;
        this.m_prevLyricsStartTime = 0.0f;
        this.m_prevLyrics = null;
        this.m_currentKeySignature = null;
        this.m_currentTimeSignature = null;
        this.m_barCurrentTime = 0.0f;
        this.m_barToAdd = null;
    }
    
    @Override
    protected void read(final Score score, final File file) throws IOException {
        try {
            this.m_htTrack = new HarmoTabTrack(score);
            this.m_accTrack = new AccompanimentTrack(score, this.m_htTrack);
            this.m_lyricsTrack = new LyricsTrack(score, this.m_htTrack);
            this.m_currentTime = 0.0f;
            this.m_prevChordStartTime = 0.0f;
            this.m_prevChord = null;
            this.m_prevLyricsStartTime = 0.0f;
            this.m_prevLyrics = null;
            this.m_implicitAlteration = new byte[84];
            this.m_currentKeySignature = null;
            this.m_currentTimeSignature = null;
            this.m_barToAdd = null;
            final InputStream input = new FileInputStream(file);
            final Reader reader = new InputStreamReader(input);
            final Scanner scanner = new Scanner(reader);
            scanner.useDelimiter("'a7");
            int index = 0;
            while (scanner.hasNext()) {
                try {
                    if (index == 0) {
                        this.extractScoreProperties(scanner.next());
                    }
                    else {
                        this.extractNote(scanner.next());
                        this.manageBars();
                    }
                    ++index;
                }
                catch (Throwable exception) {
                    exception.printStackTrace();
                }
            }
            if (this.m_prevChord != null) {
                this.m_prevChord.setCustomDuration(this.m_currentTime - this.m_prevChordStartTime);
            }
            if (this.m_prevLyrics != null) {
                this.m_prevLyrics.setDurationObject(new Duration(this.m_currentTime - this.m_prevLyricsStartTime));
            }
            input.close();
            final TabModelController tabModelController = new TabModelController(this.m_htTrack.getTabModel());
            tabModelController.populateFromHarmoTabTrack(this.m_htTrack);
            this.m_score.addTrack(this.m_accTrack);
            this.m_score.addTrack(this.m_htTrack);
            this.m_score.addTrack(this.m_lyricsTrack);
        }
        catch (FileNotFoundException e) {
            e.printStackTrace();
            throw new IOException(e);
        }
        catch (IOException e2) {
            e2.printStackTrace();
            throw new IOException(e2);
        }
    }
    
    private void extractScoreProperties(final String input) {
        final Scanner scanner = new Scanner(input);
        scanner.useDelimiter("'a4");
        int index = 0;
        while (scanner.hasNext()) {
            String field = scanner.next();
            field = field.substring(0, field.length() - 1);
            switch (index) {
                case 0: {
                    this.m_score.setTitle(this.decodeRtfString(field.split("fs17 ")[1]));
                    break;
                }
                case 1: {
                    this.m_score.setSongwriter(this.decodeRtfString(field));
                    break;
                }
                case 2: {
                    this.m_score.setComment(this.decodeRtfString(field));
                    break;
                }
                case 3: {
                    this.m_htTrack.setHarmonica(new Harmonica());
                    break;
                }
                case 4: {
                    this.m_score.setDescription(this.decodeRtfString(field));
                    break;
                }
                case 5: {
                    this.m_score.setTempo(Integer.parseInt(field));
                    break;
                }
            }
            ++index;
        }
    }
    
    private boolean extractNote(final String input) {
        HarmoTabElement htElement = null;
        if (input.startsWith("10")) {
            return this.extractKey(input);
        }
        final Scanner scanner = new Scanner(input);
        scanner.useDelimiter("'a4");
        try {
            int index = 0;
            while (scanner.hasNext()) {
                String field = scanner.next();
                field = field.substring(0, field.length() - 1);
                Label_1169: {
                    if (field.length() > 0) {
                        switch (index) {
                            case 0: {
                                final int type = Integer.parseInt(field);
                                htElement = new HarmoTabElement();
                                Figure figure = null;
                                switch (type) {
                                    case 0: {
                                        figure = new Figure((byte)1);
                                        break;
                                    }
                                    case 1: {
                                        figure = new Figure((byte)2);
                                        break;
                                    }
                                    case 2: {
                                        figure = new Figure((byte)3);
                                        break;
                                    }
                                    case 3: {
                                        figure = new Figure((byte)4);
                                        break;
                                    }
                                    case 4: {
                                        figure = new Figure((byte)5);
                                        break;
                                    }
                                    case 5: {
                                        figure = new Figure((byte)6);
                                        break;
                                    }
                                    default: {
                                        throw new UnhandledCaseError("Unhandled note type '#" + type + "'");
                                    }
                                }
                                htElement.setFigure(figure);
                                break;
                            }
                            case 1: {
                                final int h = 23 - Integer.parseInt(field);
                                final byte note = (byte)(h % 7);
                                final int octave = h / 7 + 3;
                                htElement.setHeight(new Height(note, octave));
                                break;
                            }
                            case 2: {
                                final int hole = Integer.parseInt(field);
                                final Tab tab = new Tab(hole);
                                htElement.setTab(tab);
                                break;
                            }
                            case 3: {
                                final Tab tab2 = htElement.getTab();
                                final int direction = Integer.parseInt(field);
                                switch (direction) {
                                    case 1: {
                                        tab2.setDirection((byte)1);
                                        break;
                                    }
                                    case 2: {
                                        tab2.setDirection((byte)2);
                                        break;
                                    }
                                    case 0: {
                                        tab2.setDirection((byte)0);
                                        break;
                                    }
                                    default: {
                                        throw new UnhandledCaseError("Unhandled direction '#" + direction + "'");
                                    }
                                }
                                htElement.setTab(tab2);
                                break;
                            }
                            case 4: {
                                final int arm = Integer.parseInt(field);
                                switch (arm) {
                                    case 0: {
                                        break Label_1169;
                                    }
                                    case 1: {
                                        htElement.getHeight().setAlteration((byte)1);
                                        break Label_1169;
                                    }
                                    case 2: {
                                        htElement.getHeight().setAlteration((byte)2);
                                        break Label_1169;
                                    }
                                    case 3: {
                                        htElement.getHeight().setAlteration((byte)0);
                                        break Label_1169;
                                    }
                                    default: {
                                        throw new UnhandledCaseError("Unhandled arm '#" + arm + "'");
                                    }
                                }
                                break;
                            }
                            case 5: {
                                htElement.getFigure().setDotted(this.parseBoolean(field));
                                break;
                            }
                            case 6: {
                                htElement.setRest(this.parseBoolean(field));
                                break;
                            }
                            case 7: {
                                htElement.getFigure().setTriplet(this.parseBoolean(field));
                                break;
                            }
                            case 8: {
                                final String text = this.decodeRtfString(field.trim());
                                if (!text.equals("")) {
                                    final Lyrics lyrics = new Lyrics(text);
                                    if (this.m_prevLyrics != null) {
                                        this.m_prevLyrics.setDurationObject(new Duration(this.m_currentTime - this.m_prevLyricsStartTime));
                                    }
                                    else if (this.m_currentTime != 0.0f) {
                                        this.m_lyricsTrack.add(new Silence(this.m_currentTime));
                                    }
                                    this.m_lyricsTrack.add(lyrics);
                                    this.m_prevLyricsStartTime = this.m_currentTime;
                                    this.m_prevLyrics = lyrics;
                                    break;
                                }
                                break;
                            }
                            case 9: {
                                final int effect = Integer.parseInt(field);
                                switch (effect) {
                                    case 0: {
                                        break Label_1169;
                                    }
                                    case 1: {
                                        htElement.getTab().setBend((byte)1);
                                        break Label_1169;
                                    }
                                    case 2: {
                                        htElement.getTab().setEffect(new Effect((byte)1));
                                        break Label_1169;
                                    }
                                    case 3: {
                                        htElement.getTab().setEffect(new Effect((byte)2));
                                        break Label_1169;
                                    }
                                    default: {
                                        throw new UnhandledCaseError("Unhandled effect '#" + effect + "'");
                                    }
                                }
                                break;
                            }
                            case 10: {
                                final Bar barElement = new Bar();
                                final int bar = Integer.parseInt(field) % 10;
                                final int repeats = Integer.parseInt(field) / 10 + 1;
                                if (bar != 0) {
                                    this.m_barToAdd = barElement;
                                    switch (bar) {
                                        case 1: {
                                            break;
                                        }
                                        case 2:
                                        case 4: {
                                            this.m_barToAdd.setRepeatAttribute(new RepeatAttribute(true, false));
                                            break;
                                        }
                                        case 3: {
                                            this.m_barToAdd.setRepeatAttribute(new RepeatAttribute(false, true, repeats));
                                            break;
                                        }
                                        case 5: {
                                            this.m_barToAdd.setRepeatAttribute(new RepeatAttribute(true, true, repeats));
                                            break;
                                        }
                                        default: {
                                            throw new UnhandledCaseError("Unhandled bar type '#" + bar + "'");
                                        }
                                    }
                                    this.resetImplicitAlterations();
                                    break;
                                }
                                break;
                            }
                            case 11: {
                                final Accompaniment acc = this.extractChord(field);
                                if (acc != null) {
                                    if (this.m_prevChord != null) {
                                        this.m_prevChord.setCustomDuration(this.m_currentTime - this.m_prevChordStartTime);
                                    }
                                    else if (this.m_currentTime != 0.0f) {
                                        this.m_accTrack.add(0, new Silence(this.m_currentTime));
                                    }
                                    this.m_prevChordStartTime = this.m_currentTime;
                                    this.m_prevChord = acc;
                                    break;
                                }
                                break;
                            }
                            case 12: {
                                htElement.setTied(this.parseBoolean(field));
                                break;
                            }
                        }
                    }
                }
                ++index;
            }
            if (index <= 1) {
                return false;
            }
            final byte alteration = htElement.getHeight().getAlteration();
            final int noteSoundId = htElement.getHeight().getUnalteredSoundId();
            final byte implicitAlteration = this.m_implicitAlteration[noteSoundId];
            if (alteration == 0 && implicitAlteration != 0) {
                htElement.getHeight().setAlteration(implicitAlteration);
            }
            else {
                this.m_implicitAlteration[noteSoundId] = alteration;
            }
            this.m_htTrack.add(htElement);
        }
        catch (NumberFormatException e) {
            return false;
        }
        this.m_currentTime += htElement.getDuration();
        this.m_barCurrentTime += htElement.getDuration();
        return true;
    }
    
    private Accompaniment extractChord(final String input) {
        final Accompaniment acc = new Accompaniment();
        final Scanner scanner = new Scanner(input);
        scanner.useDelimiter("%");
        try {
            int index = 0;
            while (scanner.hasNext()) {
                final String field = scanner.next();
                if (index == 0) {
                    acc.setChord(new Chord(field));
                }
                else if (field.length() > 0) {
                    final int nid = Integer.parseInt(field, 16);
                    acc.getChord().addHeight(new Height(nid));
                }
                ++index;
            }
            acc.setCustomDuration(new Duration(1.0f));
            this.m_accTrack.add(acc);
        }
        catch (NumberFormatException e) {
            e.printStackTrace();
            return null;
        }
        return acc;
    }
    
    private boolean extractKey(final String input) {
        final Key key = new Key();
        final TimeSignature timeSignature = new TimeSignature();
        final KeySignature keySignature = new KeySignature();
        final Scanner scanner = new Scanner(input);
        scanner.useDelimiter("'a4");
        int index = 0;
        while (scanner.hasNext()) {
            String field = scanner.next();
            field = field.substring(0, field.length() - 1);
            switch (index) {
                case 1: {
                    timeSignature.setNumber(Byte.parseByte(field));
                    break;
                }
                case 2: {
                    timeSignature.setReference(Byte.parseByte(field));
                    break;
                }
                case 3: {
                    byte arm = 0;
                    if (field.length() == 7) {
                        while (arm < 7 && field.charAt(arm) == '1') {
                            ++arm;
                        }
                        if (arm == 0) {
                            while (arm > -7 && field.charAt(-arm) == '2') {
                                --arm;
                            }
                        }
                    }
                    keySignature.setIndex(arm);
                    break;
                }
            }
            ++index;
        }
        final Bar bar = new Bar(key, keySignature, timeSignature, new RepeatAttribute());
        this.m_currentKeySignature = keySignature;
        this.m_currentTimeSignature = timeSignature;
        this.m_htTrack.add(bar);
        this.resetImplicitAlterations();
        return true;
    }
    
    private void resetImplicitAlterations() {
        if (this.m_currentKeySignature != null) {
            final int keySignatureValue = this.m_currentKeySignature.getValue();
            for (int n = 0; n < 84; ++n) {
                this.m_implicitAlteration[n] = 0;
            }
            for (int i = 1; i < 6; ++i) {
                if (i <= keySignatureValue) {
                    final byte armNote = KeySignature.SHARP_ORDER[i].getNote();
                    for (int n2 = 36; n2 < 84; ++n2) {
                        final Height height = new Height(n2);
                        if (height.getAlteration() == 0 && height.getNote() == armNote) {
                            this.m_implicitAlteration[n2] = 1;
                        }
                    }
                }
                else if (-i >= keySignatureValue) {
                    final byte armNote = KeySignature.FLAT_ORDER[i].getNote();
                    for (int n2 = 36; n2 < 84; ++n2) {
                        final Height height = new Height(n2);
                        if (height.getAlteration() == 0 && height.getNote() == armNote) {
                            this.m_implicitAlteration[n2] = 2;
                        }
                    }
                }
            }
        }
    }
    
    private void manageBars() {
        if (this.m_barToAdd != null) {
            this.m_htTrack.add(this.m_barToAdd);
            this.m_barCurrentTime = 0.0f;
            this.m_barToAdd = null;
        }
        if (this.m_currentTimeSignature != null && this.m_barCurrentTime >= this.m_currentTimeSignature.getTimesPerBar()) {
            this.m_barCurrentTime = 0.0f;
            this.resetImplicitAlterations();
        }
    }
    
    private boolean parseBoolean(final String str) {
        return Boolean.parseBoolean(str) || (str.equals("Vrai") || str.equals("vrai"));
    }
    
    private String decodeRtfString(final String str) {
        String result = new String(str);
        for (int pos = result.indexOf("\\'", 0); pos != -1; pos = result.indexOf("\\'", pos + 1)) {
            final String valStr = result.substring(pos + 2, pos + 4);
            final char val = (char)Integer.parseInt(valStr, 16);
            result = result.replaceAll("\\\\'" + valStr, new StringBuilder(String.valueOf(val)).toString());
        }
        return result;
    }
}
