// 
// Decompiled by Procyon v0.5.30
// 

package harmotab.desktop.components;

import harmotab.core.Localizer;
import javax.swing.JComboBox;

public class TabBlowDirectionChooser extends JComboBox
{
    private static final long serialVersionUID = 1L;
    public static final int BLOW_UP = 1;
    public static final int BLOW_DOWN = 2;
    private static String[] m_choices;
    
    static {
        TabBlowDirectionChooser.m_choices = new String[] { Localizer.get("ET_TAB_BLOW_UP"), Localizer.get("ET_TAB_BLOW_DOWN") };
    }
    
    public TabBlowDirectionChooser(final int direction) {
        super(TabBlowDirectionChooser.m_choices);
        this.setSelectedBlowDirection(direction);
    }
    
    public TabBlowDirectionChooser() {
        this(1);
    }
    
    public int getSelectedBlowDirection() {
        return this.getSelectedIndex() + 1;
    }
    
    public void setSelectedBlowDirection(final int direction) {
        this.setSelectedIndex(direction - 1);
    }
}
