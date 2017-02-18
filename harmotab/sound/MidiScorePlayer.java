// 
// Decompiled by Procyon v0.5.30
// 

package harmotab.sound;

import javax.sound.midi.MetaMessage;
import java.util.Iterator;
import javax.sound.midi.InvalidMidiDataException;
import javax.sound.midi.MidiMessage;
import javax.sound.midi.MidiEvent;
import javax.sound.midi.ShortMessage;
import javax.sound.midi.MidiChannel;
import harmotab.desktop.ErrorMessenger;
import harmotab.core.Localizer;
import javax.sound.midi.MidiSystem;
import harmotab.core.GlobalPreferences;
import javax.sound.midi.Track;
import javax.sound.midi.Sequence;
import javax.sound.midi.Instrument;
import javax.sound.midi.Synthesizer;
import javax.sound.midi.Sequencer;
import javax.sound.midi.MetaEventListener;

public class MidiScorePlayer extends ScorePlayer implements MetaEventListener
{
    private static MidiScorePlayer m_instance;
    private Sequencer m_midiSequencer;
    private Synthesizer m_midiSynthesizer;
    private Instrument[] m_instruments;
    private Sequence m_midiSequence;
    private Track m_midiTrack;
    private MidiPlayerObserver m_observer;
    private boolean m_opened;
    private int m_volume;
    private float m_countdownOffset;
    
    static {
        MidiScorePlayer.m_instance = null;
    }
    
    public static synchronized MidiScorePlayer getInstance() {
        if (MidiScorePlayer.m_instance == null) {
            MidiScorePlayer.m_instance = new MidiScorePlayer();
        }
        return MidiScorePlayer.m_instance;
    }
    
    private MidiScorePlayer() {
        this.m_opened = false;
        this.m_volume = GlobalPreferences.getGlobalVolume();
        this.m_observer = new MidiPlayerObserver(this);
        this.m_countdownOffset = 0.0f;
    }
    
    @Override
    protected void finalize() {
        if (this.m_opened) {
            this.close();
        }
    }
    
    @Override
    public synchronized void open() {
        if (this.m_opened) {
            return;
        }
        this.m_sounds = null;
        this.m_midiTrack = null;
        try {
            final Synthesizer synthesizer = MidiSystem.getSynthesizer();
            this.m_midiSynthesizer = synthesizer;
            if (synthesizer == null) {
                ErrorMessenger.showErrorMessage(Localizer.get("M_NO_DEFAULT_MIDI_OUTPUT_ERROR"));
                return;
            }
            this.m_midiSynthesizer.open();
            (this.m_midiSequencer = MidiSystem.getSequencer()).addMetaEventListener(this);
            this.m_midiSequencer.setTempoInBPM(60.0f);
            this.m_midiSequence = new Sequence(0.0f, 100);
            if (this.m_midiSynthesizer.getDefaultSoundbank() == null) {
                throw new Exception("No sound bank for the default midi synthesizer.");
            }
            this.m_instruments = this.m_midiSynthesizer.getDefaultSoundbank().getInstruments();
            this.m_midiSynthesizer.loadInstrument(this.m_instruments[0]);
        }
        catch (Throwable throwable) {
            throwable.printStackTrace();
            this.m_observer.fireScorePlayerError(new ScorePlayerEvent(this), throwable);
            return;
        }
        this.m_opened = true;
        this.m_observer.fireScorePlayerStateChanged(new ScorePlayerEvent(this));
        this.setSounds(new SoundSequence());
        this.m_observer.setDispatchingEnabled(false);
        this.play();
        this.stop();
        this.m_observer.setDispatchingEnabled(true);
    }
    
    @Override
    public synchronized void close() {
        if (!this.m_opened) {
            return;
        }
        try {
            this.m_opened = false;
            this.m_midiSynthesizer.close();
            this.m_midiSequencer.close();
        }
        catch (Throwable throwable) {
            this.m_observer.fireScorePlayerError(new ScorePlayerEvent(this), throwable);
            return;
        }
        this.m_observer.fireScorePlayerStateChanged(new ScorePlayerEvent(this));
    }
    
    @Override
    public byte getState() {
        return (byte)(this.m_opened ? 2 : 1);
    }
    
    @Override
    public void setSounds(final SoundSequence sounds) {
        super.setSounds(sounds);
        this.updateSequence();
    }
    
    @Override
    public void setInstrument(final int channel, final int instrument) {
        this.m_midiSynthesizer.loadInstrument(this.m_instruments[instrument]);
        this.addEvent(channel, 0.0f, 192, instrument);
    }
    
    @Override
    public void setGlobalVolume(final int volume) {
        this.m_volume = volume;
        final MidiChannel[] channels = this.m_midiSynthesizer.getChannels();
        final float gain = volume / 100.0f;
        for (int i = 0; i < channels.length; ++i) {
            channels[i].controlChange(7, (int)(gain * 127.0));
        }
    }
    
    @Override
    public void setTrackVolume(final int channel, final int volume) {
        final float value = volume / 100.0f * 127.0f;
        try {
            final ShortMessage volMessage = new ShortMessage();
            volMessage.setMessage(176, channel, 7, (int)value);
            this.m_midiTrack.add(new MidiEvent(volMessage, 0L));
        }
        catch (InvalidMidiDataException e) {
            e.printStackTrace();
        }
    }
    
    @Override
    public void play() {
        if (!this.m_opened) {
            throw new SoundDeviceNotOpenedError("Midi device not opened !");
        }
        try {
            this.m_midiSequencer.open();
            this.m_midiSequencer.setSequence(this.m_midiSequence);
            this.setGlobalVolume(this.m_volume);
            this.m_midiSequencer.start();
            this.m_observer.firePlaybackStarted(new ScorePlayerEvent(this));
        }
        catch (Exception ex) {
            ex.printStackTrace();
        }
    }
    
    @Override
    public void stop() {
        if (!this.m_opened) {
            throw new SoundDeviceNotOpenedError("Midi device not opened !");
        }
        this.m_midiSequencer.stop();
        this.m_midiSequencer.close();
        this.m_observer.firePlaybackStopped(new ScorePlayerEvent(this), false);
    }
    
    @Override
    public boolean isPlaying() {
        return this.m_opened && this.m_midiSequencer.isRunning();
    }
    
    @Override
    public void pause() {
        if (!this.m_opened) {
            throw new SoundDeviceNotOpenedError("Midi device not opened !");
        }
        this.m_midiSequencer.stop();
        this.m_observer.firePlaybackPaused(new ScorePlayerEvent(this));
    }
    
    @Override
    public boolean isPaused() {
        return this.m_opened && (!this.m_midiSequencer.isRunning() && this.m_midiSequencer.getMicrosecondPosition() != 0L);
    }
    
    @Override
    public float getDuration() {
        if (!this.m_opened) {
            return 0.0f;
        }
        return this.m_midiSequencer.getMicrosecondLength() / 1000000.0f + this.m_countdownOffset;
    }
    
    @Override
    public float getPosition() {
        if (!this.m_opened) {
            return 0.0f;
        }
        return this.m_midiSequencer.getMicrosecondPosition() / 1000000.0f;
    }
    
    @Override
    public void setPosition(final SoundItem item) {
        if (!this.m_opened) {
            throw new SoundDeviceNotOpenedError("Midi device not opened !");
        }
        if (item == null) {
            throw new NullPointerException();
        }
        this.m_midiSequencer.setMicrosecondPosition((long)(item.getStartTime() * 1000000.0f));
    }
    
    @Override
    public void addSoundPlayerListener(final ScorePlayerListener listener) {
        this.m_observer.addScorePlayerListener(listener);
    }
    
    @Override
    public void removeSoundPlayerListener(final ScorePlayerListener listener) {
        this.m_observer.removeScorePlayerListener(listener);
    }
    
    private void updateSequence() {
        if (this.m_midiTrack != null) {
            this.m_midiSequence.deleteTrack(this.m_midiTrack);
        }
        this.m_midiTrack = this.m_midiSequence.createTrack();
        for (final SoundItem sound : this.m_sounds) {
            if (sound.getSoundId() != -1) {
                this.addEvent(sound.m_trackId, sound.m_startTime, 144, sound.m_soundId);
                this.addEvent(sound.m_trackId, sound.m_endTime, 128, sound.m_soundId);
            }
        }
    }
    
    private void addEvent(final int channel, final float time, final int type, final int num) {
        try {
            final long ticks = (long)(time * this.m_midiSequence.getResolution() * 2.0f);
            final ShortMessage message = new ShortMessage();
            message.setMessage(type + channel, num, 64);
            final MidiEvent event = new MidiEvent(message, ticks);
            this.m_midiTrack.add(event);
        }
        catch (Exception ex) {
            ex.printStackTrace();
        }
    }
    
    @Override
    public void meta(final MetaMessage message) {
        if (message.getType() == 47) {
            this.m_midiSequencer.setMicrosecondPosition(0L);
            this.m_observer.firePlaybackStopped(new ScorePlayerEvent(this), true);
        }
        else {
            System.out.println("Midi meta event : #" + message.getType());
        }
    }
}
