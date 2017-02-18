// 
// Decompiled by Procyon v0.5.30
// 

package harmotab.desktop.setupdialog;

import javax.swing.event.ChangeEvent;
import java.awt.event.ActionEvent;
import harmotab.io.score.ScoreWriter;
import java.io.IOException;
import harmotab.io.score.PngFileWriter;
import java.io.File;
import harmotab.desktop.ErrorMessenger;
import javax.swing.event.ChangeListener;
import java.awt.event.ActionListener;
import javax.swing.JComponent;
import harmotab.core.GlobalPreferences;
import javax.swing.JLabel;
import java.awt.GridLayout;
import javax.swing.SpinnerModel;
import rvt.util.gui.LabelledSpinner;
import javax.swing.SpinnerNumberModel;
import java.awt.Component;
import java.awt.LayoutManager;
import java.awt.Container;
import javax.swing.BoxLayout;
import javax.swing.JPanel;
import javax.swing.AbstractButton;
import harmotab.core.Localizer;
import javax.swing.ButtonGroup;
import java.awt.Window;
import rvt.util.gui.FileField;
import javax.swing.JSpinner;
import javax.swing.JRadioButton;
import harmotab.core.Score;

public class PngExportSetupDialog extends SetupDialog
{
    private static final long serialVersionUID = 1L;
    private Score m_score;
    private JRadioButton m_onePageButton;
    private JRadioButton m_severalPagesButton;
    private JSpinner m_widthSpinner;
    private JSpinner m_heightSpinner;
    private FileField m_outputFolderField;
    private FileField m_outputFileNameField;
    
    public PngExportSetupDialog(final Window parent, final Score score) {
        super(parent, "Image export");
        this.m_score = null;
        this.m_onePageButton = null;
        this.m_severalPagesButton = null;
        this.m_widthSpinner = null;
        this.m_heightSpinner = null;
        this.m_outputFolderField = null;
        this.m_outputFileNameField = null;
        this.m_score = score;
        ButtonGroup pagingOptionsGroup = new ButtonGroup();
        (this.m_onePageButton = new JRadioButton(Localizer.get("ET_ONE_FILE_WHOLE_SCORE"), true)).setOpaque(false);
        (this.m_severalPagesButton = new JRadioButton(Localizer.get("ET_ONE_FILE_PER_PAGE"), false)).setOpaque(false);
        pagingOptionsGroup = new ButtonGroup();
        pagingOptionsGroup.add(this.m_onePageButton);
        pagingOptionsGroup.add(this.m_severalPagesButton);
        final JPanel pagingPane = new JPanel();
        pagingPane.setOpaque(false);
        pagingPane.setLayout(new BoxLayout(pagingPane, 3));
        pagingPane.add(this.m_onePageButton);
        pagingPane.add(this.m_severalPagesButton);
        (this.m_widthSpinner = new LabelledSpinner("px", new SpinnerNumberModel(900, 200, Integer.MAX_VALUE, 10))).setOpaque(false);
        (this.m_heightSpinner = new LabelledSpinner("px", new SpinnerNumberModel(1200, 200, Integer.MAX_VALUE, 10))).setOpaque(false);
        this.m_heightSpinner.setEnabled(false);
        final JPanel sizePanel = new JPanel(new GridLayout(2, 3));
        sizePanel.setOpaque(false);
        sizePanel.add(new JLabel(Localizer.get("ET_WIDTH_LABEL")));
        sizePanel.add(this.m_widthSpinner);
        sizePanel.add(new JLabel(" "));
        sizePanel.add(new JLabel(Localizer.get("ET_HEIGHT_LABEL")));
        sizePanel.add(this.m_heightSpinner);
        sizePanel.add(new JLabel(" "));
        this.m_outputFolderField = new FileField(GlobalPreferences.getUserDefaultDirectory(), true);
        this.m_outputFileNameField = new FileField(String.valueOf(this.m_score.getScoreName()) + ".png", false);
        final SetupCategory exportSetupCategory = new SetupCategory(Localizer.get("ET_EXPORT_PNG_IMAGE_SETUP_CATEGORY"));
        final JPanel pane = exportSetupCategory.getPanel();
        pane.add(this.createSetupField(Localizer.get("ET_PAGES"), pagingPane));
        pane.add(this.createSetupSeparator(Localizer.get("ET_PAGE_FORMAT")));
        pane.add(this.createSetupField(Localizer.get("ET_SIZE"), sizePanel));
        pane.add(this.createSetupSeparator(Localizer.get("ET_OUTPUT_FILE")));
        pane.add(this.createSetupField(Localizer.get("ET_OUTPUT_FOLDER"), this.m_outputFolderField));
        pane.add(this.createSetupField(Localizer.get("ET_FILENAME"), this.m_outputFileNameField));
        this.addSetupCategory(exportSetupCategory);
        final RadioToggleObserver radioToggleListener = new RadioToggleObserver((RadioToggleObserver)null);
        this.m_onePageButton.addActionListener(radioToggleListener);
        this.m_severalPagesButton.addActionListener(radioToggleListener);
        this.m_outputFileNameField.addChangeListener(new FileNameChangedListener((FileNameChangedListener)null));
    }
    
    @Override
    protected boolean save() {
        String path = "";
        if (this.m_outputFileNameField.getFile() == null) {
            ErrorMessenger.showErrorMessage(this.getWindow(), Localizer.get("M_NO_FILENAME_ERROR"));
            return false;
        }
        if (this.m_outputFolderField.getFile() == null || !this.m_outputFolderField.getFile().exists() || !this.m_outputFolderField.getFile().isDirectory()) {
            ErrorMessenger.showErrorMessage(this.getWindow(), Localizer.get("M_NO_FOLDER_NAME_ERROR"));
            return false;
        }
        path = String.valueOf(this.m_outputFolderField.getFile().getAbsolutePath()) + File.separator + this.m_outputFileNameField.getFile().getName();
        if (path.toUpperCase().endsWith(".PNG")) {
            path = path.substring(0, path.length() - 4);
        }
        final int width = ((Number)this.m_widthSpinner.getValue()).intValue();
        int height = ((Number)this.m_heightSpinner.getValue()).intValue();
        if (this.m_onePageButton.isSelected()) {
            height = Integer.MAX_VALUE;
        }
        final ScoreWriter writer = new PngFileWriter(this.m_score, path, width, height);
        try {
            writer.save();
        }
        catch (IOException e) {
            e.printStackTrace();
            ErrorMessenger.showErrorMessage(this.getWindow(), Localizer.get("M_FILE_WRITE_ERROR").replace("%FILE%", path));
        }
        return true;
    }
    
    @Override
    protected void discard() {
    }
    
    private class RadioToggleObserver implements ActionListener
    {
        @Override
        public void actionPerformed(final ActionEvent event) {
            PngExportSetupDialog.this.m_heightSpinner.setEnabled(PngExportSetupDialog.this.m_severalPagesButton.isSelected());
        }
    }
    
    private class FileNameChangedListener implements ChangeListener
    {
        @Override
        public void stateChanged(final ChangeEvent event) {
            final File file = PngExportSetupDialog.this.m_outputFileNameField.getFile();
            if (file != null) {
                if (file.getParent() != null && !file.getParent().equals("")) {
                    PngExportSetupDialog.this.m_outputFolderField.setFile(file.getParent());
                }
                PngExportSetupDialog.this.m_outputFileNameField.setFile(file.getName());
            }
        }
    }
}
