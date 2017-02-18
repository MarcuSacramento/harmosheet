// 
// Decompiled by Procyon v0.5.30
// 

package harmotab.desktop.components;

import harmotab.renderer.awtrenderers.AwtTabularTabRenderer;
import harmotab.renderer.awtrenderers.AwtArrowTabRenderer;
import harmotab.renderer.ElementRenderer;
import harmotab.core.Localizer;
import javax.swing.JComboBox;

public class TabStyleChooser extends JComboBox
{
    private static final long serialVersionUID = 1L;
    public static String[] m_labels;
    
    static {
        TabStyleChooser.m_labels = new String[] { Localizer.get("ET_ARROW_TAB_STYLE"), Localizer.get("ET_TABULAR_TAB_STYLE") };
    }
    
    public TabStyleChooser(final int styleIndex) {
        super(TabStyleChooser.m_labels);
        this.setSelectedIndex(styleIndex);
    }
    
    public static ElementRenderer getRenderer(final int styleIndex) {
        switch (styleIndex) {
            case 0: {
                return new AwtArrowTabRenderer();
            }
            case 1: {
                return new AwtTabularTabRenderer();
            }
            default: {
                return null;
            }
        }
    }
}
