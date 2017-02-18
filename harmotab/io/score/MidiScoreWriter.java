// 
// Decompiled by Procyon v0.5.30
// 

package harmotab.io.score;

import javax.sound.midi.InvalidMidiDataException;
import javax.sound.midi.MidiMessage;
import javax.sound.midi.MidiEvent;
import javax.sound.midi.ShortMessage;
import java.util.Iterator;
import java.io.IOException;
import javax.sound.midi.MidiSystem;
import harmotab.sound.SoundItem;
import harmotab.track.Track;
import harmotab.sound.SoundSequence;
import javax.sound.midi.Sequence;
import java.io.File;
import harmotab.core.Score;

public class MidiScoreWriter extends ScoreWriter
{
    public MidiScoreWriter(final Score score, final String path) {
        super(score, path);
    }
    
    @Override
    protected void write(final Score score, final File file) throws IOException {
        try {
            final Sequence sequence = new Sequence(0.0f, 100);
            SoundSequence sounds = new SoundSequence();
            for (final Track track : score) {
                track.getSoundLayout().processSoundsPositionning(sounds);
            }
            sounds = sounds.mergeRepeats();
            final javax.sound.midi.Track midiTrack = sequence.createTrack();
            for (final SoundItem sound : sounds) {
                if (sound.getSoundId() != -1) {
                    this.addEvent(sequence, midiTrack, sound.m_trackId, sound.m_startTime, 144, sound.m_soundId);
                    this.addEvent(sequence, midiTrack, sound.m_trackId, sound.m_endTime, 128, sound.m_soundId);
                }
            }
            for (final Track track2 : score) {
                final int channel = score.getTrackId(track2);
                this.setInstrument(sequence, midiTrack, channel, track2.getInstrument());
                this.setTrackVolume(sequence, midiTrack, channel, track2.getVolume());
            }
            final int[] fileTypes = MidiSystem.getMidiFileTypes(sequence);
            if (fileTypes.length == 0) {
                throw new Exception("Cannot get midi file types for the sequence.");
            }
            if (MidiSystem.write(sequence, fileTypes[0], file) == -1) {
                throw new Exception("An error occured writing midi file.");
            }
        }
        catch (Exception e) {
            throw new IOException(e);
        }
    }
    
    private void addEvent(final Sequence sequence, final javax.sound.midi.Track midiTrack, final int channel, final float time, final int type, final int num) {
        try {
            final long ticks = (long)(time * sequence.getResolution() * 2.0f);
            final ShortMessage message = new ShortMessage();
            message.setMessage(type + channel, num, 64);
            final MidiEvent event = new MidiEvent(message, ticks);
            midiTrack.add(event);
        }
        catch (Exception ex) {
            ex.printStackTrace();
        }
    }
    
    private void setInstrument(final Sequence sequence, final javax.sound.midi.Track midiTrack, final int channel, final int instrument) {
        this.addEvent(sequence, midiTrack, channel, 0.0f, 192, instrument);
    }
    
    private void setTrackVolume(final Sequence sequence, final javax.sound.midi.Track midiTrack, final int channel, final int volume) {
        final float value = volume / 100.0f * 127.0f;
        try {
            final ShortMessage volMessage = new ShortMessage();
            volMessage.setMessage(176, channel, 7, (int)value);
            midiTrack.add(new MidiEvent(volMessage, 0L));
        }
        catch (InvalidMidiDataException e) {
            e.printStackTrace();
        }
    }
}
