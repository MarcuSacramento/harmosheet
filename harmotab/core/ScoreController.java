// 
// Decompiled by Procyon v0.5.30
// 

package harmotab.core;

import java.util.Iterator;
import harmotab.performance.Performance;
import java.awt.print.PrinterException;
import java.awt.print.Printable;
import harmotab.io.score.ScorePrintable;
import java.awt.print.PrinterJob;
import harmotab.io.score.PngFileWriter;
import java.awt.Window;
import harmotab.desktop.setupdialog.PngExportSetupDialog;
import harmotab.io.score.HT3XScoreWriter;
import java.io.IOException;
import harmotab.io.score.MidiScoreWriter;
import java.io.File;
import javax.swing.JOptionPane;
import harmotab.io.score.ScoreReader;
import harmotab.io.score.ScoreIOUtilities;
import harmotab.throwables.ScoreIoException;
import java.awt.Component;
import javax.swing.filechooser.FileFilter;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.JFileChooser;
import harmotab.track.StaffTrack;
import harmotab.core.undo.UndoManager;
import harmotab.desktop.ErrorMessenger;
import harmotab.track.LyricsTrack;
import harmotab.track.Track;
import harmotab.track.AccompanimentTrack;
import harmotab.element.Element;
import harmotab.element.Bar;
import harmotab.element.TimeSignature;
import harmotab.element.KeySignature;
import harmotab.element.Key;
import harmotab.track.HarmoTabTrack;
import harmotab.sound.MidiScorePlayer;
import java.util.ArrayList;
import harmotab.performance.PerformancesList;
import harmotab.io.score.ScoreWriter;
import harmotab.sound.ScorePlayer;
import harmotab.performance.PerformanceListListener;

public class ScoreController implements PerformanceListListener
{
    public static final String SCORE_OPENNED_EVENT = "scoreOpenned";
    public static final String NEW_SCORE_EVENT = "newScore";
    public static final String SCORE_SAVED_EVENT = "scoreSaved";
    public static final String PERFORMANCE_LIST_CHANGED_EVENT = "performanceList";
    private Score m_score;
    private ScorePlayer m_scorePlayer;
    private ScoreWriter m_currentScoreWriter;
    private boolean m_scoreChanged;
    private boolean m_exportedScore;
    private PerformancesList m_performancesList;
    private ArrayList<ScoreControllerListener> m_listeners;
    
    public ScoreController(final Score score) {
        this.m_score = null;
        this.m_scorePlayer = null;
        this.m_currentScoreWriter = null;
        this.m_scoreChanged = false;
        this.m_exportedScore = false;
        this.m_performancesList = null;
        this.m_listeners = null;
        this.m_listeners = new ArrayList<ScoreControllerListener>();
        this.m_performancesList = null;
        this.setScore(score);
    }
    
    public ScoreController() {
        this(null);
    }
    
    protected void setScore(final Score score) {
        this.m_score = score;
        if (this.m_score != null) {
            this.m_score.addObjectListener(new ScoreObserver((ScoreObserver)null));
        }
        this.setScorePlayer(null);
        this.m_currentScoreWriter = null;
        this.m_scoreChanged = false;
        this.fireScoreControlledChanged();
    }
    
    public Score getScore() {
        return this.m_score;
    }
    
    public boolean hasScore() {
        return this.m_score != null;
    }
    
    public boolean isScoreEditable() {
        return !this.m_exportedScore;
    }
    
    public boolean isExportedScore() {
        return this.m_exportedScore;
    }
    
    public boolean hasScoreChanged() {
        return this.m_scoreChanged;
    }
    
    public ScorePlayer getScorePlayer() {
        return this.m_scorePlayer;
    }
    
    public void setScorePlayer(final ScorePlayer player) {
        final ScorePlayer midiScorePlayer = MidiScorePlayer.getInstance();
        if (this.m_scorePlayer != null && this.m_scorePlayer.isOpenned() && this.m_scorePlayer != midiScorePlayer) {
            this.m_scorePlayer.close();
        }
        this.m_scorePlayer = ((player != null) ? player : midiScorePlayer);
        if (!this.m_scorePlayer.isOpenned()) {
            if (this.m_scorePlayer == midiScorePlayer) {
                this.m_scorePlayer.asynchronousOpen();
            }
            else {
                this.m_scorePlayer.open();
            }
        }
        this.fireScorePlayerChanged();
    }
    
    public void setDefaultSoundPlayer() {
        this.setScorePlayer(null);
    }
    
    public PerformancesList getPerformancesList() {
        return this.m_performancesList;
    }
    
    public void setPerformancesList(final PerformancesList perfsList) {
        if (this.m_performancesList != null) {
            this.m_performancesList.removePerformanceListListener(this);
        }
        this.m_performancesList = perfsList;
        if (this.m_performancesList != null) {
            this.m_performancesList.addPerformanceListListener(this);
        }
    }
    
    public ScoreWriter getCurrentScoreWriter() {
        return this.m_currentScoreWriter;
    }
    
    public boolean createNewDefaultScore() {
        if (this.close()) {
            this.setScore(new Score());
            this.m_score.setDispachEvents(false, null);
            this.m_exportedScore = false;
            try {
                final StaffTrack harmoTabTrack = new HarmoTabTrack(this.m_score);
                harmoTabTrack.add(new Bar(new Key(), new KeySignature(), new TimeSignature(), new RepeatAttribute()));
                final Track accompanimentTrack = new AccompanimentTrack(this.m_score, harmoTabTrack);
                final Track lyricsTrack = new LyricsTrack(this.m_score, harmoTabTrack);
                this.m_score.addTrack(accompanimentTrack);
                this.m_score.addTrack(harmoTabTrack);
                this.m_score.addTrack(lyricsTrack);
            }
            catch (Throwable e) {
                e.printStackTrace();
                ErrorMessenger.showErrorMessage(Localizer.get("M_NEW_SCORE_ERROR"));
            }
            this.m_score.setDispachEvents(true, "newScore");
            UndoManager.getInstance().reset();
            this.fireScoreControlledChanged();
            return true;
        }
        return false;
    }
    
    public boolean open() throws ScoreIoException {
        final JFileChooser chooser = new JFileChooser();
        chooser.setFileFilter(new FileNameExtensionFilter(Localizer.get("ET_HARMOTAB_SCORE"), new String[] { "ht3", "htb", "ht3x" }));
        if (chooser.showOpenDialog(null) == 0) {
            final String path = chooser.getSelectedFile().getPath();
            final boolean openned = this.open(path);
            return openned;
        }
        return false;
    }
    
    public boolean open(final String filePath) throws ScoreIoException {
        if (!this.close()) {
            return false;
        }
        try {
            if (!this.hasScore()) {
                this.setScore(new Score());
            }
            this.m_score.setDispachEvents(false, null);
            final ScoreReader reader = ScoreIOUtilities.createScoreReader(this.m_score, filePath);
            reader.open();
            this.m_exportedScore = reader.isExportedScore();
            this.setPerformancesList(reader.getPerformancesList());
            this.m_currentScoreWriter = ScoreIOUtilities.createScoreWriter(this.m_score, filePath, this.m_performancesList);
            try {
                this.m_score.setDispachEvents(true, "scoreOpenned");
            }
            catch (Throwable e) {
                System.err.println("ScoreController::open()");
                e.printStackTrace();
            }
            UndoManager.getInstance().reset();
            this.fireScoreControlledChanged();
        }
        catch (Throwable e2) {
            this.close();
            throw new ScoreIoException(e2, filePath);
        }
        return true;
    }
    
    public boolean saveScore() {
        this.m_currentScoreWriter = ScoreIOUtilities.saveScore(null, this, this.m_currentScoreWriter);
        if (this.m_currentScoreWriter != null) {
            this.m_score.setDispachEvents(true, "scoreSaved");
            return true;
        }
        return false;
    }
    
    public boolean saveScoreAs() {
        this.m_currentScoreWriter = ScoreIOUtilities.saveScore(null, this, null);
        if (this.m_currentScoreWriter != null) {
            this.m_score.setDispachEvents(true, "scoreSaved");
            return true;
        }
        return false;
    }
    
    public boolean saveScoreAs(final String path) {
        final ScoreWriter writer = ScoreIOUtilities.createScoreWriter(this.m_score, path);
        if (writer != null) {
            this.m_currentScoreWriter = ScoreIOUtilities.saveScore(null, this, writer);
            if (this.m_currentScoreWriter != null) {
                this.m_score.setDispachEvents(true, "scoreSaved");
                return true;
            }
        }
        return false;
    }
    
    public void saveAs(final String path) throws ScoreIoException {
        final ScoreWriter writer = ScoreIOUtilities.createScoreWriter(this.m_score, path, this.m_performancesList);
        if (writer != null) {
            this.m_currentScoreWriter = ScoreIOUtilities.saveScore(null, this, writer);
            if (this.m_currentScoreWriter != null) {
                this.m_score.setDispachEvents(true, "scoreSaved");
            }
            return;
        }
        throw new ScoreIoException(null, path);
    }
    
    public void save() throws ScoreIoException {
        if (this.m_currentScoreWriter != null) {
            this.saveAs(this.m_currentScoreWriter.getFile().getAbsolutePath());
        }
        else {
            this.saveScoreAs();
        }
    }
    
    public boolean close() {
        if (!this.hasScore()) {
            return true;
        }
        if (this.m_scoreChanged) {
            final int res = JOptionPane.showConfirmDialog(null, Localizer.get("M_SAVE_BEFORE_CLOSING_QUESTION"), "HarmoTab", 1);
            if (res == 2) {
                return false;
            }
            if (res == 0) {
                this.m_currentScoreWriter = ScoreIOUtilities.saveScore(null, this, this.m_currentScoreWriter);
                if (this.m_currentScoreWriter == null) {
                    return false;
                }
            }
        }
        this.m_currentScoreWriter = null;
        this.m_performancesList = null;
        UndoManager.getInstance().reset();
        this.setScore(null);
        return true;
    }
    
    public boolean exportAsMidi() {
        final JFileChooser chooser = new JFileChooser();
        chooser.setFileFilter(new FileNameExtensionFilter(Localizer.get("ET_MIDI_FILE"), new String[] { "mid", "midi" }));
        chooser.setSelectedFile(new File(String.valueOf(this.m_score.getScoreName()) + ".mid"));
        if (chooser.showSaveDialog(null) == 0) {
            if (chooser.getSelectedFile().exists()) {
                final int res = JOptionPane.showConfirmDialog(null, Localizer.get("M_FILE_ALREADY_EXISTS_QUESTION").replace("%FILE%", chooser.getSelectedFile().getPath()), Localizer.get("ET_EXPORT_AS_MIDI_FILE"), 0);
                if (res != 0) {
                    return false;
                }
            }
            String outputFile = chooser.getSelectedFile().getPath();
            if (!outputFile.endsWith(".mid") && !outputFile.endsWith(".midi")) {
                outputFile = String.valueOf(outputFile) + ".mid";
            }
            final MidiScoreWriter writer = new MidiScoreWriter(this.m_score, outputFile);
            try {
                writer.save();
                return true;
            }
            catch (Exception e) {
                e.printStackTrace();
                ErrorMessenger.showErrorMessage(Localizer.get("M_ERROR_CREATING_FILE").replace("%FILE%", chooser.getSelectedFile().getPath()));
            }
        }
        return false;
    }
    
    public boolean exportAsMidi(final String path) throws IOException {
        final MidiScoreWriter writer = new MidiScoreWriter(this.m_score, path);
        writer.save();
        return true;
    }
    
    public File exportAsExportedScore() {
        final JFileChooser chooser = new JFileChooser();
        chooser.setFileFilter(new FileNameExtensionFilter(Localizer.get("ET_HT3X_FILE"), new String[] { "ht3x" }));
        chooser.setSelectedFile(new File(String.valueOf(this.m_score.getScoreName()) + ".ht3x"));
        if (chooser.showSaveDialog(null) == 0) {
            if (chooser.getSelectedFile().exists()) {
                final int res = JOptionPane.showConfirmDialog(null, Localizer.get("M_FILE_ALREADY_EXISTS_QUESTION").replace("%FILE%", chooser.getSelectedFile().getPath()), Localizer.get("ET_EXPORT_AS_HT3X_FILE"), 0);
                if (res != 0) {
                    return null;
                }
            }
            String outputFile = chooser.getSelectedFile().getPath();
            if (!outputFile.endsWith(".ht3x")) {
                outputFile = String.valueOf(outputFile) + ".ht3x";
            }
            final HT3XScoreWriter ht3xWriter = new HT3XScoreWriter(this.m_score, outputFile);
            try {
                ht3xWriter.save();
                return new File(outputFile);
            }
            catch (Exception e) {
                e.printStackTrace();
                ErrorMessenger.showErrorMessage(Localizer.get("M_ERROR_CREATING_FILE").replace("%FILE%", chooser.getSelectedFile().getPath()));
            }
        }
        return null;
    }
    
    public boolean exportAsExportedScore(final String path) throws IOException {
        final HT3XScoreWriter ht3xWriter = new HT3XScoreWriter(this.m_score, path);
        ht3xWriter.save();
        return true;
    }
    
    public void exportAsImage() {
        new PngExportSetupDialog(null, this.m_score).setVisible(true);
    }
    
    public void exportAsImage(final String path) throws IOException {
        final int width = 900;
        final int height = Integer.MAX_VALUE;
        final ScoreWriter writer = new PngFileWriter(this.m_score, path, width, height);
        writer.save();
    }
    
    public void print() {
        final PrinterJob job = PrinterJob.getPrinterJob();
        job.setPrintable(new ScorePrintable(this.m_score));
        job.setJobName(this.m_score.getScoreName());
        if (job.printDialog()) {
            try {
                job.print();
            }
            catch (PrinterException ex) {
                ex.printStackTrace();
                ErrorMessenger.showErrorMessage(Localizer.get("M_PRINTING_ERROR"));
            }
        }
    }
    
    @Override
    public void onPerformanceListChanged(final PerformancesList list) {
        this.m_score.fireObjectChanged("performanceList");
    }
    
    @Override
    public void onDefaultPerformanceChanged(final PerformancesList list) {
        this.m_score.fireObjectChanged("performanceList");
    }
    
    @Override
    public void onPerformanceListItemChanged(final PerformancesList list, final Performance perf) {
        this.m_score.fireObjectChanged("performanceList");
    }
    
    public void addScoreControllerListener(final ScoreControllerListener listener) {
        this.m_listeners.add(listener);
    }
    
    public void removeScoreControllerListener(final ScoreControllerListener listener) {
        this.m_listeners.remove(listener);
    }
    
    protected void fireScoreControlledChanged() {
        for (final ScoreControllerListener listener : this.m_listeners) {
            listener.onControlledScoreChanged(this, this.m_score);
        }
    }
    
    protected void fireScorePlayerChanged() {
        for (final ScoreControllerListener listener : this.m_listeners) {
            listener.onScorePlayerChanged(this, this.m_scorePlayer);
        }
    }
    
    static /* synthetic */ void access$0(final ScoreController scoreController, final boolean scoreChanged) {
        scoreController.m_scoreChanged = scoreChanged;
    }
    
    private class ScoreObserver implements HarmoTabObjectListener
    {
        @Override
        public void onObjectChanged(final HarmoTabObjectEvent event) {
            if (event.propertyIs("scoreOpenned") || event.propertyIs("scoreSaved") || event.propertyIs("newScore")) {
                ScoreController.access$0(ScoreController.this, false);
            }
            else {
                ScoreController.access$0(ScoreController.this, true);
            }
            if (ScoreController.this.m_scorePlayer != null && ScoreController.this.m_scorePlayer.isPlaying()) {
                ScoreController.this.m_scorePlayer.stop();
            }
        }
    }
}
