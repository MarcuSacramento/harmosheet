// 
// Decompiled by Procyon v0.5.30
// 

package harmotab.desktop.actions;

import java.awt.Window;
import harmotab.desktop.modeleditor.HarmonicaModelEditor;
import harmotab.harmonica.HarmonicaModel;
import harmotab.desktop.ActionIcon;
import harmotab.core.Localizer;

public class ShowModelEditorAction extends UserAction
{
    private static final long serialVersionUID = 1L;
    
    public ShowModelEditorAction() {
        super(Localizer.get("MENU_MODEL_EDITOR"), ActionIcon.getIcon((byte)34));
        this.setLittleIcon(ActionIcon.getIcon((byte)35));
    }
    
    @Override
    public void run() {
        new HarmonicaModelEditor(null, new HarmonicaModel()).setVisible(true);
    }
}
