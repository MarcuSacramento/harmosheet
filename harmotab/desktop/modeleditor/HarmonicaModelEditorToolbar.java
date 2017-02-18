// 
// Decompiled by Procyon v0.5.30
// 

package harmotab.desktop.modeleditor;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.Component;
import harmotab.core.Localizer;
import javax.swing.Icon;
import harmotab.desktop.ActionIcon;
import javax.swing.JButton;
import harmotab.harmonica.HarmonicaModelController;
import javax.swing.JToolBar;

public class HarmonicaModelEditorToolbar extends JToolBar
{
    private static final long serialVersionUID = 1L;
    private HarmonicaModelController m_controller;
    private JButton m_new;
    private JButton m_open;
    private JButton m_save;
    private JButton m_saveAs;
    
    public HarmonicaModelEditorToolbar(final HarmonicaModelController controller) {
        this.m_controller = null;
        this.m_new = null;
        this.m_open = null;
        this.m_save = null;
        this.m_saveAs = null;
        this.m_controller = controller;
        (this.m_new = new JButton(ActionIcon.getIcon((byte)4))).setToolTipText(Localizer.get("TT_NEW_MODEL"));
        (this.m_open = new JButton(ActionIcon.getIcon((byte)0))).setToolTipText(Localizer.get("TT_OPEN_MODEL"));
        (this.m_save = new JButton(ActionIcon.getIcon((byte)2))).setToolTipText(Localizer.get("TT_SAVE_MODEL"));
        (this.m_saveAs = new JButton(ActionIcon.getIcon((byte)3))).setToolTipText(Localizer.get("TT_SAVE_MODEL_AS"));
        this.add(this.m_new);
        this.add(this.m_open);
        this.add(this.m_save);
        this.add(this.m_saveAs);
        final ButtonsOberver listener = new ButtonsOberver((ButtonsOberver)null);
        this.m_new.addActionListener(listener);
        this.m_open.addActionListener(listener);
        this.m_save.addActionListener(listener);
        this.m_saveAs.addActionListener(listener);
    }
    
    private class ButtonsOberver implements ActionListener
    {
        @Override
        public void actionPerformed(final ActionEvent event) {
            if (event.getSource() == HarmonicaModelEditorToolbar.this.m_new) {
                HarmonicaModelEditorToolbar.this.m_controller.createNew();
            }
            else if (event.getSource() == HarmonicaModelEditorToolbar.this.m_open) {
                HarmonicaModelEditorToolbar.this.m_controller.open();
            }
            else if (event.getSource() == HarmonicaModelEditorToolbar.this.m_save) {
                HarmonicaModelEditorToolbar.this.m_controller.save();
            }
            else if (event.getSource() == HarmonicaModelEditorToolbar.this.m_saveAs) {
                HarmonicaModelEditorToolbar.this.m_controller.saveAs();
            }
        }
    }
}
