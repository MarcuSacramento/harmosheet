// 
// Decompiled by Procyon v0.5.30
// 

package harmotab.desktop.components;

import javax.swing.Icon;
import harmotab.desktop.GuiIcon;
import java.awt.Component;
import javax.swing.JList;
import javax.swing.JLabel;
import javax.swing.ListCellRenderer;
import javax.swing.JComboBox;

public class AlterationChooser extends JComboBox
{
    private static final long serialVersionUID = 1L;
    private JComboBox m_instance;
    
    public AlterationChooser(final byte alteration) {
        (this.m_instance = this).setOpaque(true);
        this.setRenderer(new AlterationLabelRenderer());
        for (int i = 0; i < 3; ++i) {
            this.addItem(new StringBuilder(String.valueOf(i)).toString());
        }
        this.setSelectedAlteration((byte)0);
    }
    
    public byte getSelectedAlteration() {
        switch (this.getSelectedIndex()) {
            case 0: {
                return 0;
            }
            case 1: {
                return 1;
            }
            case 2: {
                return 2;
            }
            default: {
                return 0;
            }
        }
    }
    
    public void setSelectedAlteration(final byte alteration) {
        switch (alteration) {
            case 0: {
                this.setSelectedIndex(0);
                break;
            }
            case 1: {
                this.setSelectedIndex(1);
                break;
            }
            case 2: {
                this.setSelectedIndex(2);
                break;
            }
            default: {
                throw new IllegalArgumentException("Unhandled alteration '#" + alteration + "'");
            }
        }
    }
    
    class AlterationLabelRenderer extends JLabel implements ListCellRenderer
    {
        private static final long serialVersionUID = 1L;
        
        public AlterationLabelRenderer() {
            this.setOpaque(true);
            this.setHorizontalAlignment(0);
            this.setVerticalAlignment(0);
        }
        
        @Override
        public Component getListCellRendererComponent(final JList list, final Object value, final int index, final boolean isSelected, final boolean cellHasFocus) {
            int selectedIndex = index;
            if (selectedIndex == -1) {
                selectedIndex = AlterationChooser.this.m_instance.getSelectedIndex();
            }
            if (selectedIndex == -1) {
                selectedIndex = 0;
            }
            if (isSelected) {
                this.setBackground(list.getSelectionBackground());
                this.setForeground(list.getSelectionForeground());
            }
            else {
                this.setBackground(list.getBackground());
                this.setForeground(list.getForeground());
            }
            switch (selectedIndex) {
                case 0: {
                    this.setIcon(GuiIcon.getIcon((byte)5));
                    break;
                }
                case 1: {
                    this.setIcon(GuiIcon.getIcon((byte)6));
                    break;
                }
                case 2: {
                    this.setIcon(GuiIcon.getIcon((byte)7));
                    break;
                }
            }
            return this;
        }
    }
}
