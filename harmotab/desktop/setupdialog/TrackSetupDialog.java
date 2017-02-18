// 
// Decompiled by Procyon v0.5.30
// 

package harmotab.desktop.setupdialog;

import javax.swing.JPanel;
import harmotab.desktop.DesktopController;
import javax.swing.JComponent;
import harmotab.core.Localizer;
import java.awt.Window;
import javax.swing.JTextArea;
import javax.swing.JSlider;
import harmotab.desktop.components.InstrumentChooser;
import javax.swing.JTextField;
import harmotab.track.Track;

public class TrackSetupDialog extends SetupDialog
{
    private static final long serialVersionUID = 1L;
    private Track m_track;
    private SetupCategory m_trackSetupCategory;
    private JTextField m_trackNameText;
    private InstrumentChooser m_instrumentChooser;
    private JSlider m_volumeSlider;
    private JTextArea m_commentsArea;
    
    public static TrackSetupDialog create(final Window parent, final Track track) {
        return new TrackSetupDialog(parent, track);
    }
    
    protected TrackSetupDialog(final Window parent, final Track track) {
        super(parent, Localizer.get("ET_TRACK_SETUP"));
        this.m_track = null;
        this.m_trackSetupCategory = null;
        this.m_trackNameText = null;
        this.m_instrumentChooser = null;
        this.m_volumeSlider = null;
        this.m_commentsArea = null;
        this.m_track = track;
        this.m_trackSetupCategory = new SetupCategory(Localizer.get("ET_TRACK_SETUP_CATEGORY"));
        this.m_trackNameText = new JTextField(this.m_track.getName());
        this.m_instrumentChooser = new InstrumentChooser(this.m_track.getInstrument());
        (this.m_commentsArea = new JTextArea(this.m_track.getComment())).setBorder(this.m_trackNameText.getBorder());
        (this.m_volumeSlider = new JSlider(0, 100, this.m_track.getVolume())).setMinorTickSpacing(5);
        this.m_volumeSlider.setMajorTickSpacing(10);
        this.m_volumeSlider.setPaintLabels(true);
        this.m_volumeSlider.setPaintTicks(true);
        this.m_volumeSlider.setOpaque(false);
        final JPanel trackSetupPane = this.m_trackSetupCategory.getPanel();
        trackSetupPane.add(this.createSetupSeparator(Localizer.get("ET_TRACK_SETUP")));
        trackSetupPane.add(this.createSetupField(Localizer.get("N_NAME"), this.m_trackNameText));
        trackSetupPane.add(this.createSetupSeparator(Localizer.get("ET_INSTRUMENT_SETUP")));
        trackSetupPane.add(this.createSetupField(Localizer.get("N_INSTRUMENT"), this.m_instrumentChooser));
        trackSetupPane.add(this.createSetupField(Localizer.get("N_VOLUME"), this.m_volumeSlider));
        trackSetupPane.add(this.createSetupSeparator(Localizer.get("N_COMMENTS")));
        trackSetupPane.add(this.createSetupField(Localizer.get("N_COMMENTS"), this.m_commentsArea));
        this.addSetupCategory(this.m_trackSetupCategory);
        final boolean scoreEditable = DesktopController.getInstance().getScoreController().isScoreEditable();
        this.m_trackNameText.setEnabled(scoreEditable);
        this.m_commentsArea.setEnabled(scoreEditable);
        this.displayCategory(this.m_trackSetupCategory);
    }
    
    public Track getTrack() {
        return this.m_track;
    }
    
    @Override
    protected void discard() {
    }
    
    @Override
    protected boolean save() {
        this.m_track.setName(this.m_trackNameText.getText());
        this.m_track.setInstrument(this.m_instrumentChooser.getSelectedIndex());
        this.m_track.setVolume(this.m_volumeSlider.getValue());
        this.m_track.setComment(this.m_commentsArea.getText());
        return true;
    }
}
