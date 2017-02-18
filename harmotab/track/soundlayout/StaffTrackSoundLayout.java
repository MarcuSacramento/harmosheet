// 
// Decompiled by Procyon v0.5.30
// 

package harmotab.track.soundlayout;

import harmotab.core.RepeatAttribute;
import harmotab.sound.RepeatItem;
import harmotab.sound.SoundItem;
import java.util.Iterator;
import harmotab.element.Bar;
import harmotab.element.Note;
import harmotab.element.Element;
import harmotab.track.Track;
import harmotab.sound.SoundSequence;

public class StaffTrackSoundLayout extends SoundLayout
{
    private static final float NO_CURRENT_PHRASE = -1.0f;
    public static final float APPOGIATURE_DURATION = 0.1f;
    private SoundSequence m_sounds;
    private float m_currentTime;
    private int m_trackId;
    private float m_phraseStartTime;
    private float m_tempo;
    private SoundItemGroup m_previousSound;
    private float m_appogiatureRetractTime;
    
    public StaffTrackSoundLayout(final Track track) {
        super(track);
    }
    
    @Override
    public void processSoundsPositionning(final SoundSequence sounds) {
        this.m_sounds = sounds;
        this.m_currentTime = 0.0f;
        this.m_trackId = this.getTrackId();
        this.m_phraseStartTime = -1.0f;
        this.m_tempo = this.getTempo();
        this.m_previousSound = new SoundItemGroup();
        this.m_appogiatureRetractTime = 0.0f;
        for (final Element element : this.getTrack()) {
            if (element instanceof Note) {
                this.add((Note)element);
            }
            else {
                if (!(element instanceof Bar)) {
                    continue;
                }
                this.manageBar((Bar)element);
            }
        }
    }
    
    private void add(final Note note) {
        float duration = 0.0f;
        if (note.getFigure().getType() == 6) {
            duration = 60.0f / this.m_tempo * 0.1f;
            this.m_appogiatureRetractTime += duration;
        }
        else {
            duration = 60.0f / this.m_tempo * note.getDuration() - this.m_appogiatureRetractTime;
            this.m_appogiatureRetractTime = 0.0f;
        }
        if (note.isTied() && !this.m_previousSound.isEmpty()) {
            this.m_previousSound.extend(duration);
            final SoundItem followItem = new SoundItem(note, this.m_trackId, -1, this.m_currentTime, duration);
            this.m_sounds.add(followItem);
            this.m_previousSound.add(followItem);
        }
        else {
            int soundId = note.getHeight().getSoundId();
            if (note.isRest()) {
                soundId = -1;
            }
            final SoundItem noteSound = new SoundItem(note, this.m_trackId, soundId, this.m_currentTime, duration);
            this.m_sounds.add(noteSound);
            this.m_previousSound.set(noteSound);
        }
        this.m_currentTime += duration;
    }
    
    private void manageBar(final Bar bar) {
        final RepeatAttribute repeat = bar.getRepeatAttribute();
        if (repeat != null) {
            if (repeat.isEnd()) {
                final int times = repeat.getRepeatTimes();
                if (times > 1) {
                    if (this.m_phraseStartTime != -1.0f) {
                        this.m_sounds.addRepeat(new RepeatItem(times, this.m_phraseStartTime, this.m_currentTime));
                    }
                    else {
                        System.err.println("StaffTrackSoundLayout:manageBar: No phrase starting found.");
                    }
                }
                this.m_phraseStartTime = -1.0f;
            }
            if (repeat.isBeginning()) {
                this.m_phraseStartTime = this.m_currentTime;
            }
        }
    }
}
