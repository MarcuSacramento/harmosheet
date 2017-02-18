// 
// Decompiled by Procyon v0.5.30
// 

package harmotab.track.layout;

import harmotab.renderer.ElementLocationIterator;
import harmotab.renderer.renderingelements.TiedNoteGroup;
import harmotab.core.Figure;
import java.util.Iterator;
import harmotab.renderer.renderingelements.EmptyArea;
import harmotab.renderer.renderingelements.Staff;
import java.util.ListIterator;
import harmotab.element.Element;
import harmotab.element.Note;
import harmotab.element.TrackElement;
import harmotab.track.Track;
import harmotab.core.Height;
import harmotab.renderer.LocationItem;
import harmotab.renderer.renderingelements.TripletGroup;
import harmotab.renderer.renderingelements.HoockedNoteGroup;
import harmotab.element.TimeSignature;
import harmotab.element.KeySignature;
import harmotab.element.Key;
import harmotab.element.Bar;
import harmotab.renderer.LocationList;

public class StaffTrackLayout extends TrackLayout
{
    private static final int DEFAULT_TRACK_HEIGHT = 110;
    private static final int SILENCE_ORDINATE;
    private static final int STAFF_ORDINATE;
    private static final int KEY_SIGNATURE_ORDINATE;
    private static final int TIME_SIGNATURE_ORDINATE;
    private static final int BAR_ORDINATE;
    private int m_trackId;
    private LocationList m_locations;
    private Bar m_currentBar;
    private Key m_currentKey;
    private KeySignature m_currentKeySignature;
    private TimeSignature m_currentTimeSignature;
    private int m_currentLine;
    private int m_currentX;
    private int m_widthUnitFactor;
    private int m_areaWidth;
    private int m_shifting;
    private int m_staffY;
    private int m_barNumber;
    protected int m_staffHeight;
    private int m_spacing;
    private int m_notesOnLineCount;
    private float m_barCurrentTime;
    private float m_currentTime;
    private int m_currentElementIndex;
    private HoockedNoteGroup m_currentHoockedGroup;
    private TripletGroup m_currentTripletGroup;
    private LocationItem m_previousNote;
    private byte[] m_implicitAlteration;
    
    static {
        SILENCE_ORDINATE = new Height((byte)4, 4).getOrdinate();
        STAFF_ORDINATE = new Height((byte)3, 5).getOrdinate();
        KEY_SIGNATURE_ORDINATE = new Height((byte)4).getOrdinate();
        TIME_SIGNATURE_ORDINATE = new Height((byte)6, 4).getOrdinate();
        BAR_ORDINATE = new Height((byte)3, 5).getOrdinate();
    }
    
    public StaffTrackLayout(final Track track) {
        super(track);
        this.m_locations = new LocationList();
    }
    
    @Override
    public void processElementsPositionning(final LocationList locations, final int areaWidth, final float scoreDuration) {
        this.m_locations.reset();
        this.m_trackId = this.getTrackId();
        this.m_areaWidth = areaWidth;
        this.m_currentBar = null;
        this.m_currentKey = null;
        this.m_currentKeySignature = null;
        this.m_currentTimeSignature = null;
        this.m_currentLine = 0;
        this.m_currentX = areaWidth;
        this.m_widthUnitFactor = 40;
        this.m_staffHeight = this.getTrackHeight();
        this.m_spacing = 4;
        this.m_shifting = 70 * this.m_spacing + 0 * this.m_spacing;
        this.m_notesOnLineCount = 0;
        this.m_barCurrentTime = 0.0f;
        this.m_currentTime = 0.0f;
        this.m_currentHoockedGroup = null;
        this.m_currentTripletGroup = null;
        this.m_previousNote = null;
        this.m_barNumber = -1;
        this.m_currentElementIndex = 0;
        this.m_implicitAlteration = new byte[84];
        this.m_staffY = StaffTrackLayout.STAFF_ORDINATE * this.m_spacing - this.m_shifting;
        final ListIterator<Element> i = this.m_track.listIterator();
        while (i.hasNext()) {
            this.m_currentElementIndex = i.nextIndex();
            final TrackElement trackElement = i.next();
            if (trackElement instanceof Note) {
                this.addNote((Note)trackElement);
            }
            else if (trackElement instanceof Bar) {
                this.addBar((Bar)trackElement);
            }
            else if (trackElement instanceof KeySignature) {
                this.addKeySignature((KeySignature)trackElement);
            }
            else {
                this.addElement(trackElement);
            }
            this.m_currentTime += trackElement.getDuration();
            if (this.m_currentX + trackElement.getWidthUnit() * this.m_widthUnitFactor > this.m_areaWidth) {
                this.lineFeed(false);
            }
        }
        this.fillTrackWithSilences(scoreDuration);
        this.lineFeed(true);
        locations.add(this.m_locations);
    }
    
    @Override
    public int getTrackHeight() {
        return 110;
    }
    
    private void lineFeed(final boolean trackEnds) {
        int previousLineLastElementIndex = -1;
        final LocationList lineChangeElements = new LocationList();
        if (this.m_currentLine > 0) {
            if (this.m_locations.getSize() > 1) {
                final ListIterator<LocationItem> it = this.m_locations.getListIterator(this.m_locations.getSize() - 1);
                LocationItem sameBarElement = it.next();
                if (sameBarElement.getElement() instanceof Bar) {
                    previousLineLastElementIndex = it.previousIndex();
                }
                else {
                    while (!(sameBarElement.getElement() instanceof Bar) && it.hasPrevious()) {
                        sameBarElement = it.previous();
                    }
                    previousLineLastElementIndex = it.nextIndex();
                    if (sameBarElement.getElement() instanceof Bar) {
                        it.next();
                        while (it.hasNext()) {
                            lineChangeElements.add(it.next());
                        }
                    }
                }
            }
            if (previousLineLastElementIndex == 0) {
                previousLineLastElementIndex = this.m_locations.getSize();
            }
            if (!trackEnds) {
                final LocationItem staffLoc = new LocationItem(new Staff(), this.m_areaWidth, this.m_staffY, 0, 0, this.m_areaWidth, this.m_staffHeight, this.m_trackId, this.m_currentLine, this.m_currentTime, this.m_spacing);
                this.m_locations.add(++previousLineLastElementIndex, staffLoc);
                staffLoc.setFlag(4, true);
            }
        }
        if (!trackEnds) {
            ++this.m_currentLine;
            this.m_currentX = 0;
            this.m_notesOnLineCount = 0;
            if (this.m_currentKey != null) {
                final LocationItem keyLoc = this.addElement(++previousLineLastElementIndex, this.m_currentKey, this.m_currentX, 0, this.m_currentKey.getWidthUnit(), this.m_staffHeight, this.m_currentKey.getOrdinate() * this.m_spacing - this.m_shifting, 0);
                keyLoc.setParent(this.m_currentBar);
                keyLoc.setFlag(4, true);
            }
            if (this.m_currentKeySignature != null) {
                final LocationItem keySignatureLoc = this.addElement(++previousLineLastElementIndex, this.m_currentKeySignature, this.m_currentX, 0, this.m_currentKeySignature.getWidthUnit(), this.m_staffHeight, StaffTrackLayout.KEY_SIGNATURE_ORDINATE * this.m_spacing - this.m_shifting, this.m_spacing);
                keySignatureLoc.setFlag(4, true);
            }
            if (this.m_currentTimeSignature != null) {
                final int timeY = StaffTrackLayout.TIME_SIGNATURE_ORDINATE * this.m_spacing - this.m_shifting;
                if (this.m_currentLine == 1) {
                    final LocationItem timeSignatureLoc = this.addElement(++previousLineLastElementIndex, this.m_currentTimeSignature, this.m_currentX, 0, this.m_currentTimeSignature.getWidthUnit(), this.m_staffHeight, timeY, 0);
                    timeSignatureLoc.setFlag(4, true);
                }
            }
            if (this.m_currentBar.getRepeatAttribute().isBeginning()) {
                final LocationItem prevBarItem = this.m_locations.get(this.m_currentBar);
                prevBarItem.setFlag(7, true);
                final LocationItem barLoc = this.addElement(this.m_currentBar, this.m_currentX, 0, this.m_currentBar.getWidthUnit(), this.m_staffHeight, StaffTrackLayout.BAR_ORDINATE * this.m_spacing - this.m_shifting, this.m_barNumber);
                barLoc.setFlag(8, true);
            }
            if (lineChangeElements.getSize() > 0) {
                final Iterator<LocationItem> it2 = lineChangeElements.getIterator();
                do {
                    final LocationItem locationItem;
                    final LocationItem item = locationItem = it2.next();
                    ++locationItem.m_line;
                    item.moveToX(this.m_currentX);
                    this.m_currentX = item.m_x2;
                } while (it2.hasNext());
            }
        }
        if (this.m_currentLine > 1 && !trackEnds) {
            ListIterator<LocationItem> it;
            LocationItem last;
            for (it = this.m_locations.getListIterator(this.m_locations.getSize() - 1), last = it.next(); it.hasPrevious() && (last.getLine() >= this.m_currentLine || !(last.getElement() instanceof Bar)); last = it.previous()) {}
            LocationItem first;
            for (first = last; it.hasPrevious() && !(first.getElement() instanceof Key); first = it.previous()) {}
            final double firstX = first.m_x1;
            final double lastX = last.m_poiX;
            final double factor = (this.m_areaWidth - firstX) / (lastX - firstX);
            LocationItem item2 = null;
            while (it.hasNext() && item2 != last) {
                item2 = it.next();
                if (!(item2.getElement() instanceof Staff)) {
                    item2.moveToX((int)((item2.m_x1 - firstX) * factor + firstX));
                    item2.resize((int)(item2.m_width * factor), item2.m_height);
                }
            }
            if (last.getElement() instanceof Bar) {
                last.m_poiX = last.m_x1 + last.m_width / 2 + 1;
            }
            else {
                System.err.println("StaffTrackLayout::lineFeed: last.getElement() instanceof Bar failed (" + last.getElement() + ")");
            }
        }
        if (trackEnds && this.m_locations.getSize() > 0 && this.m_currentTimeSignature != null) {
            LocationItem lastBarItem = null;
            final ListIterator<LocationItem> it3 = this.m_locations.getListIterator(this.m_locations.getSize() - 1);
            final LocationItem last2 = it3.next();
            if (last2.getElement() instanceof Bar) {
                lastBarItem = last2;
            }
            if (last2.getElement() instanceof Note && this.m_barCurrentTime >= this.m_currentTimeSignature.getTimesPerBar()) {
                ++this.m_currentElementIndex;
                lastBarItem = this.addBar(new Bar());
                lastBarItem.setFlag(4, true);
            }
            if (trackEnds && !this.m_locations.hasElementOfType((byte)(-1))) {
                final LocationItem emptyAreaLoc = new LocationItem(new EmptyArea(this.m_track), 0, this.m_staffY, this.m_currentX, 0, this.m_areaWidth - this.m_currentX, this.m_staffHeight, this.m_trackId, this.m_currentLine, this.m_currentTime, 0);
                this.m_locations.add(emptyAreaLoc);
                emptyAreaLoc.setFlag(4, true);
            }
            final LocationItem staffLoc2 = new LocationItem(new Staff(), (lastBarItem != null) ? lastBarItem.getPointOfInterestX() : this.m_areaWidth, this.m_staffY, 0, 0, this.m_areaWidth, this.m_staffHeight, this.m_trackId, this.m_currentLine, this.m_currentTime, this.m_spacing);
            this.m_locations.add(staffLoc2);
            staffLoc2.setFlag(4, true);
        }
    }
    
    private void fillTrackWithSilences(final float scoreDuration) {
        LocationItem emptyAreaLoc = null;
        while (this.m_currentTime < scoreDuration) {
            if (emptyAreaLoc == null) {
                emptyAreaLoc = new LocationItem(new EmptyArea(this.m_track), 0, this.m_staffY, this.m_currentX, 0, this.m_areaWidth - this.m_currentX, this.m_staffHeight, this.m_trackId, this.m_currentLine, this.m_currentTime, 0);
                this.m_locations.add(emptyAreaLoc);
                emptyAreaLoc.setFlag(4, true);
            }
            final float barEndTime = this.m_currentTimeSignature.getTimesPerBar();
            float remainingTime = barEndTime - this.m_barCurrentTime;
            if (remainingTime == 0.0f) {
                remainingTime = barEndTime;
            }
            if (this.m_currentTime + remainingTime > scoreDuration) {
                remainingTime = scoreDuration - this.m_currentTime;
            }
            final Note rest = new Note(new Figure(remainingTime));
            rest.setRest(true);
            final LocationItem item = this.addNote(rest);
            item.setFlag(4, true);
            if (this.m_currentX + rest.getWidthUnit() * this.m_widthUnitFactor > this.m_areaWidth) {
                this.lineFeed(false);
                final int index = this.m_locations.getSize() - 3;
                emptyAreaLoc = new LocationItem(new EmptyArea(this.m_track), 0, this.m_staffY, 0, 0, this.m_areaWidth, this.m_staffHeight, this.m_trackId, this.m_currentLine, this.m_currentTime, 0);
                this.m_locations.add(index, emptyAreaLoc);
                emptyAreaLoc.setFlag(4, true);
            }
            this.m_currentTime += rest.getDuration();
        }
    }
    
    private LocationItem addElement(final Element e, final int x, final int y, final float widthUnit, final int height, final int extra) {
        final int width = (int)(widthUnit * this.m_widthUnitFactor);
        final LocationItem newItem = LocationItem.newFromArea(e, x, y, width, height, this.m_trackId, this.m_currentLine, this.m_currentTime, extra);
        newItem.m_elementIndex = this.m_currentElementIndex;
        this.m_locations.add(newItem);
        this.m_currentX = x + width;
        return newItem;
    }
    
    private LocationItem addElement(final Element e, final int x, final int y, final float widthUnit, final int height, final int ordinate, final int extra) {
        final int width = (int)(widthUnit * this.m_widthUnitFactor);
        final LocationItem newItem = LocationItem.newFromOrdinate(e, x, y, width, height, ordinate, this.m_trackId, this.m_currentLine, this.m_currentTime, extra);
        newItem.m_elementIndex = this.m_currentElementIndex;
        this.m_locations.add(newItem);
        this.m_currentX = x + width;
        return newItem;
    }
    
    private LocationItem addElement(final int index, final Element e, final int x, final int y, final float widthUnit, final int height, final int ordinate, final int extra) {
        final int width = (int)(widthUnit * this.m_widthUnitFactor);
        final LocationItem newItem = LocationItem.newFromOrdinate(e, x, y, width, height, ordinate, this.m_trackId, this.m_currentLine, this.m_currentTime, extra);
        newItem.m_elementIndex = this.m_currentElementIndex;
        this.m_locations.add(index, newItem);
        this.m_currentX = x + width;
        return newItem;
    }
    
    private LocationItem addNote(final Note note) {
        LocationItem noteItem = null;
        int ordinate = note.getHeight().getOrdinate();
        if (note.isRest()) {
            ordinate = StaffTrackLayout.SILENCE_ORDINATE;
        }
        if (this.m_currentTimeSignature != null && this.m_barCurrentTime >= this.m_currentTimeSignature.getTimesPerBar()) {
            final LocationItem barItem = this.addBar(new Bar());
            barItem.setFlag(4, true);
        }
        if (note.isHookable() && (this.m_currentHoockedGroup == null || (this.m_currentTripletGroup == null && note.getFigure().isTriplet()) || (this.m_currentTripletGroup != null && !note.getFigure().isTriplet()))) {
            this.m_currentHoockedGroup = new HoockedNoteGroup();
            this.m_locations.add(LocationItem.newFromArea(this.m_currentHoockedGroup, this.m_currentX, 0, 0, 0, this.m_trackId, this.m_currentLine, this.m_currentTime, 0));
        }
        noteItem = this.addElement(note, this.m_currentX, 0, note.getWidthUnit(), this.getTrackHeight(), ordinate * this.m_spacing - this.m_shifting, this.m_spacing);
        if (note.isHookable()) {
            this.m_currentHoockedGroup.add(noteItem);
        }
        else {
            this.m_currentHoockedGroup = null;
        }
        if (note.isTied()) {
            if (this.m_previousNote != null) {
                final TiedNoteGroup tiedNotes = new TiedNoteGroup();
                tiedNotes.add(this.m_previousNote);
                tiedNotes.add(noteItem);
                this.m_locations.addBefore(noteItem, LocationItem.newFromArea(tiedNotes, this.m_previousNote.getX1(), this.m_previousNote.getY1(), 0, 0, noteItem.getTrackId(), this.m_previousNote.getLine(), -1.0f, 0));
            }
            else {
                System.err.println("StaffTrackLayout::addNote: Note tied without previous note.");
            }
        }
        if (note.getFigure().isTriplet()) {
            if (this.m_currentTripletGroup == null || note.getFigure().getType() != this.m_currentTripletGroup.getTripletFigure().getType()) {
                this.m_currentTripletGroup = new TripletGroup();
                this.m_locations.add(LocationItem.newFromArea(this.m_currentTripletGroup, this.m_currentX, 0, 0, 0, this.m_trackId, this.m_currentLine, this.m_currentTime, 0));
            }
            this.m_currentTripletGroup.add(noteItem);
        }
        else {
            this.m_currentTripletGroup = null;
        }
        if (!note.isRest()) {
            final byte alteration = note.getHeight().getAlteration();
            final int noteSoundId = note.getHeight().getUnalteredSoundId();
            final byte implicitAlteration = this.m_implicitAlteration[noteSoundId];
            if (alteration != implicitAlteration) {
                noteItem.setFlag(6, true);
                this.m_implicitAlteration[noteSoundId] = alteration;
            }
        }
        final float nextBarTime = this.m_barCurrentTime + note.getDuration();
        if (this.m_currentTimeSignature != null) {
            final float beatPeriod = this.m_currentTimeSignature.getTimesPerBeat();
            if (this.m_currentHoockedGroup != null && this.m_currentTripletGroup == null && Math.floor(nextBarTime / beatPeriod) != Math.floor(this.m_barCurrentTime / beatPeriod)) {
                this.m_currentHoockedGroup = null;
            }
            if (this.m_currentTripletGroup != null) {
                final float fullTripletBeatPeriod = beatPeriod * this.m_currentTripletGroup.getTripletFigure().getDuration() * 2.0f;
                if (this.m_currentTripletGroup.getGroupDuration() >= fullTripletBeatPeriod) {
                    this.m_currentTripletGroup = null;
                    this.m_currentHoockedGroup = null;
                }
            }
        }
        this.m_barCurrentTime = nextBarTime;
        ++this.m_notesOnLineCount;
        return this.m_previousNote = noteItem;
    }
    
    private LocationItem addBar(final Bar bar) {
        final float barCurrentTime = this.m_barCurrentTime;
        this.m_currentBar = bar;
        this.m_barCurrentTime = 0.0f;
        this.m_currentHoockedGroup = null;
        this.m_currentTripletGroup = null;
        ++this.m_barNumber;
        if (bar.getRepeatAttribute().isEnd() && bar.getRepeatAttribute().getRepeatTimes() > 1) {
            final ElementLocationIterator it = this.m_locations.getElementLocationIterator(this.m_locations.getSize() - 1, Bar.class);
            LocationItem prevBarItem = null;
            boolean prevBarIsPhraseStart = false;
            do {
                prevBarItem = it.previous();
                if (prevBarItem != null) {
                    final Bar prevBar = (Bar)prevBarItem.getElement();
                    if (!prevBar.getRepeatAttribute().isBeginning()) {
                        continue;
                    }
                    prevBarIsPhraseStart = true;
                }
            } while (!prevBarIsPhraseStart && prevBarItem != null);
            if (prevBarIsPhraseStart) {
                prevBarItem.setFlag(9, true);
            }
        }
        if (bar.getKey() != null) {
            this.m_currentKey = bar.getKey();
        }
        LocationItem newBarLocation = null;
        if (this.m_currentLine != 0 || this.m_notesOnLineCount != 0 || !bar.getRepeatAttribute().isSingle()) {
            newBarLocation = this.addElement(bar, this.m_currentX, 0, bar.getWidthUnit(), this.m_staffHeight, StaffTrackLayout.BAR_ORDINATE * this.m_spacing - this.m_shifting, this.m_barNumber);
            final float waitedTimesPerBar = this.m_currentTimeSignature.getTimesPerBar();
            if (barCurrentTime > waitedTimesPerBar + 0.001f) {
                newBarLocation.setFlag(0, true);
                newBarLocation.setFlag(11, true);
            }
            if (barCurrentTime < waitedTimesPerBar - 0.001f && this.m_barNumber != 1) {
                newBarLocation.setFlag(0, true);
                newBarLocation.setFlag(10, true);
            }
        }
        if (bar.getKeySignature() != null) {
            final boolean isFirstKeySignature = this.m_currentKeySignature == null;
            this.m_currentKeySignature = bar.getKeySignature();
            if (!isFirstKeySignature) {
                this.addElement(this.m_currentKeySignature, this.m_currentX, 0, this.m_currentKeySignature.getWidthUnit(), this.m_staffHeight, StaffTrackLayout.KEY_SIGNATURE_ORDINATE * this.m_spacing - this.m_shifting, this.m_spacing);
            }
        }
        if (bar.getTimeSignature() != null) {
            this.m_currentTimeSignature = bar.getTimeSignature();
            final int timeY = StaffTrackLayout.TIME_SIGNATURE_ORDINATE * this.m_spacing - this.m_shifting;
            if (this.m_currentLine == 1) {
                this.addElement(this.m_currentTimeSignature, this.m_currentX, 0, this.m_currentTimeSignature.getWidthUnit(), this.m_staffHeight, timeY, 0);
            }
        }
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
        return newBarLocation;
    }
    
    private LocationItem addKeySignature(final KeySignature ks) {
        this.m_currentKeySignature = ks;
        return null;
    }
    
    private LocationItem addElement(final Element e) {
        return this.addElement(e, this.m_currentX, 0, e.getWidthUnit(), this.m_staffHeight, 0);
    }
}
