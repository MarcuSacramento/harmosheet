// 
// Decompiled by Procyon v0.5.30
// 

package harmotab.desktop.components;

import harmotab.harmonica.HarmonicaModelController;
import harmotab.harmonica.HarmonicaModel;
import rvt.util.gui.FileUtilities;
import harmotab.io.harmonica.HarmonicaModelReader;
import java.io.File;
import harmotab.core.GlobalPreferences;
import java.util.Vector;
import javax.swing.JComboBox;

public class HarmonicaModelChooser extends JComboBox
{
    private static final long serialVersionUID = 1L;
    private static Vector<String> m_modelsName;
    private static Vector<String> m_modelsPath;
    
    static {
        HarmonicaModelChooser.m_modelsName = new Vector<String>();
        HarmonicaModelChooser.m_modelsPath = new Vector<String>();
    }
    
    public HarmonicaModelChooser() {
        super(getModelsList());
        if (this.getModel().getSize() > 1) {
            this.setSelectedIndex(1);
        }
    }
    
    public static Vector<String> getModelsList() {
        final File modelsFolder = new File(GlobalPreferences.getModelsFolder());
        HarmonicaModelChooser.m_modelsName.add("");
        HarmonicaModelChooser.m_modelsPath.add(null);
        try {
            if (modelsFolder.exists() && modelsFolder.isDirectory()) {
                String[] list;
                for (int length = (list = modelsFolder.list()).length, i = 0; i < length; ++i) {
                    final String filename = list[i];
                    if (filename.endsWith(HarmonicaModelReader.HARMOTAB_3_MODEL_FILE_EXTENSION)) {
                        HarmonicaModelChooser.m_modelsName.add(FileUtilities.getNameWithoutExtension(filename));
                        HarmonicaModelChooser.m_modelsPath.add(String.valueOf(modelsFolder.getAbsolutePath()) + File.separatorChar + filename);
                    }
                }
            }
        }
        catch (NullPointerException e) {
            System.err.println("Model folder: " + modelsFolder);
            e.printStackTrace();
        }
        return HarmonicaModelChooser.m_modelsName;
    }
    
    public String getSelectedModelPath() {
        final int index = this.getSelectedIndex();
        if (index == -1) {
            return null;
        }
        return HarmonicaModelChooser.m_modelsPath.get(index);
    }
    
    public HarmonicaModel getSelectedModel() {
        final HarmonicaModelController controller = new HarmonicaModelController(new HarmonicaModel());
        final String modelPath = this.getSelectedModelPath();
        if (modelPath != null) {
            controller.open(new File(modelPath));
        }
        return controller.getModel();
    }
}
