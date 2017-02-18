// 
// Decompiled by Procyon v0.5.30
// 

package harmotab.desktop.actions;

import java.awt.Window;
import harmotab.desktop.modeleditor.HarmonicaModelEditor;
import harmotab.desktop.DesktopController;
import java.io.File;
import harmotab.harmonica.HarmonicaModelController;
import harmotab.harmonica.HarmonicaModel;
import harmotab.desktop.ActionIcon;
import harmotab.core.Localizer;

public class OpenModelAction extends UserAction
{
    private static final long serialVersionUID = 1L;
    private String m_modelFilePath;
    
    public OpenModelAction(final String path) {
        super(Localizer.get("MENU_OPEN_MODEL"), ActionIcon.getIcon((byte)34));
        this.m_modelFilePath = null;
        this.m_modelFilePath = path;
    }
    
    @Override
    public void run() {
        final HarmonicaModel model = new HarmonicaModel();
        final HarmonicaModelController controller = new HarmonicaModelController(model);
        controller.open(new File(this.m_modelFilePath));
        final HarmonicaModelEditor editor = new HarmonicaModelEditor(DesktopController.getInstance().getGuiWindow(), model);
        editor.setVisible(true);
    }
}
