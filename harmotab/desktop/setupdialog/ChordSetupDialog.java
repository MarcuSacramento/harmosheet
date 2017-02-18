// 
// Decompiled by Procyon v0.5.30
// 

package harmotab.desktop.setupdialog;

import harmotab.sound.ScorePlayerEvent;
import harmotab.desktop.ErrorMessenger;
import harmotab.element.Element;
import harmotab.sound.SoundItem;
import harmotab.sound.SoundSequence;
import java.awt.event.ActionEvent;
import harmotab.sound.MidiScorePlayer;
import harmotab.sound.ScorePlayer;
import harmotab.sound.ScorePlayerListener;
import javax.swing.AbstractAction;
import java.util.Iterator;
import harmotab.core.Height;
import java.awt.event.ActionListener;
import java.awt.Component;
import java.awt.LayoutManager;
import javax.swing.JPanel;
import java.awt.BorderLayout;
import javax.swing.JComponent;
import javax.swing.Icon;
import harmotab.desktop.ActionIcon;
import harmotab.core.Localizer;
import java.awt.Window;
import javax.swing.JButton;
import javax.swing.JLabel;
import harmotab.desktop.components.ChordChooser;
import harmotab.element.Chord;

public class ChordSetupDialog extends SetupDialog
{
    private static final long serialVersionUID = 1L;
    private Chord m_chord;
    private ChordChooser m_chordChooser;
    private JLabel m_compositionLabel;
    private JButton m_playButton;
    
    public ChordSetupDialog(final Window parent, final Chord chord) {
        super(parent, Localizer.get("ET_CHORD_SETUP"));
        this.m_chord = null;
        this.m_chordChooser = null;
        this.m_compositionLabel = null;
        this.m_playButton = null;
        this.m_chord = chord;
        this.m_chordChooser = new ChordChooser(this.m_chord);
        this.m_playButton = new JButton(ActionIcon.getIcon((byte)6));
        this.m_compositionLabel = new JLabel("");
        final SetupCategory chordSetupCategory = new SetupCategory(Localizer.get("N_CHORD"));
        final JPanel chordSetupPane = chordSetupCategory.getPanel();
        chordSetupPane.add(this.createSetupSeparator(Localizer.get("ET_CHORD_SETUP")));
        chordSetupPane.add(this.createSetupField(Localizer.get("N_CHORD"), this.m_chordChooser));
        chordSetupPane.add(this.createSetupSeparator(Localizer.get("ET_PREVIEW")));
        final JPanel playPanel = new JPanel(new BorderLayout(30, 10));
        playPanel.setOpaque(false);
        playPanel.add(this.m_playButton, "West");
        playPanel.add(this.m_compositionLabel, "Center");
        chordSetupPane.add(this.createSetupField(Localizer.get("ET_LISTEN"), playPanel));
        this.addSetupCategory(chordSetupCategory);
        this.m_chordChooser.addActionListener(new ChordChangedAction((ChordChangedAction)null));
        this.m_playButton.addActionListener(new PlayChordAction((PlayChordAction)null));
        this.update();
        this.displayCategory(chordSetupCategory);
    }
    
    private void update() {
        final Chord chord = this.m_chordChooser.getChord();
        String composition = "";
        boolean addPeriod = false;
        for (final Height height : chord.getHeights()) {
            composition = String.valueOf(composition) + (addPeriod ? ", " : "") + height.getNoteName();
            addPeriod = true;
        }
        this.m_compositionLabel.setText("<html><h2>" + chord.getName() + "</h2>" + composition + "</html>");
    }
    
    @Override
    protected void discard() {
    }
    
    @Override
    protected boolean save() {
        this.m_chord.set(this.m_chordChooser.getChord());
        return true;
    }
    
    private class PlayChordAction extends AbstractAction implements ScorePlayerListener
    {
        private static final long serialVersionUID = 1L;
        private static final float PLAY_DURATION = 2.0f;
        private static final float HEIGHT_SEPARATION_DELAY = 0.1f;
        ScorePlayer player;
        
        private PlayChordAction() {
            this.player = MidiScorePlayer.getInstance();
        }
        
        @Override
        public void actionPerformed(final ActionEvent event) {
            final int trackId = 0;
            if (this.player.getState() == 2 && !this.player.isPlaying()) {
                final SoundSequence sounds = new SoundSequence();
                float start = 0.0f;
                float end = 2.0f;
                for (final Height height : ChordSetupDialog.this.m_chordChooser.getChord().getHeights()) {
                    sounds.add(new SoundItem(null, 0, height.getSoundId(), start, end));
                    start += 0.1f;
                    end -= 0.1f;
                }
                this.player.setSounds(sounds);
                this.player.setInstrument(0, 0);
                this.player.setTrackVolume(0, 100);
                this.player.addSoundPlayerListener(this);
                this.player.play();
            }
            else {
                ErrorMessenger.showErrorMessage(ChordSetupDialog.this.getWindow(), Localizer.get("M_MIDI_OUTPUT_ERROR"));
            }
        }
        
        @Override
        public void onPlaybackStarted(final ScorePlayerEvent event) {
            ChordSetupDialog.this.m_playButton.setEnabled(false);
        }
        
        @Override
        public void onPlaybackStopped(final ScorePlayerEvent event, final boolean endOfPlayback) {
            ChordSetupDialog.this.m_playButton.setEnabled(true);
            this.player.removeSoundPlayerListener(this);
        }
        
        @Override
        public void onPlaybackPaused(final ScorePlayerEvent event) {
        }
        
        @Override
        public void onPlayedSoundItemChanged(final ScorePlayerEvent event) {
        }
        
        @Override
        public void onScorePlayerError(final ScorePlayerEvent event, final Throwable error) {
        }
        
        @Override
        public void onScorePlayerStateChanged(final ScorePlayerEvent event) {
        }
    }
    
    private class ChordChangedAction extends AbstractAction
    {
        private static final long serialVersionUID = 1L;
        
        @Override
        public void actionPerformed(final ActionEvent e) {
            ChordSetupDialog.this.update();
        }
    }
}
