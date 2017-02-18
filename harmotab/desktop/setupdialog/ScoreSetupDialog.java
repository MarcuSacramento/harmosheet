// 
// Decompiled by Procyon v0.5.30
// 

package harmotab.desktop.setupdialog;

import java.awt.event.ActionEvent;
import harmotab.element.KeySignature;
import harmotab.element.TimeSignature;
import harmotab.harmonica.TabModelController;
import java.awt.Component;
import javax.swing.JOptionPane;
import harmotab.element.HarmoTabElement;
import harmotab.element.Bar;
import harmotab.core.undo.UndoManager;
import harmotab.core.Height;
import java.util.Iterator;
import javax.swing.JPanel;
import harmotab.harmonica.Harmonica;
import java.awt.event.ActionListener;
import harmotab.core.ScoreStatistics;
import harmotab.core.GlobalPreferences;
import harmotab.desktop.components.TrackSetupComponent;
import harmotab.track.Track;
import javax.swing.JComponent;
import harmotab.harmonica.TabModel;
import javax.swing.Icon;
import harmotab.desktop.ActionIcon;
import javax.swing.SpinnerModel;
import javax.swing.SpinnerNumberModel;
import harmotab.track.HarmoTabTrack;
import harmotab.core.Localizer;
import java.awt.Window;
import javax.swing.JLabel;
import harmotab.desktop.components.TabModelEditor;
import harmotab.desktop.components.NumberOfHolesChooser;
import harmotab.desktop.components.HarmonicaTypeChooser;
import harmotab.desktop.components.HarmonicaTunningChooser;
import javax.swing.JButton;
import harmotab.desktop.components.TimeSignatureChooser;
import harmotab.desktop.components.TonalityChooser;
import javax.swing.JTextArea;
import javax.swing.JSpinner;
import javax.swing.JTextField;
import harmotab.core.Score;
import harmotab.core.ScoreController;

public class ScoreSetupDialog extends SetupDialog
{
    private static final long serialVersionUID = 1L;
    public static int SCORE_PROPERTIES_TAB;
    public static int HARMONICA_PROPERTIES_TAB;
    protected ScoreController m_scoreController;
    protected Score m_score;
    protected boolean m_modelChanged;
    private SetupCategory m_scoreSetupCategory;
    private JTextField m_titleText;
    private JTextField m_songwriterText;
    private JTextField m_commentText;
    private JSpinner m_tempoSpinner;
    private JTextArea m_descriptionTextArea;
    private TonalityChooser m_tonalityChooser;
    private TimeSignatureChooser m_timeSignatureChooser;
    private SetupCategory m_harmonicaSetupCategory;
    private JButton m_createFromModelButton;
    private JTextField m_harmonicaNameText;
    private HarmonicaTunningChooser m_harmonicaTunningChooser;
    private HarmonicaTypeChooser m_harmonicaTypeChooser;
    private NumberOfHolesChooser m_numberOfHolesChooser;
    private TabModelEditor m_tabModelEditor;
    private SetupCategory m_statsSetupCategory;
    private JLabel m_statsLabel;
    private SetupCategory m_tracksSetupCategory;
    
    static {
        ScoreSetupDialog.SCORE_PROPERTIES_TAB = 0;
        ScoreSetupDialog.HARMONICA_PROPERTIES_TAB = 1;
    }
    
    public ScoreSetupDialog(final Window parent, final ScoreController controller) {
        super(parent, Localizer.get("ET_SCORE_SETUP"));
        this.m_scoreController = null;
        this.m_score = null;
        this.m_modelChanged = false;
        this.m_scoreSetupCategory = null;
        this.m_titleText = null;
        this.m_songwriterText = null;
        this.m_commentText = null;
        this.m_tempoSpinner = null;
        this.m_descriptionTextArea = null;
        this.m_tonalityChooser = null;
        this.m_timeSignatureChooser = null;
        this.m_harmonicaSetupCategory = null;
        this.m_createFromModelButton = null;
        this.m_harmonicaNameText = null;
        this.m_harmonicaTunningChooser = null;
        this.m_harmonicaTypeChooser = null;
        this.m_numberOfHolesChooser = null;
        this.m_tabModelEditor = null;
        this.m_statsSetupCategory = null;
        this.m_statsLabel = null;
        this.m_tracksSetupCategory = null;
        this.m_scoreController = controller;
        this.m_score = this.m_scoreController.getScore();
        this.m_modelChanged = false;
        final HarmoTabTrack htTrack = (HarmoTabTrack)this.m_score.getTrack(HarmoTabTrack.class, 0);
        this.m_scoreSetupCategory = new SetupCategory(Localizer.get("ET_SCORE_SETUP_CATEGORY"));
        this.m_harmonicaSetupCategory = new SetupCategory(Localizer.get("N_HARMONICA"));
        this.m_tracksSetupCategory = new SetupCategory(Localizer.get("ET_TRACKS_SETUP_CATEGORY"));
        this.m_statsSetupCategory = new SetupCategory(Localizer.get("ET_STATISTICS"));
        this.m_titleText = new JTextField(this.m_score.getTitleString());
        this.m_songwriterText = new JTextField(this.m_score.getSongwriterString());
        this.m_commentText = new JTextField(this.m_score.getCommentString());
        this.m_tempoSpinner = new JSpinner(new SpinnerNumberModel(this.m_score.getTempoValue(), 10, 500, 1));
        (this.m_descriptionTextArea = new JTextArea(5, 20)).setBorder(new JTextField().getBorder());
        this.m_descriptionTextArea.setFont(new JTextField().getFont());
        this.m_descriptionTextArea.setLineWrap(true);
        this.m_descriptionTextArea.setText(this.m_score.getDescription());
        this.m_tonalityChooser = new TonalityChooser((byte)0);
        this.m_timeSignatureChooser = new TimeSignatureChooser();
        final Harmonica harmonica = htTrack.getHarmonica();
        this.m_createFromModelButton = new JButton(Localizer.get("ET_CREATE_FROM_HARMONICA_MODEL"), ActionIcon.getIcon((byte)5));
        this.m_harmonicaNameText = new JTextField(harmonica.getName());
        this.m_harmonicaTunningChooser = new HarmonicaTunningChooser(harmonica.getTunning());
        this.m_harmonicaTypeChooser = new HarmonicaTypeChooser(harmonica.getModel().getHarmonicaType());
        this.m_numberOfHolesChooser = new NumberOfHolesChooser(harmonica.getModel().getNumberOfHoles());
        this.m_tabModelEditor = new TabModelEditor((TabModel)htTrack.getTabModel().clone(), harmonica.getModel().getHarmonicaType().hasPiston());
        final JPanel scoreSetupPane = this.m_scoreSetupCategory.getPanel();
        scoreSetupPane.add(this.createSetupSeparator(Localizer.get("ET_SCORE_SETUP")));
        scoreSetupPane.add(this.createSetupField(Localizer.get("N_SCORE_TITLE"), this.m_titleText));
        scoreSetupPane.add(this.createSetupField(Localizer.get("N_SCORE_SONGWRITER"), this.m_songwriterText));
        scoreSetupPane.add(this.createSetupField(Localizer.get("N_SCORE_COMMENT"), this.m_commentText));
        scoreSetupPane.add(this.createSetupField(Localizer.get("N_TEMPO"), this.m_tempoSpinner));
        scoreSetupPane.add(this.createSetupField(Localizer.get("N_DESCRIPTION"), this.m_descriptionTextArea));
        scoreSetupPane.add(this.createSetupSeparator(Localizer.get("N_STAFF_TRACK")));
        scoreSetupPane.add(this.createSetupField(Localizer.get("N_TONALITY"), this.m_tonalityChooser));
        scoreSetupPane.add(this.createSetupField(Localizer.get("N_TIME_SIGNATURE"), this.m_timeSignatureChooser));
        final JPanel harmonicaSetupPane = this.m_harmonicaSetupCategory.getPanel();
        harmonicaSetupPane.add(this.createSetupSeparator(Localizer.get("N_HARMONICA")));
        harmonicaSetupPane.add(this.createSetupField(null, this.m_createFromModelButton));
        harmonicaSetupPane.add(this.createSetupField(null, new JLabel("")));
        harmonicaSetupPane.add(this.createSetupField(Localizer.get("N_NAME"), this.m_harmonicaNameText));
        harmonicaSetupPane.add(this.createSetupField(Localizer.get("N_HARMONICA_TYPE"), this.m_harmonicaTypeChooser));
        harmonicaSetupPane.add(this.createSetupField(Localizer.get("N_HOLES"), this.m_numberOfHolesChooser));
        harmonicaSetupPane.add(this.createSetupField(Localizer.get("N_TUNNING"), this.m_harmonicaTunningChooser));
        harmonicaSetupPane.add(this.createSetupField(null, new JLabel("")));
        harmonicaSetupPane.add(this.createSetupField(null, this.m_tabModelEditor));
        final JPanel tracksSetupPane = this.m_tracksSetupCategory.getPanel();
        tracksSetupPane.add(this.createSetupSeparator("Tracks"));
        int i = 1;
        for (final Track track : this.m_score) {
            tracksSetupPane.add(this.createSetupField(String.valueOf(Localizer.get("N_TRACK")) + " " + i++, new TrackSetupComponent(this.getWindow(), track)));
        }
        GlobalPreferences.getMetronomeFeatureEnabled();
        final ScoreStatistics stats = new ScoreStatistics(this.m_score);
        String statsString = "";
        statsString = String.valueOf(statsString) + Localizer.get("ET_TRACKS_COUNT") + ": " + stats.getTracksCount() + "<br>";
        statsString = String.valueOf(statsString) + Localizer.get("ET_PLAYBACK_DURATION") + ": " + stats.getPlaybackDurationSec() + " s<br>";
        statsString = String.valueOf(statsString) + Localizer.get("ET_BARS_COUNT") + ": " + stats.getBarsCount() + "<br>";
        statsString = String.valueOf(statsString) + Localizer.get("ET_SCORE_ITEMS_COUNT") + ": " + stats.getItemsCount() + "<br>";
        statsString = String.valueOf(statsString) + Localizer.get("ET_SCORE_DISPLAYED_ITEMS_COUNT") + ": " + stats.getDisplayedItemsCount() + "<br>";
        (this.m_statsLabel = new JLabel("<html>" + statsString + "</html>")).setOpaque(false);
        final JPanel statsSetupPane = this.m_statsSetupCategory.getPanel();
        statsSetupPane.add(this.createSetupSeparator(Localizer.get("ET_STATISTICS")));
        statsSetupPane.add(this.createSetupField(null, this.m_statsLabel));
        this.addSetupCategory(this.m_scoreSetupCategory);
        this.addSetupCategory(this.m_harmonicaSetupCategory);
        this.addSetupCategory(this.m_tracksSetupCategory);
        this.addSetupCategory(this.m_statsSetupCategory);
        final boolean editable = this.m_scoreController.isScoreEditable();
        this.m_titleText.setEnabled(editable);
        this.m_songwriterText.setEnabled(editable);
        this.m_commentText.setEnabled(editable);
        this.m_tempoSpinner.setEnabled(editable);
        this.m_descriptionTextArea.setEnabled(editable);
        this.m_tonalityChooser.setEnabled(editable);
        this.m_timeSignatureChooser.setEnabled(editable);
        this.m_createFromModelButton.addActionListener(new TabMappingWizardAction((TabMappingWizardAction)null));
        this.displayCategory(this.m_scoreSetupCategory);
    }
    
    public void setSelectedTabMappingHeight(final Height height) {
        this.m_tabModelEditor.goTo(height);
    }
    
    @Override
    protected void discard() {
    }
    
    @Override
    protected boolean save() {
        final UndoManager undoManager = UndoManager.getInstance();
        undoManager.addUndoCommand(this.m_score.createRestoreCommand(), Localizer.get("MENU_SCORE_PROPERTIES"));
        this.m_score.setDispachEvents(false, null);
        this.m_score.setTitle(this.m_titleText.getText());
        this.m_score.setSongwriter(this.m_songwriterText.getText());
        this.m_score.setComment(this.m_commentText.getText());
        this.m_score.setTempo((int)this.m_tempoSpinner.getValue());
        this.m_score.setDescription(this.m_descriptionTextArea.getText());
        final HarmoTabTrack htTrack = (HarmoTabTrack)this.m_score.getTrack(HarmoTabTrack.class, 0);
        final Bar firstBar = (Bar)htTrack.get(Bar.class, 0);
        final TimeSignature timeSignature = firstBar.getTimeSignature();
        undoManager.appendToLastUndoCommand(timeSignature.createRestoreCommand());
        timeSignature.setNumber(this.m_timeSignatureChooser.getNumber());
        timeSignature.setReference(this.m_timeSignatureChooser.getReference());
        final KeySignature keySignature = firstBar.getKeySignature();
        undoManager.appendToLastUndoCommand(keySignature.createRestoreCommand());
        keySignature.setIndex(this.m_tonalityChooser.getTonality());
        undoManager.appendToLastUndoCommand(htTrack.createRestoreCommand());
        htTrack.setTabModel(this.m_tabModelEditor.getTabModel());
        htTrack.setHarmonica(new Harmonica(this.m_harmonicaNameText.getText()));
        htTrack.getHarmonica().getModel().setNumberOfHoles(this.m_numberOfHolesChooser.getNumberOfHoles());
        htTrack.getHarmonica().getModel().setHarmonicaType(this.m_harmonicaTypeChooser.getSelectedHarmonicaType());
        htTrack.getHarmonica().setTunning(this.m_harmonicaTunningChooser.getSelectedTunning());
        if (this.m_modelChanged || (this.m_tabModelEditor.hasTabModelChanged() && htTrack.get(HarmoTabElement.class, 0) != null)) {
            final TabModel tabModel = htTrack.getTabModel();
            if (tabModel != null) {
                final int res = JOptionPane.showConfirmDialog(this, Localizer.get("M_UPDATE_TAB_MAPPING_QUESTION"), "HarmoTab", 0);
                if (res == 0) {
                    final TabModelController tmc = new TabModelController(tabModel);
                    tmc.updateTabs(htTrack);
                }
            }
        }
        this.m_score.setDispachEvents(true, "propertiesChanged");
        return true;
    }
    
    private class TabMappingWizardAction implements ActionListener
    {
        @Override
        public void actionPerformed(final ActionEvent event) {
            final TabModelWizard wizard = new TabModelWizard(ScoreSetupDialog.this.getWindow());
            wizard.setTabModel(ScoreSetupDialog.this.m_tabModelEditor.getTabModel());
            wizard.setVisible(true);
            if (wizard.getTabModel() != null) {
                final Harmonica harmonica = wizard.getHarmonica();
                ScoreSetupDialog.this.m_harmonicaNameText.setText(harmonica.getModel().getName());
                ScoreSetupDialog.this.m_harmonicaTunningChooser.setSelectedItem(harmonica.getTunning().getNoteName());
                ScoreSetupDialog.this.m_harmonicaTypeChooser.setSelectedHarmonicaType(harmonica.getModel().getHarmonicaType());
                ScoreSetupDialog.this.m_numberOfHolesChooser.setValue(harmonica.getModel().getNumberOfHoles());
                ScoreSetupDialog.this.m_modelChanged = true;
            }
        }
    }
}
