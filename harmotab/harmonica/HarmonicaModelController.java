// 
// Decompiled by Procyon v0.5.30
// 

package harmotab.harmonica;

import harmotab.core.HarmoTabObjectEvent;
import rvt.util.gui.FileUtilities;
import java.io.IOException;
import harmotab.desktop.ErrorMessenger;
import harmotab.io.harmonica.HarmonicaModelReader;
import java.io.File;
import harmotab.core.GlobalPreferences;
import javax.swing.filechooser.FileFilter;
import javax.swing.JFileChooser;
import java.awt.Component;
import javax.swing.JOptionPane;
import harmotab.core.Localizer;
import harmotab.core.HarmoTabObjectListener;
import harmotab.io.harmonica.HarmonicaModelWriter;

public class HarmonicaModelController
{
    private HarmonicaModel m_model;
    private HarmonicaModelWriter m_writer;
    private boolean m_modelHasChanged;
    private ModelObserver m_modelObserver;
    
    public HarmonicaModelController(final HarmonicaModel model) {
        this.m_model = null;
        this.m_writer = null;
        this.m_modelHasChanged = false;
        this.m_modelObserver = null;
        this.m_model = model;
        this.m_writer = null;
        this.m_modelHasChanged = false;
        this.m_modelObserver = new ModelObserver();
        this.m_model.addObjectListener(this.m_modelObserver);
    }
    
    public void finalize() {
        this.m_model.removeObjectListener(this.m_modelObserver);
    }
    
    public HarmonicaModel getModel() {
        return this.m_model;
    }
    
    public boolean hasHarmonicaModelChanged() {
        return this.m_modelHasChanged;
    }
    
    public boolean createNew() {
        if (this.close()) {
            this.m_writer = null;
            this.m_model.resetModel();
            return this.m_modelHasChanged = true;
        }
        return false;
    }
    
    public boolean close() {
        if (this.m_modelHasChanged) {
            final int response = JOptionPane.showConfirmDialog(null, Localizer.get("M_SAVE_MODEL_CHANGES_QUESTION"), Localizer.get("ET_HARMONICA_MODEL_CHANGED"), 1);
            return response != 2 && (response != 0 || this.save());
        }
        return true;
    }
    
    public boolean open() {
        if (this.close()) {
            final JFileChooser fileChooser = new JFileChooser();
            final HarmonicaModelFileFilter filter = new HarmonicaModelFileFilter(true);
            fileChooser.setFileFilter(filter);
            fileChooser.setCurrentDirectory(new File(GlobalPreferences.getModelsFolder()));
            final int ret = fileChooser.showOpenDialog(null);
            if (ret == 0) {
                return this.open(fileChooser.getSelectedFile());
            }
        }
        return false;
    }
    
    public boolean open(final File modelFile) {
        final HarmonicaModelReader reader = HarmonicaModelReader.createReader(this.m_model, modelFile);
        try {
            reader.read(modelFile);
        }
        catch (IOException e) {
            ErrorMessenger.showErrorMessage(Localizer.get("M_FILE_READ_ERROR").replace("%FILE%", modelFile.getName()));
            return false;
        }
        this.m_writer = HarmonicaModelWriter.createWriter(modelFile);
        if (!HarmonicaModelFileFilter.isHarmotab3HarmonicaModelExtension(FileUtilities.getExtension(modelFile))) {
            this.m_writer = null;
        }
        this.m_modelHasChanged = false;
        return true;
    }
    
    public boolean save() {
        if (this.m_writer != null) {
            try {
                this.m_writer.writeFile(this.m_model);
                this.m_modelHasChanged = false;
                return true;
            }
            catch (IOException e) {
                e.printStackTrace();
                ErrorMessenger.showErrorMessage("Error saving file.");
                return false;
            }
        }
        return this.saveAs();
    }
    
    public boolean saveAs() {
        final JFileChooser fileChooser = new JFileChooser();
        final HarmonicaModelFileFilter filter = new HarmonicaModelFileFilter();
        fileChooser.setFileFilter(filter);
        final int ret = fileChooser.showSaveDialog(null);
        if (ret == 0) {
            File file = fileChooser.getSelectedFile();
            if (!HarmonicaModelFileFilter.isHarmotab3HarmonicaModelExtension(FileUtilities.getExtension(file))) {
                file = new File(String.valueOf(FileUtilities.getNameWithoutExtension(file.getAbsolutePath())) + "." + "hmd");
            }
            if (file.exists()) {
                final int res = JOptionPane.showConfirmDialog(null, Localizer.get("M_FILE_ALREADY_EXISTS_QUESTION").replace("%FILE%", file.getPath()), Localizer.get("MENU_SAVE_AS"), 0);
                if (res != 0) {
                    return false;
                }
            }
            try {
                (this.m_writer = HarmonicaModelWriter.createWriter(file)).writeFile(this.m_model);
                this.m_modelHasChanged = false;
                return true;
            }
            catch (IOException e) {
                e.printStackTrace();
                ErrorMessenger.showErrorMessage("Error saving file.");
            }
        }
        return false;
    }
    
    static /* synthetic */ void access$0(final HarmonicaModelController harmonicaModelController, final boolean modelHasChanged) {
        harmonicaModelController.m_modelHasChanged = modelHasChanged;
    }
    
    private class ModelObserver implements HarmoTabObjectListener
    {
        @Override
        public void onObjectChanged(final HarmoTabObjectEvent event) {
            HarmonicaModelController.access$0(HarmonicaModelController.this, true);
        }
    }
}
